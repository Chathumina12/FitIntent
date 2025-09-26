package com.campus.fitintent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import android.widget.ImageView
=======
>>>>>>> 818ab1f (Updated)
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.campus.fitintent.FitIntentApplication
import com.campus.fitintent.R
<<<<<<< HEAD
import com.campus.fitintent.databinding.FragmentNutritionBinding
import com.campus.fitintent.utils.ViewModelFactory
import com.campus.fitintent.viewmodels.NutritionViewModel
import com.google.android.material.snackbar.Snackbar
=======
import com.campus.fitintent.adapters.NutritionAdapter
import com.campus.fitintent.adapters.filterByCategory
import com.campus.fitintent.adapters.searchByQuery
import com.campus.fitintent.databinding.FragmentNutritionBinding
import com.campus.fitintent.models.*
import com.campus.fitintent.viewmodels.ViewModelFactory
import com.campus.fitintent.viewmodels.NutritionViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import java.util.*
>>>>>>> 818ab1f (Updated)

class NutritionFragment : Fragment() {

    private var _binding: FragmentNutritionBinding? = null
    private val binding get() = _binding!!
    private lateinit var nutritionViewModel: NutritionViewModel
<<<<<<< HEAD
=======
    private lateinit var nutritionAdapter: NutritionAdapter

    private var currentCategory: NutritionCategory? = null
    private var allTips = listOf<NutritionTip>()
    private var userFavorites = setOf<Long>()
    private var currentTodaysTip: NutritionTip? = null
>>>>>>> 818ab1f (Updated)

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
<<<<<<< HEAD
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
=======
        val factory = ViewModelFactory.getInstance(app)
        nutritionViewModel = ViewModelProvider(this, factory)[NutritionViewModel::class.java]

        setupUI()
        setupClickListeners()
        observeViewModel()
        loadInitialData()
    }

    private fun setupUI() {
        // Setup RecyclerView with adapter
        nutritionAdapter = NutritionAdapter(
            onTipClick = { tip ->
                // Handle tip click - expand/collapse or navigate to details
                showTipDetails(tip)
            },
            onFavoriteClick = { tip, isFavorite ->
                handleFavoriteToggle(tip, isFavorite)
            },
            userFavorites = userFavorites
        )

        binding.recyclerViewTips.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = nutritionAdapter
        }

        // Setup category chips
        setupCategoryChips()

        // Initialize loading state
        showLoading()
    }

    private fun setupCategoryChips() {
        binding.chipGroupCategories.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val checkedChip = binding.chipGroupCategories.findViewById<Chip>(checkedIds.first())
                currentCategory = when (checkedChip.id) {
                    R.id.chipCategoryAll -> null
                    R.id.chipCategoryHydration -> NutritionCategory.HYDRATION
                    R.id.chipCategoryProtein -> NutritionCategory.PROTEIN
                    R.id.chipCategoryVitamins -> NutritionCategory.VITAMINS
                    R.id.chipCategoryFiber -> NutritionCategory.FIBER
                    else -> null
                }
                filterTips()
                updateSectionTitle()
            }
>>>>>>> 818ab1f (Updated)
        }
    }

    private fun setupClickListeners() {
<<<<<<< HEAD
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
=======
        // Search button
        binding.btnSearch.setOnClickListener {
            // TODO: Implement search functionality
            showComingSoon("Search functionality")
        }

        // Today's tip favorite button
        binding.btnFavoriteTodaysTip.setOnClickListener {
            currentTodaysTip?.let { tip ->
                val isFavorite = userFavorites.contains(tip.id)
                handleFavoriteToggle(tip, !isFavorite)
            }
        }

        // View favorites card
        binding.cardViewFavorites.setOnClickListener {
            showFavoriteTips()
        }

        // Random tip card
        binding.cardRandomTip.setOnClickListener {
            showRandomTip()
        }

        // Today's tip card click
        binding.cardTodaysTip.setOnClickListener {
            currentTodaysTip?.let { tip ->
                showTipDetails(tip)
            }
        }
    }

    private fun observeViewModel() {
        // Observe loading state
        nutritionViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        // Observe all nutrition tips
        nutritionViewModel.allTips.observe(viewLifecycleOwner) { tips ->
            allTips = tips
            updateTipsDisplay()
        }

        // Observe today's tip
        nutritionViewModel.todaysTip.observe(viewLifecycleOwner) { tip ->
            currentTodaysTip = tip
            tip?.let { updateTodaysTipCard(it) }
        }

        // Observe user favorites
        nutritionViewModel.userFavorites.observe(viewLifecycleOwner) { favorites ->
            userFavorites = favorites
            updateFavoritesCount()
            // Update adapter with new favorites
            nutritionAdapter = NutritionAdapter(
                onTipClick = { tip -> showTipDetails(tip) },
                onFavoriteClick = { tip, isFavorite -> handleFavoriteToggle(tip, isFavorite) },
                userFavorites = userFavorites
            )
            binding.recyclerViewTips.adapter = nutritionAdapter
            updateTipsDisplay()
        }

        // Observe errors
        nutritionViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                showError(it)
            }
        }
    }

    private fun loadInitialData() {
        nutritionViewModel.loadAllTips()
        nutritionViewModel.loadTodaysTip()
        nutritionViewModel.loadUserFavorites()
    }

    private fun updateTodaysTipCard(tip: NutritionTip) {
        binding.apply {
            tvTodaysTipTitle.text = tip.title
            tvTodaysTipDescription.text = tip.description
            chipTodaysTipCategory.text = getCategoryDisplayName(tip.category)

            // Update favorite button state
            val isFavorite = userFavorites.contains(tip.id)
            btnFavoriteTodaysTip.setIconResource(
                if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        }
    }

    private fun updateTipsDisplay() {
        if (allTips.isEmpty()) {
            showEmptyState()
            return
        }

        val filteredTips = allTips
            .filterByCategory(currentCategory)
            .sortedByDescending { it.priority }

        if (filteredTips.isEmpty()) {
            showEmptyState()
        } else {
            hideEmptyState()
            nutritionAdapter.submitList(filteredTips)
        }
    }

    private fun filterTips() {
        updateTipsDisplay()
    }

    private fun updateSectionTitle() {
        val titleText = when (currentCategory) {
            null -> getString(R.string.all_nutrition_tips)
            NutritionCategory.HYDRATION -> getString(R.string.hydration_tips)
            NutritionCategory.PROTEIN -> getString(R.string.protein_tips)
            NutritionCategory.VITAMINS -> getString(R.string.vitamin_tips)
            NutritionCategory.FIBER -> getString(R.string.fiber_tips)
            else -> getString(R.string.all_nutrition_tips)
        }
        binding.tvSectionTitle.text = titleText
    }

    private fun updateFavoritesCount() {
        val count = userFavorites.size
        val countText = if (count == 1) {
            getString(R.string.favorite_tip_singular)
        } else {
            getString(R.string.favorite_tips_plural, count)
        }
        binding.tvFavoritesCount.text = countText
    }

    private fun handleFavoriteToggle(tip: NutritionTip, isFavorite: Boolean) {
        nutritionViewModel.toggleFavorite(tip.id)

        val message = if (isFavorite) {
            getString(R.string.tip_added_to_favorites)
        } else {
            getString(R.string.tip_removed_from_favorites)
        }

        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setAction(getString(R.string.undo)) {
                // Undo the favorite toggle
                nutritionViewModel.toggleFavorite(tip.id)
            }
            .show()
    }

    private fun showTipDetails(tip: NutritionTip) {
        // For now, show a snackbar with tip details
        // In a full implementation, this might navigate to a detail screen
        val message = "${tip.title}\n\n${tip.description}"
        if (!tip.benefits.isNullOrEmpty()) {
            Snackbar.make(binding.root, "Benefits: ${tip.benefits}", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun showFavoriteTips() {
        if (userFavorites.isEmpty()) {
            Snackbar.make(binding.root, getString(R.string.no_favorite_tips_yet), Snackbar.LENGTH_SHORT).show()
            return
        }

        val favoriteTips = allTips.filter { userFavorites.contains(it.id) }
        nutritionAdapter.submitList(favoriteTips)

        // Update section title
        binding.tvSectionTitle.text = getString(R.string.your_favorite_tips)

        // Clear category selection
        binding.chipGroupCategories.clearCheck()
        binding.chipCategoryAll.isChecked = false
        currentCategory = null
    }

    private fun showRandomTip() {
        if (allTips.isEmpty()) {
            showComingSoon("Random tip feature")
            return
        }

        val randomTip = allTips.random()
        showTipDetails(randomTip)

        // Also show it as a snackbar
        Snackbar.make(binding.root, "Random tip: ${randomTip.title}", Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.view)) {
                // Scroll to show this tip in the list
                val tipIndex = allTips.indexOf(randomTip)
                if (tipIndex >= 0) {
                    binding.recyclerViewTips.smoothScrollToPosition(tipIndex)
                }
            }
            .show()
    }

    private fun showLoading() {
        binding.progressLoading.visibility = View.VISIBLE
        binding.recyclerViewTips.visibility = View.GONE
        binding.layoutEmptyState.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressLoading.visibility = View.GONE
        binding.recyclerViewTips.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        binding.layoutEmptyState.visibility = View.VISIBLE
        binding.recyclerViewTips.visibility = View.GONE
    }

    private fun hideEmptyState() {
        binding.layoutEmptyState.visibility = View.GONE
        binding.recyclerViewTips.visibility = View.VISIBLE
    }

    private fun showError(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.retry)) {
                loadInitialData()
            }
            .show()
    }

    private fun showComingSoon(feature: String) {
        Snackbar.make(binding.root, "$feature coming soon!", Snackbar.LENGTH_SHORT).show()
    }

    private fun getCategoryDisplayName(category: NutritionCategory): String {
        return when (category) {
            NutritionCategory.HYDRATION -> getString(R.string.hydration)
            NutritionCategory.PROTEIN -> getString(R.string.protein)
            NutritionCategory.VITAMINS -> getString(R.string.vitamins)
            NutritionCategory.FIBER -> getString(R.string.fiber)
            NutritionCategory.HEALTHY_FATS -> getString(R.string.healthy_fats)
            NutritionCategory.CARBOHYDRATES -> getString(R.string.carbohydrates)
            NutritionCategory.MINERALS -> getString(R.string.minerals)
            NutritionCategory.WEIGHT_MANAGEMENT -> getString(R.string.weight_management)
            NutritionCategory.MEAL_TIMING -> getString(R.string.meal_timing)
            NutritionCategory.FOOD_PREP -> getString(R.string.food_prep)
            NutritionCategory.SUPPLEMENTS -> getString(R.string.supplements)
            NutritionCategory.GENERAL -> getString(R.string.general)
            NutritionCategory.BREAKFAST -> "Breakfast"
            NutritionCategory.LUNCH -> "Lunch"
            NutritionCategory.DINNER -> "Dinner"
            NutritionCategory.SNACK -> "Snack"
            NutritionCategory.HEALTHY_TIPS -> "Healthy Tips"
            NutritionCategory.PRE_WORKOUT -> "Pre-Workout"
            NutritionCategory.POST_WORKOUT -> "Post-Workout"
        }
>>>>>>> 818ab1f (Updated)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}