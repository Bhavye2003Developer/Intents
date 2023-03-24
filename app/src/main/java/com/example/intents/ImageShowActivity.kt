package com.example.intents

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView

class ImageShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_show)

        val image = findViewById<ImageView>(R.id.imageView)

        var bitmap : Bitmap? =null
        if (intent.hasExtra("image")){
            //convert to bitmap
            val byteArray = intent.getByteArrayExtra("image")
            if (byteArray != null) {
                bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                image.setImageBitmap(bitmap)
            }
        }
        Log.v("PathName",applicationInfo.sourceDir)
    }
}