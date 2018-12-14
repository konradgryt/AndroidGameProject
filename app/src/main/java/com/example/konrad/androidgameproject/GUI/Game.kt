package com.example.konrad.androidgameproject.GUI

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import com.example.konrad.androidgameproject.Model.Entry
import com.example.konrad.androidgameproject.Model.TYPE
import com.example.konrad.androidgameproject.R
import com.example.konrad.androidgameproject.Service.DownloadingObject
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {

    //Instance vars
    var correctAnswerIndex: Int = 0
    var correctEntry: Entry? = null

    var numberOfTimesUserAnsweredCorrectly: Int = 0
    var userAnsweredIncorrectly: Int = 0

    var currentScore: Int = 0
    var quizState: QUIZ_STATE = QUIZ_STATE.WAITING
    var type: TYPE? = null

    enum class QUIZ_STATE {
        WAITING, ANSWERED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        txtState.text = "Click 'generate question' when you are ready!"

        Category.areCharactersOn
        val shared = getSharedPreferences("App_settings", MODE_PRIVATE)

        txtHighScore.text = shared.getInt("highscore", 0).toString()
        txtCurrentScore.text = currentScore.toString()

        var difficulty = intent.getStringExtra("DIFFICULTY")
        generateQuiz(difficulty, selectCategory(Category.getActiveCategories()))

        btnNextQuestion.setOnClickListener{
            if (checkForInternetConnection()) {
                var difficulty = intent.getStringExtra("DIFFICULTY")

                Log.i("WHAT", difficulty)
                generateQuiz(difficulty, selectCategory(Category.getActiveCategories()))
            }
        }
    }

    fun selectCategory(categories: Array<String>): String {
        val randomCategoryIndex = (Math.random() * categories!!.size).toInt()
        return categories[randomCategoryIndex]
    }

    fun generateQuiz(difficulty: String, type: String) {
        quizState = QUIZ_STATE.WAITING
        txtState.text = "Select right $type"

        var questionData: Array<Entry>? = null
        if (type == "person") {
            questionData = intent.getSerializableExtra("PEOPLE") as Array<Entry>
            questionData.map { i -> i.generateId() }
        } else if (type == "planet") {
            questionData = intent.getSerializableExtra("PLANETS") as Array<Entry>
            questionData.map { i -> i.generateId() }
        } else if (type == "vehicle") {
            questionData = intent.getSerializableExtra("VEHICLES") as Array<Entry>
            questionData.map { i -> i.generateId() }
        } else if (type == "species") {
            questionData = intent.getSerializableExtra("SPECIES") as Array<Entry>
            questionData.map { i -> i.generateId() }
        }


        val numberOfEntriesInBank = questionData!!.size

        if (difficulty == "Easy" && numberOfEntriesInBank > 0) {
            button1.setVisibility(View.VISIBLE)
            button2.setVisibility(View.VISIBLE)
            val randomIndexForButton1: Int = (Math.random() * questionData!!.size).toInt()
            var randomIndexForButton2: Int = (Math.random() * questionData.size).toInt()
            while (randomIndexForButton1 == randomIndexForButton2) { //randomize again
                randomIndexForButton2 = (Math.random() * questionData.size).toInt()
            }
            val possibleAnswers = ArrayList<Entry>()
            possibleAnswers.add(questionData.get(randomIndexForButton1))
            possibleAnswers.add(questionData.get(randomIndexForButton2))
            button1.text = questionData.get(randomIndexForButton1).toString()
            button2.text = questionData.get(randomIndexForButton2).toString()

            correctAnswerIndex = (Math.random() * possibleAnswers.size).toInt()
            correctEntry = possibleAnswers.get(correctAnswerIndex)

            val downloadingImageTask = DownloadingImageTask()
            downloadingImageTask.execute(correctEntry!!.type)

        }
        else if (difficulty == "Normal" && numberOfEntriesInBank > 0) {
            button1.setVisibility(View.VISIBLE)
            button2.setVisibility(View.VISIBLE)
            button3.setVisibility(View.VISIBLE)
            val randomIndexForButton1: Int = (Math.random() * questionData!!.size).toInt()
            var randomIndexForButton2: Int = (Math.random() * questionData.size).toInt()
            var randomIndexForButton3: Int = (Math.random() * questionData.size).toInt()
            while (randomIndexForButton1 == randomIndexForButton2) { //randomize again
                randomIndexForButton2 = (Math.random() * questionData.size).toInt()
                while (randomIndexForButton2 == randomIndexForButton3 || randomIndexForButton1 == randomIndexForButton3) { //randomize again
                    randomIndexForButton3 = (Math.random() * questionData.size).toInt()
                }
            }
            val possibleAnswers = ArrayList<Entry>()
            possibleAnswers.add(questionData.get(randomIndexForButton1))
            possibleAnswers.add(questionData.get(randomIndexForButton2))
            possibleAnswers.add(questionData.get(randomIndexForButton3))
            button1.text = questionData.get(randomIndexForButton1).toString()
            button2.text = questionData.get(randomIndexForButton2).toString()
            button3.text = questionData.get(randomIndexForButton3).toString()

            correctAnswerIndex = (Math.random() * possibleAnswers.size).toInt()
            correctEntry = possibleAnswers.get(correctAnswerIndex)

            val downloadingImageTask = DownloadingImageTask()
            downloadingImageTask.execute(correctEntry!!.type)

        }
        else if (difficulty == "Hard" && numberOfEntriesInBank > 0) {
            button1.setVisibility(View.VISIBLE)
            button2.setVisibility(View.VISIBLE)
            button3.setVisibility(View.VISIBLE)
            button4.setVisibility(View.VISIBLE)
            var randomIndexForButton1: Int = (Math.random() * questionData!!.size).toInt()
            var randomIndexForButton2: Int = (Math.random() * questionData.size).toInt()
            var randomIndexForButton3: Int = (Math.random() * questionData.size).toInt()
            var randomIndexForButton4: Int = (Math.random() * questionData.size).toInt()
            while (randomIndexForButton1 == randomIndexForButton2) { //randomize again
                randomIndexForButton2 = (Math.random() * questionData.size).toInt()
                while (randomIndexForButton3 == randomIndexForButton2 || randomIndexForButton3 == randomIndexForButton1) { //randomize again
                    randomIndexForButton3 = (Math.random() * questionData.size).toInt()
                    while (randomIndexForButton4 == randomIndexForButton1 || randomIndexForButton4 == randomIndexForButton2|| randomIndexForButton4 == randomIndexForButton3) { //randomize again
                        randomIndexForButton4 = (Math.random() * questionData.size).toInt()
                    }
                }
            }


            val possibleAnswers = ArrayList<Entry>()
            possibleAnswers.add(questionData!!.get(randomIndexForButton1))
            possibleAnswers.add(questionData.get(randomIndexForButton2))
            possibleAnswers.add(questionData.get(randomIndexForButton3))
            possibleAnswers.add(questionData.get(randomIndexForButton4))
            button1.text = questionData.get(randomIndexForButton1).toString()
            button2.text = questionData.get(randomIndexForButton2).toString()
            button3.text = questionData.get(randomIndexForButton3).toString()
            button4.text = questionData.get(randomIndexForButton4).toString()

            correctAnswerIndex = (Math.random() * possibleAnswers.size).toInt()
            correctEntry = possibleAnswers.get(correctAnswerIndex)

            val downloadingImageTask = DownloadingImageTask()
            downloadingImageTask.execute(correctEntry!!.type)
        }
        var backgroundpic = ContextCompat.getDrawable(this, R.drawable.answerbox)
        button1.background = backgroundpic
        button2.background = backgroundpic
        button3.background = backgroundpic
        button4.background = backgroundpic
    }
    fun button1IsClicked(buttonView: View)  {
        if (quizState == QUIZ_STATE.WAITING) {
            evaluateAnswer(0)
        }
    }

    fun button2IsClicked(buttonView: View) {
        if (quizState == QUIZ_STATE.WAITING) {
            evaluateAnswer(1)
        }
    }

    fun button3IsClicked(buttonView: View) {
        if (quizState == QUIZ_STATE.WAITING) {
            evaluateAnswer(2)
        }
    }

    fun button4IsClicked(buttonView: View) {
        if (quizState == QUIZ_STATE.WAITING) {
            evaluateAnswer(3)
        }
    }

    override fun onStart() {
        super.onStart()

        Toast.makeText(this, "The onStart method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        checkForInternetConnection()
        Toast.makeText(this, "The onResume method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()

        Toast.makeText(this, "The onPause method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()

        Toast.makeText(this, "The onStop method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()

        Toast.makeText(this, "The onRestart method is called ", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "The onDestroy method is called ", Toast.LENGTH_SHORT).show()
    }

    fun imageViewIsClicked(imageView: View) {

        val randomNumber: Int = (Math.random() * 6).toInt() + 1
        Log.i("TAG", "THE RANDOM NUBER IS: $randomNumber")

    }

    // Check for internet connection
    private fun checkForInternetConnection(): Boolean {

        val connectivityManager: ConnectivityManager =
                this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isDeviceConnectedToInternet = networkInfo != null && networkInfo.isConnected
        if (!isDeviceConnectedToInternet) {
            createAlert()
            Log.i("INTERNET","There is no internet")
            return false
        } else {
            Log.i("INTERNET","There is internet")
            return true
        }
    }

    private fun createAlert() {
        val alertDialog: AlertDialog = AlertDialog.Builder(this@Game).create()
        alertDialog.setTitle("Network Error")
        alertDialog.setMessage("Please check internet connection")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
            dialog: DialogInterface?, which: Int ->
            startActivity(Intent(Settings.ACTION_SETTINGS))
        })
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel", {
            dialog: DialogInterface?, which: Int ->
            Toast.makeText(this@Game, " You must be connected to the internet", Toast.LENGTH_SHORT).show()
            finish() // closing current activity
        })

        alertDialog.show()
    }

    private fun evaluateAnswer(userGuess: Int) {
        quizState = QUIZ_STATE.ANSWERED
        if (userGuess != correctAnswerIndex) {
            userAnsweredIncorrectly++
            txtWrongAnswers.text = "$userAnsweredIncorrectly"
            var correctPlantName = correctEntry.toString()
            txtState.text = "Right answer: $correctPlantName"

            var backgroundpic = ContextCompat.getDrawable(this, R.drawable.answerbox_wrong)
            when (userGuess) {
                0 -> button1.setBackground(backgroundpic)
                1 -> button2.setBackground(backgroundpic)
                2 -> button3.setBackground(backgroundpic)
                3 -> button4.setBackground(backgroundpic)
            }
        } else {
            numberOfTimesUserAnsweredCorrectly++
            currentScore++
            val shared = getSharedPreferences("App_settings", MODE_PRIVATE)
            val editor = shared.edit()
            var highscore = shared.getInt("highscore", 0)
            if (currentScore > highscore) {
                editor.putInt("highscore", currentScore)
                editor.apply()
                txtHighScore.setText(currentScore.toString())
            }
            txtCurrentScore.setText(currentScore.toString())

            txtRightAnswers.text = "$numberOfTimesUserAnsweredCorrectly"
            txtState.text = "Right answer!"

            var backgroundpic2 = ContextCompat.getDrawable(this, R.drawable.answerbox_correct)
            when (correctAnswerIndex) {
                0 -> button1.setBackground(backgroundpic2)
                1 -> button2.setBackground(backgroundpic2)
                2 -> button3.setBackground(backgroundpic2)
                3 -> button4.setBackground(backgroundpic2)
            }
        }
    }

    //Downloading Image Process

    inner class DownloadingImageTask: AsyncTask<String, Int, Bitmap?>() {
        override fun doInBackground(vararg imageType: String?): Bitmap? {

            try {
                val downloadingObject = DownloadingObject()

                var bitmap : Bitmap? = null

                var index = correctEntry!!.id
                when (imageType[0]) {
                    "person" -> bitmap = downloadingObject.downloadPicture((index).toString())
                    "planet" -> bitmap = downloadingObject.downloadPicturePlanet((index).toString())
                    "species" -> bitmap = downloadingObject.downloadPictureSpecies((index).toString())
                    "vehicle" -> bitmap = downloadingObject.downloadPictureVehicle((index).toString())
                }
                return bitmap
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            imgTaken.setImageBitmap(result)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setUIWidgets(display:Boolean) {
        if (display) {
            imgTaken.setVisibility(View.VISIBLE)
            button1.setVisibility(View.VISIBLE)
            button2.setVisibility(View.VISIBLE)
            button3.setVisibility(View.VISIBLE)
            button4.setVisibility(View.VISIBLE)
            txtState.setVisibility(View.VISIBLE)
            txtRightAnswers.setVisibility(View.VISIBLE)
            txtWrongAnswers.setVisibility(View.VISIBLE)
            btnNextQuestion.setVisibility(View.VISIBLE) // suppressed
        } else {
            imgTaken.setVisibility(View.GONE)
            button1.setVisibility(View.GONE)
            button2.setVisibility(View.GONE)
            button3.setVisibility(View.GONE)
            button4.setVisibility(View.GONE)
            txtState.setVisibility(View.GONE)
            txtRightAnswers.setVisibility(View.GONE)
            txtWrongAnswers.setVisibility(View.GONE)
            btnNextQuestion.setVisibility(View.GONE) // suppressed
        }
    }

    fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }

}