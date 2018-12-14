package com.example.konrad.androidgameproject.GUI

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.GridLayout
import com.example.konrad.androidgameproject.Model.*
import com.example.konrad.androidgameproject.Service.CustomParserClass
import com.example.konrad.androidgameproject.R
import java.util.*
import kotlinx.android.synthetic.main.activity_select_difficulty.*

class Difficulty : AppCompatActivity() {
    val START_GAME = 1
    var difficulty :LEVEL? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_difficulty)
        setProgressBar(false)
    }

    fun onClickEasyDifficulty(buttonView: View)  {
        try {
            setProgressBar(true)
            val innerClassObject = DownloadingQuestionDataTask()
            innerClassObject.execute("Easy")
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    fun onClickNormalDifficulty(buttonView: View) {
        try {
            setProgressBar(true)
            val innerClassObject = DownloadingQuestionDataTask()
            innerClassObject.execute("Normal")
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    fun onClickHardDifficulty(buttonView: View) {
        try {
            setProgressBar(true)
            val innerClassObject = DownloadingQuestionDataTask()
            innerClassObject.execute("Hard")
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    fun onClickVeryHardDifficulty(buttonView: View) {
        try {
            setProgressBar(true)
            val innerClassObject = DownloadingQuestionDataTask()
            innerClassObject.execute("Very Hard")
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    inner class DownloadingQuestionDataTask: AsyncTask<String, Int, Boolean>() {

        var listOfPeople: List<Person>? = null
        var listOfPlanets: List<Planet>? = null
        var listOfVehicles: List<Vehicle>? = null
        var listOfSpecies: List<Species>? = null

        override fun doInBackground(vararg params: String?): Boolean { //called by execute
            val parser = CustomParserClass()
            var pages = 0
            when (params[0]) {
                // There is no page 1 in the api
                "Easy" -> {
                    pages = 0
                    difficulty = LEVEL.EASY
                }
                "Normal" -> {
                    pages = 2
                    difficulty = LEVEL.NORMAL
                }
                "Hard" -> {
                    pages = 3
                    difficulty = LEVEL.HARD
                }
                "Very Hard" -> {
                    pages = 4
                    difficulty = LEVEL.VERY_HARD
                }
            }

            if (Category.areCharactersOn) {
                listOfPeople = parser.parsePeopleFromJSONData(pages)
            }
            if (Category.arePlanetsOn) {
                listOfPlanets = parser.parsePlanetFromJSONData(pages)
            }
            if (Category.areVehiclesOn) {
               listOfVehicles = parser.parseVehicleFromJSONData(pages)
            }
            if (Category.areSpeciesOn) {
                listOfSpecies = parser.parseSpeciesFromJSONData(pages)
            }
            return true
        }

        override fun onPostExecute(result: Boolean) {
            super.onPostExecute(result)

            val gameIntent = Intent(this@Difficulty, Game::class.java)
            if (Category.areCharactersOn) {
                val peopleList = ArrayList<Person>()
                listOfPeople!!.map { person -> peopleList.add(person as Person) }
                val array = arrayOfNulls<Person>(listOfPeople!!.size)
                val readyArrayPeople = peopleList.toArray(array)
                gameIntent.putExtra("PEOPLE", readyArrayPeople)
            }
            if (Category.arePlanetsOn) {
                val planetsList = ArrayList<Planet>()
                listOfPlanets!!.map { planet -> planetsList.add(planet as Planet)  }
                val array2 = arrayOfNulls<Planet>(listOfPlanets!!.size)
                val readyArrayPlanets = planetsList.toArray(array2)
                gameIntent.putExtra("PLANETS", readyArrayPlanets)
            }
            if (Category.areVehiclesOn) {
                val vehiclesList = ArrayList<Vehicle>()
                listOfVehicles!!.map { vehicle -> vehiclesList.add(vehicle as Vehicle)  }
                val array3 = arrayOfNulls<Vehicle>(listOfVehicles!!.size)
                val readyArrayVehicles = vehiclesList.toArray(array3)
                gameIntent.putExtra("VEHICLES", readyArrayVehicles)
            }
            if (Category.areSpeciesOn) {
                val speciesList = ArrayList<Species>()
                listOfSpecies!!.map { species -> speciesList.add(species as Species)  }
                val array4 = arrayOfNulls<Species>(listOfSpecies!!.size)
                val readyArraySpecies = speciesList.toArray(array4)
                gameIntent.putExtra("SPECIES", readyArraySpecies)
            }
            gameIntent.putExtra("DIFFICULTY", difficulty)
            startActivityForResult(gameIntent, START_GAME)
            setProgressBar(false)
        }
    }

    // ProgressBar State
    private fun setProgressBar(show: Boolean) {
        if (show) {
            //setUIWidgets(false)
            linearLayoutProgress.setVisibility(View.VISIBLE)
            progressBar.setVisibility(View.VISIBLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            //setUIWidgets(true)
            linearLayoutProgress.setVisibility(View.GONE)
            progressBar.setVisibility(View.GONE)
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
}