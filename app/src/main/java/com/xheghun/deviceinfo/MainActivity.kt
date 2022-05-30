package com.xheghun.deviceinfo

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import com.xheghun.deviceinfo.application.DeviceInfoApplication
import com.xheghun.deviceinfo.viewmodel.DeviceInfoViewModelFactory
import com.xheghun.deviceinfo.viewmodel.DeviceInfoViewModel
import androidx.activity.viewModels
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.xheghun.deviceinfo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var cam: ActivityResultLauncher<Intent>

    private val mDeviceInfoVM: DeviceInfoViewModel by viewModels {
        DeviceInfoViewModelFactory((application as DeviceInfoApplication).repository)

    }

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)

        mBinding.scanButton.setOnClickListener { checkNecessaryPermission() }
    }

    private fun getDeviceInfo() {
        mBinding.scanButton.visibility = View.GONE
        mBinding.infoView.visibility = View.VISIBLE

        mBinding.deviceText.text = mDeviceInfoVM.deviceDetails()
        mBinding.cameraDetailsText.text = mDeviceInfoVM.cameraDetails()
        mBinding.bluetoothText.text= mDeviceInfoVM.bluetoothDetails()
        mBinding.wifiText.text = mDeviceInfoVM.wifiDetails()
    }

    private fun checkNecessaryPermission() {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_WIFI_STATE,
        ).withListener(object: MultiplePermissionsListener {

            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    if(report.areAllPermissionsGranted()) {
                        getDeviceInfo()
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: MutableList<PermissionRequest>?,
                p1: PermissionToken?
            ) {
                showRationaleDialogForPermission()
            }
        }).check()
    }

    private fun showRationaleDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage("Please allow necessary permissions to make app function properly")
            .setPositiveButton("OK") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }.setNegativeButton("CANCEL") { dialog, _ -> dialog.dismiss() }.show()

    }
}