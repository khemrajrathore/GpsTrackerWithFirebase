# Architecture Overview

## Summary
The application follows a classic Android MVC‑style architecture with Activities acting as Controllers/Views, a background Service (LocationService) handling continuous location updates, and Firebase Realtime Database serving as the Model layer for persisting user profiles, OTPs and location coordinates. Google Play Services are used for location acquisition and map rendering, while the Volley library provides lightweight HTTP communication with custom PHP back‑ends. The app loosely incorporates MVVM concepts through data‑binding‑like patterns (e.g., Information POJO) but primarily relies on direct UI‑logic coupling within Activities.

## Technology Stack
| Attribute | Value |
|-----------|-------|
| Language | Java |
| Platform | Android |
| Build System | Gradle |
| Min SDK | 21 |
| Target SDK | 25 |
| Package | com.buckleup.gpstracker |

## Dependencies
| Library | Version | Purpose |
|---------|---------|---------|
| com.android.support:appcompat-v7 | 25.2.0 | Support library for backward‑compatible UI components |
| com.android.support:design | 25.2.0 | Material Design UI components |
| com.android.support:constraint-layout | 1.0.2 | Layout manager for flexible UI design |
| com.android.volley:volley | 1.0.0 | Network request queue and HTTP client |
| com.google.android.gms:play-services | 10.2.0 | Google Play Services (Maps, Location, etc.) |
| com.google.firebase:firebase-auth | 10.0.1 | Firebase Authentication |
| com.google.firebase:firebase-core | 10.0.1 | Firebase Analytics / core services |
| com.google.firebase:firebase-database | 10.0.1 | Firebase Realtime Database |
| com.android.support:multidex | 1.0.1 | Enable multidex for apps with many methods |

## Project Structure
```
GpsTrackerWithFirebase/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/buckleup/gpstracker/   # 22 Java source files
│   │   │   ├── res/                             # Layouts, drawables, menus, values
│   │   │   └── AndroidManifest.xml
│   │   ├── androidTest/                         # Instrumented tests
│   │   └── test/                                # Unit tests
│   ├── build.gradle                             # App-level build config
│   └── google-services.json                     # Firebase config
├── build.gradle                                 # Project-level build config
├── gradle.properties
├── settings.gradle
└── README.md
```

## Key Architectural Patterns
- **Activity-based MVC**: Activities serve as both controllers and views
- **Background Service**: LocationService runs continuously for GPS tracking
- **Firebase Realtime Database**: Primary cloud data store for user profiles, OTPs, and location data
- **Volley HTTP Client**: Handles REST API calls to PHP backend
- **SharedPreferences**: Local session/state persistence
