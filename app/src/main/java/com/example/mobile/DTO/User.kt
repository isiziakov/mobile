package com.example.mobile.DTO

class User(val login : String, val password : String)
class Article(val id : Int = 0, val name : String = "", val data : ArrayList<ArticleData> = ArrayList())
class ArticleData(val id : Int, val text : String?, val image : ByteArray?, number : Int, owner : Int)
