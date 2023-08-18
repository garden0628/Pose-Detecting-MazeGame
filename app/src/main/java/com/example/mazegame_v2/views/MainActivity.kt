package com.example.mazegame_v2.views

import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalLensFacing
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mazegame_v2.R
import com.example.mazegame_v2.databinding.ActivityMainBinding
import com.example.mazegame_v2.ml.LiteModelMovenetSingleposeLightningTfliteFloat164
import com.example.mazegame_v2.viewModels.MainViewModel
import com.example.mazegame_v2.utils.CameraUtils

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    lateinit var maze: IntArray

    private lateinit var model: LiteModelMovenetSingleposeLightningTfliteFloat164
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
    }

}

