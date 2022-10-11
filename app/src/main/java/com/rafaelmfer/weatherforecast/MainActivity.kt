package com.rafaelmfer.weatherforecast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rafaelmfer.weatherforecast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}