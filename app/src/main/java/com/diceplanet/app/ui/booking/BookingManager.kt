package com.diceplanet.app.ui.booking

import kotlin.random.Random

object BookingManager {

    // =========================================================
    // Booking ปัจจุบัน
    // =========================================================

    var currentBooking: BookingData? = null
        private set

    // เก็บ Booking ID ที่เคยสร้างใน Session นี้
    private val usedBookingIds =
        mutableSetOf<String>()

    // =========================================================
    // สร้าง Booking
    // =========================================================

    fun createBooking(
        selectedDate: String,
        startTime: String,
        endTime: String,
        selectedTable: String,
        playerCount: Int,
        gameName: String,
        contactName: String,
        contactPhone: String,
        playMode: String
    ): BookingData {

        // สร้าง Booking ID
        val bookingId =
            generateBookingId()

        // สร้างข้อมูล Booking
        val booking =
            BookingData(
                bookingId = bookingId,
                selectedDate = selectedDate,
                startTime = startTime,
                endTime = endTime,
                selectedTable = selectedTable,
                playerCount = playerCount,
                gameName = gameName,
                contactName = contactName,
                contactPhone = contactPhone,
                playMode = playMode,
                status = "รอการยืนยัน"
            )

        // เก็บ Booking ปัจจุบัน
        currentBooking =
            booking

        return booking
    }

    // =========================================================
    // สร้าง Booking ID
    // =========================================================

    private fun generateBookingId(): String {

        var number: String

        do {

            number =
                buildString {

                    repeat(11) {

                        append(
                            Random.nextInt(
                                0,
                                10
                            )
                        )
                    }
                }

        } while (
            usedBookingIds.contains(
                number
            )
        )

        usedBookingIds.add(
            number
        )

        return "#DP$number"
    }

    // =========================================================
    // ล้าง Booking ปัจจุบัน
    // =========================================================

    fun clearCurrentBooking() {

        currentBooking =
            null
    }
}