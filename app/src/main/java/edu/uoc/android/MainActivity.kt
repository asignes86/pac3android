package edu.uoc.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.uoc.android.Museus.MuseusActivity
import edu.uoc.android.quizz.QuizzActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bt_museums.setOnClickListener {
            intent = Intent(applicationContext, MuseusActivity::class.java)
            startActivity(intent)
        }

        bt_maps.setOnClickListener {
            intent = Intent(applicationContext, MapsActivity::class.java)
            startActivity(intent)
        }

        bt_quizzes.setOnClickListener {
            intent = Intent(applicationContext, QuizzActivity::class.java)
            startActivity(intent)
        }
    }
}
