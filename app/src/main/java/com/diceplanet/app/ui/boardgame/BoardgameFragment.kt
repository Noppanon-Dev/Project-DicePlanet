package com.diceplanet.app.ui.boardgame

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

class BoardgameFragment : Fragment() {

    // =========================
    // View
    // =========================

    private lateinit var recyclerBoardgame: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var tvGameCount: TextView
    private lateinit var tvSort: TextView
    private lateinit var tvCategoryFilter: TextView
    private lateinit var boardgameAdapter: BoardgameAdapter


    // =========================
    // Filter UI
    // =========================

    private lateinit var categoryFilterContainer: LinearLayout
    private lateinit var categoryCheckboxContainer: LinearLayout
    private lateinit var tvClearCategory: TextView
    private lateinit var tvApplyCategory: TextView


    // =========================
    // หมวดหมู่ที่เลือก
    // =========================

    private val selectedCategories =
        mutableSetOf<String>()


    // =========================
    // หมวดหมู่หลัก
    // =========================

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


    // =========================
    // รูปแบบการเรียง
    // =========================

    private var currentSort = "ความนิยม"


    // =========================
    // สร้าง View
    // =========================

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


    // =========================
    // หลังสร้าง View
    // =========================

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )


        // =========================
        // เชื่อม View
        // =========================

        recyclerBoardgame =
            view.findViewById(
                R.id.recyclerBoardgame
            )

        etSearch =
            view.findViewById(
                R.id.etSearch
            )

        tvGameCount =
            view.findViewById(
                R.id.tvGameCount
            )

        tvSort =
            view.findViewById(
                R.id.tvSort
            )

        tvCategoryFilter =
            view.findViewById(
                R.id.tvCategoryFilter
            )

        categoryFilterContainer =
            view.findViewById(
                R.id.categoryFilterContainer
            )

        categoryCheckboxContainer =
            view.findViewById(
                R.id.categoryCheckboxContainer
            )

        tvClearCategory =
            view.findViewById(
                R.id.tvClearCategory
            )

        tvApplyCategory =
            view.findViewById(
                R.id.tvApplyCategory
            )


        // =========================
        // RecyclerView
        // =========================

        recyclerBoardgame.layoutManager =
            LinearLayoutManager(
                requireContext()
            )


        // =========================
        // รับ Category จากหน้า Home
        // =========================

        val categoryFromHome =
            arguments?.getString(
                "category"
            )

        if (!categoryFromHome.isNullOrEmpty()) {

            selectedCategories.add(
                categoryFromHome
            )
        }


        // =========================
        // ข้อมูลเกม
        // =========================

        val gameList =
            BoardGameData.gameList


        // =========================
        // Adapter
        // =========================

        boardgameAdapter =
            BoardgameAdapter(gameList) { game ->

                val bundle =
                    Bundle().apply {

                        putString(
                            "name",
                            game.name
                        )

                        putString(
                            "category",
                            game.categories.joinToString(
                                " • "
                            )
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


        recyclerBoardgame.adapter =
            boardgameAdapter


        // =========================
        // สร้าง Checkbox
        // =========================

        setupCategoryCheckboxes()


        // =========================
        // แสดงเกมตอนเริ่ม
        // =========================

        updateGameList(gameList)


        // =========================
        // ปุ่ม Filter
        // =========================

        tvCategoryFilter.setOnClickListener {

            if (
                categoryFilterContainer.visibility
                == View.VISIBLE
            ) {

                categoryFilterContainer.visibility =
                    View.GONE

            } else {

                categoryFilterContainer.visibility =
                    View.VISIBLE
            }
        }


        // =========================
        // ล้าง Category
        // =========================

        tvClearCategory.setOnClickListener {

            selectedCategories.clear()


            for (
            i in 0 until
                    categoryCheckboxContainer.childCount
            ) {

                val checkBox =
                    categoryCheckboxContainer
                        .getChildAt(i) as CheckBox

                checkBox.isChecked =
                    false
            }


            updateGameList(gameList)
        }


        // =========================
        // เสร็จสิ้น
        // =========================

        tvApplyCategory.setOnClickListener {

            updateGameList(gameList)

            categoryFilterContainer.visibility =
                View.GONE
        }


        // =========================
        // ระบบ Sort
        // =========================

        tvSort.setOnClickListener {

            val popup =
                PopupMenu(
                    requireContext(),
                    tvSort
                )


            popup.menu.add(
                "ความนิยม"
            )

            popup.menu.add(
                "A-Z"
            )

            popup.menu.add(
                "Z-A"
            )

            popup.menu.add(
                "มาใหม่"
            )


            popup.setOnMenuItemClickListener { item ->

                currentSort =
                    item.title.toString()

                tvSort.text =
                    "${currentSort}  ▾"


                updateGameList(
                    gameList
                )

                true
            }


            popup.show()
        }


        // =========================
        // ระบบ Search
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

                    updateGameList(
                        gameList
                    )
                }


                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )
    }


    // =========================
    // สร้าง Checkbox หมวดหมู่
    // =========================

    private fun setupCategoryCheckboxes() {

        categoryCheckboxContainer
            .removeAllViews()


        for (
        category in allCategories
        ) {

            val checkBox =
                CheckBox(
                    requireContext()
                )


            checkBox.text =
                category

            checkBox.textSize =
                13f


            // ถ้ามาจาก Home
            // ให้ Checkbox ถูกเลือกไว้
            checkBox.isChecked =
                selectedCategories
                    .contains(category)


            checkBox.setOnCheckedChangeListener {
                    _,
                    isChecked ->

                if (isChecked) {

                    selectedCategories.add(
                        category
                    )

                } else {

                    selectedCategories.remove(
                        category
                    )
                }
            }


            // ระยะห่างแต่ละ Checkbox
            val params =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )


            params.marginEnd =
                8


            checkBox.layoutParams =
                params


            categoryCheckboxContainer.addView(
                checkBox
            )
        }
    }


    // =========================
    // กรอง + เรียงเกม
    // =========================

    private fun updateGameList(
        gameList: List<BoardGame>
    ) {

        // =========================
        // Search
        // =========================

        val keyword =
            etSearch.text
                .toString()
                .trim()


        // =========================
        // Filter
        // =========================

        val filteredList =
            gameList.filter { game ->


                // Search
                val matchSearch =
                    keyword.isEmpty() ||
                            game.name.contains(
                                keyword,
                                ignoreCase = true
                            )


                // Category แบบ AND
                val matchCategory =
                    selectedCategories.isEmpty() ||
                            selectedCategories.all {
                                    category ->

                                game.categories
                                    .contains(
                                        category
                                    )
                            }


                // Search และ Category
                matchSearch &&
                        matchCategory
            }


        // =========================
        // Sort
        // =========================

        val sortedList =
            when (currentSort) {

                // ความนิยม
                "ความนิยม" ->

                    filteredList
                        .sortedByDescending {
                            it.popularity
                        }


                // A-Z
                "A-Z" ->

                    filteredList
                        .sortedBy {
                            it.name
                        }


                // Z-A
                "Z-A" ->

                    filteredList
                        .sortedByDescending {
                            it.name
                        }


                // มาใหม่
                "มาใหม่" ->

                    filteredList
                        .sortedByDescending {
                            it.isNew
                        }


                else ->
                    filteredList
            }


        // =========================
        // อัปเดตรายการ
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