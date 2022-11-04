package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.DTO.Article
import com.example.mobile.DTO.ArticleData
import com.example.mobile.adapters.ArticleAdapter
import com.example.mobile.web.WebApi
import java.io.InputStream


class NewArticleActivity : AppCompatActivity() {
    lateinit var article : Article
    lateinit var name : EditText
    lateinit var rv : RecyclerView
    lateinit var adapter: ArticleAdapter
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
        name = findViewById(R.id.newArticleName)
        runOnUiThread {
            name.setText(article.name)
        }
        findViewById<ImageButton>(R.id.newArticleBack).setOnClickListener { finish() }
        findViewById<ImageButton>(R.id.newArcticleSave).setOnClickListener {
            if (!name.text.isNullOrEmpty()){
                article.name = name.text.toString()
                if (id == -1){
                    add()
                }
                else{
                    update()
                }
                finish()
            }
        }
        rv = findViewById(R.id.articleDataRV)
        runOnUiThread{
            rv.layoutManager = LinearLayoutManager(this)
        }
        adapter = ArticleAdapter(article.articleData, { delete: Int -> deleteLine(delete) }, { addImage() }, { addText() } )
        runOnUiThread {
            rv.adapter = adapter
        }
        findViewById<ImageButton>(R.id.newArticleBack).setOnClickListener { finish() }
        }
    }

    private fun add(){
        WebApi.addArcticle(article)
    }

    private fun update(){
        WebApi.updateArcticle(article)
    }

    private fun addImage(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 3)
    }

    private fun addText(){
        article.articleData.add(ArticleData(0, "", null, article.articleData.size, article.id))
        adapter.notifyItemInserted(article.articleData.size - 1)
    }

    private fun deleteLine(position : Int){
        WebApi.deleteArcticleData(article.articleData[position].id)
        article.articleData.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null){
            when (requestCode){
                3 -> {
                    val uri = data.data
                    if (uri != null){
                        val iStream: InputStream? = contentResolver.openInputStream(uri)
                        val inputData = iStream?.readBytes()
                        iStream?.close()
                        if (inputData != null){
                            article.articleData.add(ArticleData(0, null, inputData, article.articleData.size, article.id))
                            adapter.notifyItemInserted(article.articleData.size - 1)
                        }
                    }
                }
            }
        }
    }
}