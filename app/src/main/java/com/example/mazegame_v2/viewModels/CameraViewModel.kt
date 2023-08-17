package com.example.mazegame_v2.viewModels

import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.os.HandlerThread
import android.view.TextureView
import androidx.lifecycle.ViewModel
import com.example.mazegame_v2.utils.CameraUtils

class CameraViewModel : ViewModel() {
    fun cameraView(textureView: TextureView, cameraManager: CameraManager) {
        val handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        // Using matrix for reverse left and right in camera textureView
        val matrix = Matrix()

        textureView.surfaceTextureListener = object: TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, width: Int, height: Int) {
                // Background thread for camera and pose detecting
                CameraUtils.open_camera(cameraManager, handler, textureView)
                matrix.setScale(-1f, 1f, width / 2f, height / 2f)
                textureView.setTransform(matrix)
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
            }
        }
    }

}