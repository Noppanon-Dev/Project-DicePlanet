package com.diceplanet.app.ui.booking

import kotlin.random.Random

object BookingManager {

    var currentBooking: BookingData? = null
        private set

    private val usedBookingIds = mutableSetOf<String>()

    fun createBooking(
        selectedDate: String,
        startTime: String,
        endTime: String,
        selectedTable: String,
        playerCount: Int,
        gameName: String,
        contactName: String,
        contactPhone: String
    ): BookingData {

        val bookingId = generateBookingId()

        val booking = BookingData(
            bookingId = bookingId,
            selectedDate = selectedDate,
            startTime = startTime,
            endTime = endTime,
            selectedTable = selectedTable,
            playerCount = playerCount,
            gameName = gameName,
            contactName = contactName,
            contactPhone = contactPhone,
            status = "รอการยืนยัน"
        )

        currentBooking = booking

        return booking
    }

    private fun generateBookingId(): String {

        var number: String

        do {
            number = buildString {
                repeat(11) {
                    append(Random.nextInt(0, 10))
                }
            }
        } while (usedBookingIds.contains(number))

        usedBookingIds.add(number)

        return "#DP$number"
    }

    fun clearCurrentBooking() {
        currentBooking = null
    }
}