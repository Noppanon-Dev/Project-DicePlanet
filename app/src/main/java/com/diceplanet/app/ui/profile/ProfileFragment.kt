package com.diceplanet.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_profile,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // =========================
        // Notification
        // =========================

        val btnNotification =
            view.findViewById<View>(R.id.btnNotification)

        btnNotification.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }


        // =========================
        // Setting
        // =========================

        val btnSetting =
            view.findViewById<View>(R.id.btnSetting)

        btnSetting.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "หน้าตั้งค่ายังไม่เปิดให้ใช้งาน",
                Toast.LENGTH_SHORT
            ).show()
        }


        // =========================
        // Login Card
        // =========================

        val btnLogin =
            view.findViewById<View>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }


        // =========================
        // Language
        // =========================

        val optionLanguage =
            view.findViewById<View>(R.id.optionLanguage)

        optionLanguage.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "ระบบเปลี่ยนภาษาจะเปิดให้ใช้งานภายหลัง",
                Toast.LENGTH_SHORT
            ).show()
        }


        // =========================
        // Contact
        // =========================

        val optionContact =
            view.findViewById<View>(R.id.optionContact)

        optionContact.setOnClickListener {

            // เปลี่ยน URL เป็นเพจร้านจริงของ Dice Planet
            val shopUrl = "https://www.facebook.com/profile.php?id=61576147981845"

            val intent = Intent(
                Intent.ACTION_VIEW,
                shopUrl.toUri()
            )

            startActivity(intent)
        }


        // =========================
// About
// =========================

        val optionAbout =
            view.findViewById<View>(R.id.optionAbout)

        optionAbout.setOnClickListener {

            Toast.makeText(
                requireContext(),
                "Dice Planet Version Beta1.0",
                Toast.LENGTH_SHORT
            ).show()

        }


        // =========================
        // Login Option
        // =========================

        val optionLogin =
            view.findViewById<View>(R.id.optionLogin)

        optionLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }
}