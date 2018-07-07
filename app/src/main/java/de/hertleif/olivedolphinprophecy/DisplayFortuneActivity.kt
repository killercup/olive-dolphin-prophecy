package de.hertleif.olivedolphinprophecy

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity

class DisplayFortuneActivity : AppCompatActivity() {
    private val TAG = "OlDolPro.Fortune"
    private var fortune = Outcome.Yes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_fortune)
        val container = findViewById<ConstraintLayout>(R.id.container)

        val yes = Math.random() < 0.5

        container.setBackgroundColor(if (yes) {
            Color.WHITE
        } else {
            Color.BLACK
        })
        this.fortune = if (yes) {
            Outcome.Yes
        } else {
            Outcome.No
        }

        val textCanvas = findViewById<FortunateView>(R.id.fortunas_telling)
        textCanvas.setStyle(fortune)
    }
}

