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

class RegisterFragment : Fragment() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var cbTerms: CheckBox

    private lateinit var btnShowPassword: ImageButton
    private lateinit var btnShowConfirmPassword: ImageButton

    private lateinit var registerScrollView: NestedScrollView

    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_register,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        cbTerms = view.findViewById(R.id.cbTerms)

        btnShowPassword = view.findViewById(R.id.btnShowPassword)
        btnShowConfirmPassword =
            view.findViewById(R.id.btnShowConfirmPassword)

        registerScrollView =
            view.findViewById(R.id.registerScrollView)

        // จัดการพื้นที่เมื่อคีย์บอร์ดเปิด
        ViewCompat.setOnApplyWindowInsetsListener(
            registerScrollView
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

        // เลื่อนเมื่อเลือกชื่อผู้ใช้
        etName.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                registerScrollView.postDelayed({
                    registerScrollView.smoothScrollTo(
                        0,
                        etName.top - 100
                    )
                }, 200)
            }
        }

        // เลื่อนเมื่อเลือก Email
        etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                registerScrollView.postDelayed({
                    registerScrollView.smoothScrollTo(
                        0,
                        etEmail.top - 100
                    )
                }, 200)
            }
        }

        // เลื่อนเมื่อเลือก Password
        etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                registerScrollView.postDelayed({
                    registerScrollView.smoothScrollTo(
                        0,
                        etPassword.top - 150
                    )
                }, 200)
            }
        }

        // เลื่อนเมื่อเลือก Confirm Password
        etConfirmPassword.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                registerScrollView.postDelayed({
                    registerScrollView.smoothScrollTo(
                        0,
                        etConfirmPassword.top - 180
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

        // แสดง / ซ่อน Confirm Password
        btnShowConfirmPassword.setOnClickListener {

            isConfirmPasswordVisible =
                !isConfirmPasswordVisible

            if (isConfirmPasswordVisible) {

                etConfirmPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                btnShowConfirmPassword.setImageResource(
                    R.drawable.ic_visibility
                )

            } else {

                etConfirmPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_PASSWORD

                btnShowConfirmPassword.setImageResource(
                    R.drawable.ic_visibility_off
                )
            }

            etConfirmPassword.setSelection(
                etConfirmPassword.text.length
            )
        }

        // สมัครสมาชิก
        view.findViewById<View>(R.id.btnRegister)
            .setOnClickListener {

                val name =
                    etName.text.toString().trim()

                val email =
                    etEmail.text.toString().trim()

                val password =
                    etPassword.text.toString()

                val confirmPassword =
                    etConfirmPassword.text.toString()

                if (name.isEmpty()) {
                    etName.error =
                        "กรุณากรอกชื่อผู้ใช้"
                    return@setOnClickListener
                }

                if (name.length < 3) {
                    etName.error =
                        "ชื่อผู้ใช้ต้องมีอย่างน้อย 3 ตัวอักษร"
                    return@setOnClickListener
                }

                if (email.isEmpty()) {
                    etEmail.error =
                        "กรุณากรอกอีเมล"
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    etPassword.error =
                        "กรุณากรอกรหัสผ่าน"
                    return@setOnClickListener
                }

                if (password.length < 6) {
                    etPassword.error =
                        "รหัสผ่านต้องมีอย่างน้อย 6 ตัวอักษร"
                    return@setOnClickListener
                }

                if (confirmPassword.isEmpty()) {
                    etConfirmPassword.error =
                        "กรุณายืนยันรหัสผ่าน"
                    return@setOnClickListener
                }

                if (password != confirmPassword) {
                    etConfirmPassword.error =
                        "รหัสผ่านไม่ตรงกัน"
                    return@setOnClickListener
                }

                if (!cbTerms.isChecked) {

                    Toast.makeText(
                        requireContext(),
                        "กรุณายอมรับข้อกำหนดการใช้งานและนโยบายความเป็นส่วนตัว",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }

                Toast.makeText(
                    requireContext(),
                    "สมัครสมาชิกสำเร็จ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        // กลับไปหน้า Login
        view.findViewById<TextView>(R.id.tvLogin)
            .setOnClickListener {

                findNavController().navigateUp()
            }
    }
}