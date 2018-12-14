package com.example.konrad.androidgameproject

import android.util.Log
import com.example.konrad.androidgameproject.Model.Person
import com.example.konrad.androidgameproject.Model.Person.Companion.currentId
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ParsePersonUtility {
    var allPersonObjects: ArrayList<Person> = ArrayList()

    fun parsePeopleFromJSONData(pages: Int): List<Person>? {
        currentId = 1
        parsePlantObjectsFromJSONData("https://swapi.co/api/people/", "")
        if (pages > 1) {
            for (i in 2..pages) {
                Log.i("WELL", i.toString())
                parsePlantObjectsFromJSONData("https://swapi.co/api/people/", "?page=$i")
            }
        }
        Log.i("WHAT", allPersonObjects.size.toString())
        return allPersonObjects
    }

    fun parsePlantObjectsFromJSONData(link: String, page: String): List<Person>? {
        var downloadingObject: DownloadingObject = DownloadingObject()
        var topLevelPlantJSONData: String =
                downloadingObject.downloadJSONDataFromLink(link + page)
        var topLevelJSONObject: JSONObject = JSONObject(topLevelPlantJSONData)
        var peopleObjectsArray: JSONArray = topLevelJSONObject.getJSONArray("results")

        var index: Int = 0

        while (index < peopleObjectsArray.length()) {
            var personObject: Person = Person()
            var currentPerson = peopleObjectsArray.getJSONObject(index)


            //Using with to refractor code above
            with(currentPerson) {
                personObject.name = getString("name")
                personObject.height = getString("height")
                personObject.mass = getString("mass")
                personObject.hair_color = getString("hair_color")
                personObject.skin_color = getString("skin_color")
                personObject.eye_color = getString("eye_color")
                personObject.birth_year = getString("birth_year")
                personObject.gender = getString("gender")
                personObject.homeworld = getString("homeworld")
                personObject.films = convertJSONArray(getJSONArray("films"))
                personObject.species   = convertJSONArray(getJSONArray("species"))
                personObject.vehicles = convertJSONArray(getJSONArray("vehicles"))
                personObject.starships = convertJSONArray(getJSONArray("starships"))
                personObject.created = getString("created")
                personObject.edited = getString("edited")
                personObject.url = getString("url")

            }
            allPersonObjects.add(personObject)
            index++
        }
        for (i in 0..allPersonObjects.size - 1) {
            Log.i("PEOPLE", "${allPersonObjects[i]}")
        }
        return allPersonObjects
    }

    fun convertJSONArray(jsonObject: JSONArray) : ArrayList<String>{
        val listdata = ArrayList<String>()
        val jArray = jsonObject
        if (jArray != null) {
            for (i in 0 until jArray.length()) {
                listdata.add(jArray.getString(i))
            }
        }
        return listdata
    }
}