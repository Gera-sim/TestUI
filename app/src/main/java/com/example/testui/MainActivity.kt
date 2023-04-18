package com.example.testui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RadioButton
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.testui.databinding.ActivityMainBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    //переменная на основе названия текущей активити
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Button.setOnClickListener { onGetRandomImagePressed() }

        binding.RadioGroup.setOnCheckedChangeListener { _, checkedId ->
            onGetRandomImagePressed()
        }

    }

    private fun onGetRandomImagePressed(): Boolean {
        val checkedId = binding.RadioGroup.checkedRadioButtonId
        val keyword = binding.RadioGroup.findViewById<RadioButton>(checkedId).text.toString()
        val encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        Glide.with(this)
            .load("https://source.unsplash.com/random/800x600$encodedKeyword")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.kandinsky_almond)
            .into(binding.ImageView)
        return false
    }

}