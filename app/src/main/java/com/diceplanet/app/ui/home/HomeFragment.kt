package com.diceplanet.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater //ใช้สำหรับเอาไฟล์ XML มาสร้างเป็นหน้าที่แสดงบนแอป
import android.view.View //ใช้จัดการสิ่งต่าง ๆ ที่อยู่บนหน้าจอ
import android.view.ViewGroup //ใช้จัดการพื้นที่ที่ Fragment จะถูกนำไปแสดง
import androidx.fragment.app.Fragment //เรียกตัวพื้นฐานสำหรับสร้างหน้า Fragment
import com.diceplanet.app.R //เรียก R เพื่อให้โค้ดสามารถเข้าถึง Resource ต่าง ๆ ของโปรเจกต์ เช่น Layout, ID, รูปภาพ และ String

// Fragment สำหรับหน้า Home
class HomeFragment : Fragment() {

    // ทำงานตอนสร้างหน้าตาของ Home
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // นำ Layout ของหน้า Home มาแสดง
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}