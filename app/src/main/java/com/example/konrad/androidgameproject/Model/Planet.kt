package com.example.konrad.androidgameproject.Model

import android.util.Log
import java.io.Serializable

class Planet (var name: String, var rotation_period: String, var orbital_period: String,
              var diameter: String, var climate: String, var gravity: String, var terrain: String,
               var surface_water: String, var population: String, var residents: ArrayList<String>?,
               var films: ArrayList<String>?, var created: String,
               var edited: String, var url: String, var id: Int = 1): Serializable {

    constructor(): this("", "", "",
            "","", "",
            "", "", "",  null, null,
            "","", "" )

    fun generateId() {
        var regex = Regex("\\D+")
        id = url.replace(regex,"").toInt()
    }
    override fun toString(): String {
        Log.i("VEHICLES", "$name $id")
        return name
    }
}
