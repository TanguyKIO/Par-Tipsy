package com.example.triplekaisse.presentation.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.triplekaisse.R
import com.example.triplekaisse.databinding.PlayerItemBinding
import com.example.triplekaisse.domain.model.Item
import com.example.triplekaisse.domain.model.Player

class PlayerAdapter(
    var players: List<Player>,
    private val onDeleteClickListener: OnPlayerDeleteClickListener
): ListAdapter<Player, PlayerViewHolder>(PlayerDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.player_item, parent, false)
        return PlayerViewHolder(view, onDeleteClickListener)
    }

    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player)
    }
}

class PlayerDiffUtil: DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player)= false

    override fun areContentsTheSame(oldItem: Player, newItem: Player)= false
}

typealias OnPlayerDeleteClickListener = (Player) -> Unit