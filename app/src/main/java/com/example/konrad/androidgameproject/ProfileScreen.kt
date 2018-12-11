package com.example.konrad.androidgameproject

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_display_profile.*
import android.R.attr.bitmap
import android.content.ContextWrapper
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import android.os.Environment.getExternalStorageDirectory
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v4.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*


class ProfileScreen : AppCompatActivity() {
    val OPEN_CAMERA_BUTTON_REQUEST_ID = 4000
    val OPEN_GALLERY_BUTTON_REQUEST_ID = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_profile)

        button666.setOnClickListener{
            // Save the image in internal storage and get the uri
            val uri:Uri = saveImageToInternalStorage(R.drawable.placeholder)

            // Display the internal storage saved image in image view
            image_view_saved.setImageURI(uri)

            // Show the saved image uri
            textview666.text = "Saved: $uri"
        }
//        val photoPath = "${Environment.getExternalStorageDirectory()}/avatar.jpg"
//        val options = BitmapFactory.Options()
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888
//        val bitmap = BitmapFactory.decodeFile(photoPath, options)


     //   Log.i("PLS", bitmap.toString())
        if (savedBitmap != null) {
            imgProfilePicture?.setImageBitmap(savedBitmap)
        } else {
            imgProfilePicture?.setImageResource(R.drawable.placeholder)
        }

        btnProfileOpenCamera?.setOnClickListener{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, OPEN_CAMERA_BUTTON_REQUEST_ID)
        }

        btnProfileOpenGallery?.setOnClickListener{
            val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, OPEN_GALLERY_BUTTON_REQUEST_ID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_CAMERA_BUTTON_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                val imageData = data?.extras?.get("data") as Bitmap
                imgProfilePicture?.setImageBitmap(imageData)
                savedBitmap = imageData
            }
        }
        if (requestCode == OPEN_GALLERY_BUTTON_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                val contentURI = data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                imgProfilePicture?.setImageBitmap(bitmap)
                savedBitmap = bitmap
            }
        }
    }

    private fun saveImageToInternalStorage(drawableId:Int): Uri{
        // Get the image from drawable resource as drawable object
        val drawable = ContextCompat.getDrawable(applicationContext,drawableId)

        // Get the bitmap from drawable object
        val bitmap = (drawable as BitmapDrawable).bitmap

        // Get the context wrapper instance
        val wrapper = ContextWrapper(applicationContext)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", MODE_PRIVATE)


        // Create a file to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }

    companion object {
        var savedBitmap: Bitmap? = null
    }
}