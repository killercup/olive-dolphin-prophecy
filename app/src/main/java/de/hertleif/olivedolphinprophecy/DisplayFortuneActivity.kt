package de.hertleif.olivedolphinprophecy

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView

class DisplayFortuneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_fortune)

        val yes = Math.random() < 0.5
        findViewById<ImageView>(R.id.yes).visibility = visible(yes)
        findViewById<ImageView>(R.id.no).visibility = visible(!yes)

        if (yes) {
            findViewById<ConstraintLayout>(R.id.fortunas_telling).setBackgroundColor(Color.WHITE)
        } else {
            findViewById<ConstraintLayout>(R.id.fortunas_telling).setBackgroundColor(Color.BLACK)
        }
    }

    fun visible(yes: Boolean): Int {
        return if (yes) {
            ImageView.VISIBLE
        } else {
            ImageView.INVISIBLE
        }
    }
}

