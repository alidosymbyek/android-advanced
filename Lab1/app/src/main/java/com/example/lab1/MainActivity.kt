package com.example.lab1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lab1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the navigation controller
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Set up the action bar with navigation
        setupActionBarWithNavController(navController)

        // Set up bottom navigation
        binding.bottomNavigation.setupWithNavController(navController)

        // Set up button click listeners
        binding.btnInstagramShare.setOnClickListener {
            navController.navigate(R.id.instagramShareFragment)
        }

        binding.btnMusicPlayer.setOnClickListener {
            navController.navigate(R.id.musicPlayerFragment)
        }

        binding.btnSystemEvents.setOnClickListener {
            navController.navigate(R.id.systemEventsFragment)
        }

        binding.btnCalendar.setOnClickListener {
            navController.navigate(R.id.calendarFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
} 