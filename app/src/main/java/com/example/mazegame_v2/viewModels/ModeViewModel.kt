package com.example.mazegame_v2.viewModels

import androidx.lifecycle.ViewModel
import com.example.mazegame_v2.models.MazeModel

class ModeViewModel : ViewModel() {
    val maze : MazeModel = MazeModel()

    fun generateMaze(mode: String) {
        maze.makeMaze(mode)
        println(maze.maze)
    }
}