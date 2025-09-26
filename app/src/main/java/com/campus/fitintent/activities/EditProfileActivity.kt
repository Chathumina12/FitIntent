package com.campus.fitintent.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ActivityEditProfileBinding

/**
 * Activity for editing user profile information
 * Minimal stub implementation for compilation
 */
class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupClickListeners()
        loadUserData()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.edit_profile)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun setupClickListeners() {
        binding.btnSave.setOnClickListener {
            saveProfile()
        }

        binding.btnCancel.setOnClickListener {
            onBackPressed()
        }

        // Profile image click
        binding.imgProfileImage.setOnClickListener {
            Toast.makeText(this, "Profile image selection coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserData() {
        // TODO: Load user data from repository
        // For now, set placeholder values
        binding.etFullName.setText("John Doe")
        binding.etEmail.setText("john.doe@example.com")
        binding.etUsername.setText("johndoe")
    }

    private fun saveProfile() {
        // TODO: Implement profile saving logic
        // For now, just show success message
        Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}