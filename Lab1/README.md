# Android Multi-Page App Lab

This Android application demonstrates various Android development concepts including Intents, Foreground Services, BroadcastReceivers, and Content Providers.

## Features

1. **Navigation Setup**
   - Multiple fragments using Navigation Component
   - Four main sections with dedicated functionality

2. **Intents and Deep Linking**
   - Image picker from gallery
   - Instagram Stories sharing via deep linking

3. **Foreground Service**
   - Music player service
   - Persistent notification
   - Play, pause, and stop controls

4. **BroadcastReceiver**
   - Airplane mode toggle detection
   - System event handling

5. **Content Provider**
   - Calendar events fetching
   - RecyclerView display of events

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Run the application on an Android device or emulator

## Requirements

- Android Studio Arctic Fox or newer
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 33 (Android 13)

## Dependencies

- Navigation Component
- Facebook SDK for Instagram sharing
- MediaPlayer for audio playback
- Calendar Provider API

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/lab1/
│   │   │       ├── MainActivity.kt
│   │   │       ├── fragments/
│   │   │       ├── services/
│   │   │       ├── receivers/
│   │   │       └── providers/
│   │   └── res/
│   │       ├── layout/
│   │       ├── navigation/
│   │       └── values/
│   └── test/
└── build.gradle
```

## Demo Video

[Link to demo video will be added]

## License

This project is licensed under the MIT License - see the LICENSE file for details. 