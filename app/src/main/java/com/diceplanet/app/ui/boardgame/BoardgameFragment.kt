package com.diceplanet.app.ui.boardgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diceplanet.app.R
import androidx.navigation.fragment.findNavController

// Fragment สำหรับหน้า Board Game
class BoardgameFragment : Fragment() {

    private lateinit var recyclerBoardgame: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var tvGameCount: TextView
    private lateinit var tvSort: TextView
    private lateinit var tvCategoryFilter: TextView
    private lateinit var boardgameAdapter: BoardgameAdapter

    // รูปแบบการเรียงเริ่มต้น
    private var currentCategory = "ทั้งหมด"
    private var currentSort = "ความนิยม"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_boardgame,
            container,
            false
        )
    }

    // กรอง + เรียงรายการเกม
    private fun updateGameList(gameList: List<BoardGame>) {

        // คำที่ค้นหา
        val keyword = etSearch.text.toString().trim()

        // กรองเกมจากชื่อ
        val filteredList = gameList.filter { game ->

            val matchSearch =
                keyword.isEmpty() ||
                        game.name.contains(
                            keyword,
                            ignoreCase = true
                        )

            val matchCategory =
                currentCategory == "ทั้งหมด" ||
                        game.category == currentCategory

            matchSearch && matchCategory
        }

        // เรียงตามตัวเลือก
        val sortedList = when (currentSort) {

            // ความนิยม
            "ความนิยม" -> {
                filteredList.sortedByDescending {
                    it.popularity
                }
            }

            // A-Z
            "A-Z" -> {
                filteredList.sortedBy {
                    it.name
                }
            }

            // Z-A
            "Z-A" -> {
                filteredList.sortedByDescending {
                    it.name
                }
            }

            // มาใหม่
            "มาใหม่" -> {
                filteredList.sortedByDescending {
                    it.isNew
                }
            }

            else -> filteredList
        }

        // อัปเดตรายการ Card
        boardgameAdapter.updateList(sortedList)

        // อัปเดตจำนวนเกม
        tvGameCount.text = "ทั้งหมด ${sortedList.size} เกม"
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // เชื่อม View จาก XML
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

        // RecyclerView เรียง Card แนวตั้ง
        recyclerBoardgame.layoutManager =
            LinearLayoutManager(requireContext())

        // ข้อมูลเกมตัวอย่าง
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

        // สร้าง Adapter
        boardgameAdapter = BoardgameAdapter(gameList) { game ->

            val bundle = Bundle().apply {

                putString("name", game.name)

                putString("category", game.category)

                putString("players", game.players)

                putString("playTime", game.playTime)

                putString("description", game.description)

                putInt("imageResId", game.imageResId)
            }

            findNavController().navigate(
                R.id.boardgameDetailFragment,
                bundle
            )
        }

        // เชื่อม Adapter กับ RecyclerView
        recyclerBoardgame.adapter =
            boardgameAdapter

        // แสดงรายการเริ่มต้นตามความนิยม
        updateGameList(gameList)

        // =========================
        // ระบบ Sort
        // =========================

        tvSort.setOnClickListener {

            val popup =
                PopupMenu(requireContext(), tvSort)

            // ตัวเลือกการเรียง
            popup.menu.add("ความนิยม")
            popup.menu.add("A-Z")
            popup.menu.add("Z-A")
            popup.menu.add("มาใหม่")

            // เมื่อเลือก Sort
            popup.setOnMenuItemClickListener { item ->

                currentSort =
                    item.title.toString()

                // เปลี่ยนข้อความปุ่ม
                tvSort.text =
                    "${currentSort}  ▾"

                // กรอง + เรียงใหม่
                updateGameList(gameList)

                true
            }

            popup.show()
        }

        tvCategoryFilter.setOnClickListener {

            val popup = PopupMenu(
                requireContext(),
                tvCategoryFilter
            )

            popup.menu.add("ทั้งหมด")
            popup.menu.add("ปาร์ตี้")
            popup.menu.add("วางแผน")
            popup.menu.add("เล่าเรื่อง")

            popup.setOnMenuItemClickListener { item ->

                currentCategory = item.title.toString()

                tvCategoryFilter.text = "☷"

                updateGameList(gameList)

                true
            }

            popup.show()
        }

        // =========================
        // ระบบ Search
        // =========================

        etSearch.addTextChangedListener(
            object : android.text.TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // ไม่ต้องทำอะไร
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    // กรอง + เรียงใหม่
                    updateGameList(gameList)
                }

                override fun afterTextChanged(
                    s: android.text.Editable?
                ) {
                    // ไม่ต้องทำอะไร
                }
            }
        )
    }
}