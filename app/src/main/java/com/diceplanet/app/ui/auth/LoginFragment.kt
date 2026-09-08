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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R

class LoginFragment : Fragment() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnShowPassword: ImageButton
    private lateinit var cbRememberMe: CheckBox

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

        // เข้าสู่ระบบ - ตอนนี้ยัง Dummy
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

                // เดี๋ยวเชื่อมไป RegisterFragment
                // หลังจากสร้างหน้า Register
                Toast.makeText(
                    requireContext(),
                    "ไปหน้าสมัครสมาชิก",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}