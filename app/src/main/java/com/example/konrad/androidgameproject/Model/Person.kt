package com.example.konrad.androidgameproject.Model

import android.util.Log
import java.io.Serializable


class Person(var name: String, var height: String, var mass: String, var hair_color: String,
             var skin_color: String, var eye_color: String, var birth_year: String, var gender: String,
             var homeworld: String, var films: ArrayList<String>?, var species : ArrayList<String>?,
             var vehicles: ArrayList<String>?, var starships: ArrayList<String>?, var created: String,
             var edited: String, var url: String): Entry(type = "person") {

    constructor(): this("", "", "", "","", "", "", "", "", null, null, null, null, "", "", "")

    override fun generateId() {
        var regex = Regex("\\D+")
        id = url.replace(regex,"").toInt()
    }

    override fun toString(): String {
        Log.i("PERSON", "$name $id")
        return name
    }

}