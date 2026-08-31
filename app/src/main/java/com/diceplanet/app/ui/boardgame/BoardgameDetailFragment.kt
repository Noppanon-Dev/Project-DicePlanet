package com.diceplanet.app.ui.boardgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class BoardgameDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_boardgame_detail,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        // เชื่อม View
        val imgGameDetail =
            view.findViewById<ImageView>(R.id.imgGameDetail)

        val tvGameDetailName =
            view.findViewById<TextView>(R.id.tvGameDetailName)

        val tvGameDetailCategory =
            view.findViewById<TextView>(R.id.tvGameDetailCategory)

        val tvGameDetailPlayers =
            view.findViewById<TextView>(R.id.tvGameDetailPlayers)

        val tvGameDetailTime =
            view.findViewById<TextView>(R.id.tvGameDetailTime)

        val tvGameDetailDescription =
            view.findViewById<TextView>(R.id.tvGameDetailDescription)

        val btnBack =
            view.findViewById<ImageButton>(R.id.btnBack)


        // รับข้อมูลจาก BoardgameFragment
        val name =
            arguments?.getString("name") ?: "Dixit"

        val category =
            arguments?.getString("category") ?: "ปาร์ตี้"

        val players =
            arguments?.getString("players") ?: "3–4 คน"

        val playTime =
            arguments?.getString("playTime") ?: "30–60 นาที"

        val description =
            arguments?.getString("description")
                ?: "รายละเอียดเกม"

        val imageResId =
            arguments?.getInt(
                "imageResId",
                R.drawable.dixit
            ) ?: R.drawable.dixit


        // แสดงข้อมูล
        imgGameDetail.setImageResource(imageResId)

        tvGameDetailName.text = name

        tvGameDetailCategory.text = category

        tvGameDetailPlayers.text = players

        tvGameDetailTime.text = playTime

        tvGameDetailDescription.text = description


        // ปุ่มย้อนกลับ
        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}