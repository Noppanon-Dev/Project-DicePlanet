package com.diceplanet.app.ui.boardgame

import com.diceplanet.app.R

object BoardGameData {

    //All Games
    val gameList = listOf(

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
        ),

        BoardGame(
            name = "Wingspan",
            categories = listOf(
                "Animals",
                "Family",
                "Strategy",
                "Educational",
                "Engine Building",
                "Set Collection"
            ),
            players = "1–5 คน",
            playTime = "40–70 นาที",
            description =
                "สะสมนกและสร้างแหล่งที่อยู่อาศัยเพื่อทำคะแนน",
            imageResId =
                R.drawable.wingspan,
            popularity = 95,
            isNew = false
        ),

        BoardGame(
            name = "Ticket to Ride",
            categories = listOf(
                "Family",
                "Strategy",
                "Travel",
                "Set Collection",
                "Hand Management"
            ),
            players = "2–5 คน",
            playTime = "30–60 นาที",
            description =
                "สร้างเส้นทางรถไฟเชื่อมเมืองต่าง ๆ เพื่อทำภารกิจและเก็บคะแนน",
            imageResId =
                R.drawable.ticket_to_ride,
            popularity = 94,
            isNew = false
        ),

        BoardGame(
            name = "Pandemic",
            categories = listOf(
                "Family",
                "Strategy",
                "Educational",
                "Deduction",
                "Hand Management"
            ),
            players = "2–4 คน",
            playTime = "45–60 นาที",
            description =
                "ร่วมมือกันหยุดการแพร่ระบาดและค้นหาวิธีรักษาโรคทั่วโลก",
            imageResId =
                R.drawable.pandemic,
            popularity = 92,
            isNew = false
        ),

        BoardGame(
            name = "Carcassonne",
            categories = listOf(
                "Family",
                "Strategy",
                "Medieval",
                "City Building",
                "Set Collection"
            ),
            players = "2–5 คน",
            playTime = "30–45 นาที",
            description =
                "วางแผ่นดินเพื่อสร้างเมือง ถนน และพื้นที่ต่าง ๆ พร้อมสะสมคะแนน",
            imageResId =
                R.drawable.carcassonne,
            popularity = 88,
            isNew = false
        ),

        BoardGame(
            name = "7 Wonders",
            categories = listOf(
                "Family",
                "Strategy",
                "Civilization",
                "Economic",
                "Card Game",
                "City Building"
            ),
            players = "2–7 คน",
            playTime = "30 นาที",
            description =
                "พัฒนาอารยธรรมและสร้างสิ่งมหัศจรรย์เพื่อสร้างเมืองที่ยิ่งใหญ่",
            imageResId =
                R.drawable.seven_wonders,
            popularity = 91,
            isNew = false
        ),

        BoardGame(
            name = "Splendor",
            categories = listOf(
                "Family",
                "Strategy",
                "Economic",
                "Set Collection",
                "Engine Building"
            ),
            players = "2–4 คน",
            playTime = "30 นาที",
            description =
                "สะสมอัญมณี ซื้อกิจการ และดึงดูดขุนนางเพื่อทำคะแนน",
            imageResId =
                R.drawable.splendor,
            popularity = 89,
            isNew = false
        ),

        BoardGame(
            name = "Terraforming Mars",
            categories = listOf(
                "Strategy",
                "Science Fiction",
                "Economic",
                "Engine Building",
                "Exploration"
            ),
            players = "1–5 คน",
            playTime = "120 นาที",
            description =
                "บริหารทรัพยากรและพัฒนาดาวอังคารให้สามารถอยู่อาศัยได้",
            imageResId =
                R.drawable.terraforming_mars,
            popularity = 98,
            isNew = false
        ),

        BoardGame(
            name = "Codenames",
            categories = listOf(
                "Party Game",
                "Word Game",
                "Deduction",
                "Social Deduction"
            ),
            players = "2–8 คน",
            playTime = "15 นาที",
            description =
                "ให้คำใบ้เพื่อช่วยทีมค้นหาสายลับของตัวเอง",
            imageResId =
                R.drawable.codenames,
            popularity = 87,
            isNew = false
        ),

        BoardGame(
            name = "Sushi Go!",
            categories = listOf(
                "Family",
                "Party Game",
                "Card Game",
                "Set Collection",
                "Hand Management"
            ),
            players = "2–5 คน",
            playTime = "15 นาที",
            description =
                "เลือกการ์ดอาหารญี่ปุ่นและสะสมชุดอาหารเพื่อทำคะแนน",
            imageResId =
                R.drawable.sushi_go,
            popularity = 84,
            isNew = false
        ),

        BoardGame(
            name = "King of Tokyo",
            categories = listOf(
                "Family",
                "Party Game",
                "Dice",
                "Action",
                "Fighting"
            ),
            players = "2–6 คน",
            playTime = "30 นาที",
            description =
                "สวมบทสัตว์ประหลาดและต่อสู้เพื่อยึดครองเมืองโตเกียว",
            imageResId =
                R.drawable.king_of_tokyo,
            popularity = 86,
            isNew = false
        ),

        BoardGame(
            name = "Root",
            categories = listOf(
                "Strategy",
                "Adventure",
                "Fantasy",
                "Political",
                "Warfare"
            ),
            players = "2–4 คน",
            playTime = "60–90 นาที",
            description =
                "นำฝ่ายของคุณต่อสู้เพื่อควบคุมป่าและบรรลุเป้าหมาย",
            imageResId =
                R.drawable.root,
            popularity = 96,
            isNew = false
        ),

        BoardGame(
            name = "The Quacks of Quedlinburg",
            categories = listOf(
                "Family",
                "Strategy",
                "Dice",
                "Set Collection",
                "Solo"
            ),
            players = "2–4 คน",
            playTime = "45 นาที",
            description =
                "สุ่มส่วนผสมลงหม้อและตัดสินใจว่าจะเสี่ยงต่อหรือหยุด",
            imageResId =
                R.drawable.quacks_of_quedlinburg,
            popularity = 83,
            isNew = false
        ),

        BoardGame(
            name = "Everdell",
            categories = listOf(
                "Animals",
                "Fantasy",
                "Family",
                "Strategy",
                "City Building",
                "Engine Building"
            ),
            players = "1–4 คน",
            playTime = "40–80 นาที",
            description =
                "สร้างเมืองในป่าและรวบรวมสิ่งมีชีวิตเพื่อพัฒนาอาณาจักร",
            imageResId =
                R.drawable.everdell,
            popularity = 93,
            isNew = false
        ),

        BoardGame(
            name = "Dune: Imperium",
            categories = listOf(
                "Strategy",
                "Science Fiction",
                "Economic",
                "Warfare",
                "Negotiation"
            ),
            players = "1–4 คน",
            playTime = "60–120 นาที",
            description =
                "วางแผนการเมืองและสงครามเพื่อสร้างอำนาจบนดาวอาร์ราคิส",
            imageResId =
                R.drawable.dune_imperium,
            popularity = 97,
            isNew = false
        ),

        BoardGame(
            name = "Werewolf",
            categories = listOf(
                "Party Game",
                "Bluffing",
                "Deduction",
                "Social Deduction",
                "Role Playing"
            ),
            players = "7–15 คน",
            playTime = "30–60 นาที",
            description =
                "ค้นหามนุษย์หมาป่าที่ซ่อนตัวอยู่ในหมู่ผู้เล่น",
            imageResId =
                R.drawable.werewolf,
            popularity = 85,
            isNew = false
        ),

        BoardGame(
            name = "UNO",
            categories = listOf(
                "Family",
                "Party Game",
                "Card Game",
                "Hand Management",
                "Set Collection"
            ),
            players = "2–10 คน",
            playTime = "15–30 นาที",
            description =
                "จับคู่สีหรือตัวเลขและใช้การ์ดพิเศษเพื่อกำจัดการ์ดในมือ",
            imageResId =
                R.drawable.uno,
            popularity = 97,
            isNew = false
        ),

        BoardGame(
            name = "Gloomhaven",
            categories = listOf(
                "Fantasy",
                "Adventure",
                "Strategy",
                "Role Playing",
                "Fighting",
                "Solo"
            ),
            players = "1–4 คน",
            playTime = "60–120 นาที",
            description =
                "ออกผจญภัย ต่อสู้กับศัตรู และพัฒนาตัวละครผ่านภารกิจต่าง ๆ",
            imageResId =
                R.drawable.gloomhaven,
            popularity = 99,
            isNew = false
        )
    )
}