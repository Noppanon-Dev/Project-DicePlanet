package com.diceplanet.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        // กำหนดให้หน้าภายใน Booking ยังคงเลือกปุ่ม Booking
        navController.addOnDestinationChangedListener { _, destination, _ ->

            // ซ่อน Bottom Navigation ในหน้า Login
            if (destination.id == R.id.loginFragment) {
                bottomNavigation.visibility = View.GONE
            } else {
                bottomNavigation.visibility = View.VISIBLE
            }

            // ให้ Booking ยังคงถูกเลือกในทุกหน้าของ Booking
            when (destination.id) {

                R.id.bookingFragment,
                R.id.booking1_1Fragment,
                R.id.booking1_2Fragment,
                R.id.booking1_3Fragment,
                R.id.booking1_3GameDetailFragment,
                R.id.booking1_4Fragment,
                R.id.booking1_5Fragment -> {

                    bottomNavigation.menu
                        .findItem(R.id.bookingFragment)
                        .isChecked = true
                }
            }
        }
    }
}