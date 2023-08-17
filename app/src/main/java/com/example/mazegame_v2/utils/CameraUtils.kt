package com.example.mazegame_v2.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import androidx.core.app.ActivityCompat.getSystemService
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.example.mazegame_v2.views.MainActivity

object CameraUtils {
    fun get_permission(mainActivity: MainActivity) {
        if (ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mainActivity, arrayOf(android.Manifest.permission.CAMERA), 101)
        }
    }

    @SuppressLint
    fun open_camera(cameraManager: CameraManager) {
        cameraManager.openCamera(cameraManager.cameraIdList[0], object: CameraDevice.StateCallback(){
            override fun onOpened(p0: CameraDevice) {
                TODO("Not yet implemented")
            }

            override fun onDisconnected(p0: CameraDevice) {
                TODO("Not yet implemented")
            }

            override fun onError(p0: CameraDevice, p1: Int) {
                TODO("Not yet implemented")
            }
        })
    }
}