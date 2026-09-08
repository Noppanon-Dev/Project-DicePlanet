package com.diceplanet.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
            findNavController().navigate(R.id.loginFragment)
        }

        // ปุ่ม "ดูทั้งหมด" ของหมวดหมู่บอร์ดเกม
        val tvAllCategory = view.findViewById<TextView>(R.id.tvAllCategory)

        tvAllCategory.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "หน้าหมวดหมู่บอร์ดเกมยังไม่ได้สร้าง",
                Toast.LENGTH_SHORT
            ).show()
        }

        // ปุ่ม "ดูทั้งหมด" ของหมวดหมู่บอร์ดเกม
        val tvAllGames = view.findViewById<TextView>(R.id.tvAllGames)

        tvAllGames.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "หน้าเกมทั้งหมดยังไม่ได้สร้าง",
                Toast.LENGTH_SHORT
            ).show()
        }

        // หมวดหมู่ "ทั้งหมด"
        val categoryAll = view.findViewById<View>(R.id.categoryAll)

        categoryAll.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "หมวดหมู่: ทั้งหมด",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}