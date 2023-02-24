package com.example.triplekaisse.presentation.menu

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.triplekaisse.R
import com.example.triplekaisse.domain.model.Player

class PlayerViewHolder(view: View, private val onDeleteClickListener: OnPlayerDeleteClickListener): RecyclerView.ViewHolder(view) {

    private val playerText: TextView = view.findViewById(R.id.player)
    private val button: ImageButton = view.findViewById(R.id.delete)

    fun bind(player: Player) {
        playerText.text = player.name
        button.setOnClickListener {
            onDeleteClickListener(player)
        }
    }
}