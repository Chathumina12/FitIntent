package com.campus.fitintent.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.databinding.ActivityOnboardingBinding
import com.campus.fitintent.fragments.*
import com.campus.fitintent.utils.ViewModelFactory
import com.campus.fitintent.viewmodels.OnboardingViewModel

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var onboardingViewModel: OnboardingViewModel
    private lateinit var pagerAdapter: OnboardingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        val app = application as FitIntentApplication
        val factory = ViewModelFactory(app.userRepository)
        onboardingViewModel = ViewModelProvider(this, factory)[OnboardingViewModel::class.java]

        setupViewPager()
        observeViewModel()
    }

    private fun setupViewPager() {
        pagerAdapter = OnboardingPagerAdapter(this)
        binding.viewPager.apply {
            adapter = pagerAdapter
            isUserInputEnabled = false // Disable swiping, use buttons for navigation
        }

        // Setup progress indicator
        binding.dotsIndicator.setViewPager2(binding.viewPager)

        // Skip button
        binding.skipButton.setOnClickListener {
            navigateToMainActivity()
        }

        // Next button
        binding.nextButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < pagerAdapter.itemCount - 1) {
                binding.viewPager.currentItem = currentItem + 1
            }
        }

        // Get Started button (shown on last screen)
        binding.getStartedButton.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun observeViewModel() {
        onboardingViewModel.onboardingComplete.observe(this) { isComplete ->
            if (isComplete) {
                navigateToMainActivity()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun updateButtons(position: Int) {
        val isLastPage = position == pagerAdapter.itemCount - 1

        binding.skipButton.visibility = if (isLastPage) View.GONE else View.VISIBLE
        binding.nextButton.visibility = if (isLastPage) View.GONE else View.VISIBLE
        binding.getStartedButton.visibility = if (isLastPage) View.VISIBLE else View.GONE
    }

    inner class OnboardingPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 8 // 3 welcome screens + 5 quiz questions

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> WelcomeFragment1()
                1 -> WelcomeFragment2()
                2 -> WelcomeFragment3()
                3 -> QuizFragment1() // Main goal
                4 -> QuizFragment2() // Exercise frequency
                5 -> QuizFragment3() // Workout preference
                6 -> QuizFragment4() // Fitness level
                7 -> QuizFragment5() // Time availability
                else -> WelcomeFragment1()
            }
        }
    }
}