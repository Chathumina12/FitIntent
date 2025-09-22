package com.campus.fitintent.database

import androidx.room.TypeConverter
import com.campus.fitintent.models.*
import java.util.Date

/**
 * Type converters for Room database
 * Converts complex types to primitive types that Room can persist
 */
class Converters {
    // Date converters
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Enum converters
    @TypeConverter
    fun fromGender(value: Gender?): String? {
        return value?.name
    }

    @TypeConverter
    fun toGender(value: String?): Gender? {
        return value?.let { Gender.valueOf(it) }
    }

    @TypeConverter
    fun fromActivityLevel(value: ActivityLevel?): String? {
        return value?.name
    }

    @TypeConverter
    fun toActivityLevel(value: String?): ActivityLevel? {
        return value?.let { ActivityLevel.valueOf(it) }
    }

    @TypeConverter
    fun fromFitnessGoal(value: FitnessGoal?): String? {
        return value?.name
    }

    @TypeConverter
    fun toFitnessGoal(value: String?): FitnessGoal? {
        return value?.let { FitnessGoal.valueOf(it) }
    }

    @TypeConverter
    fun fromHabitCategory(value: HabitCategory): String {
        return value.name
    }

    @TypeConverter
    fun toHabitCategory(value: String): HabitCategory {
        return HabitCategory.valueOf(value)
    }

    @TypeConverter
    fun fromHabitFrequency(value: HabitFrequency): String {
        return value.name
    }

    @TypeConverter
    fun toHabitFrequency(value: String): HabitFrequency {
        return HabitFrequency.valueOf(value)
    }

    @TypeConverter
    fun fromHabitLogStatus(value: HabitLogStatus): String {
        return value.name
    }

    @TypeConverter
    fun toHabitLogStatus(value: String): HabitLogStatus {
        return HabitLogStatus.valueOf(value)
    }

    @TypeConverter
    fun fromWorkoutCategory(value: WorkoutCategory): String {
        return value.name
    }

    @TypeConverter
    fun toWorkoutCategory(value: String): WorkoutCategory {
        return WorkoutCategory.valueOf(value)
    }

    @TypeConverter
    fun fromDifficultyLevel(value: DifficultyLevel): String {
        return value.name
    }

    @TypeConverter
    fun toDifficultyLevel(value: String): DifficultyLevel {
        return DifficultyLevel.valueOf(value)
    }

    @TypeConverter
    fun fromStreakType(value: StreakType): String {
        return value.name
    }

    @TypeConverter
    fun toStreakType(value: String): StreakType {
        return StreakType.valueOf(value)
    }

    @TypeConverter
    fun fromBadgeType(value: BadgeType): String {
        return value.name
    }

    @TypeConverter
    fun toBadgeType(value: String): BadgeType {
        return BadgeType.valueOf(value)
    }

    @TypeConverter
    fun fromNutritionCategory(value: NutritionCategory): String {
        return value.name
    }

    @TypeConverter
    fun toNutritionCategory(value: String): NutritionCategory {
        return NutritionCategory.valueOf(value)
    }

    @TypeConverter
    fun fromAnswerType(value: AnswerType): String {
        return value.name
    }

    @TypeConverter
    fun toAnswerType(value: String): AnswerType {
        return AnswerType.valueOf(value)
    }
}