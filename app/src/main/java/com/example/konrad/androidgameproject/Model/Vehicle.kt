package com.example.konrad.androidgameproject.Model

import android.util.Log
import java.io.Serializable

class Vehicle (var name: String, var model: String, var manufacturer: String,
               var cost_in_credits: String, var length: String, var max_atmosphering_speed: String,
               var crew: String, var passengers: String, var cargo_capacity: String,
               var consumables: String, var vehicle_class: String, var pilots: ArrayList<String>?,
               var films: ArrayList<String>?, var created: String,
               var edited: String, var url: String, var id: Int = 1): Serializable {

    constructor(): this("", "", "",
            "","", "",
            "", "", "",  "", "",
            null,null,"", "", "" )

    fun generateId() {
        var regex = Regex("\\D+")
        id = url.replace(regex,"").toInt()
    }
    override fun toString(): String {
        Log.i("VEHICLES", "$name $id")
        return name
    }
}
