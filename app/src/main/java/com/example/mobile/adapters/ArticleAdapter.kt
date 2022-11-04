package com.example.mobile.adapters

import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.DTO.Article
import com.example.mobile.DTO.ArticleData
import com.example.mobile.R

class PanelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val addImage : Button = itemView.findViewById(R.id.article_add_data_text)
    val addText : Button = itemView.findViewById(R.id.article_add_data_image)
}

class ArticleImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val image : ImageView = itemView.findViewById(R.id.articleDataImage)
    val delete : ImageButton = itemView.findViewById(R.id.articleDataDelete)
}

class ArticleTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val text : EditText = itemView.findViewById(R.id.articleDataText)
    val delete : ImageButton = itemView.findViewById(R.id.articleDataDelete)
}

class ArticleAdapter(private val data: ArrayList<ArticleData>,
                     private val delete : (delete: Int) -> Unit,
                     private val addImage : () -> Unit,
                     private val addText : () -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = data.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleTextViewHolder){
            holder.delete.setOnClickListener { delete(position) }
            holder.text.setText(data[position].text)
            holder.text.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    data[holder.adapterPosition].text = s.toString()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
        }
        if (holder is ArticleImageViewHolder){
            holder.delete.setOnClickListener { delete(position) }
            holder.image.setImageBitmap(BitmapFactory.decodeByteArray(data[position].image, 0, data[position].image!!.size))
        }
        if (holder is PanelViewHolder){
            holder.addImage.setOnClickListener {
                addImage()
            }
            holder.addText.setOnClickListener {
                addText()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
        if (viewType == -1){
            return PanelViewHolder(itemView.inflate(R.layout.article_item_panel, parent, false))
        }
        if (data[viewType].image != null){
            return ArticleImageViewHolder(itemView.inflate(R.layout.article_item_image, parent, false))
        }
        return ArticleTextViewHolder(itemView.inflate(R.layout.article_item_text, parent, false))
    }

    override fun getItemId(position : Int) = position.toLong()

    override fun getItemViewType(position : Int) = if (position == data.size) -1 else position
}