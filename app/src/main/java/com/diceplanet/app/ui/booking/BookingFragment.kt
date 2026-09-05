package com.diceplanet.app.ui.booking

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class BookingFragment : Fragment() {

    private lateinit var bookingContainer: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_booking,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        bookingContainer =
            view.findViewById(R.id.bookingContainer)

        val tvAddBooking =
            view.findViewById<TextView>(R.id.tvAddBooking)

        tvAddBooking.setOnClickListener {
            findNavController().navigate(R.id.booking1_1Fragment)
        }

        showCurrentBooking()
    }

    private fun showCurrentBooking() {

        bookingContainer.removeAllViews()

        val booking = BookingManager.currentBooking

        if (booking == null) {
            val tvNoBooking = TextView(requireContext())

            tvNoBooking.text = "ยังไม่มีการจอง"
            tvNoBooking.textSize = 24f
            tvNoBooking.setTextColor(
                android.graphics.Color.parseColor("#777777")
            )
            tvNoBooking.setTypeface(null, android.graphics.Typeface.BOLD)

            tvNoBooking.gravity = android.view.Gravity.CENTER

            bookingContainer.addView(tvNoBooking)

            return
        }

        val bookingView = LayoutInflater.from(requireContext())
            .inflate(
                R.layout.item_booking,
                bookingContainer,
                false
            )

        val tvBookingId =
            bookingView.findViewById<TextView>(R.id.tvBookingId)

        val tvBookingStatus =
            bookingView.findViewById<TextView>(R.id.tvBookingStatus)

        val tvBookingDate =
            bookingView.findViewById<TextView>(R.id.tvBookingDate)

        val tvBookingTime =
            bookingView.findViewById<TextView>(R.id.tvBookingTime)

        val tvBookingTable =
            bookingView.findViewById<TextView>(R.id.tvBookingTable)

        val tvBookingGame =
            bookingView.findViewById<TextView>(R.id.tvBookingGame)

        val tvBookingPlayers =
            bookingView.findViewById<TextView>(R.id.tvBookingPlayers)

        val btnViewBooking =
            bookingView.findViewById<TextView>(R.id.btnViewBooking)

        val btnCancelBooking =
            bookingView.findViewById<TextView>(R.id.btnCancelBooking)

        tvBookingId.text = booking.bookingId
        tvBookingStatus.text = booking.status

        tvBookingDate.text =
            formatDate(booking.selectedDate)

        tvBookingTime.text =
            "${booking.startTime} - ${booking.endTime}"

        tvBookingTable.text =
            if (booking.selectedTable.startsWith("A")) {
                "ชั้น 1 - ${booking.selectedTable}"
            } else {
                "ชั้น 2 - ${booking.selectedTable}"
            }

        tvBookingGame.text =
            "เกม: ${booking.gameName}"

        tvBookingPlayers.text =
            "จำนวนผู้เล่น: ${booking.playerCount} คน"

        btnViewBooking.setOnClickListener {
            showBookingDetail(booking)
        }

        btnCancelBooking.setOnClickListener {
            showCancelDialog()
        }

        bookingContainer.addView(bookingView)
    }

    private fun showBookingDetail(
        booking: BookingData
    ) {

        val message = """
            เลขที่การจอง: ${booking.bookingId}
            
            วันที่: ${formatDate(booking.selectedDate)}
            เวลา: ${booking.startTime} - ${booking.endTime}
            โต๊ะ: ${
            if (booking.selectedTable.startsWith("A")) {
                "ชั้น 1 - ${booking.selectedTable}"
            } else {
                "ชั้น 2 - ${booking.selectedTable}"
            }
        }
            จำนวนผู้เล่น: ${booking.playerCount} คน
            เกม: ${booking.gameName}
            
            ชื่อผู้จอง: ${booking.contactName}
            เบอร์โทร: ${booking.contactPhone}
            
            สถานะ: ${booking.status}
        """.trimIndent()

        AlertDialog.Builder(requireContext())
            .setTitle("รายละเอียดการจอง")
            .setMessage(message)
            .setPositiveButton("ปิด", null)
            .show()
    }

    private fun showCancelDialog() {

        AlertDialog.Builder(requireContext())
            .setTitle("ยกเลิกการจอง")
            .setMessage("คุณต้องการยกเลิกการจองนี้ใช่หรือไม่?")
            .setNegativeButton("ไม่", null)
            .setPositiveButton("ยืนยัน") { _, _ ->

                BookingManager.clearCurrentBooking()

                showCurrentBooking()
            }
            .show()
    }

    private fun formatDate(date: String): String {

        if (date.isEmpty()) return "-"

        val parts = date.split("/")

        if (parts.size != 3) {
            return date
        }

        val monthName = when (parts[1]) {
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
            else -> parts[1]
        }

        return "${parts[0]} $monthName ${parts[2]}"
    }
}