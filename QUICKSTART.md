# Quick Start Guide

## Get Up and Running in 5 Minutes

### Prerequisites Check
```bash
# Check Java (required: JDK 17+)
java -version

# Check Android SDK (required)
echo $ANDROID_HOME
```

### Step 1: Clone and Open
```bash
# Clone the repository
git clone https://github.com/FalconHawk1/billar-app.git
cd billar-app

# Make gradlew executable
chmod +x gradlew
```

### Step 2: Build the App
```bash
# Option A: Build with Gradle
./gradlew assembleDebug

# Option B: Open in Android Studio
# File → Open → Select billar-app folder
# Wait for Gradle sync
# Click Run button
```

### Step 3: Run on Device/Emulator
```bash
# Install on connected device
./gradlew installDebug

# Or use Android Studio's Run button (Shift+F10)
```

## What You'll See

When the app launches:

1. **KPI Dashboard** at the top showing:
   - ⏰ Tiempo: 00:00 (timer starts automatically)
   - 💲 Costo: $0.00 (updates based on time)
   - 📥 Entrada: 0
   - 🎱 Carambolas: 0

2. **Two Initial Players** (left and right):
   - Player 1 (white background)
   - Player 2 (gold background)

3. **Camera View** in center:
   - Placeholder for IP camera stream
   - Video controls at bottom

## Try It Out

### Update Player Scores
- Click **-1** button: Decrease score by 1
- Click **+1** button: Increase score by 1  
- Click **+5** button: Increase score by 5

### Add More Players
Currently hardcoded to 2 players. To add more:
```kotlin
// In PlayerViewModel.kt
playerViewModel.addPlayer()  // Adds Player 3
playerViewModel.addPlayer()  // Adds Player 4
// Up to 6 players supported
```

### Watch the Timer
- Timer starts automatically
- Updates every second
- Cost calculated as: (elapsed_minutes × $5.00/min)

### End Session
- Click **Cerrar Sesión** button (top-right)
- Timer stops
- Final cost displayed

## Configuration

### Change Price Per Minute
```kotlin
// In GameSessionViewModel or via DataStore
sessionViewModel.setPricePerMinute(10.0)  // $10.00/min
```

### Set Camera URL
```kotlin
// In CameraViewModel
cameraViewModel.setCameraUrl("rtsp://camera-ip:554/stream")
```

### Configure Table ID
```kotlin
// Via PreferencesManager
preferencesManager.saveTableId("table_2")
```

## Customization

### Change Player Colors
Edit `utils/Extensions.kt`:
```kotlin
fun getPlayerColor(index: Int): Color {
    return when (index) {
        0 -> Color(0xFFFF0000)  // Red
        1 -> Color(0xFF00FF00)  // Green
        // ... add your colors
    }
}
```

### Change Theme Colors
Edit `ui/theme/Color.kt`:
```kotlin
val BackgroundDark = Color(0xFF000000)  // Pure black
val AccentGold = Color(0xFFFFAA00)      // Orange gold
```

## Troubleshooting

### Build Fails
```bash
# Clean and rebuild
./gradlew clean
./gradlew assembleDebug

# Check connectivity
./verify_build.sh
```

### App Crashes
1. Check Logcat in Android Studio
2. Look for stack traces
3. Common issues:
   - Null pointer: Check state initialization
   - Compose error: Check recomposition

### No Internet Connection Error
This is expected! The app works offline:
- API calls are optional
- All features work locally
- Data can sync when backend is ready

## Next Steps

### Connect to Backend API
1. Update `utils/Constants.kt`:
```kotlin
const val DEFAULT_API_BASE_URL = "https://your-api.com/"
```

2. Implement actual API in backend

3. Update repository calls from dummy to real

### Add Real Video Stream
1. Get RTSP camera URL
2. Set in CameraViewModel:
```kotlin
cameraViewModel.setCameraUrl("rtsp://your-camera:554/stream")
```

3. ExoPlayer will handle playback automatically

### Persist Data
The app uses DataStore for:
- Table configuration
- Camera URL
- Pricing
- Table ID

Data persists across app restarts.

## Development

### Add New Feature
1. Create use case in `domain/usecase/`
2. Update ViewModel in `presentation/`
3. Add UI in `presentation/player/components/`

See [DEVELOPMENT.md](DEVELOPMENT.md) for detailed guide.

### Run Tests
```bash
# Unit tests
./gradlew test

# UI tests
./gradlew connectedAndroidTest
```

## Resources

- **README.md**: Full project documentation
- **UI_STRUCTURE.md**: UI layout and design
- **DEVELOPMENT.md**: Developer guide
- **verify_build.sh**: Build diagnostics

## Support

For issues:
1. Check Logcat for errors
2. Review documentation
3. Check GitHub issues
4. Create new issue with details

## License

[Add license information]

---

**Happy Coding! 🎱**

Built with ❤️ using Jetpack Compose and MVVM architecture.
