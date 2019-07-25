package io.connected.gameoflife

import io.connected.gameoflife.GameOfLife.Companion.ALIVE
import io.connected.gameoflife.GameOfLife.Companion.DEAD
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GameOfLifeTest {

    lateinit var sut: GameOfLife

    private val rows = 5
    private val columns = 5

    @Before
    fun setUp(){
        sut = GameOfLife(rows, columns)
    }

    @Test
    fun `isCellAlive - is true when cell value is ALIVE`() {
        sut.board[0][0] = ALIVE

        assertTrue(sut.isCellAlive(0, 0))
    }

    @Test
    fun `isCellAlive - is false when cell value is DEAD`(){
        sut.board[0][0] = DEAD

        assertFalse(sut.isCellAlive(0,0))
    }

    @Test
    fun `countNeighbours - has one neighbour - returns 1`(){
        sut.board[0][1] = ALIVE
        val count = sut.countNeighbours(0,0)

        assertEquals(1, count)
    }

    @Test
    fun `countNeighbours - has four neighbours - returns 4`(){
        sut.board[0][0] = ALIVE
        sut.board[0][1] = ALIVE
        sut.board[0][2] = ALIVE
        sut.board[1][0] = ALIVE

        sut.board[1][1] = ALIVE // Tested
        val count = sut.countNeighbours(1,1)

        assertEquals(4, count)
    }

    @Test
    fun `countNeighbours - does not count itself `(){
        sut.board[0][0] = ALIVE
        val count = sut.countNeighbours(0,0)

        assertEquals(0, count)
    }
    @Test
    fun `step - underpopulated has one alive neighbour -- dies`(){
        sut.board[0][0] = ALIVE
        sut.board[1][1] = ALIVE
        sut.step()

        assertFalse(sut.isCellAlive(0,0))
        assertFalse(sut.isCellAlive(1,1))
    }
    @Test
    fun `step - overcrowded has 4 neighbors - dies`(){
        // Neighbors
        sut.board[0][0] = ALIVE
        sut.board[0][1] = ALIVE
        sut.board[0][2] = ALIVE
        sut.board[1][0] = ALIVE

        sut.board[1][1] = ALIVE // Tested
        sut.step()
        assertFalse(sut.isCellAlive(1,1))
    }

    @Test
    fun `step - dead cell has exactly 3 neighbors - is born`(){
        // Neighbors
        sut.board[0][0] = ALIVE
        sut.board[0][1] = ALIVE
        sut.board[0][2] = ALIVE

        sut.board[1][1] = DEAD

        sut.step()
        assertTrue(sut.isCellAlive(1,1))
    }

    @Test
    fun `step - live cell has exactly 2 neighbors - stays alive`() {
        // Neighbors
        sut.board[0][0] = ALIVE
        sut.board[0][1] = ALIVE

        sut.board[1][1] = ALIVE

        sut.step()
        assertTrue(sut.isCellAlive(1,1))
    }

    @Test
    fun `step - alive cell has exactly 3 neighbors - stays alive`(){
        // Neighbors
        sut.board[0][0] = ALIVE
        sut.board[0][1] = ALIVE
        sut.board[0][2] = ALIVE

        sut.board[1][1] = ALIVE

        sut.step()
        assertTrue(sut.isCellAlive(1,1))
    }
}