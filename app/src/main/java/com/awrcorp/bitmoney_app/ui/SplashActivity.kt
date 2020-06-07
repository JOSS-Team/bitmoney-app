package com.awrcorp.bitmoney_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.awrcorp.bitmoney_app.ui.auth.AuthActivity
import com.awrcorp.bitmoney_app.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this@SplashActivity, MainActivity::class.java))

//        startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
        finish()
    }
}
