package com.diceplanet.app.ui.booking

import android.annotation.SuppressLint
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

        // =========================
        // รับข้อมูลจาก Booking 1.1
        // =========================

        playerCount =
            arguments?.getInt("playerCount", 0) ?: 0

        playMode =
            arguments?.getString("playMode") ?: "hourly"
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        // =========================
        // Find Views
        // =========================

        floor1Container =
            view.findViewById(R.id.floor1Container)

        floor2Container =
            view.findViewById(R.id.floor2Container)

        tvPlayerCount =
            view.findViewById(R.id.tvPlayerCount)

        // =========================
        // แสดงจำนวนผู้เล่น
        // =========================

        tvPlayerCount.text =
            "จำนวนผู้เล่น: $playerCount คน"

        // =========================
        // สร้างโต๊ะ
        // =========================

        createTables()

        // =========================
        // ปุ่มย้อนกลับ
        // =========================

        view.findViewById<View>(R.id.btnBack)
            .setOnClickListener {

                val bundle =
                    createBookingBundle()

                parentFragmentManager.setFragmentResult(
                    "booking1_2_result",
                    bundle
                )

                findNavController()
                    .popBackStack()
            }

        // =========================
        // ปุ่มถัดไป
        // =========================

        view.findViewById<View>(R.id.btnNext)
            .setOnClickListener {

                // ต้องเลือกโต๊ะก่อน
                if (selectedTable == null) {
                    return@setOnClickListener
                }

                val bundle =
                    createBookingBundle()

                findNavController().navigate(
                    R.id.booking1_3Fragment,
                    bundle
                )
            }
    }

    // =========================================================
    // สร้าง Bundle สำหรับ Booking
    // =========================================================

    private fun createBookingBundle(): Bundle {

        return Bundle().apply {

            // วันที่
            putString(
                "selectedDate",
                arguments?.getString(
                    "selectedDate"
                ) ?: ""
            )

            // เวลาเริ่ม
            putString(
                "startTime",
                arguments?.getString(
                    "startTime"
                ) ?: ""
            )

            // เวลาสิ้นสุด
            putString(
                "endTime",
                arguments?.getString(
                    "endTime"
                ) ?: ""
            )

            // จำนวนผู้เล่น
            putInt(
                "playerCount",
                playerCount
            )

            // รูปแบบการเล่น
            putString(
                "playMode",
                playMode
            )

            // โต๊ะที่เลือก
            putString(
                "selectedTable",
                selectedTable
                    ?.findViewById<TextView>(
                        R.id.tvTableName
                    )
                    ?.text
                    ?.toString()
                    ?: ""
            )
        }
    }

    // =========================================================
    // สร้างโต๊ะ
    // =========================================================

    private fun createTables() {

        // =========================
        // ชั้น 1
        // =========================

        addTable(
            container = floor1Container,
            tableName = "A1",
            minPlayers = 2,
            maxPlayers = 4,
            booked = false
        )

        addTable(
            container = floor1Container,
            tableName = "A2",
            minPlayers = 2,
            maxPlayers = 4,
            booked = false
        )

        addTable(
            container = floor1Container,
            tableName = "A3",
            minPlayers = 4,
            maxPlayers = 6,
            booked = false
        )

        // =========================
        // ชั้น 2
        // =========================

        addTable(
            container = floor2Container,
            tableName = "B1",
            minPlayers = 2,
            maxPlayers = 4,
            booked = false
        )

        addTable(
            container = floor2Container,
            tableName = "B2",
            minPlayers = 5,
            maxPlayers = 7,
            booked = true
        )

        addTable(
            container = floor2Container,
            tableName = "B3",
            minPlayers = 3,
            maxPlayers = 6,
            booked = true
        )
    }

    // =========================================================
    // เพิ่มโต๊ะ
    // =========================================================

    @SuppressLint("SetTextI18n")
    private fun addTable(
        container: LinearLayout,
        tableName: String,
        minPlayers: Int,
        maxPlayers: Int,
        booked: Boolean
    ) {

        val tableView =
            LayoutInflater.from(requireContext())
                .inflate(
                    R.layout.item_booking_table,
                    container,
                    false
                )

        val tableItem =
            tableView.findViewById<LinearLayout>(
                R.id.tableItem
            )

        val tvTableName =
            tableView.findViewById<TextView>(
                R.id.tvTableName
            )

        val tvTableCapacity =
            tableView.findViewById<TextView>(
                R.id.tvTableCapacity
            )

        // =========================
        // ข้อมูลโต๊ะ
        // =========================

        tvTableName.text =
            tableName

        tvTableCapacity.text =
            "$minPlayers - $maxPlayers คน"

        // =========================
        // โต๊ะถูกจองแล้ว
        // =========================

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

            // =========================
            // ตรวจจำนวนผู้เล่น
            // =========================

            val canSelect =
                playerCount in minPlayers..maxPlayers

            if (!canSelect) {

                // จำนวนผู้เล่นไม่ตรงกับโต๊ะ
                tableItem.alpha = 0.35f
                tableItem.isClickable = false
                tableItem.isEnabled = false

            } else {

                // =========================
                // โต๊ะว่างและเลือกได้
                // =========================

                tableItem.setOnClickListener {

                    // -------------------------
                    // กดโต๊ะเดิม = ยกเลิก
                    // -------------------------

                    if (selectedTable == tableItem) {

                        tableItem.setBackgroundResource(
                            R.drawable.bg_status_available
                        )

                        selectedTable = null

                    } else {

                        // -------------------------
                        // คืนโต๊ะเก่า
                        // -------------------------

                        selectedTable
                            ?.setBackgroundResource(
                                R.drawable.bg_status_available
                            )

                        // -------------------------
                        // เลือกโต๊ะใหม่
                        // -------------------------

                        tableItem.setBackgroundResource(
                            R.drawable.bg_status_selected
                        )

                        selectedTable =
                            tableItem
                    }
                }
            }
        }

        // =========================
        // เพิ่มโต๊ะเข้า Container
        // =========================

        container.addView(
            tableView
        )
    }
}