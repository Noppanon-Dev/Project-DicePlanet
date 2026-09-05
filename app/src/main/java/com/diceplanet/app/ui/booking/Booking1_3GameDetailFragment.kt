package com.diceplanet.app.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class Booking1_3GameDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_booking1_3_game_detail,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val btnSelectGame =
            view.findViewById<Button>(R.id.btnSelectGame)

        val name =
            arguments?.getString("name") ?: "Dixit"

        val category =
            arguments?.getString("category") ?: "ปาร์ตี้"

        val players =
            arguments?.getString("players") ?: "3–4 คน"

        val playTime =
            arguments?.getString("playTime") ?: "30–60 นาที"

        val description =
            arguments?.getString("description") ?: "รายละเอียดเกม"

        val imageResId =
            arguments?.getInt(
                "imageResId",
                R.drawable.dixit
            ) ?: R.drawable.dixit

        imgGameDetail.setImageResource(imageResId)
        tvGameDetailName.text = name
        tvGameDetailCategory.text = category
        tvGameDetailPlayers.text = players
        tvGameDetailTime.text = playTime
        tvGameDetailDescription.text = description

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnSelectGame.setOnClickListener {

            val bundle = Bundle().apply {

                // ข้อมูลเกม
                putString(
                    "name",
                    arguments?.getString("name", "Dixit") ?: "Dixit"
                )

                putString(
                    "category",
                    arguments?.getString("category", "ปาร์ตี้") ?: "ปาร์ตี้"
                )

                putString(
                    "players",
                    arguments?.getString("players", "3–4 คน") ?: "3–4 คน"
                )

                putString(
                    "playTime",
                    arguments?.getString("playTime", "30–60 นาที")
                        ?: "30–60 นาที"
                )

                putString(
                    "description",
                    arguments?.getString("description", "")
                        ?: ""
                )

                putInt(
                    "imageResId",
                    arguments?.getInt("imageResId", R.drawable.dixit)
                        ?: R.drawable.dixit
                )

                // ข้อมูลการจอง
                putString(
                    "selectedDate",
                    arguments?.getString("selectedDate", "") ?: ""
                )

                putString(
                    "startTime",
                    arguments?.getString("startTime", "20:00") ?: "20:00"
                )

                putString(
                    "endTime",
                    arguments?.getString("endTime", "22:00") ?: "22:00"
                )

                putInt(
                    "playerCount",
                    arguments?.getInt("playerCount", 0) ?: 0
                )

                putString(
                    "playMode",
                    arguments?.getString("playMode", "hourly") ?: "hourly"
                )

                putString(
                    "selectedTable",
                    arguments?.getString("selectedTable", "") ?: ""
                )
            }

            findNavController().navigate(
                R.id.booking1_4Fragment,
                bundle
            )
        }
    }
}