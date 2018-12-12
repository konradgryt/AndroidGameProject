package com.example.konrad.androidgameproject

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
import com.example.konrad.androidgameproject.Utility.encodeTobase64
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {

    //Instance vars
    var correctAnswerIndex: Int = 0
    var correctPerson: Person? = null
    var numberOfTimesUserAnsweredCorrectly: Int = 0
    var userAnsweredIncorrectly: Int = 0
    var currentScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val shared = getSharedPreferences("App_settings", MODE_PRIVATE)
        val currentAvatar = shared.getString("currentAvatar", "empty")
        val currentNickname = shared.getString("currentNickname", "player")

        txtHighScore.text = shared.getInt("highscore", 0).toString()


        btnNextPlant.setOnClickListener{
            if (checkForInternetConnection()) {
                var difficulty = intent.getStringExtra("DIFFICULTY")

                Log.i("WHAT", difficulty)
                generateQuiz(difficulty)
            }
        }
    }

    fun generateQuiz(difficulty: String) {
        val questionData = intent.getSerializableExtra("PEOPLE") as Array<Person>
        val numberOfPeopleInBank = questionData.size

        if (difficulty == "Easy" && numberOfPeopleInBank > 0) {
            button3.setVisibility(View.GONE)
            button4.setVisibility(View.GONE)
            val randomIndexForButton1: Int = (Math.random() * questionData.size).toInt()
            var randomIndexForButton2: Int = (Math.random() * questionData.size).toInt()
            while (randomIndexForButton1 == randomIndexForButton2) { //randomize again
                randomIndexForButton2 = (Math.random() * questionData.size).toInt()
            }
            val possibleAnswers = ArrayList<Person>()
            possibleAnswers.add(questionData.get(randomIndexForButton1))
            possibleAnswers.add(questionData.get(randomIndexForButton2))
            button1.text = questionData.get(randomIndexForButton1).toString()
            button2.text = questionData.get(randomIndexForButton2).toString()

            correctAnswerIndex = (Math.random() * possibleAnswers.size).toInt()
            correctPerson = possibleAnswers.get(correctAnswerIndex)

            val downloadingImageTask = DownloadingImageTask()
            downloadingImageTask.execute(possibleAnswers.get(correctAnswerIndex).name)

        }
        else if (difficulty == "Normal" && numberOfPeopleInBank > 0) {
            button4.setVisibility(View.GONE)
            val randomIndexForButton1: Int = (Math.random() * questionData.size).toInt()
            var randomIndexForButton2: Int = (Math.random() * questionData.size).toInt()
            var randomIndexForButton3: Int = (Math.random() * questionData.size).toInt()
            while (randomIndexForButton1 == randomIndexForButton2) { //randomize again
                randomIndexForButton2 = (Math.random() * questionData.size).toInt()
                while (randomIndexForButton2 == randomIndexForButton3) { //randomize again
                    randomIndexForButton3 = (Math.random() * questionData.size).toInt()
                }
            }
            val possibleAnswers = ArrayList<Person>()
            possibleAnswers.add(questionData.get(randomIndexForButton1))
            possibleAnswers.add(questionData.get(randomIndexForButton2))
            possibleAnswers.add(questionData.get(randomIndexForButton3))
            button1.text = questionData.get(randomIndexForButton1).toString()
            button2.text = questionData.get(randomIndexForButton2).toString()
            button3.text = questionData.get(randomIndexForButton3).toString()

            correctAnswerIndex = (Math.random() * possibleAnswers.size).toInt()
            correctPerson = possibleAnswers.get(correctAnswerIndex)

            val downloadingImageTask = DownloadingImageTask()
            downloadingImageTask.execute(possibleAnswers.get(correctAnswerIndex).name)

        }
        else if (numberOfPeopleInBank > 0) {
            var randomPlantIndexForButton1: Int = (Math.random() * questionData.size).toInt()
            var randomPlantIndexForButton2: Int = (Math.random() * questionData.size).toInt()
            var randomPlantIndexForButton3: Int = (Math.random() * questionData.size).toInt()
            var randomPlantIndexForButton4: Int = (Math.random() * questionData.size).toInt()

            var allRandomPlants = ArrayList<Person>()
            allRandomPlants.add(questionData.get(randomPlantIndexForButton1))
            allRandomPlants.add(questionData.get(randomPlantIndexForButton2))
            allRandomPlants.add(questionData.get(randomPlantIndexForButton3))
            allRandomPlants.add(questionData.get(randomPlantIndexForButton4))

            button1.text = questionData.get(randomPlantIndexForButton1).toString()
            button2.text = questionData.get(randomPlantIndexForButton2).toString()
            button3.text = questionData.get(randomPlantIndexForButton3).toString()
            button4.text = questionData.get(randomPlantIndexForButton4).toString()

            correctAnswerIndex = (Math.random() * allRandomPlants.size).toInt()
            correctPerson = allRandomPlants.get(correctAnswerIndex)

            val downloadingImageTask = DownloadingImageTask()
            downloadingImageTask.execute(allRandomPlants.get(correctAnswerIndex).name)
        }
        var backgroundpic = ContextCompat.getDrawable(this, R.drawable.answerbox)
        button1.background = backgroundpic
        button2.background = backgroundpic
        button3.background = backgroundpic
        button4.background = backgroundpic
    }
    fun button1IsClicked(buttonView: View)  {
        evaluateAnswer(0)
    }

    fun button2IsClicked(buttonView: View) {
        evaluateAnswer(1)
    }

    fun button3IsClicked(buttonView: View) {
        evaluateAnswer(2)
    }

    fun button4IsClicked(buttonView: View) {
        evaluateAnswer(3)
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
        if (userGuess != correctAnswerIndex) {
            userAnsweredIncorrectly++
            txtWrongAnswers.text = "$userAnsweredIncorrectly"
            var correctPlantName = correctPerson.toString()
            txtState.text = "Wrong. Choose : $correctPlantName"

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
            txtState.text = "Right!"

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
        override fun doInBackground(vararg pictureName: String?): Bitmap? {

            try {
                val downloadingObject = DownloadingObject()
                var index = correctPerson!!.id
                val personBitMap: Bitmap? = downloadingObject.downloadPicture((index).toString())

                return personBitMap
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
            btnNextPlant.setVisibility(View.VISIBLE) // suppressed
        } else {
            imgTaken.setVisibility(View.GONE)
            button1.setVisibility(View.GONE)
            button2.setVisibility(View.GONE)
            button3.setVisibility(View.GONE)
            button4.setVisibility(View.GONE)
            txtState.setVisibility(View.GONE)
            txtRightAnswers.setVisibility(View.GONE)
            txtWrongAnswers.setVisibility(View.GONE)
            btnNextPlant.setVisibility(View.GONE) // suppressed
        }
    }

    fun dpToPx(dp: Float, context: Context): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }

}