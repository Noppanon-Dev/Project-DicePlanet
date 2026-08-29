package com.diceplanet.app.ui.boardgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diceplanet.app.R

// Fragment สำหรับหน้า Board Game
class BoardgameFragment : Fragment() {

    // ทำงานตอนสร้างหน้าตาของ Board Game
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // นำ Layout ของหน้า Board Game มาแสดง
        return inflater.inflate(R.layout.fragment_boardgame, container, false)
    }
}