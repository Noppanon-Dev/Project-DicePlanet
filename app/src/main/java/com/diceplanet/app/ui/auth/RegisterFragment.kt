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
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// หน้าสมัครสมาชิกของแอป Dice Planet
class RegisterFragment : Fragment() {

    // ตัวแปรสำหรับช่องกรอกข้อมูลสมัครสมาชิก
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var cbTerms: CheckBox

    // ตัวแปรสำหรับปุ่มแสดง / ซ่อนรหัสผ่าน
    private lateinit var btnShowPassword: ImageButton
    private lateinit var btnShowConfirmPassword: ImageButton

    // ตัวแปรสำหรับพื้นที่เลื่อนหน้าจอ
    private lateinit var registerScrollView: NestedScrollView

    // เก็บสถานะว่าตอนนี้กำลังแสดงรหัสผ่านอยู่หรือไม่
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    // Firebase //

    // ตัวเชื่อมต่อ Firebase Authentication ใช้สำหรับสร้างบัญชีผู้ใช้ใหม่
    private lateinit var auth: FirebaseAuth

    // URL ของ Firebase Realtime Database
    private val databaseUrl =
        "https://project-diceplanet-default-rtdb.asia-southeast1.firebasedatabase.app/"

    // สร้างหน้าจอ Register
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

    // เริ่มทำงานหลังจากสร้างหน้าจอ Register แล้ว
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // เชื่อมต่อกับ Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // เชื่อมช่องกรอกข้อมูลกับ ID ในไฟล์ XML
        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword)
        cbTerms = view.findViewById(R.id.cbTerms)

        // เชื่อมปุ่มแสดง / ซ่อนรหัสผ่าน
        btnShowPassword = view.findViewById(R.id.btnShowPassword)
        btnShowConfirmPassword =
            view.findViewById(R.id.btnShowConfirmPassword)

        // เชื่อมพื้นที่เลื่อนหน้าจอ
        registerScrollView =
            view.findViewById(R.id.registerScrollView)

        // จัดการพื้นที่ด้านล่างเมื่อคีย์บอร์ดเปิดขึ้นมา
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

        // เลื่อนหน้าจอให้เห็นช่องกรอกข้อมูลเมื่อผู้ใช้กดเลือก
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

        // ปุ่มย้อนกลับไปหน้าก่อนหน้า
        view.findViewById<ImageButton>(R.id.btnBack)
            .setOnClickListener {
                findNavController().navigateUp()
            }

        // แสดง / ซ่อน Password
        btnShowPassword.setOnClickListener {

            // สลับสถานะการแสดงรหัสผ่าน
            isPasswordVisible = !isPasswordVisible

            if (isPasswordVisible) {

                // แสดงรหัสผ่านเป็นข้อความปกติ
                etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

                btnShowPassword.setImageResource(
                    R.drawable.ic_visibility
                )

            } else {

                // ซ่อนรหัสผ่าน
                etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or
                            InputType.TYPE_TEXT_VARIATION_PASSWORD

                btnShowPassword.setImageResource(
                    R.drawable.ic_visibility_off
                )
            }

            // ให้เคอร์เซอร์อยู่ตำแหน่งเดิมหลังเปลี่ยนรูปแบบรหัสผ่าน
            etPassword.setSelection(etPassword.text.length)
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

            // ให้เคอร์เซอร์อยู่ตำแหน่งเดิม
            etConfirmPassword.setSelection(
                etConfirmPassword.text.length
            )
        }

        // ปุ่มสมัครสมาชิก
        val btnRegister =
            view.findViewById<View>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            // รับข้อมูลที่ผู้ใช้กรอกในแต่ละช่อง
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirmPassword =
                etConfirmPassword.text.toString()

            // ตรวจสอบว่ากรอก Username หรือยัง
            if (name.isEmpty()) {
                etName.error = "กรุณากรอกชื่อผู้ใช้"
                return@setOnClickListener
            }

            // Username ต้องมีอย่างน้อย 3 ตัวอักษร
            if (name.length < 3) {
                etName.error =
                    "ชื่อผู้ใช้ต้องมีอย่างน้อย 3 ตัวอักษร"
                return@setOnClickListener
            }

            // ตรวจสอบว่ากรอก Email หรือยัง
            if (email.isEmpty()) {
                etEmail.error = "กรุณากรอกอีเมล"
                return@setOnClickListener
            }

            // ตรวจสอบรูปแบบ Email ว่าถูกต้องหรือไม่
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "รูปแบบอีเมลไม่ถูกต้อง"
                return@setOnClickListener
            }

            // ตรวจสอบว่ากรอก Password หรือยัง
            if (password.isEmpty()) {
                etPassword.error = "กรุณากรอกรหัสผ่าน"
                return@setOnClickListener
            }

            // Password ต้องมีอย่างน้อย 6 ตัวอักษร
            if (password.length < 6) {
                etPassword.error =
                    "รหัสผ่านต้องมีอย่างน้อย 6 ตัวอักษร"
                return@setOnClickListener
            }

            // ตรวจสอบว่ากรอก Confirm Password หรือยัง
            if (confirmPassword.isEmpty()) {
                etConfirmPassword.error =
                    "กรุณายืนยันรหัสผ่าน"
                return@setOnClickListener
            }

            // ตรวจสอบว่ารหัสผ่านทั้งสองช่องตรงกันหรือไม่
            if (password != confirmPassword) {
                etConfirmPassword.error =
                    "รหัสผ่านไม่ตรงกัน"
                return@setOnClickListener
            }

            // ตรวจสอบว่าผู้ใช้ยอมรับข้อกำหนดแล้วหรือยัง
            if (!cbTerms.isChecked) {
                Toast.makeText(
                    requireContext(),
                    "กรุณายอมรับข้อกำหนดการใช้งานและนโยบายความเป็นส่วนตัว",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // ปิดปุ่มสมัครสมาชิกชั่วคราว
            // เพื่อป้องกันการกดสมัครซ้ำระหว่างกำลังบันทึกข้อมูล
            btnRegister.isEnabled = false

            // ส่งข้อมูลไปยังฟังก์ชันสมัครสมาชิกด้วย Firebase
            registerWithFirebase(
                name,
                email,
                password,
                btnRegister
            )
        }

        // กดข้อความ Login เพื่อกลับไปหน้า Login
        view.findViewById<TextView>(R.id.tvLogin)
            .setOnClickListener {
                findNavController().navigateUp()
            }
    }

    // สมัครสมาชิกและบันทึกข้อมูลลง Firebase
    private fun registerWithFirebase(
        name: String,
        email: String,
        password: String,
        btnRegister: View
    ) {

        // ส่ง Email และ Password ไปสร้างบัญชีใน Firebase Authentication
        auth.createUserWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener { task ->

            // ตรวจสอบว่าการสร้างบัญชีสำเร็จหรือไม่
            if (task.isSuccessful) {

                // ดึง UID ของผู้ใช้ที่เพิ่งสมัครสมาชิก
                // UID คือรหัสประจำตัวที่ Firebase สร้างให้ผู้ใช้แต่ละคน
                val uid = auth.currentUser?.uid

                // ตรวจสอบว่าได้รับ UID หรือไม่
                if (uid == null) {

                    // เปิดปุ่มสมัครสมาชิกกลับมา
                    btnRegister.isEnabled = true

                    // แจ้งเตือนหากไม่สามารถอ่าน UID ได้
                    showToast("ไม่สามารถอ่าน UID ของผู้ใช้ได้")

                    return@addOnCompleteListener
                }

                // เชื่อมต่อ Firebase Realtime Database
                val database = FirebaseDatabase
                    .getInstance(databaseUrl)
                    .reference

                // เลือกตำแหน่ง users และเข้าไปยัง UID ของผู้ใช้
                // ตัวอย่าง: users/abc123
                val userRef = database
                    .child("users")
                    .child(uid)

                // ==========================================
                // บันทึก Username
                // ==========================================

                // บันทึกชื่อผู้ใช้ลงในตำแหน่ง users/{uid}/username
                userRef.child("username")
                    .setValue(name)
                    .addOnCompleteListener { usernameTask ->

                        // ตรวจสอบว่าบันทึก Username สำเร็จหรือไม่
                        if (usernameTask.isSuccessful) {

                            // ==========================================
                            // บันทึก Email
                            // ==========================================

                            // บันทึก Email ลงใน users/{uid}/email
                            userRef.child("email")
                                .setValue(email)
                                .addOnCompleteListener { emailTask ->

                                    // ตรวจสอบว่าบันทึก Email สำเร็จหรือไม่
                                    if (emailTask.isSuccessful) {

                                        // กำหนด Role เริ่มต้น
                                        // บันทึกลงในตำแหน่ง roles/{uid}
                                        database.child("roles")
                                            .child(uid)
                                            .setValue("member")
                                            .addOnCompleteListener { roleTask ->

                                                // เปิดปุ่มสมัครสมาชิกกลับมา
                                                btnRegister.isEnabled = true

                                                // ตรวจสอบว่าบันทึก Role สำเร็จหรือไม่
                                                if (roleTask.isSuccessful) {

                                                    // แจ้งว่าสมัครสมาชิกสำเร็จ
                                                    showToast("สมัครสมาชิกสำเร็จ")

                                                    // สร้างตัวเลือกการเปลี่ยนหน้า
                                                    // เพื่อกลับไป Home และไม่ให้ย้อนกลับมาหน้า Register
                                                    val navOptions = androidx.navigation.NavOptions.Builder()
                                                        .setPopUpTo(R.id.homeFragment, false)
                                                        .setLaunchSingleTop(true)
                                                        .build()

                                                    // เปลี่ยนไปหน้า Home
                                                    findNavController().navigate(
                                                        R.id.homeFragment,
                                                        null,
                                                        navOptions
                                                    )

                                                } else {

                                                    // แจ้งเตือนเมื่อบันทึก Role ไม่สำเร็จ
                                                    showToast(
                                                        "บันทึกข้อมูลแล้ว แต่กำหนด Role ไม่สำเร็จ: " +
                                                                (roleTask.exception?.localizedMessage
                                                                    ?: "Unknown error")
                                                    )
                                                }
                                            }

                                    } else {

                                        // เปิดปุ่มสมัครสมาชิกกลับมา
                                        btnRegister.isEnabled = true

                                        // แจ้งเตือนเมื่อบันทึก Email ไม่สำเร็จ
                                        showToast(
                                            "บันทึก Username แล้ว แต่บันทึก Email ไม่สำเร็จ: " +
                                                    (emailTask.exception?.localizedMessage
                                                        ?: "Unknown error")
                                        )
                                    }
                                }

                        } else {

                            // เปิดปุ่มสมัครสมาชิกกลับมา
                            btnRegister.isEnabled = true

                            // แจ้งเตือนเมื่อบันทึก Username ไม่สำเร็จ
                            showToast(
                                "บันทึก Username ไม่สำเร็จ: " +
                                        (usernameTask.exception?.localizedMessage
                                            ?: "Unknown error")
                            )
                        }
                    }

            } else {

                // เปิดปุ่มสมัครสมาชิกกลับมา
                btnRegister.isEnabled = true

                // รับข้อความ Error จาก Firebase
                // ถ้าไม่มีข้อความ Error ให้ใช้ข้อความที่กำหนดไว้แทน
                val errorMessage =
                    task.exception?.localizedMessage
                        ?: "ไม่สามารถสมัครสมาชิกได้"

                showToast(errorMessage)
            }
        }
    }

    // แสดงข้อความแจ้งเตือน //
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