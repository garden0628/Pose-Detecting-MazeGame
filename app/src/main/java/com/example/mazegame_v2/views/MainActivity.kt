package com.example.mazegame_v2.views

import android.content.Context
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.TextureView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mazegame_v2.R
import com.example.mazegame_v2.databinding.ActivityMainBinding
import com.example.mazegame_v2.viewModels.MainViewModel
import kotlinx.coroutines.*
import com.example.mazegame_v2.utils.CameraUtils
import com.example.mazegame_v2.viewModels.CameraViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var cameraManager: CameraManager
    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler
    private lateinit var matrix: Matrix
    lateinit var maze: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        cameraViewModel = ViewModelProvider(this)[CameraViewModel::class.java]

        // Get maze flattened list information from ChooseModeActivity
        maze = intent.getIntArrayExtra("maze")!!
        // Change maze flattened list to 2D array version for playing game
        mainViewModel.setUp(maze)
        println(mainViewModel.gameModel.numberMaze)

        CameraUtils.get_permission(this@MainActivity)
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraViewModel.cameraView(mainBinding.textureView, cameraManager)



//        lifecycleScope.launch(Dispatchers.Default) {
//            // Access the textureView on the main thread using withContext
//            withContext(Dispatchers.Main) {
//                cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
//            }
//        }
    }

}