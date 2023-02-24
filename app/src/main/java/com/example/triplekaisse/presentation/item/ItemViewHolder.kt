package com.example.triplekaisse.presentation.item

import android.graphics.Color
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.triplekaisse.R
import com.example.triplekaisse.domain.model.Item

class ItemViewHolder(view: View, private val onUpdateClickListener: OnUpdateClickListener): RecyclerView.ViewHolder(view) {


    private val description: TextView = view.findViewById(R.id.item_description)
    private val button: ImageButton = view.findViewById(R.id.hide_button)

    fun bind(item: Item) {
        description.text = item.description
        if (item.active != 0) {
            button.setBackgroundResource(R.drawable.ic_hide)
            description.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            button.setBackgroundResource(R.drawable.ic_show)
            description.setTextColor(Color.parseColor("#B7B7B7"))
        }
        button.setOnClickListener {
            item.let { it1 -> onUpdateClickListener(it1) }
        }
    }
}