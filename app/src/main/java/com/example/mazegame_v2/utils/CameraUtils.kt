package com.example.mazegame_v2.utils

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.view.Surface
import android.view.TextureView
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalLensFacing
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.mazegame_v2.ml.LiteModelMovenetSingleposeLightningTfliteFloat164
import com.example.mazegame_v2.models.PoseDetectModel
import com.example.mazegame_v2.poseDetection.PoseDetection
import com.example.mazegame_v2.views.MainActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

object CameraUtils {

    fun get_permission(mainActivity: MainActivity) {
        if (ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(mainActivity, arrayOf(android.Manifest.permission.CAMERA), 101)
        }
    }

    @OptIn(ExperimentalLensFacing::class)
    fun open_camera(mainActivity: MainActivity, previewView: PreviewView, model: LiteModelMovenetSingleposeLightningTfliteFloat164) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(mainActivity)
        var bitmap: Bitmap
        var poseDetection : PoseDetection = PoseDetection()

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            // val imageAnalysis = cameraViewModel.setImageAnalysis(this@MainActivity, model)
            val imageAnalysis = ImageAnalysis.Builder()
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(mainActivity.mainExecutor, ImageAnalysis.Analyzer { image ->
                bitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
                image.use { bitmap.copyPixelsFromBuffer(image.planes[0].buffer) }
                image.close()
                poseDetection.poseDetection(bitmap, model)
            })

            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_EXTERNAL).build()

            cameraProvider.bindToLifecycle(mainActivity, cameraSelector, preview, imageAnalysis)
        }, ContextCompat.getMainExecutor(mainActivity))
    }


}