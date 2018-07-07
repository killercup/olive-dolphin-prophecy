package de.hertleif.olivedolphinprophecy

import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

class DisplayFortuneActivity : AppCompatActivity() {
    private val TAG = "OlDolPro.FortuneAct"
    private var fortune = Outcome.Yes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_fortune)
        val container = findViewById<ConstraintLayout>(R.id.container)

        if (Math.random() < 0.5) {
            fortune = Outcome.Yes
            container.setBackgroundColor(Color.WHITE)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        } else {
            fortune = Outcome.No
            container.setBackgroundColor(Color.BLACK)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
            window.statusBarColor = Color.BLACK
        }

        val textCanvas = findViewById<FortunateView>(R.id.fortunas_telling)
        textCanvas.setStyle(fortune)
        textCanvas.setOnTouchCallback {
            Log.d(TAG, "tapped on canvas, closing activity")
            finish()
        }
    }


}

