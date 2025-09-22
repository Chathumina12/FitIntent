package com.campus.fitintent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.R
import com.campus.fitintent.databinding.FragmentNutritionBinding
import com.campus.fitintent.utils.ViewModelFactory
import com.campus.fitintent.viewmodels.NutritionViewModel
import com.google.android.material.snackbar.Snackbar

class NutritionFragment : Fragment() {

    private var _binding: FragmentNutritionBinding? = null
    private val binding get() = _binding!!
    private lateinit var nutritionViewModel: NutritionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutritionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        val app = requireActivity().application as FitIntentApplication
        val factory = ViewModelFactory(nutritionRepository = app.nutritionRepository)
        nutritionViewModel = ViewModelProvider(this, factory)[NutritionViewModel::class.java]

        setupUI()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupUI() {
        // Setup RecyclerViews
        binding.tipsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mealsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun observeViewModel() {
        // Observe daily summary
        nutritionViewModel.dailySummary.observe(viewLifecycleOwner) { summary ->
            binding.caloriesText.text = summary.calories.toString()
            binding.proteinText.text = "${summary.protein}g"
            binding.waterText.text = "${summary.waterGlasses}/8"

            // Update progress bars
            updateProgressBars(summary)

            // Update water tracker
            updateWaterTracker(summary.waterGlasses)
        }

        // Observe nutrition tips
        nutritionViewModel.nutritionTips.observe(viewLifecycleOwner) { tips ->
            // Update tips adapter when implemented
        }

        // Observe meal suggestions
        nutritionViewModel.mealSuggestions.observe(viewLifecycleOwner) { meals ->
            // Update meals adapter when implemented
        }
    }

    private fun setupClickListeners() {
        // Track meal button
        binding.trackMealButton.setOnClickListener {
            showTrackMealDialog()
        }

        // Add water button
        binding.addWaterButton.setOnClickListener {
            nutritionViewModel.addWaterGlass()
            Snackbar.make(binding.root, "Water intake updated", Snackbar.LENGTH_SHORT).show()
        }

        // View all meals
        binding.viewAllMeals.setOnClickListener {
            navigateToAllMeals()
        }
    }

    private fun updateProgressBars(summary: com.campus.fitintent.viewmodels.DailySummary) {
        // Calorie progress
        val calorieProgress = (summary.calories * 100) / summary.calorieGoal
        binding.calorieProgressBar.progress = calorieProgress.coerceAtMost(100)
        binding.calorieProgressText.text = "${summary.calories} / ${summary.calorieGoal} cal"

        // Protein progress
        val proteinProgress = (summary.protein * 100) / summary.proteinGoal
        binding.proteinProgressBar.progress = proteinProgress.coerceAtMost(100)
        binding.proteinProgressText.text = "${summary.protein} / ${summary.proteinGoal} grams"
    }

    private fun updateWaterTracker(glasses: Int) {
        binding.waterProgressLayout.removeAllViews()

        for (i in 1..8) {
            val waterGlassView = ImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.water_glass_size),
                    resources.getDimensionPixelSize(R.dimen.water_glass_size)
                )
                setPadding(4, 4, 4, 4)

                if (i <= glasses) {
                    setImageResource(R.drawable.ic_water_filled)
                } else {
                    setImageResource(R.drawable.ic_water_empty)
                }
                contentDescription = "Glass $i"
            }
            binding.waterProgressLayout.addView(waterGlassView)
        }
    }

    private fun showTrackMealDialog() {
        // Show dialog to track a meal
        Snackbar.make(binding.root, "Opening meal tracker", Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToAllMeals() {
        // Navigate to all meals screen
        Snackbar.make(binding.root, "Opening meal suggestions", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}