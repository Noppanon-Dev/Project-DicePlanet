package com.diceplanet.app.ui.boardgame

data class BoardGame(
    val name: String,
    val categories: List<String>,
    val players: String,
    val playTime: String,
    val description: String,
    val imageResId: Int,
    val popularity: Int,
    val isNew: Boolean
)