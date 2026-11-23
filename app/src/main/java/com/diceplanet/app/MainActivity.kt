package com.diceplanet.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.diceplanet.app.ui.auth.RoleManager
import com.diceplanet.app.ui.auth.UserRole
import com.google.android.material.bottomnavigation.BottomNavigationView

// หน้าหลักของแอป ใช้ควบคุมการเปลี่ยนหน้าและ Bottom Navigation
class MainActivity : AppCompatActivity() {

    // ตัวควบคุมการเปลี่ยนหน้าในแอป
    private lateinit var navController: NavController

    // ตัวควบคุมเมนูด้านล่างของแอป
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // แสดง Layout หลักของแอป
        setContentView(R.layout.activity_main)

        // กำหนดไอคอน Status Bar ให้เป็นสีเข้ม
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = true

        // ค้นหา Navigation Host จาก Layout
        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment
            ) as NavHostFragment

        // เชื่อมต่อ NavController เพื่อควบคุมการเปลี่ยนหน้า
        navController = navHostFragment.navController

        // เชื่อมต่อ Bottom Navigation จาก Layout
        bottomNavigation = findViewById(R.id.bottom_navigation)

        // ==========================================
        // Bottom Navigation
        // ==========================================

        // ตรวจสอบเมื่อผู้ใช้กดเลือกเมนูด้านล่าง
        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.homeFragment -> {
                    navController.navigate(
                        R.id.homeFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(
                                R.id.homeFragment,
                                true
                            )
                            .setLaunchSingleTop(true)
                            .build()
                    )
                    true
                }

                R.id.boardgameFragment -> {
                    navigateTo(R.id.boardgameFragment)
                    true
                }

                R.id.bookingFragment -> {
                    openProtectedDestination(R.id.bookingFragment)
                    true
                }

                R.id.memberFragment -> {
                    openProtectedDestination(R.id.memberFragment)
                    true
                }

                R.id.profileFragment -> {
                    navigateTo(R.id.profileFragment)
                    true
                }

             else -> false
            }
        }

        // ตรวจสอบ Destination เมื่อมีการเปลี่ยนหน้า

        navController.addOnDestinationChangedListener { _, destination, _ ->

            // ซ่อน Bottom Navigation ในหน้า Login และ Register
            if (
                destination.id == R.id.loginFragment ||
                destination.id == R.id.registerFragment
            ) {
                bottomNavigation.visibility = View.GONE
            } else {
                bottomNavigation.visibility = View.VISIBLE
            }

            // กำหนดสถานะเมนูให้ตรงกับหน้าที่กำลังเปิดอยู่
            when (destination.id) {

                R.id.homeFragment -> {
                    bottomNavigation.menu
                        .findItem(R.id.homeFragment)
                        .isChecked = true
                }

                R.id.boardgameFragment,
                R.id.boardgameDetailFragment -> {
                    bottomNavigation.menu
                        .findItem(R.id.boardgameFragment)
                        .isChecked = true
                }

                R.id.bookingFragment,
                R.id.booking1_1Fragment,
                R.id.booking1_2Fragment,
                R.id.booking1_3Fragment,
                R.id.booking1_4Fragment,
                R.id.booking1_5Fragment -> {
                    bottomNavigation.menu
                        .findItem(R.id.bookingFragment)
                        .isChecked = true
                }

                R.id.memberFragment,
                R.id.privilegeFragment -> {
                    bottomNavigation.menu
                        .findItem(R.id.memberFragment)
                        .isChecked = true
                }

                R.id.profileFragment -> {
                    bottomNavigation.menu
                        .findItem(R.id.profileFragment)
                        .isChecked = true
                }
            }
        }
    }

    // เปลี่ยนหน้าแบบปกติ

    // รับ ID ของหน้าที่ต้องการเปิด แล้วสั่งให้ NavController เปลี่ยนหน้า
    private fun navigateTo(destinationId: Int) {

        // ตรวจสอบว่าปัจจุบันอยู่หน้าเดิมหรือไม่
        // ถ้าอยู่หน้าเดิมแล้ว ไม่ต้องเปลี่ยนหน้าอีก
        if (navController.currentDestination?.id == destinationId) {
            return
        }

        // เปลี่ยนไปยังหน้าที่กำหนด
        navController.navigate(
            destinationId,
            null,
            NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build()
        )
    }

    // ตรวจสอบสิทธิ์ผู้ใช้จาก Firebase

    // ตรวจสอบ Role ก่อนอนุญาตให้เข้า Booking หรือ Member
    private fun openProtectedDestination(destinationId: Int) {

        // เรียก RoleManager ให้ตรวจสอบ Role ของผู้ใช้จาก Firebase
        RoleManager.getCurrentRole { role ->

            // ตรวจสอบว่า Activity ยังทำงานอยู่หรือไม่
            if (isFinishing || isDestroyed) {
                return@getCurrentRole
            }

            // ตรวจสอบผลลัพธ์ Role ที่ได้รับกลับมาจาก RoleManager
            when (role) {

                // กรณีผู้ใช้ยังไม่ได้ Login
                UserRole.GUEST -> {

                    // ถ้ายังไม่ได้อยู่หน้า Login ให้เปลี่ยนไปหน้า Login
                    if (navController.currentDestination?.id != R.id.loginFragment) {
                        navController.navigate(
                            R.id.loginFragment,
                            null,
                            NavOptions.Builder()
                                .setLaunchSingleTop(true)
                                .build()
                        )
                    }
                }

                // กรณีเป็น Member หรือ Staff
                // อนุญาตให้เข้า Booking และ Member ได้
                UserRole.MEMBER,
                UserRole.STAFF -> {
                    navigateTo(destinationId)
                }

                // กรณีไม่สามารถตรวจสอบ Role ได้
                UserRole.UNKNOWN -> {

                    // แจ้งเตือนผู้ใช้และไม่อนุญาตให้เข้าไปยังหน้าปลายทาง
                    Toast.makeText(
                        this,
                        "ไม่สามารถตรวจสอบสิทธิ์ผู้ใช้ได้",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}