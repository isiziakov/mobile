package com.example.mobile

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.mobile.DTO.User
import com.example.mobile.helpful.PreferencesHelper
import com.example.mobile.web.WebApi


class MainActivity : AppCompatActivity() {
    private var login : EditText? = null
    private var password : EditText? = null
    private var error : TextView? = null
    private var enter : Button? = null
    private var enterGuest : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PreferencesHelper.setup(this)

        checkEnter()

        initializeComponents()
    }

    private fun initializeComponents(){
        login = findViewById(R.id.login)
        password = findViewById(R.id.password)
        enter = findViewById(R.id.enterMain)
        enter?.setOnClickListener {
            enter()
        }
        enterGuest = findViewById(R.id.enterAsGuest)
        enterGuest?.setOnClickListener {
            TODO()
        }
        error = findViewById(R.id.enterError)
    }

    private fun checkEnter(){
        val login = PreferencesHelper.getString("login", "")
        val password = PreferencesHelper.getString("password", "")

        if (login != null && password != null){
            if (login != "" && password != "") {
                val user = User(login, password)
                WebApi.checkUser(user) { result ->
                    if (result) {
                        toMenu()
                    }
                    else{
                        PreferencesHelper.putString("login", "")
                        PreferencesHelper.putString("password", "")
                    }
                }
            }
        }
    }

    private fun enter(){
        if (login != null && password != null){
            val user = User(login!!.text.toString(), password!!.text.toString())
            WebApi.checkUser(user) { result ->
                if (result){
                    PreferencesHelper.putString("login", user.login)
                    PreferencesHelper.putString("password", user.password)
                    toMenu()
                }
                else{
                    runOnUiThread {
                        error?.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun toMenu(){
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }
}