package com.example.konrad.androidgameproject.Model

import java.io.Serializable
import android.util.Log

class Species (var name: String, var classification: String, var designation: String, var average_height: String,
                 var skin_colors: String, var hair_colors: String, var eye_colors: String, var average_lifespan: String,
                 var homeworld: String, var language: String, var people: ArrayList<String>?, var films: ArrayList<String>?, var created: String,
                 var edited: String, var url: String, var id: Int = 1): Serializable {

        constructor(): this("", "", "", "","", "", "", "", "",  "", null,null,"","", "" )

    fun generateId() {
        var regex = Regex("\\D+")
        id = url.replace(regex,"").toInt()
    }
    override fun toString(): String {
        Log.i("SPECIES", "$name $id")
        return name
    }

}