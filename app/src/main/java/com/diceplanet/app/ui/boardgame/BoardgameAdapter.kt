package com.diceplanet.app.ui.boardgame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diceplanet.app.R

class BoardgameAdapter(
    private var gameList: List<BoardGame>,
    private val onGameClick: (BoardGame) -> Unit
) : RecyclerView.Adapter<BoardgameAdapter.BoardgameViewHolder>() {

    class BoardgameViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val imgGame: ImageView =
            itemView.findViewById(R.id.imgGame)

        val tvGameName: TextView =
            itemView.findViewById(R.id.tvGameName)

        val tvGameCategory: TextView =
            itemView.findViewById(R.id.tvGameCategory)

        val tvGamePlayers: TextView =
            itemView.findViewById(R.id.tvGamePlayers)

        val tvGameTime: TextView =
            itemView.findViewById(R.id.tvGameTime)

        val tvGameDescription: TextView =
            itemView.findViewById(R.id.tvGameDescription)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BoardgameViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_boardgame,
                parent,
                false
            )

        return BoardgameViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: BoardgameViewHolder,
        position: Int
    ) {

        val game = gameList[position]

        holder.imgGame.setImageResource(game.imageResId)

        holder.tvGameName.text = game.name

        holder.tvGameCategory.text = game.category

        holder.tvGamePlayers.text = game.players

        holder.tvGameTime.text = game.playTime

        holder.tvGameDescription.text = game.description

        // กด Card
        holder.itemView.setOnClickListener {
            onGameClick(game)
        }
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    fun updateList(newList: List<BoardGame>) {
        gameList = newList
        notifyDataSetChanged()
    }
}