package com.example.intents

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newScreen = findViewById<Button>(R.id.newScreen)
        val browser = findViewById<Button>(R.id.browser)
        val whatsapp = findViewById<Button>(R.id.whatsapp)
        val call = findViewById<Button>(R.id.call)
        val linkedin = findViewById<Button>(R.id.linkedin)
        val camera = findViewById<Button>(R.id.camera)

        newScreen.setOnClickListener {
           startActivity(Intent(applicationContext, newActivity::class.java))
        }
        browser.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_WEB_SEARCH)
            startActivity(browserIntent)
        }
        whatsapp.setOnClickListener {
            val messagingIntent : Intent = Intent(Intent.ACTION_SEND)
            messagingIntent.setType("text/plain")
            messagingIntent.putExtra(Intent.EXTRA_TEXT,"This is a message")
            startActivity(Intent.createChooser(messagingIntent, "Please select app"))
        }
        call.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            startActivity(phoneIntent)
        }
        linkedin.setOnClickListener {
            val webpage: Uri = Uri.parse("https://www.linkedin.com/in/bhavye-jain-8980ab255")
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
        camera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
                Log.d("cameraError", e.toString())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val thumbnail: Bitmap? = data?.getParcelableExtra("data")
            Log.d("bitmapString",thumbnail.toString())
            // Do other work with full size photo saved in locationForPhotos
            val stream : ByteArrayOutputStream = ByteArrayOutputStream()
            thumbnail?.compress(Bitmap.CompressFormat.PNG,100,stream)
            val byteArray = stream.toByteArray()
            val showImageIntent = Intent(applicationContext, ImageShowActivity::class.java)
            showImageIntent.putExtra("image",byteArray)
            startActivity(showImageIntent)
        }
    }
}