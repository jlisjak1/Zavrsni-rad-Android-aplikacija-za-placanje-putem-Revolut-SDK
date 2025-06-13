package com.example.zavrsnirad.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.zavrsnirad.R
import com.example.zavrsnirad.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeBinding
    lateinit var animTop: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflacija layouta koristenjem ViewBinginda
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ucitavanje animacije za tekst dobrodoslice
        animTop = AnimationUtils.loadAnimation(this, R.anim.fromtop)
        // Postavljanje animacije na tekst dobrodoslice
        binding.tvWelcome.animation = animTop
        binding.tvDescription.animation = animTop

        // Postavljanje slusaca klika za gumb "Continue"
        binding.btnContinue.setOnClickListener {
            // Prelazak u MainActivity
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
        }
    }
}
