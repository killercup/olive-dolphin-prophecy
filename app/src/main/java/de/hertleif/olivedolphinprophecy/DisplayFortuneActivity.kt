package de.hertleif.olivedolphinprophecy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class DisplayFortuneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_fortune)

        val message = if (Math.random() < 0.5) {
            "Yes"
        } else {
            "No"
        }

        val textView = findViewById<TextView>(R.id.fortunas_telling).apply {
            text = message
        }
    }
}

