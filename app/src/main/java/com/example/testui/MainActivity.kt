package com.example.testui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
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
private var useKeyword: Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.EditText.setText(getString(R.string.carrot))

//        binding.TextView.setTextColor(Color.RED)
//        binding.TextView.setLines(1)
//        избавляемся от повторов
        with(binding.TextView) {
            setTextColor(Color.RED)
            setLines(1)
        }

        with(binding.ImageView) {
            setBackgroundResource(R.drawable.ic_launcher_background)
            layoutParams.width= resources.getDimensionPixelSize(R.dimen.image_width)
            layoutParams.height= resources.getDimensionPixelSize(R.dimen.image_height)
            requestLayout()
        }
//          requestLayout - для того, чтобы атрибуты применились

        binding.Button.setOnClickListener{ onGetRandomImagePressed()}
        binding.Button.setOnLongClickListener { showToastWithRandomNumber() }

        val currentTextValue: String = binding.TextView.text.toString()
        Log.d("TESTLOG", "Current text value: $currentTextValue")

        val textValueWithRandomNumber = "$currentTextValue ${Random.nextInt(100)}"
        binding.TextView.text = textValueWithRandomNumber

        val updatedTextValue = binding.TextView.text.toString()
        Log.d("TESTLOG", "Updated text value: $updatedTextValue")

        binding.EditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                return@setOnEditorActionListener onGetRandomImagePressed()
            }
            return@setOnEditorActionListener false
        }


        //срабатывает только когда пользователь нажимает, но не реагирует на дефолтное значение
        binding.CheckBox.setOnClickListener{
            this.useKeyword=binding.CheckBox.isChecked
            updateUI()
        }
        binding.CheckBox.setOnCheckedChangeListener { _, isChecked ->

        }
        updateUI()
         }

    private fun onGetRandomImagePressed():Boolean {
        val keyword = binding.EditText.text.toString()
        if (useKeyword && keyword.isBlank()){
            binding.EditText.error = "Keyword is empty"
            return true
        }
        val encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8.name())
        Glide.with(this)
            .load("https://source.unsplash.com/random/800x600$encodedKeyword")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.kandinsky_almond)
            .into(binding.ImageView)
    }

    private fun showToastWithRandomNumber():Boolean {
        val number = Random.nextInt(100)
        val message = getString(R.string.random_number, number)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        return true //скрывать ли клавиатуру
    }
    private fun updateUI()= with(binding){
        CheckBox.isChecked = useKeyword
        if (useKeyword) {
            EditText.visibility = View.VISIBLE
        }else{
            EditText.visibility = View.GONE
        }

    }


    }