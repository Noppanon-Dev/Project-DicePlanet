
package com.diceplanet.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.diceplanet.app.auth.RoleManager
import com.diceplanet.app.auth.UserRole
import com.google.android.material.bottomnavigation.BottomNavigationView

// หน้าหลักของแอป ใช้ควบคุมการเปลี่ยนหน้าและ Bottom Navigation
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // แสดง Layout ของหน้า Main
        setContentView(R.layout.activity_main)

        // ตั้งค่าสีของ Status Bar
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = true

        // หา Navigation Host
        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment
            ) as NavHostFragment

        // ตัวควบคุม Navigation
        navController = navHostFragment.navController

        // หา Bottom Navigation
        bottomNavigation = findViewById(R.id.bottom_navigation)

        // =========================
        // Bottom Navigation
        // =========================
        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                // Home
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

                // Board Game
                R.id.boardgameFragment -> {
                    navigateTo(R.id.boardgameFragment)
                    true
                }

                // Booking (ต้อง Login ก่อน)
                R.id.bookingFragment -> {
                    openProtectedDestination(R.id.bookingFragment)
                    true
                }

                // Member (ต้อง Login ก่อน)
                R.id.memberFragment -> {
                    openProtectedDestination(R.id.memberFragment)
                    true
                }

                // Profile
                R.id.profileFragment -> {
                    navigateTo(R.id.profileFragment)
                    true
                }

                else -> false
            }
        }

        // =========================
        // ตรวจสอบ Destination
        // =========================
        navController.addOnDestinationChangedListener { _, destination, _ ->

            // ซ่อน Bottom Navigation ในหน้า Login / Register
            if (
                destination.id == R.id.loginFragment ||
                destination.id == R.id.registerFragment
            ) {
                bottomNavigation.visibility = View.GONE
            } else {
                bottomNavigation.visibility = View.VISIBLE
            }

            // กำหนดปุ่มที่ถูกเลือก
            when (destination.id) {

                // Home
                R.id.homeFragment -> {
                    bottomNavigation.menu
                        .findItem(R.id.homeFragment)
                        .isChecked = true
                }

                // Board Game
                R.id.boardgameFragment,
                R.id.boardgameDetailFragment -> {
                    bottomNavigation.menu
                        .findItem(R.id.boardgameFragment)
                        .isChecked = true
                }

                // Booking
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

                // Member
                R.id.memberFragment,
                R.id.privilegeFragment -> {
                    bottomNavigation.menu
                        .findItem(R.id.memberFragment)
                        .isChecked = true
                }

                // Profile
                R.id.profileFragment -> {
                    bottomNavigation.menu
                        .findItem(R.id.profileFragment)
                        .isChecked = true
                }
            }
        }
    }

    // เปลี่ยนหน้าแบบปกติ
    private fun navigateTo(destinationId: Int) {
        if (navController.currentDestination?.id == destinationId) {
            return
        }

        navController.navigate(
            destinationId,
            null,
            NavOptions.Builder()
                .setLaunchSingleTop(true)
                .build()
        )
    }

    // ตรวจสอบสิทธิ์ก่อนเข้า Booking / Member
    private fun openProtectedDestination(destinationId: Int) {

        RoleManager.getCurrentRole { role ->

            if (isFinishing || isDestroyed) {
                return@getCurrentRole
            }

            when (role) {

                // Guest ต้อง Login ก่อน
                UserRole.GUEST -> {
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

                // Member และ Staff เข้าได้
                UserRole.MEMBER,
                UserRole.STAFF -> {
                    navigateTo(destinationId)
                }

                // ไม่พบ Role หรือเกิดข้อผิดพลาด
                UserRole.UNKNOWN -> {
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
