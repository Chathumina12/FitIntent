package com.campus.fitintent.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.campus.fitintent.R
import com.campus.fitintent.databinding.ItemNutritionTipBinding
import com.campus.fitintent.models.*

/**
 * Adapter for displaying nutrition tips in RecyclerView
 * Handles tip display, favorites, and sharing functionality
 */
class NutritionAdapter(
    private val onTipClick: (NutritionTip) -> Unit,
    private val onFavoriteClick: (NutritionTip, Boolean) -> Unit,
    private val userFavorites: Set<Long> = emptySet()
) : ListAdapter<NutritionTip, NutritionAdapter.NutritionTipViewHolder>(NutritionTipDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NutritionTipViewHolder {
        val binding = ItemNutritionTipBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NutritionTipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NutritionTipViewHolder, position: Int) {
        val tip = getItem(position)
        val isFavorite = userFavorites.contains(tip.id)
        holder.bind(tip, isFavorite)
    }

    inner class NutritionTipViewHolder(
        private val binding: ItemNutritionTipBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var isExpanded = false

        fun bind(tip: NutritionTip, isFavorite: Boolean) {
            with(binding) {
                // Basic tip info
                tvTipTitle.text = tip.title
                tvTipDescription.text = tip.description

                // Category info
                chipCategory.text = getCategoryDisplayName(tip.category)

                // Difficulty
                tvDifficulty.text = getDifficultyDisplayName(tip.difficulty)

                // Benefits (initially hidden)
                if (!tip.benefits.isNullOrEmpty()) {
                    tvBenefits.text = tip.benefits
                    layoutBenefits.visibility = if (isExpanded) View.VISIBLE else View.GONE
                } else {
                    layoutBenefits.visibility = View.GONE
                }

                // Category icon and color
                imgCategoryIcon.setImageResource(getCategoryIcon(tip.category))
                cardCategoryIcon.setCardBackgroundColor(getCategoryColor(tip.category))

                // Favorite state
                updateFavoriteButton(isFavorite)

                // Click handlers
                root.setOnClickListener {
                    onTipClick(tip)
                    toggleExpansion()
                }

                btnFavorite.setOnClickListener {
                    val newFavoriteState = !isFavorite
                    onFavoriteClick(tip, newFavoriteState)
                    updateFavoriteButton(newFavoriteState)
                }

                btnShare.setOnClickListener {
                    shareTip(tip)
                }
            }
        }

        private fun toggleExpansion() {
            isExpanded = !isExpanded
            binding.layoutBenefits.visibility = if (isExpanded) View.VISIBLE else View.GONE
        }

        private fun updateFavoriteButton(isFavorite: Boolean) {
            with(binding.btnFavorite) {
                if (isFavorite) {
                    setIconResource(R.drawable.ic_favorite)
                    iconTint = context.getColorStateList(R.color.red_primary)
                } else {
                    setIconResource(R.drawable.ic_favorite_border)
                    iconTint = context.getColorStateList(R.color.on_surface_variant)
                }
            }
        }

        private fun shareTip(tip: NutritionTip) {
            val shareText = buildString {
                append("💡 ${tip.title}\n\n")
                append("${tip.description}\n\n")
                if (!tip.benefits.isNullOrEmpty()) {
                    append("🌟 Benefits:\n${tip.benefits}\n\n")
                }
                append("📱 Shared from FitIntent")
            }

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, shareText)
                putExtra(Intent.EXTRA_SUBJECT, "Nutrition Tip: ${tip.title}")
            }

            val context = binding.root.context
            context.startActivity(
                Intent.createChooser(shareIntent, context.getString(R.string.share_tip))
            )
        }

        private fun getCategoryDisplayName(category: NutritionCategory): String {
            return when (category) {
                NutritionCategory.HYDRATION -> binding.root.context.getString(R.string.hydration)
                NutritionCategory.PROTEIN -> binding.root.context.getString(R.string.protein)
                NutritionCategory.VITAMINS -> binding.root.context.getString(R.string.vitamins)
                NutritionCategory.FIBER -> binding.root.context.getString(R.string.fiber)
                NutritionCategory.HEALTHY_FATS -> binding.root.context.getString(R.string.healthy_fats)
                NutritionCategory.CARBOHYDRATES -> binding.root.context.getString(R.string.carbohydrates)
                NutritionCategory.MINERALS -> binding.root.context.getString(R.string.minerals)
                NutritionCategory.WEIGHT_MANAGEMENT -> binding.root.context.getString(R.string.weight_management)
                NutritionCategory.MEAL_TIMING -> binding.root.context.getString(R.string.meal_timing)
                NutritionCategory.FOOD_PREP -> binding.root.context.getString(R.string.food_prep)
                NutritionCategory.SUPPLEMENTS -> binding.root.context.getString(R.string.supplements)
                NutritionCategory.GENERAL -> binding.root.context.getString(R.string.general)
                NutritionCategory.BREAKFAST -> "Breakfast"
                NutritionCategory.LUNCH -> "Lunch"
                NutritionCategory.DINNER -> "Dinner"
                NutritionCategory.SNACK -> "Snack"
                NutritionCategory.HEALTHY_TIPS -> "Healthy Tips"
                NutritionCategory.PRE_WORKOUT -> "Pre-Workout"
                NutritionCategory.POST_WORKOUT -> "Post-Workout"
            }
        }

        private fun getDifficultyDisplayName(difficulty: TipDifficulty): String {
            return when (difficulty) {
                TipDifficulty.EASY -> binding.root.context.getString(R.string.difficulty_easy)
                TipDifficulty.MEDIUM -> binding.root.context.getString(R.string.difficulty_medium)
                TipDifficulty.ADVANCED -> binding.root.context.getString(R.string.difficulty_advanced)
            }
        }

        private fun getCategoryIcon(category: NutritionCategory): Int {
            return when (category) {
                NutritionCategory.HYDRATION -> R.drawable.ic_water_drop
                NutritionCategory.PROTEIN -> R.drawable.ic_egg
                NutritionCategory.VITAMINS -> R.drawable.ic_vitamin
                NutritionCategory.FIBER -> R.drawable.ic_grain
                NutritionCategory.HEALTHY_FATS -> R.drawable.ic_avocado
                NutritionCategory.CARBOHYDRATES -> R.drawable.ic_bread
                NutritionCategory.MINERALS -> R.drawable.ic_mineral
                NutritionCategory.WEIGHT_MANAGEMENT -> R.drawable.ic_scale
                NutritionCategory.MEAL_TIMING -> R.drawable.ic_schedule
                NutritionCategory.FOOD_PREP -> R.drawable.ic_kitchen
                NutritionCategory.SUPPLEMENTS -> R.drawable.ic_pills
                NutritionCategory.GENERAL -> R.drawable.ic_nutrition
                NutritionCategory.BREAKFAST -> R.drawable.ic_sunrise
                NutritionCategory.LUNCH -> R.drawable.ic_sunrise
                NutritionCategory.DINNER -> R.drawable.ic_moon
                NutritionCategory.SNACK -> R.drawable.ic_avocado
                NutritionCategory.HEALTHY_TIPS -> R.drawable.ic_tips
                NutritionCategory.PRE_WORKOUT -> R.drawable.ic_fitness
                NutritionCategory.POST_WORKOUT -> R.drawable.ic_muscle
            }
        }

        private fun getCategoryColor(category: NutritionCategory): Int {
            return when (category) {
                NutritionCategory.HYDRATION -> binding.root.context.getColor(R.color.blue_primary)
                NutritionCategory.PROTEIN -> binding.root.context.getColor(R.color.red_primary)
                NutritionCategory.VITAMINS -> binding.root.context.getColor(R.color.orange_primary)
                NutritionCategory.FIBER -> binding.root.context.getColor(R.color.brown_primary)
                NutritionCategory.HEALTHY_FATS -> binding.root.context.getColor(R.color.green_primary)
                NutritionCategory.CARBOHYDRATES -> binding.root.context.getColor(R.color.yellow_primary)
                NutritionCategory.MINERALS -> binding.root.context.getColor(R.color.purple_primary)
                NutritionCategory.WEIGHT_MANAGEMENT -> binding.root.context.getColor(R.color.teal_primary)
                NutritionCategory.MEAL_TIMING -> binding.root.context.getColor(R.color.indigo_primary)
                NutritionCategory.FOOD_PREP -> binding.root.context.getColor(R.color.cyan_primary)
                NutritionCategory.SUPPLEMENTS -> binding.root.context.getColor(R.color.pink_primary)
                NutritionCategory.GENERAL -> binding.root.context.getColor(R.color.green_primary)
                NutritionCategory.BREAKFAST -> binding.root.context.getColor(R.color.yellow_primary)
                NutritionCategory.LUNCH -> binding.root.context.getColor(R.color.orange_primary)
                NutritionCategory.DINNER -> binding.root.context.getColor(R.color.indigo_primary)
                NutritionCategory.SNACK -> binding.root.context.getColor(R.color.green_primary)
                NutritionCategory.HEALTHY_TIPS -> binding.root.context.getColor(R.color.teal_primary)
                NutritionCategory.PRE_WORKOUT -> binding.root.context.getColor(R.color.red_primary)
                NutritionCategory.POST_WORKOUT -> binding.root.context.getColor(R.color.blue_primary)
            }
        }
    }

    private class NutritionTipDiffCallback : DiffUtil.ItemCallback<NutritionTip>() {
        override fun areItemsTheSame(oldItem: NutritionTip, newItem: NutritionTip): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NutritionTip, newItem: NutritionTip): Boolean {
            return oldItem == newItem
        }
    }
}

/**
 * Extension function for converting tips to display format
 */
fun List<NutritionTip>.filterByCategory(category: NutritionCategory?): List<NutritionTip> {
    return if (category == null) this else filter { it.category == category }
}

/**
 * Extension function for filtering tips by difficulty
 */
fun List<NutritionTip>.filterByDifficulty(difficulty: TipDifficulty?): List<NutritionTip> {
    return if (difficulty == null) this else filter { it.difficulty == difficulty }
}

/**
 * Extension function for searching tips
 */
fun List<NutritionTip>.searchByQuery(query: String): List<NutritionTip> {
    if (query.isBlank()) return this
    val lowerQuery = query.lowercase()
    return filter { tip ->
        tip.title.lowercase().contains(lowerQuery) ||
        tip.description.lowercase().contains(lowerQuery) ||
        tip.benefits?.lowercase()?.contains(lowerQuery) == true ||
        tip.tags?.lowercase()?.contains(lowerQuery) == true
    }
}

/**
 * Data class for nutrition tip display with user interaction state
 */
data class NutritionTipDisplay(
    val tip: NutritionTip,
    val isFavorite: Boolean = false,
    val isRead: Boolean = false,
    val isExpanded: Boolean = false
)