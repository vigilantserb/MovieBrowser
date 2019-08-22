package com.stameni.com.whatshouldiwatch.screens.settings

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.stameni.com.whatshouldiwatch.common.Constants

class RequestPermissionUseCase(
    private val activity: Activity?
) {
    private val _permissionsApproved = MutableLiveData<Int>()
    val permissionApproved: LiveData<Int>
        get() = _permissionsApproved

    fun requestPermissions(
        list: ArrayList<String>,
        deniedText: String
    ) {
        Dexter.withActivity(activity)
            .withPermissions(list)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (!report!!.areAllPermissionsGranted()) {
                        Toast.makeText(
                            activity!!.applicationContext,
                            deniedText,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        _permissionsApproved.value = Constants.SUCCESS
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

            }).check()
    }
}