package com.diceplanet.app.ui.booking

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
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

    @SuppressLint("SetTextI18n")
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

    @SuppressLint("UseKtx", "SetTextI18n")
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
            if (filteredBooking.playMode == "fullDay") {
                "เหมาวัน"
            } else {
                "${filteredBooking.startTime} - ${filteredBooking.endTime}"
            }

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

    @SuppressLint("SetTextI18n")
    private fun showBookingDetail(
        booking: BookingData
    ) {

        val dialog = Dialog(requireContext())

        dialog.setContentView(
            R.layout.dialog_booking_detail
        )

        dialog.window?.setBackgroundDrawableResource(
            android.R.color.transparent
        )

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val tvDialogBookingId =
            dialog.findViewById<TextView>(
                R.id.tvDialogBookingId
            )

        val tvDialogStatus =
            dialog.findViewById<TextView>(
                R.id.tvDialogStatus
            )

        val tvDialogDate =
            dialog.findViewById<TextView>(
                R.id.tvDialogDate
            )

        val tvDialogTime =
            dialog.findViewById<TextView>(
                R.id.tvDialogTime
            )

        val tvDialogTable =
            dialog.findViewById<TextView>(
                R.id.tvDialogTable
            )

        val tvDialogPlayers =
            dialog.findViewById<TextView>(
                R.id.tvDialogPlayers
            )

        val tvDialogGame =
            dialog.findViewById<TextView>(
                R.id.tvDialogGame
            )

        val tvDialogContactName =
            dialog.findViewById<TextView>(
                R.id.tvDialogContactName
            )

        val tvDialogContactPhone =
            dialog.findViewById<TextView>(
                R.id.tvDialogContactPhone
            )

        val btnDialogClose =
            dialog.findViewById<TextView>(
                R.id.btnDialogClose
            )

        // =========================
        // ข้อมูล
        // =========================

        tvDialogBookingId.text =
            booking.bookingId

        tvDialogStatus.text =
            booking.status

        tvDialogDate.text =
            formatDate(
                booking.selectedDate
            )

        tvDialogTime.text =
            if (booking.playMode == "fullDay") {
                "เหมาวัน"
            } else {
                "${booking.startTime} - ${booking.endTime}"
            }

        tvDialogTable.text =
            if (booking.selectedTable.startsWith("A")) {
                "ชั้น 1 - ${booking.selectedTable}"
            } else {
                "ชั้น 2 - ${booking.selectedTable}"
            }

        tvDialogPlayers.text =
            "${booking.playerCount} คน"

        tvDialogGame.text =
            if (booking.gameName.isNotEmpty()) {
                booking.gameName
            } else {
                "-"
            }

        tvDialogContactName.text =
            booking.contactName

        tvDialogContactPhone.text =
            booking.contactPhone

        // =========================
        // ปิด
        // =========================

        btnDialogClose.setOnClickListener {
            dialog.dismiss()
        }

        // =========================
        // แสดง Dialog
        // =========================

        dialog.show()

        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.88).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
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