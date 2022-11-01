package com.example.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mobile.helpful.PreferencesHelper

class MenuActivity : AppCompatActivity() {
    private var articles : Button? = null
    private var themes : Button? = null
    private var exit : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        initializeComponents()
    }

    private fun initializeComponents(){
        articles = findViewById(R.id.editArticles)
        articles?.setOnClickListener {
            val intent = Intent(this, ArticlesActivity::class.java)
            startActivity(intent)
        }
        themes = findViewById(R.id.editThemes)
        themes?.setOnClickListener {
            val intent = Intent(this, ThemesActivity::class.java)
            startActivity(intent)
        }
        exit = findViewById(R.id.editExit)
        exit?.setOnClickListener {
            PreferencesHelper.putString("login", "")
            PreferencesHelper.putString("password", "")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}