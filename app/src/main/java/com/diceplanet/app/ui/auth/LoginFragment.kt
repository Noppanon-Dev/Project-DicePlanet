package com.diceplanet.app.ui.auth

import android.os.Bundle
import android.text.InputType
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
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class LoginFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnShowPassword: ImageButton
    private lateinit var cbRememberMe: CheckBox
    private lateinit var loginScrollView: NestedScrollView

    private var isPasswordVisible = false

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

        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        btnShowPassword = view.findViewById(R.id.btnShowPassword)
        cbRememberMe = view.findViewById(R.id.cbRememberMe)
        loginScrollView = view.findViewById(R.id.loginScrollView)

        // จัดการพื้นที่เมื่อคีย์บอร์ดเปิด
        ViewCompat.setOnApplyWindowInsetsListener(loginScrollView) { v, insets ->

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
        view.findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
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

        // ลืมรหัสผ่าน - Dummy
        view.findViewById<TextView>(R.id.tvForgotPassword)
            .setOnClickListener {

                Toast.makeText(
                    requireContext(),
                    "ฟังก์ชันลืมรหัสผ่านยังไม่เปิดใช้งาน",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // เข้าสู่ระบบ - Dummy
        view.findViewById<View>(R.id.btnLogin)
            .setOnClickListener {

                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString()

                if (email.isEmpty()) {
                    etEmail.error = "กรุณากรอก Email"
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    etPassword.error = "กรุณากรอก Password"
                    return@setOnClickListener
                }

                Toast.makeText(
                    requireContext(),
                    "ระบบ Login จะเชื่อมต่อ Backend ภายหลัง",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // สมัครสมาชิก
        view.findViewById<TextView>(R.id.tvRegister)
            .setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
    }
}