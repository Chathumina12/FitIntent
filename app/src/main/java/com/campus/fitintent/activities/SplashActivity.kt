package com.campus.fitintent.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ActivitySplashBinding
import com.campus.fitintent.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashDuration = 2000L // 2 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start animations
        startAnimations()

        // Check user session and navigate
        checkUserSessionAndNavigate()
    }

    private fun startAnimations() {
        // Logo fade in and scale animation
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_scale)
        binding.logoImage.startAnimation(fadeIn)

        // App name fade in animation
        val fadeInText = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.appName.startAnimation(fadeInText)

        // Loading indicator animation
        binding.loadingIndicator.animate()
            .alpha(1f)
            .setDuration(500)
            .setStartDelay(500)
            .start()
    }

    private fun checkUserSessionAndNavigate() {
        lifecycleScope.launch {
            val app = application as FitIntentApplication
            val userRepository = app.userRepository

            // Wait for splash duration
            delay(splashDuration)

            try {
                val isLoggedIn = userRepository.isUserLoggedIn()
                val currentUserResult = if (isLoggedIn) userRepository.getCurrentUser() else null
                val isFirstLaunch = userRepository.isFirstLaunch()

                val intent = when {
                    currentUserResult is Result.Success && currentUserResult.data.isOnboardingComplete -> {
                        // User is logged in and has completed onboarding
                        Intent(this@SplashActivity, MainActivity::class.java)
                    }
                    currentUserResult is Result.Success && !currentUserResult.data.isOnboardingComplete -> {
                        // User is logged in but hasn't completed onboarding
                        Intent(this@SplashActivity, OnboardingActivity::class.java)
                    }
                    isFirstLaunch -> {
                        // First time user - show welcome screens
                        Intent(this@SplashActivity, WelcomeActivity::class.java)
                    }
                    else -> {
                        // Returning user, not logged in
                        Intent(this@SplashActivity, AuthActivity::class.java)
                    }
                }

                // Add transition animation
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            } catch (e: Exception) {
                // In case of any error, navigate to Auth screen
                val intent = Intent(this@SplashActivity, AuthActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                finish()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Disable back button during splash screen
    }
}