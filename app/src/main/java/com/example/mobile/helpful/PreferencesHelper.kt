package com.example.mobile.helpful

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

object PreferencesHelper {
    private lateinit var prefs: SharedPreferences
    private lateinit var editor : Editor

    fun setup(context : Context){
        prefs = context.getSharedPreferences("mobile", MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun getString(key : String, default : String) = prefs.getString(key, default)

    fun putString(key : String, value : String){
        editor.putString(key, value)
        editor.apply()
    }
}