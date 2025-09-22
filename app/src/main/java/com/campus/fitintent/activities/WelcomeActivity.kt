package com.campus.fitintent.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ActivityWelcomeBinding
import com.campus.fitintent.fragments.WelcomeFragment1
import com.campus.fitintent.fragments.WelcomeFragment2
import com.campus.fitintent.fragments.WelcomeFragment3

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var welcomeAdapter: WelcomeAdapter
    private val dotIndicators = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupDotIndicators()
    }

    private fun setupViewPager() {
        welcomeAdapter = WelcomeAdapter(this)
        binding.viewPager.adapter = welcomeAdapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDotIndicators(position)
            }
        })
    }

    private fun setupDotIndicators() {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }

        // Create 3 dot indicators
        for (i in 0 until 3) {
            val dot = ImageView(this).apply {
                this.layoutParams = layoutParams
                setImageDrawable(ContextCompat.getDrawable(this@WelcomeActivity, R.drawable.dot_inactive))
            }
            binding.dotsLayout.addView(dot)
            dotIndicators.add(dot)
        }

        // Set first dot as active
        updateDotIndicators(0)
    }

    private fun updateDotIndicators(position: Int) {
        dotIndicators.forEachIndexed { index, dot ->
            if (index == position) {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_active))
            } else {
                dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_inactive))
            }
        }
    }

    fun navigateToOnboarding() {
        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }

    inner class WelcomeAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> WelcomeFragment1()
                1 -> WelcomeFragment2()
                2 -> WelcomeFragment3()
                else -> WelcomeFragment1()
            }
        }
    }
}