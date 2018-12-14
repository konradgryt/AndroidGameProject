package com.example.konrad.androidgameproject.Service

import android.util.Log
import com.example.konrad.androidgameproject.Model.Person
import com.example.konrad.androidgameproject.Model.Planet
import com.example.konrad.androidgameproject.Model.Species
import com.example.konrad.androidgameproject.Model.Vehicle
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class CustomParserClass {
    var allPersonObjects: ArrayList<Person> = ArrayList()
    var allVehicleObjects: ArrayList<Vehicle> = ArrayList()
    var allPlanetObjects: ArrayList<Planet> = ArrayList()
    var allSpeciesObjects: ArrayList<Species> = ArrayList()

    fun parsePeopleFromJSONData(pages: Int): List<Person>? {
        parsePersonObjectsFromJSONData(personURL, "")
        if (pages > 1) {
            for (i in 2..pages) {
                Log.i("WELL", i.toString())
                parsePersonObjectsFromJSONData(personURL, "?page=$i")
            }
        }
        Log.i("WHAT", allPersonObjects.size.toString())
        return allPersonObjects
    }

    fun parsePlanetFromJSONData(pages: Int): List<Planet>? {
        parsePlanetObjectsFromJSONData(planetURL, "")
        if (pages > 1) {
            for (i in 2..pages) {
                Log.i("WELL", i.toString())
                parsePlanetObjectsFromJSONData(planetURL, "?page=$i")
            }
        }
        Log.i("WHAT", allPlanetObjects.size.toString())
        return allPlanetObjects
    }

    fun parseVehicleFromJSONData(pages: Int): List<Vehicle>? {
        parseVehicleObjectsFromJSONData(vehicleURL, "")
        if (pages > 1) {
            for (i in 2..pages) {
                Log.i("WELL", i.toString())
                parseVehicleObjectsFromJSONData(vehicleURL, "?page=$i")
            }
        }
        Log.i("WHAT", allVehicleObjects.size.toString())
        return allVehicleObjects
    }

    fun parseSpeciesFromJSONData(pages: Int): List<Species>? {
        parseSpeciesObjectsFromJSONData(speciesURL, "")
        if (pages > 1) {
            for (i in 2..pages) {
                Log.i("WELL", i.toString())
                parseSpeciesObjectsFromJSONData(speciesURL, "?page=$i")
            }
        }
        Log.i("WHAT", allSpeciesObjects.size.toString())
        return allSpeciesObjects
    }


    fun parsePersonObjectsFromJSONData(link: String, page: String): List<Person>? {
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
    fun parsePlanetObjectsFromJSONData(link: String, page: String): List<Planet>? {
        var downloadingObject: DownloadingObject = DownloadingObject()
        var topLevelPlanetJSONData: String =
                downloadingObject.downloadJSONDataFromLink(link + page)
        var topLevelJSONObject: JSONObject = JSONObject(topLevelPlanetJSONData)
        var planetsObjectsArray: JSONArray = topLevelJSONObject.getJSONArray("results")

        var index: Int = 0

        while (index < planetsObjectsArray.length()) {
            var planetObject: Planet = Planet()
            var currentPlanet = planetsObjectsArray.getJSONObject(index)

            //Using with to refractor code above
            with(currentPlanet) {
                planetObject.name = getString("name")
                planetObject.rotation_period = getString("rotation_period")
                planetObject.orbital_period = getString("orbital_period")
                planetObject.diameter = getString("diameter")
                planetObject.climate = getString("climate")
                planetObject.gravity = getString("gravity")
                planetObject.terrain = getString("terrain")
                planetObject.surface_water = getString("surface_water")
                planetObject.population = getString("population")
                planetObject.films = convertJSONArray(getJSONArray("films"))
                planetObject.residents   = convertJSONArray(getJSONArray("residents"))
                planetObject.created = getString("created")
                planetObject.edited = getString("edited")
                planetObject.url = getString("url")

            }
            allPlanetObjects.add(planetObject)
            index++
        }
        for (i in 0..allPlanetObjects.size - 1) {
            Log.i("PLANETS", "${allPlanetObjects[i]}")
        }
        return allPlanetObjects
    }
    fun parseVehicleObjectsFromJSONData(link: String, page: String): List<Vehicle>? {
        var downloadingObject: DownloadingObject = DownloadingObject()
        var topLevelVehicleJSONData: String =
                downloadingObject.downloadJSONDataFromLink(link + page)
        var topLevelJSONObject: JSONObject = JSONObject(topLevelVehicleJSONData)
        var vehiclesObjectsArray: JSONArray = topLevelJSONObject.getJSONArray("results")

        var index: Int = 0

        while (index < vehiclesObjectsArray.length()) {
            var vehicleObject: Vehicle = Vehicle()
            var currentVehicle = vehiclesObjectsArray.getJSONObject(index)

            //Using with to refractor code above
            with(currentVehicle) {
                vehicleObject.name = getString("name")
                vehicleObject.model = getString("model")
                vehicleObject.manufacturer = getString("manufacturer")
                vehicleObject.cost_in_credits = getString("cost_in_credits")
                vehicleObject.length = getString("length")
                vehicleObject.max_atmosphering_speed = getString("max_atmosphering_speed")
                vehicleObject.crew = getString("crew")
                vehicleObject.passengers = getString("passengers")
                vehicleObject.cargo_capacity = getString("cargo_capacity")
                vehicleObject.consumables = getString("consumables")
                vehicleObject.pilots   = convertJSONArray(getJSONArray("pilots"))
                vehicleObject.films   = convertJSONArray(getJSONArray("films"))
                vehicleObject.created = getString("created")
                vehicleObject.edited = getString("edited")
                vehicleObject.url = getString("url")
            }

            allVehicleObjects.add(vehicleObject)
            index++
        }
        for (i in 0..allVehicleObjects.size - 1) {
            Log.i("VEHICLES", "${allVehicleObjects[i]}")
        }
        return allVehicleObjects
    }
    fun parseSpeciesObjectsFromJSONData(link: String, page: String): List<Species>? {
        var downloadingObject: DownloadingObject = DownloadingObject()
        var topLevelSpeciesJSONData: String =
                downloadingObject.downloadJSONDataFromLink(link + page)
        var topLevelJSONObject: JSONObject = JSONObject(topLevelSpeciesJSONData)
        var speciesObjectsArray: JSONArray = topLevelJSONObject.getJSONArray("results")

        var index: Int = 0

        while (index < speciesObjectsArray.length()) {
            var speciesObject: Species = Species()
            var currentSpecies = speciesObjectsArray.getJSONObject(index)

            //Using with to refractor code above
            with(currentSpecies) {
                speciesObject.name = getString("name")
                speciesObject.classification = getString("classification")
                speciesObject.designation = getString("designation")
                speciesObject.average_height = getString("average_height")
                speciesObject.skin_colors = getString("skin_colors")
                speciesObject.hair_colors = getString("hair_colors")
                speciesObject.eye_colors = getString("eye_colors")
                speciesObject.average_lifespan = getString("average_lifespan")
                speciesObject.homeworld = getString("homeworld")
                speciesObject.language = getString("language")
                speciesObject.people   = convertJSONArray(getJSONArray("people"))
                speciesObject.films   = convertJSONArray(getJSONArray("films"))
                speciesObject.created = getString("created")
                speciesObject.edited = getString("edited")
                speciesObject.url = getString("url")
            }

            allSpeciesObjects.add(speciesObject)
            index++
        }
        for (i in 0..allSpeciesObjects.size - 1) {
            Log.i("SPECIES", "${allSpeciesObjects[i]}")
        }
        return allSpeciesObjects
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

    companion object {
        val personURL = "https://swapi.co/api/people/"
        val vehicleURL = "https://swapi.co/api/vehicles/"
        val planetURL = "https://swapi.co/api/planets/"
        val speciesURL = "https://swapi.co/api/species/"
    }
}