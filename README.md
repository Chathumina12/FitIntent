# FitIntent - Fitness Habit Building App 💪

A comprehensive Android application designed to help users build and maintain healthy fitness habits through gamification, personalized workouts, and nutrition guidance.

## 📱 Features

### Core Functionality
- **User Authentication**: Secure login/signup with BCrypt password hashing
- **Personalized Onboarding**: 5-question quiz to customize user experience
- **21-Day Habit Tracking**: Build lasting habits with streak tracking
- **Workout Library**: Pre-built workouts with timer functionality
- **Nutrition Tips**: Daily nutrition guidance and tips
- **Gamification**: Points, badges, and rewards system
- **Progress Dashboard**: Visual tracking of fitness journey

### Key Screens
1. **Splash Screen** - App branding and loading
2. **Authentication** - Login/Signup with validation
3. **Onboarding Quiz** - Personalization questions
4. **Dashboard** - Daily progress and quick actions
5. **Habits** - Track and manage daily habits
6. **Workouts** - Browse and perform workouts
7. **Nutrition** - Daily tips and guidance
8. **Rewards** - Badges and achievements
9. **Profile** - User settings and statistics

## 🛠 Technical Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room with SQLite
- **UI**: Material Design 3
- **Async**: Kotlin Coroutines
- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)

## 📋 Prerequisites

- Android Studio Hedgehog (2023.1.1) or newer
- JDK 11 or higher
- Android SDK with API 34
- Kotlin 1.9.20 or higher

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/fitintent.git
cd fitintent
```

### 2. Open in Android Studio
1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the FitIntent folder
4. Click "OK"

### 3. Configure SDK Path
Update `local.properties` with your Android SDK path:
```properties
sdk.dir=C:\\Users\\YOUR_USERNAME\\AppData\\Local\\Android\\Sdk
```

### 4. Sync Project
- Click "Sync Project with Gradle Files" in Android Studio
- Wait for dependencies to download

### 5. Run the App
- Connect an Android device or start an emulator (API 24+)
- Click "Run" (Shift+F10)

## 📂 Project Structure

```
FitIntent/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/campus/fitintent/
│   │   │   │   ├── activities/        # Activity classes
│   │   │   │   ├── fragments/         # Fragment classes
│   │   │   │   ├── viewmodels/        # MVVM ViewModels
│   │   │   │   ├── repository/        # Data repositories
│   │   │   │   ├── models/            # Data models
│   │   │   │   ├── database/          # Room database
│   │   │   │   ├── utils/             # Utility classes
│   │   │   │   └── adapters/          # RecyclerView adapters
│   │   │   └── res/
│   │   │       ├── layout/            # XML layouts
│   │   │       ├── values/            # Resources (strings, colors, etc.)
│   │   │       └── drawable/          # Images and drawables
│   │   ├── test/                      # Unit tests
│   │   └── androidTest/                # Instrumented tests
│   └── build.gradle                    # App-level build configuration
├── gradle/                             # Gradle wrapper
├── build.gradle                        # Project-level build configuration
└── settings.gradle                     # Project settings
```

## 🏃‍♂️ Pre-built Workouts

The app includes 4 pre-configured workout routines:

1. **Morning HIIT** (15 minutes)
   - High-intensity interval training
   - Perfect for quick morning sessions

2. **Full Body Strength** (30 minutes)
   - Comprehensive strength training
   - Targets all major muscle groups

3. **Flexibility Flow** (20 minutes)
   - Stretching and flexibility exercises
   - Great for recovery days

4. **Core Crusher** (15 minutes)
   - Focused core strengthening
   - Build abs and stability

## 🏆 Gamification System

### Points System
- Complete Workout: 50 points
- Complete Daily Habit: 10 points
- Maintain Streak: 5 points/day
- Unlock Badge: 100 points

### Badges
- **Early Bird**: Complete 5 morning workouts
- **Consistency King**: 7-day streak
- **Strength Master**: Complete 10 strength workouts
- **Hydration Hero**: Track water for 14 days
- **Flexibility Pro**: Complete 10 flexibility sessions

## 🔒 Security Features

- BCrypt password hashing with salt
- Encrypted SharedPreferences
- Input validation on all forms
- SQL injection prevention
- ProGuard obfuscation for release builds

## 🧪 Testing

### Run Unit Tests
```bash
./gradlew test
```

### Run Instrumented Tests
```bash
./gradlew connectedAndroidTest
```

### Check Code Coverage
```bash
./gradlew createDebugCoverageReport
```

## 📦 Build & Release

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

The APK will be generated in:
`app/build/outputs/apk/`

## 🐛 Known Issues

- Google Sign-In is currently a placeholder
- Dark mode support is pending
- Limited to portrait orientation

## 🤝 Contributing

This is a campus project. For any contributions or suggestions:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📄 License

This project is created for academic purposes as part of a campus project.

## 👨‍💻 Author

**[Your Name]**
- Email: [your.email@campus.edu]
- Student ID: IT22561602

## 🙏 Acknowledgments

- Campus instructors and mentors
- Material Design guidelines
- Android development community

## 📞 Support

For issues or questions:
- Check the `/planning/` folder for detailed documentation
- Review code comments in source files
- Refer to Android documentation for SDK-specific issues

---

**Version**: 1.0.0
**Last Updated**: 2025-09-21
**Status**: In Development