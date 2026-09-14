package com.diceplanet.app.ui.member

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diceplanet.app.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton

class MemberFragment : Fragment() {

    private val memberId = "DP12345678909"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_member,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // =========================
        // ข้อมูลสมาชิก Dummy
        // =========================

        val memberLevel = "Silver Member"
        val currentExp = 1250
        val maxExp = 2000

        // =========================
        // แสดงข้อมูล
        // =========================

        view.findViewById<TextView>(
            R.id.tvMemberLevel
        ).text = memberLevel

        // =========================
        // EXP Progress
        // =========================

        val progressExp =
            view.findViewById<ProgressBar>(
                R.id.progressExp
            )

        val tvExp =
            view.findViewById<TextView>(
                R.id.tvExp
            )

        progressExp.max = maxExp
        progressExp.progress = currentExp

        tvExp.text =
            "$currentExp / $maxExp EXP"

        // =========================
        // ดูสิทธิพิเศษ
        // =========================

        view.findViewById<View>(
            R.id.btnPrivilege
        ).setOnClickListener {

            findNavController().navigate(
                R.id.privilegeFragment
            )
        }

        // =========================
        // แสดง QR Code
        // =========================

        view.findViewById<View>(
            R.id.btnShowQr
        ).setOnClickListener {

            showQrCode()
        }

        // =========================
        // ดูประวัติทั้งหมด
        // =========================

        view.findViewById<TextView>(
            R.id.tvAllHistory
        ).setOnClickListener {

            Toast.makeText(
                requireContext(),
                "ประวัติแต้มทั้งหมดจะเชื่อม Backend ภายหลัง",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // =========================
    // QR Code
    // =========================

    private fun showQrCode() {

        // 1 ID = 1 QR Code
        val qrData = "DICEPLANET:$memberId"

        // สร้าง QR
        val bitMatrix = MultiFormatWriter().encode(
            qrData,
            BarcodeFormat.QR_CODE,
            700,
            700
        )

        val qrBitmap: Bitmap =
            BarcodeEncoder().createBitmap(bitMatrix)

        // สร้าง Dialog
        val dialog = Dialog(requireContext())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(
            R.layout.dialog_member_qr
        )

        // พื้นหลัง Dialog โปร่งใส
        dialog.window?.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )

        // ให้ Popup กว้างประมาณ 90% ของหน้าจอ
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        // QR Image
        val imgQrCode =
            dialog.findViewById<ImageView>(
                R.id.imgQrCode
            )

        imgQrCode.setImageBitmap(qrBitmap)

        // Member ID
        val tvQrMemberId =
            dialog.findViewById<TextView>(
                R.id.tvQrMemberId
            )

        tvQrMemberId.text = memberId

        // ปุ่ม X
        val btnCloseQr =
            dialog.findViewById<ImageButton>(
                R.id.btnCloseQr
            )

        btnCloseQr.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        // ต้องตั้งขนาดหลัง show()
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.90).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}