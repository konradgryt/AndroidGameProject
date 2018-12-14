package com.example.konrad.androidgameproject.Service

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class DownloadingObject {
    fun downloadJSONDataFromLink(link: String): String {
        //For appending and insert methods, which are overloaded so as to accept data of any type.
        //Append adds at the end, insert at the specific point
        val stringBuilder: StringBuilder = StringBuilder()

        val url: URL = URL(link)
        val urlConnection = url.openConnection() as HttpURLConnection //creating connection, connection is created
        try {
            //Saving and reading text from input stream ( that decodes bytes into characters, for better efficiency)
            val bufferedInputStream: BufferedInputStream = BufferedInputStream(urlConnection.inputStream) //internal buffered array with bytes
            val bufferedReader: BufferedReader = BufferedReader(InputStreamReader(bufferedInputStream) as Reader?) //converting bytes to characters

            var inputLineString: String? // temporary string to hold each line read from the bufferedreader
            inputLineString = bufferedReader.readLine()
            while (inputLineString != null) {
                stringBuilder.append(inputLineString)
                inputLineString = bufferedReader.readLine()
            }
        } finally {
            //finally executes regardless of success or failure of try block - we will disconnect from the URLConnection
            urlConnection.disconnect() // to prevent memory leak problems related to not disconnecting
        }

        return stringBuilder.toString()
    }

    fun downloadPicture(id: String?): Bitmap? {

        var bitmap: Bitmap? = null

        var pictureLink: String = "$personImage$id.jpg"

        Log.i("PICTURE", pictureLink)
        val pictureURL = URL(pictureLink)
        val inputStream = pictureURL.openConnection().getInputStream()
        if (inputStream != null) {
            bitmap = BitmapFactory.decodeStream(inputStream)
        }

        return bitmap
    }

    fun downloadPicturePlanet(id: String?): Bitmap? {

        var bitmap: Bitmap? = null

        var pictureLink: String = "$planetImage$id.jpg"

        Log.i("PICTURE", pictureLink)
        val pictureURL = URL(pictureLink)
        val inputStream = pictureURL.openConnection().getInputStream()
        if (inputStream != null) {
            bitmap = BitmapFactory.decodeStream(inputStream)
        }

        return bitmap
    }

    fun downloadPictureVehicle(id: String?): Bitmap? {

        var bitmap: Bitmap? = null

        var pictureLink: String = "$vehicleImage$id.jpg"

        Log.i("PICTURE", pictureLink)
        val pictureURL = URL(pictureLink)
        val inputStream = pictureURL.openConnection().getInputStream()
        if (inputStream != null) {
            bitmap = BitmapFactory.decodeStream(inputStream)
        }

        return bitmap
    }

    fun downloadPictureSpecies(id: String?): Bitmap? {

        var bitmap: Bitmap? = null

        var pictureLink: String = "$speciesImage$id.jpg"

        Log.i("PICTURE", pictureLink)
        val pictureURL = URL(pictureLink)
        val inputStream = pictureURL.openConnection().getInputStream()
        if (inputStream != null) {
            bitmap = BitmapFactory.decodeStream(inputStream)
        }

        return bitmap
    }

    companion object {
        val personImage = "https://starwars-visualguide.com/assets/img/characters/"
        val planetImage = "https://starwars-visualguide.com/assets/img/planets/"
        val vehicleImage = "https://starwars-visualguide.com/assets/img/vehicles/"
        val speciesImage = "https://starwars-visualguide.com/assets/img/species/"
    }
}