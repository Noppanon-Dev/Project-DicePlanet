package com.diceplanet.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R
import com.diceplanet.app.ui.boardgame.BoardGame
import com.diceplanet.app.ui.boardgame.BoardGameData

// Fragment สำหรับหน้า Home
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // =========================
        // Login
        // =========================

        val tvLogin =
            view.findViewById<TextView>(R.id.tvLogin)

        tvLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }


        // =========================
        // ดูทั้งหมด
        // =========================

        val tvAllCategory =
            view.findViewById<TextView>(R.id.tvAllCategory)

        tvAllCategory.setOnClickListener {
            openBoardgame()
        }


        // =========================
        // หมวดหมู่เด่น
        // =========================

        // Family
        val categoryFamily =
            view.findViewById<View>(R.id.categoryFamily)

        categoryFamily.setOnClickListener {
            openBoardgame("Family")
        }


        // Puzzle
        val categoryPuzzle =
            view.findViewById<View>(R.id.categoryPuzzle)

        categoryPuzzle.setOnClickListener {
            openBoardgame("Puzzle")
        }


        // Party Game
        val categoryParty =
            view.findViewById<View>(R.id.categoryParty)

        categoryParty.setOnClickListener {
            openBoardgame("Party Game")
        }


        // Strategy
        val categoryStrategy =
            view.findViewById<View>(R.id.categoryStrategy)

        categoryStrategy.setOnClickListener {
            openBoardgame("Strategy")
        }


        // Fantasy
        val categoryFantasy =
            view.findViewById<View>(R.id.categoryFantasy)

        categoryFantasy.setOnClickListener {
            openBoardgame("Fantasy")
        }


        // =========================
        // เกมยอดนิยม
        // =========================

        val game1 =
            view.findViewById<View>(R.id.gameCard1)

        val game2 =
            view.findViewById<View>(R.id.gameCard2)

        val game3 =
            view.findViewById<View>(R.id.gameCard3)


        val popularGames =
            BoardGameData.gameList
                .sortedByDescending {
                    it.popularity
                }
                .take(3)


        setupGameCard(
            game1,
            popularGames.getOrNull(0),
            R.id.imgGame1,
            R.id.tvGameName1
        )

        setupGameCard(
            game2,
            popularGames.getOrNull(1),
            R.id.imgGame2,
            R.id.tvGameName2
        )

        setupGameCard(
            game3,
            popularGames.getOrNull(2),
            R.id.imgGame3,
            R.id.tvGameName3
        )


        // =========================
        // ดูเกมทั้งหมด
        // =========================

        val tvAllGames =
            view.findViewById<TextView>(R.id.tvAllGames)

        tvAllGames.setOnClickListener {
            openBoardgame()
        }
    }


    // =========================
    // เปิดหน้า Board Game
    // =========================

    private fun openBoardgame(category: String? = null) {

        val bundle = category?.let {
            Bundle().apply {
                putString("category", it)
            }
        }

        findNavController().navigate(
            R.id.boardgameFragment,
            bundle
        )
    }


    // =========================
    // ตั้งค่าการ์ดเกม
    // =========================

    private fun setupGameCard(
        card: View,
        game: BoardGame?,
        imageId: Int,
        nameId: Int
    ) {

        if (game == null) {
            card.visibility = View.GONE
            return
        }

        card.visibility = View.VISIBLE


        // รูปเกม
        val imageView =
            card.findViewById<ImageView>(imageId)

        imageView.setImageResource(
            game.imageResId
        )


        // ชื่อเกม
        val gameName =
            card.findViewById<TextView>(nameId)

        gameName.text = game.name


        // กดการ์ดเกม
        card.setOnClickListener {

            val bundle = Bundle().apply {

                putString(
                    "name",
                    game.name
                )

                putString(
                    "category",
                    game.categories.joinToString(" • ")
                )

                putString(
                    "players",
                    game.players
                )

                putString(
                    "playTime",
                    game.playTime
                )

                putString(
                    "description",
                    game.description
                )

                putInt(
                    "imageResId",
                    game.imageResId
                )
            }

            findNavController().navigate(
                R.id.boardgameDetailFragment,
                bundle
            )
        }
    }
}