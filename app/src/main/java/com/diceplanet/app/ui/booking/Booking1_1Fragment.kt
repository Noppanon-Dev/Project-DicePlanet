package com.diceplanet.app.ui.booking

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.graphics.Color

import android.widget.Button
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.diceplanet.app.R


class Booking1_1Fragment : Fragment() {

    // =====================================================
    // View
    // =====================================================

    private lateinit var calendarView: CalendarView

    private lateinit var tvSelectedDate: TextView
    private lateinit var tvSelectedTime: TextView
    private lateinit var tvPlayerCount: TextView

    private lateinit var btnDecreasePlayer: Button
    private lateinit var btnIncreasePlayer: Button
    private lateinit var btnNext: Button
    private lateinit var btnBack: ImageButton

    private lateinit var btnHourly: LinearLayout
    private lateinit var btnFullDay: LinearLayout

    private lateinit var hourlySection: LinearLayout
    private lateinit var fullDaySection: LinearLayout

    private lateinit var btnStartTime: Button
    private lateinit var btnEndTime: Button

    // =====================================================
    // Icon
    // =====================================================

    private lateinit var ivHourly: ImageView
    private lateinit var ivFullDay: ImageView
    private lateinit var ivFullDaySection: ImageView
    private lateinit var ivPriceTag: ImageView

    // =====================================================
    // ค่า
    // =====================================================

    private var playerCount = 0

    // เวลาเริ่มต้น
    private var startTime = "20:00"

    // เวลาสิ้นสุด
    private var endTime = "22:00"


    // =====================================================
    // สร้างหน้า
    // =====================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(
            R.layout.fragment_booking1_1,
            container,
            false
        )
    }


    // =====================================================
    // เมื่อ View พร้อมใช้งาน
    // =====================================================

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)


        // =====================================================
        // เชื่อม View
        // =====================================================

        calendarView =
            view.findViewById(R.id.calendarView)

        tvSelectedDate =
            view.findViewById(R.id.tvSelectedDate)

        tvSelectedTime =
            view.findViewById(R.id.tvSelectedTime)

        tvPlayerCount =
            view.findViewById(R.id.tvPlayerCount)


        btnDecreasePlayer =
            view.findViewById(R.id.btnDecreasePlayer)

        btnIncreasePlayer =
            view.findViewById(R.id.btnIncreasePlayer)

        btnNext =
            view.findViewById(R.id.btnNext)

        btnBack =
            view.findViewById(R.id.btnBack)


        btnHourly =
            view.findViewById(R.id.btnHourly)

        btnFullDay =
            view.findViewById(R.id.btnFullDay)


        hourlySection =
            view.findViewById(R.id.hourlySection)

        fullDaySection =
            view.findViewById(R.id.fullDaySection)


        btnStartTime =
            view.findViewById(R.id.btnStartTime)

        btnEndTime =
            view.findViewById(R.id.btnEndTime)


        // =====================================================
        // เชื่อม Icon
        // =====================================================

        ivHourly =
            view.findViewById(R.id.ivHourly)

        ivFullDay =
            view.findViewById(R.id.ivFullDay)

        ivFullDaySection =
            view.findViewById(R.id.ivFullDaySection)

        ivPriceTag =
            view.findViewById(R.id.ivPriceTag)


        // =====================================================
        // สี Icon ของ Section เหมาวัน
        // =====================================================

        ivFullDaySection.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.yellow
            )
        )


        // =====================================================
        // สี Icon ราคา
        // =====================================================

        ivPriceTag.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.red
            )
        )


        // =====================================================
        // เลือกวันที่
        // =====================================================

        calendarView.setOnDateChangeListener {
                _,
                year,
                month,
                dayOfMonth ->

            tvSelectedDate.text =
                "$dayOfMonth/${month + 1}/$year"
        }


        // =====================================================
        // เลือก "รายชั่วโมง"
        // =====================================================

        btnHourly.setOnClickListener {

            // แสดง Section รายชั่วโมง
            hourlySection.visibility =
                View.VISIBLE

            // ซ่อน Section เหมาวัน
            fullDaySection.visibility =
                View.GONE


            // อัปเดตเวลา
            updateSelectedTime()


            // Background ปุ่ม
            btnHourly.setBackgroundResource(
                R.drawable.bg_booking_option_selected
            )

            btnFullDay.setBackgroundResource(
                R.drawable.bg_booking_option
            )


            // =================================================
            // สี Icon
            // รายชั่วโมง = Purple
            // เหมาวัน = ดำ
            // =================================================

            ivHourly.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.purple
                )
            )

            ivFullDay.setColorFilter(
                Color.BLACK
            )
        }


        // =====================================================
        // เลือก "เหมาวัน"
        // =====================================================

        btnFullDay.setOnClickListener {

            // ซ่อน Section รายชั่วโมง
            hourlySection.visibility =
                View.GONE

            // แสดง Section เหมาวัน
            fullDaySection.visibility =
                View.VISIBLE


            // เปลี่ยนข้อความ
            tvSelectedTime.text =
                "เวลาที่เลือกคือ เหมาทั้งวัน"


            // Background ปุ่ม
            btnFullDay.setBackgroundResource(
                R.drawable.bg_booking_option_selected
            )

            btnHourly.setBackgroundResource(
                R.drawable.bg_booking_option
            )


            // =================================================
            // สี Icon
            // รายชั่วโมง = ดำ
            // เหมาวัน = Yellow
            // =================================================

            ivHourly.setColorFilter(
                Color.BLACK
            )

            ivFullDay.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.yellow
                )
            )
        }


        // =====================================================
        // เลือกเวลาเริ่มต้น
        // =====================================================

        btnStartTime.setOnClickListener {

            val startTimes = arrayOf(
                "15:00",
                "16:00",
                "17:00",
                "18:00",
                "19:00",
                "20:00",
                "21:00"
            )


            AlertDialog.Builder(requireContext())

                .setTitle("เลือกเวลาเริ่มต้น")

                .setItems(startTimes) { _, which ->

                    // เก็บเวลาใหม่
                    startTime =
                        startTimes[which]


                    // แสดงบนปุ่ม
                    btnStartTime.text =
                        startTime


                    // -----------------------------------------
                    // ตรวจสอบเวลาสิ้นสุด
                    // -----------------------------------------

                    val startHour =
                        startTime
                            .substringBefore(":")
                            .toInt()


                    val endHour =
                        endTime
                            .substringBefore(":")
                            .toInt()


                    // ถ้าเวลาสิ้นสุด
                    // น้อยกว่าหรือเท่ากับเวลาเริ่ม
                    // ให้ปรับเป็นเวลาถัดไป

                    if (endHour <= startHour) {

                        endTime =
                            String.format(
                                "%02d:00",
                                startHour + 1
                            )


                        btnEndTime.text =
                            endTime
                    }


                    // อัปเดตข้อความ
                    updateSelectedTime()
                }

                .show()
        }


        // =====================================================
        // เลือกเวลาสิ้นสุด
        // =====================================================

        btnEndTime.setOnClickListener {

            val startHour =
                startTime
                    .substringBefore(":")
                    .toInt()


            // รายการเวลาที่เลือกได้
            val endTimes =
                mutableListOf<String>()


            // เวลาสิ้นสุดต้องมากกว่าเวลาเริ่ม
            // อย่างน้อย 1 ชั่วโมง
            //
            // ร้านปิด 22:00

            for (
            hour in (startHour + 1)..22
            ) {

                endTimes.add(
                    String.format(
                        "%02d:00",
                        hour
                    )
                )
            }


            AlertDialog.Builder(requireContext())

                .setTitle("เลือกเวลาสิ้นสุด")

                .setItems(
                    endTimes.toTypedArray()
                ) { _, which ->

                    endTime =
                        endTimes[which]


                    btnEndTime.text =
                        endTime


                    updateSelectedTime()
                }

                .show()
        }


        // =====================================================
        // ลดจำนวนผู้เล่น
        // =====================================================

        btnDecreasePlayer.setOnClickListener {

            // ห้ามต่ำกว่า 0

            if (playerCount > 0) {

                playerCount--
            }


            updatePlayerCount()
        }


        // =====================================================
        // เพิ่มจำนวนผู้เล่น
        // =====================================================

        btnIncreasePlayer.setOnClickListener {

            playerCount++


            updatePlayerCount()
        }


        // =====================================================
        // ปุ่มย้อนกลับ
        // =====================================================

        btnBack.setOnClickListener {

            findNavController()
                .navigateUp()
        }


        // =====================================================
        // ปุ่มถัดไป
        // =====================================================

        btnNext.setOnClickListener {

            /*
             * ตอนนี้ยังไม่เชื่อม Booking 1.2
             *
             * ข้อมูลที่จะส่งต่อ:
             *
             * selectedDate
             * startTime
             * endTime
             * playerCount
             * รูปแบบการเล่น
             */

            // TODO:
            //
            // findNavController().navigate(
            //     R.id.action_booking1_1_to_booking1_2
            // )
        }


        // =====================================================
        // ค่าเริ่มต้น
        // =====================================================

        tvPlayerCount.text =
            "0"


        btnStartTime.text =
            startTime


        btnEndTime.text =
            endTime


        // =====================================================
        // เริ่มต้นเป็น "รายชั่วโมง"
        // =====================================================

        hourlySection.visibility =
            View.VISIBLE

        fullDaySection.visibility =
            View.GONE


        tvSelectedTime.visibility =
            View.VISIBLE


        btnHourly.setBackgroundResource(
            R.drawable.bg_booking_option_selected
        )

        btnFullDay.setBackgroundResource(
            R.drawable.bg_booking_option
        )


        // =====================================================
        // สี Icon ตอนเปิดหน้าครั้งแรก
        // =====================================================

        ivHourly.setColorFilter(
            ContextCompat.getColor(
                requireContext(),
                R.color.purple
            )
        )

        ivFullDay.setColorFilter(
            Color.BLACK
        )


        // อัปเดตเวลา
        updateSelectedTime()
    }


    // =====================================================
    // อัปเดตข้อความเวลาที่เลือก
    // =====================================================

    private fun updateSelectedTime() {

        tvSelectedTime.text =
            "เวลาที่เลือกคือ $startTime - $endTime"
    }


    // =====================================================
    // อัปเดตจำนวนผู้เล่น
    // =====================================================

    private fun updatePlayerCount() {

        // ป้องกันค่าติดลบ

        if (playerCount < 0) {

            playerCount = 0
        }


        tvPlayerCount.text =
            playerCount.toString()
    }
}