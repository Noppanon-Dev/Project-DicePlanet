package com.diceplanet.app.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class BookingFragment : Fragment() {

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

        val tvAddBooking =
            view.findViewById<TextView>(R.id.tvAddBooking)

        tvAddBooking.setOnClickListener {

            findNavController().navigate(
                R.id.booking1_1Fragment
            )
        }
    }
}