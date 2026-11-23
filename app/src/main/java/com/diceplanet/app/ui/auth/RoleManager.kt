package com.diceplanet.app.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// ประเภท Role ของผู้ใช้งาน
enum class UserRole {
    GUEST,
    MEMBER,
    STAFF,
    UNKNOWN
}

object RoleManager {

    private const val DATABASE_URL =
        "https://project-diceplanet-default-rtdb.asia-southeast1.firebasedatabase.app/"

    private val auth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    private val database
        get() = FirebaseDatabase
            .getInstance(DATABASE_URL)
            .reference

    // อ่าน Role ของผู้ใช้ปัจจุบัน
    fun getCurrentRole(
        onResult: (UserRole) -> Unit
    ) {

        val currentUser = auth.currentUser

        // ยังไม่ได้ Login
        if (currentUser == null) {
            onResult(UserRole.GUEST)
            return
        }

        // อ่าน Role จาก Realtime Database
        database.child("roles")
            .child(currentUser.uid)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {

                    override fun onDataChange(
                        snapshot: DataSnapshot
                    ) {

                        val role = snapshot.getValue(String::class.java)

                        val userRole = when (role) {
                            "member" -> UserRole.MEMBER
                            "staff" -> UserRole.STAFF
                            else -> UserRole.UNKNOWN
                        }

                        onResult(userRole)
                    }

                    override fun onCancelled(
                        error: DatabaseError
                    ) {
                        onResult(UserRole.UNKNOWN)
                    }
                }
            )
    }
}
