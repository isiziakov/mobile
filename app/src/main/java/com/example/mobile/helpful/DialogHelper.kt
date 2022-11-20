package com.example.mobile.helpful

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog


object DialogHelper {
    fun makeDialog(context: Context, title : String, message : String) : AlertDialog.Builder{
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title) // заголовок

        builder.setMessage(message) // сообщение

        builder.setCancelable(true)

        return builder
    }
}