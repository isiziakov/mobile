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
import com.example.mobile.adapters.AllArticlesAdapter
import com.example.mobile.web.WebApi

class ArticlesActivity : AppCompatActivity() {
    var articles : ArrayList<Article> = ArrayList()
    lateinit var adapter : AllArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        articles.add(Article(0, "Test1"))
        articles.add(Article(0, "Test2"))
        articles.add(Article(0, "Test3"))
        initialize()
    }

    private fun initialize(){
        val rv = findViewById<RecyclerView>(R.id.allArticlesRecyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = createAdapter()
        rv.adapter = adapter
        findViewById<ImageButton>(R.id.articlesBack).setOnClickListener { finish() }
        val search = findViewById<EditText>(R.id.articlesSearch)
        search.addTextChangedListener {
            getArticles(search.text.toString())
        }
        getArticles(search.text.toString())
        findViewById<ImageButton>(R.id.articlesAdd).setOnClickListener {
            toArticle(-1)
        }
    }

    private fun updateArticle(position : Int){
        toArticle(articles[position].id)
    }

    private fun deleteArticle(position : Int){
        TODO()
    }

    private fun createAdapter() = AllArticlesAdapter(articles, true, { update ->  updateArticle(update)},
        { delete -> deleteArticle(delete)})

    private fun getArticles(name : String){
        WebApi.getAllArticles(name) { result ->
            run {
                articles.clear()
                articles.addAll(result)
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun toArticle(id : Int){
        val intent = Intent(this, NewArticleActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}