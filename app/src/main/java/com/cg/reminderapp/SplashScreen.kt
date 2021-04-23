package com.cg.reminderapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    private var TIME_OUT:Long = 2500
    lateinit var iv: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        iv= findViewById(R.id.splashScreenLogo)
        val anim= AnimationUtils.loadAnimation(this, R.anim.anim_splashscreen_logo)
        iv.startAnimation(anim)
        loadSplashScreen()
    }
    private fun loadSplashScreen(){
        Handler().postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        },TIME_OUT)
    }
}