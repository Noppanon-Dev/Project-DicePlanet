package com.diceplanet.app.ui.boardgame

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
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
        super.onViewCreated(
            view,
            savedInstanceState
        )

        // =====================================================
        // Find Views
        // =====================================================

        val imgGameDetail =
            view.findViewById<ImageView>(
                R.id.imgGameDetail
            )

        val imgGameDetailContainer =
            view.findViewById<FrameLayout>(
                R.id.imgGameDetailContainer
            )

        val gameDetailCategoryContainer =
            view.findViewById<LinearLayout>(
                R.id.gameDetailCategoryContainer
            )

        val tvGameDetailName =
            view.findViewById<TextView>(
                R.id.tvGameDetailName
            )

        val tvGameDetailPlayers =
            view.findViewById<TextView>(
                R.id.tvGameDetailPlayers
            )

        val tvGameDetailTime =
            view.findViewById<TextView>(
                R.id.tvGameDetailTime
            )

        val tvGameDetailDescription =
            view.findViewById<TextView>(
                R.id.tvGameDetailDescription
            )

        val btnBack =
            view.findViewById<ImageButton>(
                R.id.btnBack
            )

        val btnSelectGame =
            view.findViewById<TextView>(
                R.id.btnSelectGame
            )

        // =====================================================
        // รับข้อมูลเกม
        // =====================================================

        val name =
            arguments?.getString("name")
                ?: "Dixit"

        val categories =
            arguments
                ?.getStringArray("categories")
                ?.toList()
                ?: listOf(
                    arguments?.getString(
                        "category"
                    ) ?: "Family"
                )

        val players =
            arguments?.getString("players")
                ?: "3–4 คน"

        val playTime =
            arguments?.getString("playTime")
                ?: "30–60 นาที"

        val description =
            arguments?.getString("description")
                ?: "รายละเอียดเกม"

        val imageResId =
            arguments?.getInt(
                "imageResId",
                R.drawable.dixit
            ) ?: R.drawable.dixit

        // =====================================================
        // ตรวจว่ามาจาก Booking หรือไม่
        // =====================================================

        val fromBooking =
            arguments?.getBoolean(
                "fromBooking",
                false
            ) == true

        // =====================================================
        // แสดงข้อมูลเกม
        // =====================================================

        imgGameDetail.setImageResource(
            imageResId
        )

        tvGameDetailName.text =
            name

        tvGameDetailPlayers.text =
            players

        tvGameDetailTime.text =
            playTime

        tvGameDetailDescription.text =
            description

        // =====================================================
        // Category Pills
        // =====================================================

        gameDetailCategoryContainer
            .removeAllViews()

        for (category in categories) {

            val categoryView =
                TextView(requireContext())

            categoryView.text =
                category

            categoryView.textSize =
                11f

            categoryView.setTextColor(
                Color.rgb(
                    51,
                    51,
                    51
                )
            )

            categoryView.gravity =
                Gravity.CENTER

            categoryView.setPadding(
                14.dp(),
                0,
                14.dp(),
                0
            )

            val background =
                GradientDrawable().apply {

                    shape =
                        GradientDrawable.RECTANGLE

                    cornerRadius =
                        100f

                    setColor(
                        getCategoryColor(
                            category
                        )
                    )
                }

            categoryView.background =
                background

            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    28.dp()
                )

            params.marginEnd =
                8.dp()

            categoryView.layoutParams =
                params

            gameDetailCategoryContainer
                .addView(
                    categoryView
                )
        }

        // =====================================================
        // เปลี่ยนสีพื้นหลังตามสีโลโก้เกม
        // =====================================================

        val bitmap =
            BitmapFactory.decodeResource(
                resources,
                imageResId
            )

        if (bitmap != null) {

            Palette.from(bitmap)
                .generate { palette ->

                    val dominantColor =
                        palette?.getDominantColor(
                            Color.LTGRAY
                        ) ?: Color.LTGRAY

                    imgGameDetailContainer.setBackgroundColor(
                        dominantColor
                    )
                }
        }

        // =====================================================
        // ปุ่มเลือกเกม
        // =====================================================

        if (fromBooking) {

            btnSelectGame.visibility =
                View.VISIBLE

            btnSelectGame.setOnClickListener {

                // =================================================
                // ส่งข้อมูลไป Booking 1.4
                // =================================================

                val bundle =
                    Bundle().apply {

                        // -------------------------
                        // ข้อมูลเกม
                        // -------------------------

                        putString(
                            "name",
                            name
                        )

                        putStringArray(
                            "categories",
                            categories.toTypedArray()
                        )

                        putString(
                            "players",
                            players
                        )

                        putString(
                            "playTime",
                            playTime
                        )

                        putString(
                            "description",
                            description
                        )

                        putInt(
                            "imageResId",
                            imageResId
                        )

                        // -------------------------
                        // ข้อมูล Booking
                        // -------------------------

                        putString(
                            "selectedDate",
                            arguments?.getString(
                                "selectedDate"
                            ) ?: ""
                        )

                        putString(
                            "startTime",
                            arguments?.getString(
                                "startTime"
                            ) ?: ""
                        )

                        putString(
                            "endTime",
                            arguments?.getString(
                                "endTime"
                            ) ?: ""
                        )

                        putString(
                            "selectedTable",
                            arguments?.getString(
                                "selectedTable"
                            ) ?: ""
                        )

                        putInt(
                            "playerCount",
                            arguments?.getInt(
                                "playerCount",
                                0
                            ) ?: 0
                        )

                        putString(
                            "playMode",
                            arguments?.getString(
                                "playMode"
                            ) ?: "hourly"
                        )
                    }

                findNavController().navigate(
                    R.id.booking1_4Fragment,
                    bundle
                )
            }

        } else {

            btnSelectGame.visibility =
                View.GONE
        }

        // =====================================================
        // ปุ่มย้อนกลับ
        // =====================================================

        btnBack.setOnClickListener {

            findNavController()
                .navigateUp()
        }
    }

    // =========================================================
    // Category Colors
    // =========================================================

    @SuppressLint("UseKtx")
    private fun getCategoryColor(
        category: String
    ): Int {

        return when (category) {

            "Abstract" ->
                Color.parseColor("#DDEBFF")

            "Action" ->
                Color.parseColor("#FFD6D6")

            "Adventure" ->
                Color.parseColor("#FFE0B2")

            "Animals" ->
                Color.parseColor("#D9F2D9")

            "Bluffing" ->
                Color.parseColor("#E5D5FF")

            "Card Game" ->
                Color.parseColor("#FFE0E6")

            "City Building" ->
                Color.parseColor("#D9E8FF")

            "Civilization" ->
                Color.parseColor("#FFF0C2")

            "Deduction" ->
                Color.parseColor("#E4D9FF")

            "Dice" ->
                Color.parseColor("#FFD9B8")

            "Educational" ->
                Color.parseColor("#D6F5E3")

            "Economic" ->
                Color.parseColor("#FFF2B8")

            "Engine Building" ->
                Color.parseColor("#D7E9FF")

            "Exploration" ->
                Color.parseColor("#D8F3F0")

            "Family" ->
                Color.parseColor("#CFE5FF")

            "Fantasy" ->
                Color.parseColor("#E5D4FF")

            "Fighting" ->
                Color.parseColor("#FFD4D4")

            "Hand Management" ->
                Color.parseColor("#FFE2C2")

            "Horror" ->
                Color.parseColor("#D9D1E9")

            "Humor" ->
                Color.parseColor("#FFF0B3")

            "Medieval" ->
                Color.parseColor("#E8D7C4")

            "Mystery" ->
                Color.parseColor("#DDD6FF")

            "Mythology" ->
                Color.parseColor("#E7D4FF")

            "Negotiation" ->
                Color.parseColor("#D5E8FF")

            "Party Game" ->
                Color.parseColor("#E3D1FF")

            "Political" ->
                Color.parseColor("#FFD9E2")

            "Puzzle" ->
                Color.parseColor("#FFF0A8")

            "Racing" ->
                Color.parseColor("#FFD6B8")

            "Role Playing" ->
                Color.parseColor("#DCCEFF")

            "Science Fiction" ->
                Color.parseColor("#D6E7FF")

            "Set Collection" ->
                Color.parseColor("#D8F0D8")

            "Social Deduction" ->
                Color.parseColor("#E3D7FF")

            "Solo" ->
                Color.parseColor("#FFE1C7")

            "Sports" ->
                Color.parseColor("#D5EFD5")

            "Storytelling" ->
                Color.parseColor("#FFDCE8")

            "Strategy" ->
                Color.parseColor("#D5E8D4")

            "Survival" ->
                Color.parseColor("#D8EBD8")

            "Travel" ->
                Color.parseColor("#D5F0F0")

            "Warfare" ->
                Color.parseColor("#FFD5D5")

            "Word Game" ->
                Color.parseColor("#FFF0C9")

            else ->
                Color.parseColor("#EAEAEA")
        }
    }

    // =========================================================
    // DP Helper
    // =========================================================

    private fun Int.dp(): Int {

        return (
                this *
                        resources
                            .displayMetrics
                            .density
                ).toInt()
    }
}