package com.example.mazegame_v2.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mazegame_v2.R
import com.example.mazegame_v2.databinding.ActivityChooseModeBinding
import com.example.mazegame_v2.viewModels.ModeViewModel

class ChooseModeActivity : AppCompatActivity() {

    private lateinit var modeViewModel: ModeViewModel
    private lateinit var modeBinding: ActivityChooseModeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modeViewModel = ViewModelProvider(this)[ModeViewModel::class.java]
        modeBinding = DataBindingUtil.setContentView(this, R.layout.activity_choose_mode)

        // Set click listeners for buttons
        modeBinding.easyButton.setOnClickListener { onButtonClick("Easy") }
        modeBinding.mediumButton.setOnClickListener { onButtonClick("Medium") }
        modeBinding.hardButton.setOnClickListener { onButtonClick("Hard") }
    }

    // Click listener function
    private fun onButtonClick(mode: String) {
        modeViewModel.generateMaze(mode)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("maze", modeViewModel.maze.maze.toIntArray())
        startActivity(intent)
    }
}

