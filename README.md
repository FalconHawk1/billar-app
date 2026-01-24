# Billiard Player View Application

This is a comprehensive billiard player view application built with Jetpack Compose following MVVM architecture pattern.

## Architecture

The application follows **MVVM (Model-View-ViewModel)** pattern with clean architecture principles:

### Data Layer (`data/`)
- **Models**: Player, GameSession, TableConfig
- **DTOs**: Request/Response models for API communication
- **API Service**: Retrofit-based BilliardApiService
- **Repository**: TableRepository interface and implementation
- **Local Storage**: PreferencesManager using DataStore, ConfigurationManager

### Domain Layer (`domain/`)
- **Use Cases**:
  - GetTableConfigUseCase
  - UpdatePlayerScoreUseCase
  - AddPlayerUseCase
  - RemovePlayerUseCase
  - StartGameSessionUseCase
  - EndGameSessionUseCase

### Presentation Layer (`presentation/`)
- **ViewModels**:
  - PlayerViewModel: Manages player state and scoring
  - GameSessionViewModel: Manages time, cost calculation, and KPIs
  - CameraViewModel: Manages camera stream state and controls
  
- **UI Components**:
  - PlayerScreen: Main screen container
  - KpiTopBar: Top bar with KPIs (Time, Cost, Entry, Carambolas)
  - CameraView: Video player component with controls
  - PlayerCard: Individual player card with score controls
  - VideoControls: Play, pause, rewind, record, live indicator

### UI Theme (`ui/theme/`)
- Dark theme with navy/dark blue background (#1a1f2e)
- Color palette for players (white, gold, green, blue, pink, plum)
- Button colors (red for -1, green for +1, blue for +5)

### Utils (`utils/`)
- Constants: Application-wide constants
- Extensions: Utility extension functions
- TimeFormatter: Time formatting utilities

## Features

### Player Management
- Support for 2-6 players dynamically
- Each player has:
  - Name/identifier
  - Score display
  - Score controls: -1, +1, +5 buttons
  - Unique color coding

### IP Camera Integration
- Center video display area for IP camera stream
- Support for RTSP/HTTP streams using ExoPlayer (Media3)
- Video controls:
  - Play/Pause button
  - Rewind button
  - Recording button
  - LIVE indicator
  - Timestamp display

### KPI Dashboard
Display metrics in card format:
- **Tiempo** (Time): Playing time in HH:MM format
- **Costo** (Cost): Cost by time in currency format
- **Entrada** (Entry): Entry count
- **Carambolas**: Carambolas count

### Session Management
- Automatic session start
- Real-time timer (updates every second)
- Cost calculation based on elapsed time and price per minute
- Session end functionality

## Dependencies

### Core
- Kotlin 1.9.0
- Jetpack Compose (BOM 2024.04.01)
- Material3

### Networking
- Retrofit 2.9.0
- Gson Converter
- OkHttp Logging Interceptor

### Architecture Components
- ViewModel & LiveData (Compose integration)
- Coroutines
- Navigation Compose
- DataStore Preferences

### Media
- Media3 ExoPlayer (with RTSP support)

### Image Loading
- Coil Compose

## Configuration

The app uses DataStore Preferences to store:
- Table ID
- Camera URL
- Price per minute
- API base URL
- Table name

Configuration can be retrieved from API or stored locally.

## API Integration

The app is designed to integrate with a backend API for:
- Starting game sessions
- Updating session data
- Ending sessions
- Updating player scores
- Retrieving table configuration

API endpoints are defined in `BilliardApiService.kt`.

## Building

```bash
./gradlew assembleDebug
```

## Structure

```
co.hitech.billar_app/
├── data/
│   ├── local/
│   │   ├── PreferencesManager.kt
│   │   └── ConfigurationManager.kt
│   ├── remote/
│   │   ├── api/
│   │   │   └── BilliardApiService.kt
│   │   └── dto/
│   │       ├── SessionRequest.kt
│   │       ├── SessionResponse.kt
│   │       ├── ConfigRequest.kt
│   │       └── ConfigResponse.kt
│   ├── model/
│   │   ├── Player.kt
│   │   ├── GameSession.kt
│   │   └── TableConfig.kt
│   └── repository/
│       ├── TableRepository.kt
│       └── TableRepositoryImpl.kt
├── domain/
│   └── usecase/
│       ├── GetTableConfigUseCase.kt
│       ├── UpdatePlayerScoreUseCase.kt
│       ├── AddPlayerUseCase.kt
│       ├── RemovePlayerUseCase.kt
│       ├── StartGameSessionUseCase.kt
│       └── EndGameSessionUseCase.kt
├── presentation/
│   ├── player/
│   │   ├── PlayerScreen.kt
│   │   ├── PlayerViewModel.kt
│   │   └── components/
│   │       ├── KpiTopBar.kt
│   │       ├── CameraView.kt
│   │       ├── PlayerCard.kt
│   │       └── VideoControls.kt
│   ├── session/
│   │   └── GameSessionViewModel.kt
│   └── camera/
│       └── CameraViewModel.kt
├── ui/
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
└── utils/
    ├── Constants.kt
    ├── Extensions.kt
    └── TimeFormatter.kt
```

## Design Notes

- Dark theme with navy/dark blue background
- Semi-transparent cards for KPIs
- Player cards positioned on left and right sides
- Video player in center
- Proper spacing and padding
- Rounded corners on cards and buttons
- Icons for each KPI

## Future Enhancements

- Dependency Injection with Hilt
- Unit and UI tests
- Actual ExoPlayer integration for video streaming
- Backend API implementation
- Analytics and logging
- Offline mode support

## License

[Add your license here]
