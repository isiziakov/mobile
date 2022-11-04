package com.example.mobile.web

import android.annotation.SuppressLint
import android.util.Log
import com.example.mobile.DTO.Article
import com.example.mobile.DTO.User
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


object WebApi {
    private val client = OkHttpClient()
    private val gson = Gson()
    private const val baseUrl = "http://10.0.2.2:5296/api/"
    private val JSON = "application/json; charset=utf-8".toMediaType()

    private fun createGet(url : String) = Request.Builder()
        .url(baseUrl + url)
        .build()

    private fun createPost(url : String, json : String) = Request.Builder()
        .url(baseUrl + url)
        .post(json.toRequestBody(JSON))
        .build()

    private fun createPut(url : String, json : String) = Request.Builder()
        .url(baseUrl + url)
        .put(json.toRequestBody(JSON))
        .build()

    private fun createDelete(url : String, json : String) = Request.Builder()
        .url(baseUrl + url)
        .delete(json.toRequestBody(JSON))
        .build()

    @SuppressLint("SuspiciousIndentation")
    fun checkUser(user : User, result : (result: Boolean) -> Unit){
        val request = createPost("user/" + user.login, gson.toJson(user).toString())
            client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                result(false)
            }

            override fun onResponse(call: Call, response: Response) {
               result(response.code == 200)
            }
        })
    }

    fun getAllArticles(name : String, result : (result: List<Article>) -> Unit){
        val request = createGet("article/$name")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                result(ArrayList())
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body?.string()
                result(gson.fromJson(res, Array<Article>::class.java).asList())
            }
        })
    }

    fun getArticle(id : Int, result: (result: Article) -> Unit){
        val request = createGet("article/article/$id")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                result(Article())
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body?.string()
                result(gson.fromJson(res, Article::class.java))
            }
        })
    }

    fun addArcticle(article: Article){
        val request = createPost("article/", gson.toJson(article).toString())
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

    fun updateArcticle(article: Article){
        val request = createPut("article/", gson.toJson(article).toString())
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

    fun deleteArcticle(id: Int){
        val request = createDelete("article/${id}", "")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
            }
        })
    }

    fun deleteArcticleData(id: Int){
        val request = createDelete("articledata/${id}", "")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
            }
        })
    }
}