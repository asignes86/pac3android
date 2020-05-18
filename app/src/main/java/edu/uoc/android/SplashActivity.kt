package edu.uoc.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        goToMain()
    }

    override fun onResume() {
        super.onResume()
        FirebaseAnalytics.getInstance(applicationContext)
            .setCurrentScreen(this, this::class.java.simpleName, null)
    }

    private fun goToMain() {
        Thread.sleep(1000)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
