package com.diceplanet.app.ui.boardgame

import com.diceplanet.app.R

object BoardGameData {

    val gameList = listOf(

        // =========================
        // Dixit
        // =========================

        BoardGame(
            name = "Dixit",

            categories = listOf(
                "Family",
                "Puzzle",
                "Party Game",
                "Storytelling",
                "Social Deduction"
            ),

            players = "3–4 คน",

            playTime = "30–60 นาที",

            description =
                "ตีความภาพและเรื่องราว พร้อมค้นหาคำใบ้เพื่อร่วมโต๊ะเดียวกัน",

            imageResId =
                R.drawable.dixit,

            popularity = 100,

            isNew = false
        ),


        // =========================
        // Catan
        // =========================

        BoardGame(
            name = "Catan",

            categories = listOf(
                "Family",
                "Strategy",
                "Economic",
                "Civilization",
                "Negotiation",
                "Exploration"
            ),

            players = "3–4 คน",

            playTime = "60–90 นาที",

            description =
                "สร้างถนนและเมือง แลกเปลี่ยนทรัพยากรเพื่อพัฒนาอาณาจักรของคุณ",

            imageResId =
                R.drawable.catan,

            popularity = 90,

            isNew = false
        ),


        // =========================
        // Azul
        // =========================

        BoardGame(
            name = "Azul",

            categories = listOf(
                "Family",
                "Puzzle",
                "Strategy",
                "Abstract",
                "Set Collection",
                "Hand Management"
            ),

            players = "2–4 คน",

            playTime = "30–45 นาที",

            description =
                "เลือกกระเบื้องและจัดวางให้สวยงามเพื่อทำคะแนนให้ได้มากที่สุด",

            imageResId =
                R.drawable.azul,

            popularity = 80,

            isNew = true
        )
    )
}