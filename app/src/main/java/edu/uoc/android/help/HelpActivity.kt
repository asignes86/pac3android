package edu.uoc.android.help

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import edu.uoc.android.R

class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        memoryLeak()
    }

    fun memoryLeak() {
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }
}
