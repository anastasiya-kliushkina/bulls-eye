package com.example.bullseye

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bullseye.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    // Create a binding variable that will hold the binding object for the layout file
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable the ActionBar and display the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        super.onCreate(savedInstanceState)
        // Convert the xml layout file into its corresponding view objects with inflate()
        binding = ActivityAboutBinding.inflate(layoutInflater)

        // Set the content view of activity using view binding
        setContentView(binding.root)

        // Activity title
        title = getString(R.string.about_page_title)

        // Go Back button
        binding.backButton?.setOnClickListener{
            // finish() method signifies that you’re done with this acitivty
            // it closes current activity and navigates back to the activity that started it

            finish()
        }
    }
}