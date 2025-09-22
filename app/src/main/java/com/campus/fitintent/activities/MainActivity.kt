package com.campus.fitintent.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ActivityMainBinding
import com.campus.fitintent.fragments.*
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigation()

        // Load the default fragment
        if (savedInstanceState == null) {
            loadFragment(DashboardFragment(), "dashboard")
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(DashboardFragment(), "dashboard")
                    true
                }
                R.id.nav_workouts -> {
                    loadFragment(WorkoutFragment(), "workouts")
                    true
                }
                R.id.nav_nutrition -> {
                    loadFragment(NutritionFragment(), "nutrition")
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment(), "profile")
                    true
                }
                else -> false
            }
        })
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        // Check if the fragment is already loaded
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)

        if (existingFragment != null && existingFragment == activeFragment) {
            return // Fragment is already active
        }

        val transaction = supportFragmentManager.beginTransaction()

        // Hide the currently active fragment
        activeFragment?.let {
            transaction.hide(it)
        }

        if (existingFragment != null) {
            // Show the existing fragment
            transaction.show(existingFragment)
            activeFragment = existingFragment
        } else {
            // Add the new fragment
            transaction.add(R.id.fragmentContainer, fragment, tag)
            activeFragment = fragment
        }

        transaction.commit()
    }

    override fun onBackPressed() {
        // If not on home fragment, go to home
        if (binding.bottomNavigation.selectedItemId != R.id.nav_home) {
            binding.bottomNavigation.selectedItemId = R.id.nav_home
        } else {
            super.onBackPressed()
        }
    }
}