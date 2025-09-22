package com.campus.fitintent.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ActivityAuthBinding
import com.campus.fitintent.fragments.LoginFragment
import com.campus.fitintent.fragments.SignupFragment

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Start with login fragment
        if (savedInstanceState == null) {
            showLoginFragment()
        }
    }

    fun showLoginFragment() {
        replaceFragment(LoginFragment(), "login")
    }

    fun showSignupFragment() {
        replaceFragment(SignupFragment(), "signup")
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.authFragmentContainer, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}