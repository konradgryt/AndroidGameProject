package com.example.konrad.androidgameproject.View

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_display_profile.*
import android.util.Log
import java.io.*
import android.graphics.BitmapFactory
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64.*
import com.example.konrad.androidgameproject.R


class Profile : AppCompatActivity() {
    val OPEN_CAMERA_BUTTON_REQUEST_ID = 4000
    val OPEN_GALLERY_BUTTON_REQUEST_ID = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_profile)

        val shared = getSharedPreferences("App_settings", MODE_PRIVATE)
        val currentAvatar = shared.getString("currentAvatar", "empty")
        val currentNickname = shared.getString("currentNickname", "player")

        txtProfileNickname.setText(currentNickname)
        if (currentAvatar != "empty") {
            imgProfilePicture?.setImageBitmap(resizeBitmap(decodeBase64(currentAvatar!!), 300, 300))
        } else {
            val placeholder = BitmapFactory.decodeResource(resources, R.drawable.placeholder)
            imgProfilePicture?.setImageBitmap(Bitmap.createScaledBitmap(placeholder, 300, 300, false))
        }


        txtProfileNickname?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val editor = getSharedPreferences("App_settings", MODE_PRIVATE).edit()
                editor.putString("currentNickname", p0.toString())
                editor.apply()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })


        btnProfileOpenCamera?.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, OPEN_CAMERA_BUTTON_REQUEST_ID)
        }

        btnProfileOpenGallery?.setOnClickListener{
            val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, OPEN_GALLERY_BUTTON_REQUEST_ID)
        }
    }

    //bitmap to base64
    fun encodeTobase64(image: Bitmap): String {
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        val imageEncoded = encodeToString(b, DEFAULT)
        Log.d("Image Log:", imageEncoded)
        return imageEncoded
    }

    //base 64 to bitmap
    fun decodeBase64(input: String): Bitmap {
        val decodedByte = decode(input, 0)
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.size)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_CAMERA_BUTTON_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                val imageData = data?.extras?.get("data") as Bitmap
                imgProfilePicture?.setImageBitmap(Bitmap.createScaledBitmap(imageData, 300, 300, false))
                val shared = getSharedPreferences("App_settings", MODE_PRIVATE)
                val editor = shared.edit()
                editor.putString("currentAvatar", encodeTobase64(imageData))
                editor.apply()
            }
        }
        if (requestCode == OPEN_GALLERY_BUTTON_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                val contentURI = data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                imgProfilePicture?.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 300, false))
                val shared = getSharedPreferences("App_settings", MODE_PRIVATE)
                val editor = shared.edit()
                editor.putString("currentAvatar", encodeTobase64(bitmap))
                editor.apply()
            }
        }
    }

    private fun resizeBitmap(bitmap:Bitmap, width:Int, height:Int):Bitmap{
        return Bitmap.createScaledBitmap(
                bitmap,
                width,
                height,
                false
        )
    }
}
