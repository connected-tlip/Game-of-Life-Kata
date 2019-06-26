package io.connected.gameoflife

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ROWS = 48
        const val COLS = 27
    }

    private val game = GameOfLife(ROWS, COLS).apply {
        for (i in 0 until ROWS) {
            for (j in 0 until COLS) {
                board[i][j] = if (Math.random() < 0.5) 0 else 1
            }
        }
    }
    private val views = Array(ROWS) { Array(COLS) { null as View? } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            game.step()
            update()
        }

        populateLayout()
        update()
    }

    fun populateLayout() {
        main.weightSum = ROWS.toFloat()
        for (i in 0 until ROWS) {
            main.addView(LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1f
                )
                weightSum = COLS.toFloat()

                for (j in 0 until COLS) {
                    views[i][j] = View(this@MainActivity).apply {
                        layoutParams = LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                1f
                        )
                        addView(this)
                    }
                }
            })
        }
    }

    private fun update() {
        for (i in 0 until ROWS) {
            for (j in 0 until COLS) {
                val color =
                        if (game.isCellAlive(i, j))
                            android.R.color.black
                        else
                            android.R.color.white
                views[i][j]?.setBackgroundColor(resources.getColor(color))
            }
        }
    }

}
