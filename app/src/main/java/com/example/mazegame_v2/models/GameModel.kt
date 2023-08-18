package com.example.mazegame_v2.models

import kotlin.math.sqrt

class GameModel() {
    lateinit var numberMaze: Array<IntArray>

    fun make2DArray(flattenedList: IntArray) {
        val size = sqrt(flattenedList.size.toDouble()).toInt()
        numberMaze = Array(size) { IntArray(size) }

        for (i in 0 until size) {
            for (j in 0 until size) {
                numberMaze[i][j] = flattenedList[(size * i) + j]
            }
        }
    }

    fun printMaze() {
        for (i in 0 until numberMaze.size) {
            for (j in 0 until numberMaze[i].size) {
                print(numberMaze[i][j])
                print(" ")
            }
            println()
        }
    }
}