package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.DTO.Article
import com.example.mobile.web.WebApi

class NewArticleActivity : AppCompatActivity() {
    lateinit var article : Article
    var id : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newarticle)
        id = intent.getIntExtra("id", -1)
        if (id > -1){
            WebApi.getArticle(id) { result ->
                run {
                    article = result
                    initialize()
                }
            }
        }
        else{
            article = Article()
            initialize()
        }
    }

    private fun initialize(){
        findViewById<ImageButton>(R.id.articleBack).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.articleSave).setOnClickListener {
            if (id == -1){
                add()
            }
            else{
                update()
            }
        }
        /*val rv = findViewById<RecyclerView>(R.id.allArticlesRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = createAdapter()
        rv.adapter = adapter
        val search = findViewById<EditText>(R.id.articlesSearch)
        search.addTextChangedListener {
            getArticles(search.text.toString())
        }
        getArticles(search.text.toString())
        findViewById<ImageButton>(R.id.articlesAdd).setOnClickListener {
            toArticle(-1)
        }*/
    }

    private fun add(){
        TODO()
    }

    private fun update(){
        TODO()
    }
}