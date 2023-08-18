package com.example.mazegame_v2.views

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalLensFacing
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.mazegame_v2.R
import com.example.mazegame_v2.databinding.ActivityMainBinding
import com.example.mazegame_v2.ml.LiteModelMovenetSingleposeLightningTfliteFloat164
import com.example.mazegame_v2.poseDetection.PoseDetection
import com.example.mazegame_v2.viewModels.MainViewModel
import com.example.mazegame_v2.utils.CameraUtils
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    lateinit var maze: IntArray

    private lateinit var model: LiteModelMovenetSingleposeLightningTfliteFloat164
    val imageProcessor = ImageProcessor.Builder().add(ResizeOp(192, 192, ResizeOp.ResizeMethod.BILINEAR)).build()
    val matrix = Matrix()

    @OptIn(ExperimentalLensFacing::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Get maze flattened list information from ChooseModeActivity
        maze = intent.getIntArrayExtra("maze")!!
        // Change maze flattened list to 2D array version for playing game
        mainViewModel.setUp(maze)
        println(mainViewModel.gameModel.printMaze())


        matrix.setScale(-1f, 1f, mainBinding.previewView.width / 2f, mainBinding.previewView.height / 2f)
        mainBinding.previewView.transformMatrixToGlobal(matrix)

        // Using Camera code part
        model = LiteModelMovenetSingleposeLightningTfliteFloat164.newInstance(this)
        CameraUtils.open_camera(this@MainActivity, mainBinding.previewView, model)

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        var bitmap: Bitmap
        var poseDetection : PoseDetection = PoseDetection()

//        cameraProviderFuture.addListener({
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//
//            val preview = Preview.Builder().build().also {
//                it.setSurfaceProvider(mainBinding.previewView.surfaceProvider)
//            }
//
//            // val imageAnalysis = cameraViewModel.setImageAnalysis(this@MainActivity, model)
//            val imageAnalysis = ImageAnalysis.Builder()
//                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build()
//
//            imageAnalysis.setAnalyzer(mainExecutor, ImageAnalysis.Analyzer { image ->
//                bitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
//                image.use { bitmap.copyPixelsFromBuffer(image.planes[0].buffer) }
//                image.close()
//                poseDetection.poseDetection(bitmap, model)
//            })
//
//            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_EXTERNAL).build()
//
//            cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview, imageAnalysis)
//        }, ContextCompat.getMainExecutor(this))
    }

}

