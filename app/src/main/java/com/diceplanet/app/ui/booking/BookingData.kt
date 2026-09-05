package com.diceplanet.app.ui.booking

data class BookingData(
    val bookingId: String,
    val selectedDate: String,
    val startTime: String,
    val endTime: String,
    val selectedTable: String,
    val playerCount: Int,
    val gameName: String,
    val contactName: String,
    val contactPhone: String,
    var status: String = "รอการยืนยัน"
)