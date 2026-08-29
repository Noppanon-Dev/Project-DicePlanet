package com.diceplanet.app

import android.os.Bundle //เรียกของที่ Android ใช้ตอนเปิดหรือสร้างหน้านี้
import androidx.appcompat.app.AppCompatActivity //เรียกตัวที่ใช้สร้างหน้า Activity ของแอป
import androidx.navigation.fragment.NavHostFragment //เรียกตัวที่ใช้เป็นพื้นที่สำหรับแสดงหน้า Fragment ต่างๆตามที่กำหนดไว้ใน nav_graph.xml
import androidx.navigation.ui.setupWithNavController //เรียกตัวช่วยที่เอาไว้เชื่อมปุ่ม Bottom Nav กับระบบเปลี่ยนหน้า
import com.google.android.material.bottomnavigation.BottomNavigationView //เรียกตัว Bottom Navigation ที่อยู่ด้านล่างของแอป

// หน้าหลักของแอป ใช้ควบคุมการเปลี่ยนหน้าและ Bottom Navigation
class MainActivity : AppCompatActivity() {

    // ทำงานตอนเปิดหน้า Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // แสดง Layout ของหน้า Main
        setContentView(R.layout.activity_main)

        // หา Navigation Host ที่ใช้แสดงหน้า Fragment ต่าง ๆ
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                    as NavHostFragment

        // ตัวควบคุมการเปลี่ยนหน้า
        val navController = navHostFragment.navController

        // หา Bottom Navigation จากหน้า Main
        val bottomNavigation =
            findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // เชื่อม Bottom Navigation กับระบบเปลี่ยนหน้า
        bottomNavigation.setupWithNavController(navController)
    }
}