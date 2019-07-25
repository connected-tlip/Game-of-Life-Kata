package io.connected.gameoflife

class GameOfLife(val rows: Int, val cols: Int) {

    companion object {
        const val DEAD = 0
        const val ALIVE = 1

    }

    var board = Array(rows) { Array(cols) { DEAD } }

    fun isCellAlive(row: Int, col: Int): Boolean {
        if (row !in 0 until board.size) return false
        if (col !in 0 until board[row].size) return false

        return board[row][col] == ALIVE
    }

    fun countNeighbours(row: Int, col: Int): Int {
        var count = 0
        for (r in row - 1..row + 1) {
            for (c in col - 1..col + 1) {
                if (r == row && c == col)
                    continue
                count += if (isCellAlive(r, c)) 1 else 0
            }
        }
        return count
    }

    fun step() {
        val newBoard = Array(rows) { Array(cols) { DEAD } }
        for (r in 0 until board.size) {
            for (c in 0 until board[r].size) {
                val neighbors = countNeighbours(r, c)

                newBoard[r][c] =
                        when {
                            neighbors < 2 || neighbors > 3 -> DEAD
                            neighbors == 3 -> ALIVE
                            else -> board[r][c]
                        }
            }
        }
        board = newBoard
    }
}