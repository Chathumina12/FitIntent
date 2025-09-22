# FitIntent ProGuard Rules

# Keep application class
-keep public class com.campus.fitintent.FitIntentApplication

# Keep data models
-keep class com.campus.fitintent.models.** { *; }

# Keep Room entities, DAOs, and database
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao class * { *; }
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.TypeConverters class * { *; }

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Keep custom views and widgets
-keep class com.campus.fitintent.widgets.** { *; }

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# BCrypt
-keep class org.mindrot.jbcrypt.** { *; }

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
}

# Keep attributes for debugging
-keepattributes SourceFile,LineNumberTable

# Keep generic type information for Gson/Jackson if used
-keepattributes Signature
-keepattributes *Annotation*

# Obfuscate package names
-repackageclasses 'com.campus.fitintent'