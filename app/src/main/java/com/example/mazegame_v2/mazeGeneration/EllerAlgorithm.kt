package com.example.mazegame_v2.mazeGeneration

import java.util.Random

class EllerAlgorithm (private val size: Int) {
    private val rand = Random()
    private val sets = mutableListOf<Int>()
    private val tmps = mutableListOf<Int>()

    init {
        for (i in 0 until size*size){
            sets.add(15)
        }
    }

    private fun findSet(cell1: Int, cell2: Int): Boolean {
        return ((sets[cell1] and 1) == 0) && (((sets[cell2] shr 2) and 1) == 0)
    }

    private fun UnionSetsHorizontal(cell1: Int, cell2: Int) {
        sets[cell1] -= 1
        sets[cell2] -= 4
    }

    private fun UnionSetsVertical(cell1: Int, cell2: Int) {
        sets[cell1] -= 2
        sets[cell2] -= 8
    }

    private fun UnionVerticals() {
        var flag = 0
        for (idx in 0 until tmps.size) {
            if (flag == 0) {
                if (idx == tmps.size -1) {
                    UnionSetsVertical(tmps[idx], tmps[idx] + size)
                } else {
                    if (rand.nextBoolean()) {
                        UnionSetsVertical(tmps[idx], tmps[idx] + size)
                        flag = 1
                    }
                }
            } else {
                if (rand.nextBoolean()) {
                    UnionSetsVertical(tmps[idx], tmps[idx] + size)
                }
            }
        }
        tmps.clear()
    }

    fun generateMaze(): List<Int> {
        for (row in 0 until size-1) {
            for (col in 0 until size) {
                if (col < size-1) {
                    if (rand.nextBoolean()) {
                        UnionSetsHorizontal(row * size + col, row * size + (col + 1))
                        if (tmps.size == 0) tmps.add(row * size + col)
                        tmps.add(row * size + (col + 1))
                    } else {
                        if (tmps.size == 0) {
                            UnionSetsVertical(row * size + col, (row + 1) * size + col)
                        } else UnionVerticals()
                    }
                } else {
                    if (tmps.size == 0) {
                        UnionSetsVertical(row * size + col, (row + 1) * size + col)
                    } else UnionVerticals()
                }
            }
        }

        for (col in 0 until size-1) {
            UnionSetsHorizontal((size-1) * size + col, (size-1) * size + (col + 1))
        }
        return sets
    }
}