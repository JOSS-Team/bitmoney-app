package com.awrcorp.bitmoney_app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.ui.auth.AuthActivity
import com.awrcorp.bitmoney_app.ui.main.MainActivity
import com.awrcorp.bitmoney_app.utils.Anicantik

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            val token = Anicantik.getInstance(this).getId()
            if (token == 0) {
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }, 800)
    }
}
