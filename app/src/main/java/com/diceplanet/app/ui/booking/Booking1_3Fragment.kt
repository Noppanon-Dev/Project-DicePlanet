package com.diceplanet.app.ui.booking

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diceplanet.app.R
import com.diceplanet.app.ui.boardgame.BoardGame
import com.diceplanet.app.ui.boardgame.BoardgameAdapter

class Booking1_3Fragment : Fragment() {

    private lateinit var recyclerBoardgame: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var tvGameCount: TextView
    private lateinit var tvSort: TextView
    private lateinit var tvCategoryFilter: TextView
    private lateinit var boardgameAdapter: BoardgameAdapter

    private var currentSort = "ความนิยม"
    private var currentCategory = "ทั้งหมด"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_booking1_3,
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
        // เชื่อม View
        // =========================

        recyclerBoardgame =
            view.findViewById(R.id.recyclerBoardgame)

        etSearch =
            view.findViewById(R.id.etSearch)

        tvGameCount =
            view.findViewById(R.id.tvGameCount)

        tvSort =
            view.findViewById(R.id.tvSort)

        tvCategoryFilter =
            view.findViewById(R.id.tvCategoryFilter)


        // =========================
        // RecyclerView
        // =========================

        recyclerBoardgame.layoutManager =
            LinearLayoutManager(requireContext())


        // =========================
        // ข้อมูลเกม
        // =========================

        val gameList = listOf(

            BoardGame(
                name = "Dixit",
                category = "ปาร์ตี้",
                players = "3–4 คน",
                playTime = "30–60 นาที",
                description = "ตีความภาพและเรื่องราว พร้อมค้นหาคำใบ้เพื่อร่วมโต๊ะเดียวกัน",
                imageResId = R.drawable.dixit,
                popularity = 100,
                isNew = false
            ),

            BoardGame(
                name = "Catan",
                category = "วางแผน",
                players = "3–4 คน",
                playTime = "60–90 นาที",
                description = "สร้างถนนและเมือง แลกเปลี่ยนทรัพยากรเพื่อพัฒนาอาณาจักรของคุณ",
                imageResId = R.drawable.catan,
                popularity = 90,
                isNew = false
            ),

            BoardGame(
                name = "Azul",
                category = "วางแผน",
                players = "2–4 คน",
                playTime = "30–45 นาที",
                description = "เลือกกระเบื้องและจัดวางให้สวยงามเพื่อทำคะแนนให้ได้มากที่สุด",
                imageResId = R.drawable.azul,
                popularity = 80,
                isNew = true
            )
        )


        // =========================
        // Adapter
        // =========================

        boardgameAdapter =
            BoardgameAdapter(gameList) { game ->

                val bundle = Bundle().apply {

                    putString(
                        "name",
                        game.name
                    )

                    putString(
                        "category",
                        game.category
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

                    // ข้อมูลจาก Booking
                    putString(
                        "selectedDate",
                        arguments?.getString("selectedDate", "")
                    )

                    putString(
                        "startTime",
                        arguments?.getString("startTime", "20:00")
                    )

                    putString(
                        "endTime",
                        arguments?.getString("endTime", "22:00")
                    )

                    putInt(
                        "playerCount",
                        arguments?.getInt("playerCount", 0) ?: 0
                    )

                    putString(
                        "playMode",
                        arguments?.getString("playMode", "hourly")
                    )

                    putString(
                        "selectedTable",
                        arguments?.getString("selectedTable", "") ?: ""
                    )
                }

                findNavController().navigate(
                    R.id.booking1_3GameDetailFragment,
                    bundle
                )
            }


        recyclerBoardgame.adapter =
            boardgameAdapter


        // =========================
        // แสดงเกมเริ่มต้น
        // =========================

        updateGameList(gameList)


        // =========================
        // Sort
        // =========================

        tvSort.setOnClickListener {

            val popup =
                PopupMenu(
                    requireContext(),
                    tvSort
                )

            popup.menu.add("ความนิยม")
            popup.menu.add("A-Z")
            popup.menu.add("Z-A")
            popup.menu.add("มาใหม่")

            popup.setOnMenuItemClickListener { item ->

                currentSort =
                    item.title.toString()

                tvSort.text =
                    "${currentSort}  ▾"

                updateGameList(gameList)

                true
            }

            popup.show()
        }


        // =========================
        // Category Filter
        // =========================

        tvCategoryFilter.setOnClickListener {

            val popup =
                PopupMenu(
                    requireContext(),
                    tvCategoryFilter
                )

            popup.menu.add("ทั้งหมด")
            popup.menu.add("ปาร์ตี้")
            popup.menu.add("วางแผน")
            popup.menu.add("เล่าเรื่อง")

            popup.setOnMenuItemClickListener { item ->

                currentCategory =
                    item.title.toString()

                updateGameList(gameList)

                true
            }

            popup.show()
        }


        // =========================
        // Search
        // =========================

        etSearch.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    updateGameList(gameList)
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )


        // =========================
        // ปุ่มย้อนกลับ
        // =========================

        view.findViewById<View>(R.id.btnBack)
            .setOnClickListener {

                findNavController()
                    .popBackStack()
            }
    }


    // =========================
    // กรอง + เรียงเกม
    // =========================

    private fun updateGameList(
        gameList: List<BoardGame>
    ) {

        val keyword =
            etSearch.text
                .toString()
                .trim()

        val filteredList =
            gameList.filter { game ->

                val matchSearch =
                    keyword.isEmpty() ||
                            game.name.contains(
                                keyword,
                                ignoreCase = true
                            )

                val matchCategory =
                    currentCategory == "ทั้งหมด" ||
                            game.category == currentCategory

                matchSearch &&
                        matchCategory
            }


        val sortedList =
            when (currentSort) {

                "ความนิยม" ->
                    filteredList.sortedByDescending {
                        it.popularity
                    }

                "A-Z" ->
                    filteredList.sortedBy {
                        it.name
                    }

                "Z-A" ->
                    filteredList.sortedByDescending {
                        it.name
                    }

                "มาใหม่" ->
                    filteredList.sortedByDescending {
                        it.isNew
                    }

                else ->
                    filteredList
            }


        boardgameAdapter.updateList(
            sortedList
        )

        tvGameCount.text =
            "ทั้งหมด ${sortedList.size} เกม"
    }
}