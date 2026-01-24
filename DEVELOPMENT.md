# Development Guide

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 17 or later
- Android SDK with minimum API level 24
- Gradle 8.7

### Setup
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the app on an emulator or device

## Architecture Overview

This project follows **Clean Architecture** with **MVVM** pattern:

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│  (ViewModels, Composables, UI)          │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│          Domain Layer                    │
│     (Use Cases, Business Logic)          │
└──────────────┬──────────────────────────┘
               │
               ▼
┌─────────────────────────────────────────┐
│           Data Layer                     │
│  (Repositories, API, Local Storage)      │
└─────────────────────────────────────────┘
```

## Key Components

### 1. Data Layer

#### Models (`data/model/`)
- `Player.kt`: Player entity with id, name, score, color
- `GameSession.kt`: Session entity with timing and cost info
- `TableConfig.kt`: Table configuration including camera URL

#### API (`data/remote/`)
- `BilliardApiService.kt`: Retrofit interface for API calls
- DTOs for request/response mapping

#### Local Storage (`data/local/`)
- `PreferencesManager.kt`: DataStore wrapper for preferences
- `ConfigurationManager.kt`: Configuration management

#### Repository (`data/repository/`)
- `TableRepository.kt`: Interface defining data operations
- `TableRepositoryImpl.kt`: Implementation with API and local storage

### 2. Domain Layer

#### Use Cases (`domain/usecase/`)
Each use case encapsulates a single business operation:
- `GetTableConfigUseCase`: Retrieve table configuration
- `UpdatePlayerScoreUseCase`: Update player score
- `AddPlayerUseCase`: Add new player logic
- `RemovePlayerUseCase`: Remove player validation
- `StartGameSessionUseCase`: Start session
- `EndGameSessionUseCase`: End session

### 3. Presentation Layer

#### ViewModels (`presentation/`)
- `PlayerViewModel`: Manages player list and scores
- `GameSessionViewModel`: Manages timer, cost, KPIs
- `CameraViewModel`: Manages camera stream state

#### UI Components (`presentation/player/components/`)
- `KpiTopBar.kt`: KPI dashboard
- `CameraView.kt`: Video player
- `VideoControls.kt`: Video control buttons
- `PlayerCard.kt`: Player score card
- `PlayerScreen.kt`: Main screen composition

## State Management

### StateFlow Pattern
ViewModels expose state via `StateFlow`:

```kotlin
private val _players = MutableStateFlow<List<Player>>(emptyList())
val players: StateFlow<List<Player>> = _players.asStateFlow()
```

UI observes state with `collectAsState()`:

```kotlin
val players by playerViewModel.players.collectAsState()
```

### State Classes
Use sealed classes for complex states:

```kotlin
sealed class CameraState {
    object Idle : CameraState()
    object Loading : CameraState()
    object Playing : CameraState()
    object Paused : CameraState()
    data class Error(val message: String) : CameraState()
}
```

## Adding New Features

### Adding a New Player Action

1. **Create Use Case** (`domain/usecase/`):
```kotlin
class NewActionUseCase(private val repository: TableRepository) {
    suspend operator fun invoke(params: Params): Result<Response> {
        // Business logic
        return repository.performAction(params)
    }
}
```

2. **Update ViewModel** (`presentation/`):
```kotlin
fun performNewAction(params: Params) {
    viewModelScope.launch {
        _isLoading.value = true
        val result = newActionUseCase(params)
        result.onSuccess { 
            // Handle success
        }
        result.onFailure {
            // Handle error
        }
        _isLoading.value = false
    }
}
```

3. **Update UI** (`presentation/player/components/`):
```kotlin
Button(onClick = { viewModel.performNewAction(params) }) {
    Text("New Action")
}
```

### Adding a New API Endpoint

1. **Add DTO** (`data/remote/dto/`):
```kotlin
data class NewRequest(
    @SerializedName("param")
    val param: String
)

data class NewResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val data: Data?
)
```

2. **Update API Service** (`data/remote/api/`):
```kotlin
@POST("api/new-endpoint")
suspend fun newEndpoint(
    @Body request: NewRequest
): Response<NewResponse>
```

3. **Update Repository**:
```kotlin
suspend fun newOperation(param: String): Result<Data> {
    return try {
        val response = apiService.newEndpoint(NewRequest(param))
        if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!.data!!)
        } else {
            Result.failure(Exception(response.message()))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

## Testing

### Unit Tests
Test ViewModels and Use Cases:

```kotlin
@Test
fun `updateScore increases player score`() {
    // Arrange
    val viewModel = PlayerViewModel(...)
    
    // Act
    viewModel.updateScore(playerId, 5)
    
    // Assert
    val player = viewModel.getPlayer(playerId)
    assertEquals(5, player?.score)
}
```

### UI Tests
Test Composables:

```kotlin
@Test
fun playerCard_displaysCorrectScore() {
    composeTestRule.setContent {
        PlayerCard(
            player = Player(id = "1", name = "Test", score = 42),
            onScoreChange = {}
        )
    }
    
    composeTestRule
        .onNodeWithText("42")
        .assertIsDisplayed()
}
```

## Code Style

### Naming Conventions
- Classes: PascalCase (`PlayerViewModel`)
- Functions: camelCase (`updateScore`)
- Constants: UPPER_SNAKE_CASE (`DEFAULT_PRICE`)
- Private vars: _prefix (`_players`)

### File Organization
- One public class per file
- File name matches class name
- Group related classes in same package

### Comments
- Use KDoc for public APIs
- Inline comments for complex logic
- TODO for future improvements

## Dependencies

### Adding New Dependency

1. Add to `app/build.gradle.kts`:
```kotlin
implementation("group:artifact:version")
```

2. Sync Gradle

3. Update README if it's a major dependency

## Debugging

### Logging
Use Android Logcat with tags:

```kotlin
private const val TAG = "PlayerViewModel"

Log.d(TAG, "Score updated: $newScore")
Log.e(TAG, "Error updating score", exception)
```

### Network Debugging
Enable OkHttp logging:

```kotlin
val logging = HttpLoggingInterceptor()
logging.level = HttpLoggingInterceptor.Level.BODY

val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .build()
```

## Performance

### Best Practices
- Use `remember` for expensive calculations
- Implement proper `key` in `LazyColumn`
- Use `derivedStateOf` for computed state
- Avoid recomposition with `remember` and `mutableStateOf`

### Memory
- Cancel coroutines in `onCleared()`
- Use `viewModelScope` for ViewModel operations
- Release resources in ViewModel cleanup

## Common Issues

### Build Errors
1. **Plugin not found**: Check network connectivity to Google Maven
2. **Version conflicts**: Run `./gradlew dependencies` to check
3. **Sync failed**: Invalidate caches and restart

### Runtime Errors
1. **Compose errors**: Check state hoisting
2. **Network errors**: Verify API configuration
3. **Crashes**: Check Logcat for stack traces

## Resources

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [MVVM Architecture](https://developer.android.com/topic/architecture)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Retrofit](https://square.github.io/retrofit/)
- [ExoPlayer](https://exoplayer.dev/)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write/update tests
5. Submit a pull request

## License

[Add your license information]
