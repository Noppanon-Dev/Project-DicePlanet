package com.diceplanet.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.diceplanet.app.R

// Fragment สำหรับหน้า Home
class HomeFragment : Fragment() {

    // ทำงานตอนสร้างหน้าตาของ Home
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // นำ Layout ของหน้า Home มาแสดง
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    // ทำงานหลังจาก Layout ถูกสร้างขึ้นแล้ว
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ปุ่ม "กรุณาเข้าสู่ระบบ"
        val tvLogin = view.findViewById<TextView>(R.id.tvLogin)

        tvLogin.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "หน้า Login ยังไม่ได้สร้าง",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}