package com.example.bullseye

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bullseye.databinding.ActivityMainBinding
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // set value for the seekBar
    private var sliderValue = 50
    // set random value for the target
    private var targetValue = newTargetValue()
    private var totalScore = 0
    private var currentRound = 1

    // Create a binding variable that will hold the binding object for the layout file
    private lateinit var binding: ActivityMainBinding // type class naming is based on layout file activity_main.xml

    override fun onCreate(savedInstanceState: Bundle?) {

        // This function is responsible for setting the theme back
        // to the postSplashScreenTheme - fullscreen theme that the main activity uses.
        installSplashScreen()

        //supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        // Convert the xml layout file into its corresponding view objects with inflate()
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Access the root view of layout file and assign to a view variable
        val view = binding.root
        // Set the content view of activity using view binding
        setContentView(view)

        // Start new game when activity launches
        startNewGame()

        // Access the view and change
        // binding.targetTextView.text = "52"
        // binding.hitMeButton.height = 200

        // Add target text update on button click
        //binding.targetTextView.text = targetValue.toString()
        // Add round text update
        //binding.gameRoundTextView?.text = currentRound.toString()

        // Add click interaction to the button - set on click listener
        binding.hitMeButton.setOnClickListener {
            // print message to the Logcat window. i - information
            //Log.i("Button Click Event", "You clicked the HIT ME button")
            showResult()
            totalScore += pointsForCurrentRound()
            binding.gameScoreTextView?.text = totalScore.toString()

        }

        // Start new game on Start Over button click
        binding.startOverButton?.setOnClickListener {
            startNewGame()
        }

        // Open About activity on click on Info button
        binding.infoButton?.setOnClickListener{
            navigateToAboutScreen()
        }

        // Add update for the seekBar
        // object implements the interface - need to add all it's methods
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // methods are overriden from the interface that declared them
            // onProgressChanged method is triggered when the progress of the seekbar is changed
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderValue = progress // set slider value to the current progress point
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

    }

    private fun navigateToAboutScreen(){
        // Create an instance of Intent
        val intent = Intent(this, AboutActivity::class.java)
        // The intention is to start an activity
        startActivity(intent)

    }

    private fun differenceAmount() = abs(targetValue - sliderValue)

    private fun newTargetValue() = Random.nextInt(1, 100)

    // Display a result in a dialog message
    private fun pointsForCurrentRound(): Int {
        val maxScore = 100
        val difference = differenceAmount()
            //abs(targetValue - sliderValue) // absolute - always returns positive value
        var bonusPoints = 0
        if (difference == 0) {
            bonusPoints = 100
        }else if (difference == 1){
            bonusPoints = 50
        }
        return maxScore - difference + bonusPoints
    }

    private fun startNewGame(){
        // set all variables to defaults
        totalScore = 0
        currentRound = 1
        sliderValue = 50
        targetValue = newTargetValue()

        // set text views and SeekBar to the defaults
        binding.targetTextView.text = targetValue.toString()
        binding.gameScoreTextView?.text = totalScore.toString()
        binding.gameRoundTextView?.text = currentRound.toString()
        binding.seekBar.progress = sliderValue

    }

    private fun showResult() {

        // The R class is generated when the app is compiled.
        // It creates numeric vals that allow to get the contents inside the res folder
        // - IDs of views, strings and layouts.
        val dialogTitle = alertDialog() // getString(R.string.result_dialog_title)
        val dialogMessage =
            getString(R.string.result_dialog_message, sliderValue, pointsForCurrentRound())
        // val dialogMessage = "The slider value is $sliderValue"

        // Declare Alert Dialog Builder - builds dialog
        val builder = AlertDialog.Builder(this)

        // Set dialog title and message
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        // Set action on dialog button click
        builder.setPositiveButton(R.string.result_dialog_button_text) { dialog, _ ->
            dialog.dismiss()

            // Generate new target value whenever player dismisses the dialog
            targetValue = newTargetValue()
            binding.targetTextView.text = targetValue.toString()

            // Update the round on dialog button click
            currentRound += 1
            binding.gameRoundTextView?.text = currentRound.toString()
        }
        // Create and show the dialog
        builder.create().show()
    }

    private fun alertDialog(): String {
        val difference = differenceAmount()
            //abs(targetValue - sliderValue)

        val title: String = when {
            difference == 0 -> { getString(R.string.alert_title_1) }
            difference < 5 -> { getString(R.string.alert_title_2) }
            difference <= 10 -> { getString(R.string.alert_title_3) }
            else -> { getString(R.string.alert_title_4) }
        }

        return title
    }

}