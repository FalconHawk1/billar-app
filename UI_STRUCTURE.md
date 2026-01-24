# UI Structure Documentation

## Screen Layout

```
┌─────────────────────────────────────────────────────────────────────────┐
│ Mesa de Jugadores                                    [Cerrar Sesión]    │
│ Bienvenido, Player User                                                  │
│                                                                          │
│ ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐                │
│ │  🕐      │  │  💲      │  │  📥      │  │  🎱      │                │
│ │  Tiempo  │  │  Costo   │  │  Entrada │  │Carambolas│                │
│ │  00:00   │  │  $0.00   │  │    0     │  │    0     │                │
│ └──────────┘  └──────────┘  └──────────┘  └──────────┘                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                          │
│  ┌───────────┐    ┌──────────────────────┐    ┌───────────┐           │
│  │           │    │                      │    │           │           │
│  │ Player 1  │    │   [LIVE] 🔴          │    │ Player 2  │           │
│  │  (White)  │    │                      │    │  (Gold)   │           │
│  │           │    │                      │    │           │           │
│  │    0      │    │   VIDEO STREAM       │    │    0      │           │
│  │           │    │     AREA             │    │           │           │
│  │  [-1]     │    │   (Camera View)      │    │  [-1]     │           │
│  │  [+1]     │    │                      │    │  [+1]     │           │
│  │  [+5]     │    │                      │    │  [+5]     │           │
│  │           │    │                      │    │           │           │
│  └───────────┘    │ ▶ ⟲ ⏺ | 12:34:56 | 🔴│    └───────────┘           │
│                   └──────────────────────┘                              │
│                                                                          │
└─────────────────────────────────────────────────────────────────────────┘
```

## Component Breakdown

### 1. KpiTopBar (Top Section)
- **Header**:
  - Title: "Mesa de Jugadores" (gold color)
  - Subtitle: "Bienvenido, Player User"
  - Close Session Button (red)

- **KPI Cards** (4 cards in a row):
  - **Tiempo**: Clock icon + Time in HH:MM format
  - **Costo**: Money icon + Cost in $0.00 format
  - **Entrada**: Login icon + Entry count
  - **Carambolas**: Sports icon + Carambolas count

### 2. Main Content Area (3-column layout)

#### Left Column - Player Cards (First Half)
- Player 1 (White background)
- Player 3 (Light Green) - if added
- Player 5 (Light Pink) - if added

#### Center Column - Camera View
- **Video Display**:
  - Black background
  - LIVE indicator (top-left, red badge)
  - Recording indicator (top-right, red dot if recording)
  - Loading state with spinner
  - Error state with icon
  - Idle state with camera icon

- **Video Controls** (bottom bar):
  - Play/Pause button
  - Rewind button
  - Record button (red when active)
  - Timestamp display (center)
  - "EN VIVO" button (right)

#### Right Column - Player Cards (Second Half)
- Player 2 (Gold/Yellow background)
- Player 4 (Light Blue) - if added
- Player 6 (Plum) - if added

### 3. Player Card Structure

```
┌─────────────────┐
│  Jugador 1      │  ← Player name
│                 │
│    ┌─────┐      │
│    │ 42  │      │  ← Score display (large, centered)
│    └─────┘      │
│                 │
│  [-1]    [+1]   │  ← Score buttons (red, green)
│     [+5]        │  ← +5 button (blue)
└─────────────────┘
```

## Color Scheme

### Background
- Main: `#1a1f2e` (Dark navy/blue)
- Cards: `#2a2f3e` (Slightly lighter)

### Accent Colors
- Primary: `#9C27B0` (Purple)
- Secondary: `#FFD700` (Gold)

### Player Colors
1. Player 1: `#FFFFFF` (White)
2. Player 2: `#FFD700` (Gold)
3. Player 3: `#90EE90` (Light Green)
4. Player 4: `#ADD8E6` (Light Blue)
5. Player 5: `#FFB6C1` (Light Pink)
6. Player 6: `#DDA0DD` (Plum)

### Button Colors
- Score -1: `#EF5350` (Red)
- Score +1: `#66BB6A` (Green)
- Score +5: `#42A5F5` (Blue)

## Responsive Behavior

### Layout Weights
- Left player column: `weight(1f)` (~25%)
- Center video area: `weight(2f)` (~50%)
- Right player column: `weight(1f)` (~25%)

### Player Distribution
- Players are distributed evenly between left and right columns
- Left column: Players 1, 3, 5 (odd indices)
- Right column: Players 2, 4, 6 (even indices)

### Dynamic Features
- Add player: Adds to next available slot (up to 6)
- Remove player: Removes last player (minimum 2 required)
- Score updates: Immediate local update + API sync

## States

### Camera States
- `Idle`: Initial state, shows camera icon
- `Loading`: Shows spinner with "Cargando stream..."
- `Playing`: Shows video stream
- `Paused`: Video paused
- `Error`: Shows error icon with message

### Session States
- Active: Timer running, cost calculating
- Ended: Timer stopped, final costs displayed

### Player States
- Score can be 0 or positive
- Color changes based on player index
- Maximum 6 players supported
