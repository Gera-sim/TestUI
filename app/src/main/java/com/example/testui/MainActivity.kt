package com.example.testui


import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.example.testui.databinding.ActivityMainBinding
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.sql.DataSource


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
        binding.ProgressBar.visibility = View.VISIBLE
        Glide.with(this)
            .load("https://source.unsplash.com/random/800x600$encodedKeyword")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .listener(object : ReqestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.ProgressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.ProgressBar.visibility = View.GONE
                    return false
                }
            }


            )
            .into(binding.ImageView)
        return false
    }

}