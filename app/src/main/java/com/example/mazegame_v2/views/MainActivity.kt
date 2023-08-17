package com.example.mazegame_v2.views

import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.view.TextureView
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mazegame_v2.R
import com.example.mazegame_v2.databinding.ActivityMainBinding
import com.example.mazegame_v2.viewModels.MainViewModel
import kotlinx.coroutines.*
import com.example.mazegame_v2.utils.CameraUtils

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var cameraManager: CameraManager
    lateinit var maze: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Get maze flattened list information from ChooseModeActivity
        maze = intent.getIntArrayExtra("maze")!!
        // Change maze flattened list to 2D array version for playing game
        mainViewModel.setUp(maze)

        CameraUtils.get_permission(this@MainActivity)
        mainBinding.textureView.surfaceTextureListener = object: TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                // Background thread for camera and pose detecting
                lifecycleScope.launch(Dispatchers.Default) {
                    val backgroundThread = HandlerThread("CameraBackground")
                    backgroundThread.start()
                    background
                    // Access the textureView on the main thread using withContext
                    withContext(Dispatchers.Main) {
                        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
                    }
                }

            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                TODO("Not yet implemented")
            }
        }


    }

}