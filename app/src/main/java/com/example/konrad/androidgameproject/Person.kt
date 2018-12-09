package com.example.konrad.androidgameproject

import android.util.Log
import org.json.JSONArray

class Person(var name: String, var height: String, var mass: String, var hair_color: String,
             var skin_color: String, var eye_color: String, var birth_year: String, var gender: String,
             var homeworld: String, var films: JSONArray?, var species : JSONArray?,
            var vehicles: JSONArray?, var starships: JSONArray?, var created: String,
            var edited: String, var url: String, var id: Int = 1){

    constructor(): this("", "", "", "","", "", "", "", "", null, null, null, null, "", "", "")

    init {
        if (currentId == 17) {
            currentId++
            id = currentId
            currentId++
        }
        else if (currentId == 35) {
            currentId++
            id = 87
        } else {
            id = currentId
            currentId++
        }
    }

    override fun toString(): String {
        Log.i("PERSON", "$name $id")
        return "$name"
    }

    companion object {
        var currentId = 1
    }
}