package com.diceplanet.app.ui.auth

import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnShowPassword: ImageButton
    private lateinit var cbRememberMe: CheckBox
    private lateinit var loginScrollView: NestedScrollView

    private var isPasswordVisible = false

    // Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_login,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()

        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        btnShowPassword = view.findViewById(R.id.btnShowPassword)
        cbRememberMe = view.findViewById(R.id.cbRememberMe)
        loginScrollView = view.findViewById(R.id.loginScrollView)

        // จัดการพื้นที่เมื่อคีย์บอร์ดเปิด
        ViewCompat.setOnApplyWindowInsetsListener(
            loginScrollView
        ) { v, insets ->

            val imeInsets = insets.getInsets(
                WindowInsetsCompat.Type.ime()
            )

            val systemInsets = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            val bottomPadding = maxOf(
                imeInsets.bottom,
                systemInsets.bottom
            )

            v.setPadding(
                v.paddingLeft,
                v.paddingTop,
                v.paddingRight,
                bottomPadding
            )

            insets
        }

        // เมื่อเลือก Email ให้เลื่อน Email ขึ้น
        etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                loginScrollView.postDelayed({
                    loginScrollView.smoothScrollTo(
                        0,
                        etEmail.top - 100
                    )
                }, 200)
            }
        }

        // เมื่อเลือก Password ให้เลื่อน Password ขึ้น
        etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                loginScrollView.postDelayed({
                    loginScrollView.smoothScrollTo(
                        0,
                        etPassword.top - 150
                    )
                }, 200)
            }
        }

        // ปุ่มย้อนกลับ
        view.findViewById<ImageButton>(R.id.btnBack)
            .setOnClickListener {
                findNavController().navigateUp()
            }

        // แสดง / ซ่อน Password
        btnShowPassword.setOnClickListener {

            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {

                etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                btnShowPassword.setImageResource(
                    R.drawable.ic_visibility
                )

            } else {

                etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_PASSWORD

                btnShowPassword.setImageResource(
                    R.drawable.ic_visibility_off
                )
            }

            etPassword.setSelection(
                etPassword.text.length
            )
        }

        // ลืมรหัสผ่าน
        view.findViewById<TextView>(R.id.tvForgotPassword)
            .setOnClickListener {

                val email = etEmail.text.toString().trim()

                if (email.isEmpty()) {
                    etEmail.error = "กรุณากรอก Email ก่อน"
                    return@setOnClickListener
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.error = "รูปแบบ Email ไม่ถูกต้อง"
                    return@setOnClickListener
                }

                resetPassword(email)
            }

        // เข้าสู่ระบบ
        val btnLogin = view.findViewById<View>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            if (email.isEmpty()) {
                etEmail.error = "กรุณากรอก Email"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "รูปแบบ Email ไม่ถูกต้อง"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "กรุณากรอก Password"
                return@setOnClickListener
            }

            // เริ่ม Login
            btnLogin.isEnabled = false

            loginWithFirebase(
                email,
                password,
                btnLogin
            )
        }

        // สมัครสมาชิก
        view.findViewById<TextView>(R.id.tvRegister)
            .setOnClickListener {
                findNavController().navigate(
                    R.id.registerFragment
                )
            }
    }

    // Login ด้วย Firebase Authentication
    private fun loginWithFirebase(
        email: String,
        password: String,
        btnLogin: View
    ) {

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->

            btnLogin.isEnabled = true

            if (task.isSuccessful) {

                showToast("เข้าสู่ระบบสำเร็จ")

                // กลับไปหน้า Home และล้างหน้าที่อยู่ด้านบน
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(
                        R.id.homeFragment,
                        false
                    )
                    .setLaunchSingleTop(true)
                    .build()

                if (isAdded &&
                    findNavController().currentDestination?.id == R.id.loginFragment
                ) {
                    findNavController().navigate(
                        R.id.homeFragment,
                        null,
                        navOptions
                    )
                }

            } else {

                val errorMessage =
                    task.exception?.localizedMessage
                        ?: "ไม่สามารถเข้าสู่ระบบได้"

                showToast(
                    "เข้าสู่ระบบไม่สำเร็จ: $errorMessage"
                )
            }
        }
    }

    // ส่ง Email สำหรับรีเซ็ตรหัสผ่าน
    private fun resetPassword(email: String) {

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    showToast(
                        "ส่งลิงก์รีเซ็ตรหัสผ่านไปยัง Email แล้ว"
                    )

                } else {

                    val errorMessage =
                        task.exception?.localizedMessage
                            ?: "ไม่สามารถส่ง Email ได้"

                    showToast(
                        "ส่ง Email ไม่สำเร็จ: $errorMessage"
                    )
                }
            }
    }

    private fun showToast(message: String) {
        context?.let {
            Toast.makeText(
                it,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}