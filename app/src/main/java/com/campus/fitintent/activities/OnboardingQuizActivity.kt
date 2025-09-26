package com.campus.fitintent.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ActivityOnboardingQuizBinding
// Quiz fragments will be implemented later

/**
 * Activity for handling onboarding quiz flow
 * Minimal stub implementation for compilation
 */
class OnboardingQuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingQuizBinding
    private var currentStep = 1
    private val totalSteps = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupClickListeners()

        // Start with first quiz question
        if (savedInstanceState == null) {
            showQuizStep(1)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Personalization Quiz"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupClickListeners() {
        binding.btnNext.setOnClickListener {
            if (currentStep < totalSteps) {
                nextStep()
            } else {
                finishQuiz()
            }
        }

        binding.btnPrevious.setOnClickListener {
            if (currentStep > 1) {
                previousStep()
            }
        }

        binding.btnSkip.setOnClickListener {
            skipQuiz()
        }
    }

    private fun showQuizStep(step: Int) {
        currentStep = step
        updateProgressBar()
        updateButtons()

        // TODO: Implement quiz fragments
        val fragment = androidx.fragment.app.Fragment()
        // For now, just show a simple fragment

        replaceFragment(fragment)
    }

    private fun updateProgressBar() {
        val progress = (currentStep * 100) / totalSteps
        binding.progressBar.progress = progress
        binding.tvProgress.text = "Step $currentStep of $totalSteps"
    }

    private fun updateButtons() {
        binding.btnPrevious.isEnabled = currentStep > 1
        binding.btnNext.text = if (currentStep == totalSteps) {
            "Finish"
        } else {
            getString(R.string.next)
        }
    }

    private fun nextStep() {
        if (currentStep < totalSteps) {
            showQuizStep(currentStep + 1)
        }
    }

    private fun previousStep() {
        if (currentStep > 1) {
            showQuizStep(currentStep - 1)
        }
    }

    private fun finishQuiz() {
        // TODO: Save quiz results
        Toast.makeText(this, "Quiz completed! Preferences updated.", Toast.LENGTH_LONG).show()

        // Navigate back or to main activity
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun skipQuiz() {
        Toast.makeText(this, "Quiz skipped", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.quizFragmentContainer, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (currentStep > 1) {
            previousStep()
        } else {
            super.onBackPressed()
        }
    }
}