package com.example.guide

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter



class ClassNumberActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_number)
        val number = findViewById<TextView>(R.id.newClassNumber)
        val img = findViewById<ImageView>(R.id.newClassBarcode)
        val code = "86459875"
        number.text = code
        img.setImageBitmap(createQRImage(code,500,500))
    }
    private fun createQRImage(code:String, widthPix: Int, heightPix: Int): Bitmap {
        val bitMatrix: BitMatrix = QRCodeWriter().encode(code,BarcodeFormat.QR_CODE,widthPix,heightPix)
        val pixels = IntArray(widthPix * heightPix)
        for (y in 0 until heightPix) {
            for (x in 0 until widthPix) {
                if (bitMatrix.get(x, y)) {
                    pixels[y * widthPix + x] = -0x1000000
                } else {
                    pixels[y * widthPix + x] = -0x1
                }
            }
        }
        val bitmap: Bitmap? = Bitmap.createBitmap(widthPix, heightPix, Bitmap.Config.ARGB_8888)
        bitmap!!.setPixels(pixels, 0, widthPix, 0, 0, widthPix, heightPix)
        return bitmap
    }
}