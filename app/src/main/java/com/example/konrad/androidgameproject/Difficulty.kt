package com.example.konrad.androidgameproject

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Contacts
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import java.util.*
import kotlinx.android.synthetic.main.activity_game.*

class Difficulty : AppCompatActivity() {
    val START_GAME = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_difficulty)
    }

    fun onClickEasyDifficulty(buttonView: View)  {
        try {
            val innerClassObject = DownloadingQuestionDataTask()
            innerClassObject.execute("Easy")
        } catch(e: Exception) {
            Log.i("DATA", "Noooooo")
            e.printStackTrace()
        }
    }

    fun onClickNormalDifficulty(buttonView: View) {
        try {
            val innerClassObject = DownloadingQuestionDataTask()
            innerClassObject.execute("Normal")
        } catch(e: Exception) {
            Log.i("DATA", "Noooooo")
            e.printStackTrace()
        }
    }

    fun onClickHardDifficulty(buttonView: View) {
        try {
            val innerClassObject = DownloadingQuestionDataTask()
            innerClassObject.execute("Hard")
        } catch(e: Exception) {
            Log.i("DATA", "Noooooo")
            e.printStackTrace()
        }
    }

    fun onClickVeryHardDifficulty(buttonView: View) {
        try {
            val innerClassObject = DownloadingQuestionDataTask()
            innerClassObject.execute("Very Hard")
        } catch(e: Exception) {
            Log.i("DATA", "Noooooo")
            e.printStackTrace()
        }
    }

    inner class DownloadingQuestionDataTask: AsyncTask<String, Int, List<Person>>() {
        override fun doInBackground(vararg params: String?): List<Person>? { //called by execute
            val parsePerson = ParsePersonUtility()
            var pages = 0
            when (params[0]) {
                // There is no page 1 in the api
                "Easy" -> pages = 0
                "Normal" -> pages = 2
                "Hard" -> pages = 3
                "Very Hard" -> pages = 3
            }

            Log.i("PAGES", pages.toString())
            return parsePerson.parsePeopleFromJSONData(pages)
        }

        override fun onPostExecute(result: List<Person>?) {
            super.onPostExecute(result)

            val list = ArrayList<Person>()
            result!!.map { person -> list.add(person)  }
            val array = arrayOfNulls<Person>(result!!.size)
            var readyArray = list.toArray(array)

            val gameIntent = Intent(this@Difficulty, Game::class.java)
            gameIntent.putExtra("PEOPLE", readyArray)
            startActivityForResult(gameIntent, START_GAME)
        }
    }
}