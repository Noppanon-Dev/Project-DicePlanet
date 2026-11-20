package com.diceplanet.app.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var tvLoginTitle: TextView
    private lateinit var tvLoginDescription: TextView
    private lateinit var btnLogin: Button
    private lateinit var optionLogin: View

    private val databaseUrl =
        "https://project-diceplanet-default-rtdb.asia-southeast1.firebasedatabase.app/"

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

        auth = FirebaseAuth.getInstance()

        tvLoginTitle =
            view.findViewById(R.id.tvLoginTitle)

        tvLoginDescription =
            view.findViewById(R.id.tvLoginDescription)

        btnLogin =
            view.findViewById(R.id.btnLogin)

        optionLogin =
            view.findViewById(R.id.optionLogin)

        // Notification
        view.findViewById<View>(R.id.btnNotification)
            .setOnClickListener {
                findNavController().navigate(
                    R.id.loginFragment
                )
            }

        // Setting
        view.findViewById<View>(R.id.btnSetting)
            .setOnClickListener {
                showToast("หน้าตั้งค่ายังไม่เปิดให้ใช้งาน")
            }

        // Login Card
        btnLogin.setOnClickListener {
            if (auth.currentUser != null) {
                logout()
            } else {
                findNavController().navigate(
                    R.id.loginFragment
                )
            }
        }

        // Language
        view.findViewById<View>(R.id.optionLanguage)
            .setOnClickListener {
                showToast(
                    "ระบบเปลี่ยนภาษาจะเปิดให้ใช้งานภายหลัง"
                )
            }

        // Contact
        view.findViewById<View>(R.id.optionContact)
            .setOnClickListener {

                val shopUrl =
                    "https://www.facebook.com/profile.php?id=61576147981845"

                val intent = Intent(
                    Intent.ACTION_VIEW,
                    shopUrl.toUri()
                )

                startActivity(intent)
            }

        // About
        view.findViewById<View>(R.id.optionAbout)
            .setOnClickListener {
                showToast("Dice Planet Version Beta1.0")
            }

        // Login Option
        optionLogin.setOnClickListener {
            findNavController().navigate(
                R.id.loginFragment
            )
        }

        // โหลดสถานะบัญชี
        updateProfile()
    }

    override fun onResume() {
        super.onResume()

        // ตรวจสอบสถานะอีกครั้งเมื่อกลับมาหน้า Profile
        if (::auth.isInitialized) {
            updateProfile()
        }
    }

    // โหลดข้อมูลบัญชี
    private fun updateProfile() {

        val currentUser = auth.currentUser

        if (currentUser == null) {

            // ยังไม่ได้ Login
            showGuestProfile()
            return
        }

        // แสดงข้อมูลพื้นฐานระหว่างรอ Database
        tvLoginTitle.text = "กำลังโหลดข้อมูล..."
        tvLoginDescription.text =
            currentUser.email ?: ""

        btnLogin.text = "ออกจากระบบ"
        optionLogin.visibility = View.GONE

        val userRef = FirebaseDatabase
            .getInstance(databaseUrl)
            .reference
            .child("users")
            .child(currentUser.uid)

        userRef.get()
            .addOnSuccessListener { snapshot ->

                if (!isAdded || auth.currentUser?.uid != currentUser.uid) {
                    return@addOnSuccessListener
                }

                val username =
                    snapshot.child("username")
                        .getValue(String::class.java)

                val email =
                    snapshot.child("email")
                        .getValue(String::class.java)
                        ?: currentUser.email
                        ?: ""

                tvLoginTitle.text =
                    username ?: "สมาชิก Dice Planet"

                tvLoginDescription.text = email

            }
            .addOnFailureListener { error ->

                if (!isAdded) return@addOnFailureListener

                tvLoginTitle.text = "สมาชิก Dice Planet"
                tvLoginDescription.text =
                    currentUser.email ?: ""

                showToast(
                    "โหลดข้อมูลไม่สำเร็จ: ${error.localizedMessage}"
                )
            }
    }

    // แสดงหน้า Profile สำหรับ Guest
    private fun showGuestProfile() {

        tvLoginTitle.text = "เข้าสู่ระบบ"

        tvLoginDescription.text =
            "เข้าสู่ระบบเพื่อใช้งานฟีเจอร์\nและสิทธิประโยชน์ของคุณ"

        btnLogin.text = "เข้าสู่ระบบ"

        optionLogin.visibility = View.VISIBLE
    }

    // Logout
    private fun logout() {

        auth.signOut()

        showGuestProfile()

        showToast("ออกจากระบบสำเร็จ")
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(
                it,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
