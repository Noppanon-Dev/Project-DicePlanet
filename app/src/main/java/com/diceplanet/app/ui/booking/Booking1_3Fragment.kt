package com.diceplanet.app.ui.booking

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diceplanet.app.R
import com.diceplanet.app.ui.boardgame.BoardGameData
import com.diceplanet.app.ui.boardgame.BoardgameAdapter

class Booking1_3Fragment : Fragment() {

    private lateinit var recyclerBoardgame: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var tvGameCount: TextView
    private lateinit var tvSort: TextView
    private lateinit var tvCategoryFilter: TextView
    private lateinit var categoryFilterContainer: LinearLayout
    private lateinit var categoryCheckboxContainer: LinearLayout
    private lateinit var tvClearCategory: TextView
    private lateinit var tvApplyCategory: TextView
    private lateinit var boardgameAdapter: BoardgameAdapter

    private var currentSort = "ความนิยม"

    private val selectedCategories = mutableSetOf<String>()

    private val allCategories = listOf(
        "Abstract",
        "Action",
        "Adventure",
        "Animals",
        "Bluffing",
        "Card Game",
        "City Building",
        "Civilization",
        "Deduction",
        "Dice",
        "Educational",
        "Economic",
        "Engine Building",
        "Exploration",
        "Family",
        "Fantasy",
        "Fighting",
        "Hand Management",
        "Horror",
        "Humor",
        "Medieval",
        "Mystery",
        "Mythology",
        "Negotiation",
        "Party Game",
        "Political",
        "Puzzle",
        "Racing",
        "Role Playing",
        "Science Fiction",
        "Set Collection",
        "Social Deduction",
        "Solo",
        "Sports",
        "Storytelling",
        "Strategy",
        "Survival",
        "Travel",
        "Warfare",
        "Word Game"
    )

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

        categoryFilterContainer =
            view.findViewById(R.id.categoryFilterContainer)

        categoryCheckboxContainer =
            view.findViewById(R.id.categoryCheckboxContainer)

        tvClearCategory =
            view.findViewById(R.id.tvClearCategory)

        tvApplyCategory =
            view.findViewById(R.id.tvApplyCategory)


        // =========================
        // RecyclerView
        // =========================

        recyclerBoardgame.layoutManager =
            LinearLayoutManager(requireContext())


        // =========================
        // ข้อมูลเกม
        // ใช้ข้อมูลชุดเดียวกับ BoardgameFragment
        // =========================

        val gameList =
            BoardGameData.gameList


        // =========================
        // Adapter
        // =========================

        boardgameAdapter =
            BoardgameAdapter(gameList) { game ->

                val bundle = Bundle().apply {

                    // ข้อมูลเกม
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

                    // =========================
                    // ข้อมูลจาก Booking
                    // =========================

                    putString(
                        "selectedDate",
                        arguments?.getString(
                            "selectedDate",
                            ""
                        )
                    )

                    putString(
                        "startTime",
                        arguments?.getString(
                            "startTime",
                            "20:00"
                        )
                    )

                    putString(
                        "endTime",
                        arguments?.getString(
                            "endTime",
                            "22:00"
                        )
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
                            "playMode",
                            "hourly"
                        )
                    )

                    putString(
                        "selectedTable",
                        arguments?.getString(
                            "selectedTable",
                            ""
                        ) ?: ""
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
        // สร้าง CheckBox หมวดหมู่
        // =========================

        setupCategoryCheckboxes()


        // =========================
        // แสดงเกมเริ่มต้น
        // =========================

        updateGameList(gameList)


        // =========================
        // เปิด / ปิด Filter
        // =========================

        tvCategoryFilter.setOnClickListener {

            if (categoryFilterContainer.visibility ==
                View.VISIBLE
            ) {

                categoryFilterContainer.visibility =
                    View.GONE

            } else {

                categoryFilterContainer.visibility =
                    View.VISIBLE
            }
        }


        // =========================
        // ล้างหมวดหมู่
        // =========================

        tvClearCategory.setOnClickListener {

            selectedCategories.clear()

            for (i in 0 until categoryCheckboxContainer.childCount) {

                val child =
                    categoryCheckboxContainer.getChildAt(i)

                if (child is CheckBox) {
                    child.isChecked = false
                }
            }

            updateGameList(gameList)
        }


        // =========================
        // เสร็จสิ้น
        // =========================

        tvApplyCategory.setOnClickListener {

            categoryFilterContainer.visibility =
                View.GONE

            updateGameList(gameList)
        }


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


    // =====================================================
    // สร้าง CheckBox หมวดหมู่
    // =====================================================

    private fun setupCategoryCheckboxes() {

        categoryCheckboxContainer.removeAllViews()

        for (category in allCategories) {

            val checkBox =
                CheckBox(requireContext())

            checkBox.text = category

            checkBox.textSize = 13f

            checkBox.setTextColor(
                resources.getColor(
                    android.R.color.black,
                    null
                )
            )

            checkBox.isChecked =
                selectedCategories.contains(category)

            checkBox.setOnCheckedChangeListener {
                    _, isChecked ->

                if (isChecked) {

                    selectedCategories.add(
                        category
                    )

                } else {

                    selectedCategories.remove(
                        category
                    )
                }

                updateGameList(
                    BoardGameData.gameList
                )
            }

            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

            params.setMargins(
                0,
                0,
                12,
                0
            )

            checkBox.layoutParams =
                params

            categoryCheckboxContainer
                .addView(checkBox)
        }
    }


    // =====================================================
    // กรอง + เรียงเกม
    // =====================================================

    private fun updateGameList(
        gameList: List<com.diceplanet.app.ui.boardgame.BoardGame>
    ) {

        val keyword =
            etSearch.text
                .toString()
                .trim()


        val filteredList =
            gameList.filter { game ->

                // =========================
                // Search
                // =========================

                val matchSearch =
                    keyword.isEmpty() ||
                            game.name.contains(
                                keyword,
                                ignoreCase = true
                            ) ||
                            game.categories.any {
                                it.contains(
                                    keyword,
                                    ignoreCase = true
                                )
                            }


                // =========================
                // Category
                //
                // AND
                //
                // Family + Puzzle
                // ต้องมีทั้ง Family และ Puzzle
                // =========================

                val matchCategory =
                    selectedCategories.isEmpty() ||
                            selectedCategories.all { category ->

                                game.categories.contains(
                                    category
                                )
                            }


                matchSearch &&
                        matchCategory
            }


        // =========================
        // Sort
        // =========================

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


        // =========================
        // อัปเดต RecyclerView
        // =========================

        boardgameAdapter.updateList(
            sortedList
        )


        // =========================
        // จำนวนเกม
        // =========================

        tvGameCount.text =
            "ทั้งหมด ${sortedList.size} เกม"
    }
}