package com.diceplanet.app.ui.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// ประเภท Role ของผู้ใช้งาน //

enum class UserRole {
    GUEST,
    MEMBER,
    STAFF,
    UNKNOWN
}

// RoleManager //

// Object สำหรับจัดการและตรวจสอบ Role ของผู้ใช้งาน
object RoleManager {

    // URL ของ Firebase Realtime Database
    private const val DATABASE_URL =
        "https://project-diceplanet-default-rtdb.asia-southeast1.firebasedatabase.app/"

    // เชื่อมต่อกับ Firebase Authentication
    // ใช้ตรวจสอบว่าปัจจุบันมีผู้ใช้งาน Login อยู่หรือไม่
    private val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    // เชื่อมต่อกับ Firebase Realtime Database
    // ใช้สำหรับอ่านข้อมูล Role ของผู้ใช้งาน
    private val database
        get() = FirebaseDatabase
            .getInstance(DATABASE_URL)
            .reference

    // อ่าน Role ของผู้ใช้งานปัจจุบัน //

    fun getCurrentRole(
        onResult: (UserRole) -> Unit
    ) {

        // ดึงข้อมูลผู้ใช้งานที่กำลัง Login อยู่จาก Firebase Authentication
        val currentUser = auth.currentUser

        // ตรวจสอบว่ามีผู้ใช้งาน Login อยู่หรือไม่
        if (currentUser == null) {

            // ถ้าไม่มีผู้ใช้งานที่ Login อยู่ ให้กำหนดเป็น Guest
            onResult(UserRole.GUEST)

            // หยุดการทำงาน เพราะไม่จำเป็นต้องค้นหา Role ใน Database
            return
        }

        // อ่าน Role จาก Firebase Realtime Database

        database.child("roles")
            .child(currentUser.uid)

            // อ่านข้อมูลจาก Database เพียงครั้งเดียว
            // เมื่ออ่านเสร็จ Firebase จะส่งผลลัพธ์กลับมา
            .addListenerForSingleValueEvent(

                // สร้าง Listener เพื่อรอรับผลลัพธ์จาก Firebase
                object : ValueEventListener {

                    // ทำงานเมื่ออ่านข้อมูลจาก Database สำเร็จ
                    override fun onDataChange(
                        snapshot: DataSnapshot
                    ) {

                        // ดึงข้อมูล Role จาก Firebase
                        // และแปลงข้อมูลให้อยู่ในรูปแบบ String
                        val role = snapshot.getValue(String::class.java)

                        // ตรวจสอบข้อความ Role ที่ได้รับจาก Database
                        // แล้วแปลงให้เป็นประเภท UserRole ของระบบ
                        val userRole = when (role) {

                            // ถ้าข้อมูลเป็น "member" ให้กำหนดเป็น MEMBER
                            "member" -> UserRole.MEMBER

                            // ถ้าข้อมูลเป็น "staff" ให้กำหนดเป็น STAFF
                            "staff" -> UserRole.STAFF

                            // ถ้าไม่มีข้อมูลหรือไม่ตรงกับเงื่อนไข
                            // ให้กำหนดเป็น UNKNOWN
                            else -> UserRole.UNKNOWN
                        }

                        // ส่งผลลัพธ์ Role กลับไปยังส่วนที่เรียกใช้งาน
                        onResult(userRole)
                    }

                    // ทำงานเมื่อ Firebase ไม่สามารถอ่านข้อมูลได้
                    override fun onCancelled(
                        error: DatabaseError
                    ) {

                        // ส่งผลลัพธ์ UNKNOWN กลับไป
                        // เนื่องจากไม่สามารถตรวจสอบ Role ได้
                        onResult(UserRole.UNKNOWN)
                    }
                }
            )
    }
}
