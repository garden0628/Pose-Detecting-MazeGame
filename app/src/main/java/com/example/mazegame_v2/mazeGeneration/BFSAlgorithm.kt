package com.example.mazegame_v2.mazeGeneration

import java.util.LinkedList

class BFSAlgorithm (private val maze: List<Int>, private val size: Int) {
    private val startingPoint = 0
    private val goalPoint = maze.size - 1
    private val queue = LinkedList<Int>()
    private val visit = BooleanArray(maze.size)

    fun checkIt(): Boolean {
        queue.add(startingPoint)
        while (queue.size > 0) {
            val cur = queue.removeFirst()
            if (cur == goalPoint) return true

            // Can move right?
            if ((maze[cur] and 1) == 0 && ((cur + 1) % size) != 0 && !visit[cur + 1]){
                queue.add(cur + 1)
                visit[cur + 1] = true
            }

            // Can move down?
            if (((maze[cur] shr 1) and 1) == 0 && (cur + size < size * size) && !visit[cur + size]) {
                queue.add(cur + size)
                visit[cur + size] = true
            }

            // Can move left?
            if (((maze[cur] shr 2) and 1) == 0 && ((cur - 1) % size) >= 0 && !visit[cur - 1]) {
                queue.add(cur - 1)
                visit[cur - 1] = true
            }

            // Can move up?
            if (((maze[cur] shr 3) and 1) == 0 && (cur - size >= 0) && !visit[cur - size]){
                queue.add(cur - size)
                visit[cur - size] = true
            }
        }
        return false
    }
}