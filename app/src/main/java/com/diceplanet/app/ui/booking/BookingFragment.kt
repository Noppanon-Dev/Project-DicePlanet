package com.diceplanet.app.ui.booking

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupMenu
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

        val tvBookingFilter =
            view.findViewById<TextView>(R.id.tvBookingFilter)

        tvBookingFilter.setOnClickListener {
            showBookingFilter(tvBookingFilter)
        }

        showCurrentBooking()
    }

    // =========================
    // FILTER
    // =========================

    private fun showBookingFilter(filterView: TextView) {

        val popupMenu = PopupMenu(
            requireContext(),
            filterView
        )

        popupMenu.menu.add("ทั้งหมด")
        popupMenu.menu.add("รอการยืนยัน")
        popupMenu.menu.add("ยืนยันแล้ว")
        popupMenu.menu.add("ยกเลิกแล้ว")

        popupMenu.setOnMenuItemClickListener { item ->

            filterView.text = "${item.title} ⌄"

            showCurrentBooking(
                item.title.toString()
            )

            true
        }

        popupMenu.show()
    }

    // =========================
    // SHOW BOOKING
    // =========================

    private fun showCurrentBooking(
        filter: String = "ทั้งหมด"
    ) {

        bookingContainer.removeAllViews()

        val booking = BookingManager.currentBooking

        val filteredBooking = when (filter) {

            "ทั้งหมด" -> booking

            else -> {
                if (booking?.status == filter) {
                    booking
                } else {
                    null
                }
            }
        }

        // ไม่มีการจอง
        if (filteredBooking == null) {

            val tvNoBooking =
                TextView(requireContext())

            tvNoBooking.text = "ยังไม่มีการจอง"
            tvNoBooking.textSize = 24f

            tvNoBooking.setTextColor(
                Color.parseColor("#777777")
            )

            tvNoBooking.setTypeface(
                null,
                Typeface.BOLD
            )

            tvNoBooking.gravity =
                Gravity.CENTER

            bookingContainer.addView(
                tvNoBooking
            )

            return
        }

        // =========================
        // BOOKING CARD
        // =========================

        val bookingView =
            LayoutInflater.from(requireContext())
                .inflate(
                    R.layout.item_booking,
                    bookingContainer,
                    false
                )

        val tvBookingId =
            bookingView.findViewById<TextView>(
                R.id.tvBookingId
            )

        val tvBookingStatus =
            bookingView.findViewById<TextView>(
                R.id.tvBookingStatus
            )

        val tvBookingDate =
            bookingView.findViewById<TextView>(
                R.id.tvBookingDate
            )

        val tvBookingTime =
            bookingView.findViewById<TextView>(
                R.id.tvBookingTime
            )

        val tvBookingTable =
            bookingView.findViewById<TextView>(
                R.id.tvBookingTable
            )

        val tvBookingGame =
            bookingView.findViewById<TextView>(
                R.id.tvBookingGame
            )

        val tvBookingPlayers =
            bookingView.findViewById<TextView>(
                R.id.tvBookingPlayers
            )

        val btnViewBooking =
            bookingView.findViewById<TextView>(
                R.id.btnViewBooking
            )

        val btnCancelBooking =
            bookingView.findViewById<TextView>(
                R.id.btnCancelBooking
            )

        // =========================
        // ใส่ข้อมูล
        // =========================

        tvBookingId.text =
            filteredBooking.bookingId

        tvBookingStatus.text =
            filteredBooking.status

        tvBookingDate.text =
            formatDate(
                filteredBooking.selectedDate
            )

        tvBookingTime.text =
            "${filteredBooking.startTime} - ${filteredBooking.endTime}"

        tvBookingTable.text =
            if (filteredBooking.selectedTable.startsWith("A")) {
                "ชั้น 1 - ${filteredBooking.selectedTable}"
            } else {
                "ชั้น 2 - ${filteredBooking.selectedTable}"
            }

        tvBookingGame.text =
            "เกม: ${filteredBooking.gameName}"

        tvBookingPlayers.text =
            "จำนวนผู้เล่น: ${filteredBooking.playerCount} คน"

        // =========================
        // ดูรายละเอียด
        // =========================

        btnViewBooking.setOnClickListener {

            showBookingDetail(
                filteredBooking
            )
        }

        // =========================
        // ยกเลิก
        // =========================

        btnCancelBooking.setOnClickListener {

            showCancelDialog()
        }

        bookingContainer.addView(
            bookingView
        )
    }

    // =========================
    // BOOKING DETAIL
    // =========================

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

    // =========================
    // CANCEL BOOKING
    // =========================

    private fun showCancelDialog() {

        AlertDialog.Builder(requireContext())
            .setTitle("ยกเลิกการจอง")
            .setMessage(
                "คุณต้องการยกเลิกการจองนี้ใช่หรือไม่?"
            )
            .setNegativeButton(
                "ไม่",
                null
            )
            .setPositiveButton(
                "ยืนยัน"
            ) { _, _ ->

                BookingManager.clearCurrentBooking()

                showCurrentBooking()
            }
            .show()
    }

    // =========================
    // FORMAT DATE
    // =========================

    private fun formatDate(
        date: String
    ): String {

        if (date.isEmpty()) {
            return "-"
        }

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