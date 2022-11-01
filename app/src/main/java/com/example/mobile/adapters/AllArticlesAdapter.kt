package com.example.mobile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.DTO.Article
import com.example.mobile.R

class AllArticlesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val name : TextView = itemView.findViewById(R.id.articlesItemName)
    val delete : ImageButton = itemView.findViewById(R.id.articlesItemDelete)
}

class AllArticlesAdapter(private val articles: ArrayList<Article>,
    private val admin : Boolean,
    private val update : (update: Int) -> Unit,
    private val delete : (delete: Int) -> Unit)
    : RecyclerView.Adapter<AllArticlesViewHolder>() {

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: AllArticlesViewHolder, position: Int) {
        holder.name.text = articles[position].name
        holder.itemView.setOnClickListener {
            update(position)
        }
        if (admin){
            holder.delete.setOnClickListener {
                delete(position)
            }
        }
        else{
            holder.delete.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllArticlesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.articles_item, parent, false)
        return AllArticlesViewHolder(itemView)
    }

    override fun getItemId(position : Int) = position.toLong()

    override fun getItemViewType(position : Int) = position
}