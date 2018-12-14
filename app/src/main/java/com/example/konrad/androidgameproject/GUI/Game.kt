package com.example.konrad.androidgameproject.GUI

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.*
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.konrad.androidgameproject.Model.Entry
import com.example.konrad.androidgameproject.Model.TYPE
import com.example.konrad.androidgameproject.R
import com.example.konrad.androidgameproject.Service.DownloadingObject
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class Game : AppCompatActivity() {

    //Instance vars
    var correctAnswerIndex: Int = 0
    var correctEntry: Entry? = null

    var numberOfTimesUserAnsweredCorrectly: Int = 0
    var userAnsweredIncorrectly: Int = 0

    var currentScore: Int = 0
    var quizState: QUIZ_STATE = QUIZ_STATE.WAITING
    var level: LEVEL? = null
    var type: TYPE? = null

    enum class QUIZ_STATE {
        WAITING, ANSWERED
    }

    private fun playAnimationOnView(view: View?, technique: Techniques) {
        if (Options.areAnimationsOn) {
            YoYo.with(technique).duration(500).repeat(0).playOn(view)
        }
    }

    private fun vibrate() {
        if (Options.areVibrationsOn) {
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                v.vibrate(500)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        txtState.text = "Click 'generate question' when you are ready!"

        val shared = getSharedPreferences("App_settings", MODE_PRIVATE)
        txtHighScore.text = shared.getInt("highscore", 0).toString()
        txtCurrentScore.text = currentScore.toString()

        level = intent.getSerializableExtra("DIFFICULTY") as LEVEL

        if (checkForInternetConnection()) {
            generateQuiz(level, selectCategory(Category.getActiveCategories()))
        }
        btnNextQuestion.setOnClickListener{
            playAnimationOnView(btnNextQuestion, Techniques.Pulse)
            if (checkForInternetConnection()) {
                generateQuiz(level, selectCategory(Category.getActiveCategories()))
            }
        }
    }

    fun selectCategory(categories: Array<String>): String {
        val randomCategoryIndex = (Math.random() * categories!!.size).toInt()
        return categories[randomCategoryIndex]
    }

    fun generateQuiz(level: LEVEL?, type: String) {
        quizState = QUIZ_STATE.WAITING
        txtState.text = "Category: $type"

        var questionData: Array<Entry>? = null
        when (type) {
            "person" -> {
                questionData = intent.getSerializableExtra("PEOPLE") as Array<Entry>
                questionData.map { i -> i.generateId() }
            }
            "planet" -> {
                questionData = intent.getSerializableExtra("PLANETS") as Array<Entry>
                questionData.map { i -> i.generateId() }
            }
            "vehicle" -> {
                questionData = intent.getSerializableExtra("VEHICLES") as Array<Entry>
                questionData.map { i -> i.generateId() }
            }
            "species" -> {
                questionData = intent.getSerializableExtra("SPECIES") as Array<Entry>
                questionData.map { i -> i.generateId() }
            }
        }

        val numberOfEntriesInBank = questionData!!.size

        val list = ArrayList<Int>()
        for (i in 0 until questionData!!.size) {
            list.add(i)
        }
        list.shuffle()

        if (level == LEVEL.EASY && numberOfEntriesInBank > 0) {

            buttonsEasy.setVisibility(View.VISIBLE)
            val randomIndexForButton1: Int = list[0]
            var randomIndexForButton2: Int = list[1]

            val possibleAnswers = ArrayList<Entry>()
            possibleAnswers.add(questionData.get(randomIndexForButton1))
            possibleAnswers.add(questionData.get(randomIndexForButton2))
            button1easy.text = questionData.get(randomIndexForButton1).toString()
            button2easy.text = questionData.get(randomIndexForButton2).toString()

            correctAnswerIndex = (Math.random() * possibleAnswers.size).toInt()
            correctEntry = possibleAnswers.get(correctAnswerIndex)

            val downloadingImageTask = DownloadingImageTask()
            downloadingImageTask.execute(correctEntry!!.type)

        }
        else if (level == LEVEL.NORMAL && numberOfEntriesInBank > 0) {
            buttonsNormal.setVisibility(View.VISIBLE)
            val randomIndexForButton1: Int = list[0]
            var randomIndexForButton2: Int = list[1]
            var randomIndexForButton3: Int = list[2]

            val possibleAnswers = ArrayList<Entry>()
            possibleAnswers.add(questionData.get(randomIndexForButton1))
            possibleAnswers.add(questionData.get(randomIndexForButton2))
            possibleAnswers.add(questionData.get(randomIndexForButton3))
            button1normal.text = questionData.get(randomIndexForButton1).toString()
            button2normal.text = questionData.get(randomIndexForButton2).toString()
            button3normal.text = questionData.get(randomIndexForButton3).toString()

            correctAnswerIndex = (Math.random() * possibleAnswers.size).toInt()
            correctEntry = possibleAnswers.get(correctAnswerIndex)

            val downloadingImageTask = DownloadingImageTask()
            downloadingImageTask.execute(correctEntry!!.type)

        }
        else if (level == LEVEL.HARD || level == LEVEL.VERY_HARD && numberOfEntriesInBank > 0) {
            buttonsHard.setVisibility(View.VISIBLE)
            val randomIndexForButton1: Int = list[0]
            var randomIndexForButton2: Int = list[1]
            var randomIndexForButton3: Int = list[2]
            var randomIndexForButton4: Int = list[3]

            val possibleAnswers = ArrayList<Entry>()
            possibleAnswers.add(questionData!!.get(randomIndexForButton1))
            possibleAnswers.add(questionData.get(randomIndexForButton2))
            possibleAnswers.add(questionData.get(randomIndexForButton3))
            possibleAnswers.add(questionData.get(randomIndexForButton4))
            button1hard.text = questionData.get(randomIndexForButton1).toString()
            button2hard.text = questionData.get(randomIndexForButton2).toString()
            button3hard.text = questionData.get(randomIndexForButton3).toString()
            button4hard.text = questionData.get(randomIndexForButton4).toString()

            correctAnswerIndex = (Math.random() * possibleAnswers.size).toInt()
            correctEntry = possibleAnswers.get(correctAnswerIndex)

            val downloadingImageTask = DownloadingImageTask()
            downloadingImageTask.execute(correctEntry!!.type)
        }
        var answerboxDrawable = ContextCompat.getDrawable(this, R.drawable.answerbox)
        button1easy.background = answerboxDrawable
        button2easy.background = answerboxDrawable
        button1normal.background = answerboxDrawable
        button2normal.background = answerboxDrawable
        button3normal.background = answerboxDrawable
        button1hard.background = answerboxDrawable
        button2hard.background = answerboxDrawable
        button3hard.background = answerboxDrawable
        button4hard.background = answerboxDrawable
    }
    fun button1IsClicked(buttonView: View)  {
        if (quizState == QUIZ_STATE.WAITING) {
            playAnimationOnView(buttonView, Techniques.Pulse)
            evaluateAnswer(0)
        }
    }

    fun button2IsClicked(buttonView: View) {
        if (quizState == QUIZ_STATE.WAITING) {
            playAnimationOnView(buttonView, Techniques.Pulse)
            evaluateAnswer(1)
        }
    }

    fun button3IsClicked(buttonView: View) {
        if (quizState == QUIZ_STATE.WAITING) {
            playAnimationOnView(buttonView, Techniques.Pulse)
            evaluateAnswer(2)
        }
    }

    fun button4IsClicked(buttonView: View) {
        if (quizState == QUIZ_STATE.WAITING) {
            playAnimationOnView(buttonView, Techniques.Pulse)
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
            vibrate()
            userAnsweredIncorrectly++
            txtWrongAnswers.text = "$userAnsweredIncorrectly"
            var correctEntryName = correctEntry.toString()
            txtState.text = "Correct answer: \n$correctEntryName"

            var answerboxWrongDrawable = ContextCompat.getDrawable(this, R.drawable.answerbox_wrong)
            when (userGuess) {
                0 -> {
                    button1easy.setBackground(answerboxWrongDrawable)
                    button1normal.setBackground(answerboxWrongDrawable)
                    button1hard.setBackground(answerboxWrongDrawable)
                }
                1 -> {
                    button2easy.setBackground(answerboxWrongDrawable)
                    button2normal.setBackground(answerboxWrongDrawable)
                    button2hard.setBackground(answerboxWrongDrawable)
                }
                2 -> {
                    button3normal.setBackground(answerboxWrongDrawable)
                    button3hard.setBackground(answerboxWrongDrawable)
                }
                3 -> button4hard.setBackground(answerboxWrongDrawable)
            }
            if (currentScore > 0) {
                when(level) {
                    LEVEL.EASY -> currentScore--
                    LEVEL.NORMAL -> currentScore -= 2
                    LEVEL.HARD -> currentScore -= 3
                    LEVEL.VERY_HARD -> currentScore -= 4
                }
            }
            playAnimationOnView(txtCurrentScore,Techniques.Shake)
        } else {
            numberOfTimesUserAnsweredCorrectly++
            when(level) {
                LEVEL.EASY -> currentScore++
                LEVEL.NORMAL -> currentScore += 2
                LEVEL.HARD -> currentScore += 3
                LEVEL.VERY_HARD -> currentScore += 4
            }
            val shared = getSharedPreferences("App_settings", MODE_PRIVATE)
            val editor = shared.edit()
            var highscore = shared.getInt("highscore", 0)
            if (currentScore > highscore) {
                editor.putInt("highscore", currentScore)
                editor.apply()
                txtHighScore.setText(currentScore.toString())
                playAnimationOnView(txtHighScore,Techniques.Pulse)
            }


            txtRightAnswers.text = "$numberOfTimesUserAnsweredCorrectly"
            txtState.text = "Correct!"

            var answerboxCorrectDrawable = ContextCompat.getDrawable(this, R.drawable.answerbox_correct)
            when (correctAnswerIndex) {
                0 -> {
                    button1easy.setBackground(answerboxCorrectDrawable)
                    button1normal.setBackground(answerboxCorrectDrawable)
                    button1hard.setBackground(answerboxCorrectDrawable)
                }
                1 -> {
                    button2easy.setBackground(answerboxCorrectDrawable)
                    button2normal.setBackground(answerboxCorrectDrawable)
                    button2hard.setBackground(answerboxCorrectDrawable)
                }
                2 -> {
                    button3normal.setBackground(answerboxCorrectDrawable)
                    button3hard.setBackground(answerboxCorrectDrawable)
                }
                3 -> button4hard.setBackground(answerboxCorrectDrawable)
            }
            playAnimationOnView(txtCurrentScore,Techniques.Pulse)
        }
        txtCurrentScore.text = currentScore.toString()
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
}