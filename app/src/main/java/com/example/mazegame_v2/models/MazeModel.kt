package com.example.mazegame_v2.models

import com.example.mazegame_v2.mazeGeneration.BFSAlgorithm
import com.example.mazegame_v2.mazeGeneration.EllerAlgorithm

class MazeModel() {
    lateinit var maze: List<Int>

    fun makeMaze(mode: String) {
        val size = chooseMazeSize(mode)

        val algorithm = EllerAlgorithm(size)
        maze = algorithm.generateMaze()

        val checkingAlgorithm = BFSAlgorithm(maze, size)
        if (!checkingAlgorithm.checkIt()) makeMaze(mode)
    }

    private fun chooseMazeSize(mode: String):Int {
        if (mode == "Easy") return 7
        else if (mode == "Medium") return 10
        else return 12
    }
}