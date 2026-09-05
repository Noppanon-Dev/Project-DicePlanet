package com.diceplanet.app.ui.booking

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class Booking1_4Fragment : Fragment() {

    private var selectedDate = ""
    private var startTime = ""
    private var endTime = ""
    private var selectedTable = ""
    private var playerCount = 0
    private var gameName = ""

    private var contactName = "สุดหล่อ ต่อเติม"
    private var contactPhone = "081-222-3333"

    private lateinit var tvSelectedDate: TextView
    private lateinit var tvSelectedTime: TextView
    private lateinit var tvSelectedTable: TextView
    private lateinit var tvPlayerCount: TextView
    private lateinit var tvSelectedGame: TextView
    private lateinit var tvContactName: TextView
    private lateinit var tvContactPhone: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedDate =
            arguments?.getString("selectedDate", "") ?: ""

        startTime =
            arguments?.getString("startTime", "") ?: ""

        endTime =
            arguments?.getString("endTime", "") ?: ""

        selectedTable =
            arguments?.getString("selectedTable", "") ?: ""

        playerCount =
            arguments?.getInt("playerCount", 0) ?: 0

        gameName =
            arguments?.getString("name", "") ?: ""

        // ข้อมูลจำลอง
        // อนาคตเปลี่ยนตรงนี้ให้ดึงข้อมูลจากบัญชีที่ Login ได้
        loadContactInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_booking1_4,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        tvSelectedDate =
            view.findViewById(R.id.tvSelectedDate)

        tvSelectedTime =
            view.findViewById(R.id.tvSelectedTime)

        tvSelectedTable =
            view.findViewById(R.id.tvSelectedTable)

        tvPlayerCount =
            view.findViewById(R.id.tvPlayerCount)

        tvSelectedGame =
            view.findViewById(R.id.tvSelectedGame)

        tvContactName =
            view.findViewById(R.id.tvContactName)

        tvContactPhone =
            view.findViewById(R.id.tvContactPhone)

        tvSelectedDate.text =
            formatDate(selectedDate)

        tvSelectedTime.text =
            "$startTime - $endTime"

        tvSelectedTable.text =
            if (selectedTable.startsWith("A")) {
                "ชั้น 1 - $selectedTable"
            } else {
                "ชั้น 2 - $selectedTable"
            }

        tvPlayerCount.text =
            "$playerCount คน"

        tvSelectedGame.text =
            gameName

        updateContactUI()

        view.findViewById<View>(R.id.btnBack)
            .setOnClickListener {
                findNavController().navigateUp()
            }

        view.findViewById<TextView>(R.id.btnEditContact)
            .setOnClickListener {
                showEditContactDialog()
            }

        view.findViewById<View>(R.id.btnConfirmBooking)
            .setOnClickListener {

                BookingManager.createBooking(
                    selectedDate = selectedDate,
                    startTime = startTime,
                    endTime = endTime,
                    selectedTable = selectedTable,
                    playerCount = playerCount,
                    gameName = gameName,
                    contactName = contactName,
                    contactPhone = contactPhone
                )

                findNavController().navigate(
                    R.id.booking1_5Fragment
                )
            }
    }

    private fun loadContactInfo() {
        // ตอนนี้ใช้ข้อมูลจำลอง
        // อนาคตสามารถเปลี่ยนเป็นข้อมูลจาก User ที่ Login ได้
    }

    private fun updateContactUI() {
        tvContactName.text = contactName
        tvContactPhone.text = contactPhone
    }

    private fun showEditContactDialog() {

        val layout = LinearLayout(requireContext())

        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 0, 50, 0)

        val nameInput = EditText(requireContext())
        nameInput.hint = "ชื่อ"
        nameInput.setText(contactName)

        val phoneInput = EditText(requireContext())
        phoneInput.hint = "เบอร์โทร"
        phoneInput.setText(contactPhone)
        phoneInput.inputType =
            InputType.TYPE_CLASS_PHONE

        layout.addView(nameInput)
        layout.addView(phoneInput)

        AlertDialog.Builder(requireContext())
            .setTitle("แก้ไขข้อมูลติดต่อ")
            .setView(layout)
            .setNegativeButton("ยกเลิก", null)
            .setPositiveButton("บันทึก") { _, _ ->

                contactName =
                    nameInput.text.toString()

                contactPhone =
                    phoneInput.text.toString()

                updateContactUI()
            }
            .show()
    }

    private fun formatDate(date: String): String {

        if (date.isEmpty()) {
            return "-"
        }

        val parts = date.split("/")

        if (parts.size != 3) {
            return date
        }

        val day = parts[0]
        val month = parts[1]
        val year = parts[2]

        val monthName = when (month) {
            "01" -> "มกราคม"
            "02" -> "กุมภาพันธ์"
            "03" -> "มีนาคม"
            "04" -> "เมษายน"
            "05" -> "พฤษภาคม"
            "06" -> "มิถุนายน"
            "07" -> "กรกฎาคม"
            "08" -> "สิงหาคม"
            "09" -> "กันยายน"
            "10" -> "ตุลาคม"
            "11" -> "พฤศจิกายน"
            "12" -> "ธันวาคม"
            else -> month
        }

        return "$day $monthName $year"
    }
}