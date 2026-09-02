package com.diceplanet.app.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class Booking1_2Fragment : Fragment() {

    private var playerCount = 0
    private var playMode = "hourly"
    private var selectedTable: LinearLayout? = null

    private lateinit var floor1Container: LinearLayout
    private lateinit var floor2Container: LinearLayout
    private lateinit var tvPlayerCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // รับจำนวนผู้เล่นจาก Booking 1.1
        playerCount = arguments?.getInt("playerCount", 0) ?: 0
        playMode = arguments?.getString("playMode", "hourly") ?: "hourly"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_booking1_2,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        floor1Container = view.findViewById(R.id.floor1Container)
        floor2Container = view.findViewById(R.id.floor2Container)
        tvPlayerCount = view.findViewById(R.id.tvPlayerCount)

        // แสดงจำนวนผู้เล่น
        tvPlayerCount.text = "จำนวนผู้เล่น: $playerCount คน"

        // สร้างโต๊ะ
        createTables()

        // ปุ่มย้อนกลับ
        view.findViewById<View>(R.id.btnBack).setOnClickListener {

            val bundle = Bundle().apply {
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
            }

            parentFragmentManager.setFragmentResult(
                "booking1_2_result",
                bundle
            )

            findNavController().popBackStack()
        }
    }

    private fun createTables() {

        // ชั้น 1
        addTable(
            floor1Container,
            "A1",
            2,
            4,
            false
        )

        addTable(
            floor1Container,
            "A2",
            2,
            4,
            false
        )

        addTable(
            floor1Container,
            "A3",
            4,
            6,
            false
        )

        // ชั้น 2
        addTable(
            floor2Container,
            "B1",
            2,
            4,
            false
        )

        addTable(
            floor2Container,
            "B2",
            5,
            7,
            true
        )

        addTable(
            floor2Container,
            "B3",
            3,
            6,
            true
        )
    }

    private fun addTable(
        container: LinearLayout,
        tableName: String,
        minPlayers: Int,
        maxPlayers: Int,
        booked: Boolean
    ) {

        val tableView = LayoutInflater.from(requireContext())
            .inflate(
                R.layout.item_booking_table,
                container,
                false
            )

        val tableItem =
            tableView.findViewById<LinearLayout>(R.id.tableItem)

        val tvTableName =
            tableView.findViewById<TextView>(R.id.tvTableName)

        val tvTableCapacity =
            tableView.findViewById<TextView>(R.id.tvTableCapacity)

        tvTableName.text = tableName
        tvTableCapacity.text = "$minPlayers - $maxPlayers คน"

        // โต๊ะถูกจองแล้ว
        if (booked) {

            tableItem.setBackgroundResource(
                R.drawable.bg_status_booked
            )

            tableItem.isClickable = false
            tableItem.isEnabled = false
            tableItem.alpha = 1f

            tvTableName.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.white
                )
            )

            tvTableCapacity.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.white
                )
            )

        } else {

            // ตรวจจำนวนผู้เล่น
            val canSelect =
                playerCount in minPlayers..maxPlayers

            if (!canSelect) {

                // คนไม่ตรงกับจำนวนที่โต๊ะรองรับ
                tableItem.alpha = 0.35f
                tableItem.isClickable = false
                tableItem.isEnabled = false

            } else {

                // โต๊ะว่างและเลือกได้
                tableItem.setOnClickListener {

                    // ถ้ากดโต๊ะเดิมที่เลือกอยู่ → ยกเลิก
                    if (selectedTable == tableItem) {

                        tableItem.setBackgroundResource(
                            R.drawable.bg_status_available
                        )

                        selectedTable = null

                    } else {

                        // คืนสีโต๊ะเก่ากลับเป็นว่าง
                        selectedTable?.setBackgroundResource(
                            R.drawable.bg_status_available
                        )

                        // เลือกโต๊ะใหม่
                        tableItem.setBackgroundResource(
                            R.drawable.bg_status_selected
                        )

                        selectedTable = tableItem
                    }
                }
            }
        }

        container.addView(tableView)
    }
}