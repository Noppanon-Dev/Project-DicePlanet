package com.diceplanet.app.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class Booking1_5Fragment : Fragment() {

    private lateinit var tvBookingId: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_booking1_5,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        tvBookingId =
            view.findViewById(R.id.tvBookingId)

        val booking = BookingManager.currentBooking

        tvBookingId.text =
            booking?.bookingId ?: "#DP00000000000"

        view.findViewById<View>(R.id.btnBackHome)
            .setOnClickListener {

                findNavController().navigate(
                    R.id.bookingFragment
                )
            }
    }
}