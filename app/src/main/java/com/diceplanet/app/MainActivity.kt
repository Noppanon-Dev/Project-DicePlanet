package com.diceplanet.app

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

// หน้าหลักของแอป ใช้ควบคุมการเปลี่ยนหน้าและ Bottom Navigation
class MainActivity : AppCompatActivity() {
    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

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
        bottomNavigation =
            findViewById(R.id.bottom_navigation)


        // =========================
        // Bottom Navigation
        // =========================

        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.homeFragment -> {

                    navController.navigate(
                        R.id.homeFragment,
                        null,
                        androidx.navigation.NavOptions.Builder()
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

                    navController.navigate(
                        R.id.boardgameFragment,
                        null,
                        androidx.navigation.NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )

                    true
                }

                R.id.bookingFragment -> {

                    navController.navigate(
                        R.id.bookingFragment,
                        null,
                        androidx.navigation.NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )

                    true
                }

                R.id.memberFragment -> {

                    navController.navigate(
                        R.id.memberFragment,
                        null,
                        androidx.navigation.NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )

                    true
                }

                R.id.profileFragment -> {

                    navController.navigate(
                        R.id.profileFragment,
                        null,
                        androidx.navigation.NavOptions.Builder()
                            .setLaunchSingleTop(true)
                            .build()
                    )

                    true
                }

                else -> false
            }
        }


        // =========================
        // ตรวจสอบ Destination
        // =========================

        navController.addOnDestinationChangedListener {
                _,
                destination,
                _ ->

            // =========================
            // ซ่อน Bottom Navigation
            // =========================

            if (
                destination.id == R.id.loginFragment ||
                destination.id == R.id.registerFragment
            ) {

                bottomNavigation.visibility = View.GONE

            } else {

                bottomNavigation.visibility = View.VISIBLE
            }


            // =========================
            // กำหนดปุ่มที่ถูกเลือก
            // =========================

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
}