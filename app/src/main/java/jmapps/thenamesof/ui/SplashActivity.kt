package jmapps.thenamesof.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import jmapps.thenamesof.MainActivity

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toMainActivity = Intent(this, MainActivity::class.java)
        startActivity(toMainActivity)
        finish()
    }
}