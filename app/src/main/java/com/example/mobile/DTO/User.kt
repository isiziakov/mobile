package com.example.mobile.DTO

class User(val login : String, val password : String)
class Article(val id : Int = 0, var name : String = "", val articleData : ArrayList<ArticleData> = ArrayList())
class ArticleData(val id : Int, var text : String?, val image : ByteArray?, val number : Int, val owner : Int)
