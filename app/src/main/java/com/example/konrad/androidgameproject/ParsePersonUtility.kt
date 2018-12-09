package com.example.konrad.androidgameproject

import android.util.Log
import com.example.konrad.androidgameproject.Person.Companion.currentId
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ParsePersonUtility {
    var allPersonObjects: ArrayList<Person> = ArrayList()

    fun parsePeopleFromJSONData(): List<Person>? {
        currentId = 1
        for (i in 1..3) {
            parsePlantObjectsFromJSONData("https://swapi.co/api/people/", "?page=$i")
        }
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
                personObject.films = getJSONArray("films")
                personObject.species   = getJSONArray("species")
                personObject.vehicles = getJSONArray("vehicles")
                personObject.starships = getJSONArray("starships")
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
}