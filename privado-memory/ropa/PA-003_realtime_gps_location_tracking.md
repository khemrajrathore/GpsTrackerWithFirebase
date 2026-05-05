# PA-003: Real-Time GPS Location Tracking

## Overview
| Field | Value |
|-------|-------|
| **PA ID** | PA-003 |
| **Name** | Real-Time GPS Location Tracking |
| **Description** | Continuous collection and storage of the user's precise GPS coordinates. A background Android Service (`LocationService`) captures latitude and longitude from device sensors every ~4 seconds and writes them to Firebase Realtime Database under the user's profile. The `MapsActivity` also independently pushes location updates to Firebase at 5-15 second intervals. |
| **Processing Type** | Collection, Storage, Continuous Monitoring |
| **Lawful Basis** | Consent (should be explicit; currently implicit on sign-in) |
| **Data Controller** | App Operator (BuckleUp / GpsTracker) |

## Data Subjects
| Subject | Description |
|---------|-------------|
| Signed-In App Users | Any authenticated user whose device location is being tracked |

## Data Elements Collected
| # | Data Element | Category | Sensitivity | Source | Evidence |
|---|-------------|----------|-------------|--------|----------|
| 1 | GPS Latitude | Precise Location | CRITICAL | Device GPS sensor (`LocationManager`) | `LocationService.java:96` |
| 2 | GPS Longitude | Precise Location | CRITICAL | Device GPS sensor (`LocationManager`) | `LocationService.java:97` |
| 3 | Location Provider | Device Metadata | LOW | Android LocationManager | `LocationService.java:93` |
| 4 | Timestamp (userlog) | Temporal | LOW | `System.currentTimeMillis()` | `LocationService.java:100` |

## Data Flows
```
Device GPS Sensor
    │
    ├──► LocationService (Background Service)
    │      • requestLocationUpdates every 4,000ms
    │      • GPS_PROVIDER + NETWORK_PROVIDER
    │
    ├──► Firebase Realtime Database (HTTPS)
    │      • Path: /Register/{uid}/latitude
    │      • Path: /Register/{uid}/longitude
    │      • Updated on every location change
    │
    └──► MapsActivity (Foreground)
           • FusedLocationApi updates every 5-15 seconds
           • Also writes to /Register/{uid}/latitude & longitude
           • Renders on Google Maps UI
```

## Data Storage & Retention
| Storage Location | Data Stored | Protocol | Retention |
|-----------------|-------------|----------|-----------|
| Firebase Realtime Database (`/Register/{uid}/`) | latitude, longitude (overwritten on each update) | HTTPS | Indefinite (latest value persisted) |
| Device Memory (static fields) | `finlat`, `finlon` | Local | App process lifetime |

## Third Parties / Processors
| Third Party | Role | Data Shared | Legal Basis |
|------------|------|-------------|-------------|
| Google Firebase Realtime Database | Processor | GPS coordinates (lat/long) | Contract |
| Google Play Services (Location API) | Processor | Raw device location | Contract |
| Google Maps SDK | Processor | GPS coordinates (for map rendering) | Contract |

## Security Measures
| Measure | Status | Notes |
|---------|--------|-------|
| HTTPS transport to Firebase | ✅ Implemented | Encrypted in transit |
| Location permission check | ✅ Implemented | `ACCESS_FINE_LOCATION` runtime check in MapsActivity |
| User consent dialog | ❌ Missing | No explicit location tracking consent prompt |
| Data minimisation | ❌ Missing | Precise location collected every 4s, no downsampling |
| Data retention policy | ❌ Missing | Coordinates persist indefinitely in Firebase |
| Location tracking opt-out | ❌ Missing | No user control to pause/stop tracking |

## Privacy Risks & Issues
| # | Risk | Severity | Description |
|---|------|----------|-------------|
| 1 | No explicit location consent | CRITICAL | Location tracking auto-starts on sign-in without a consent dialog or opt-out |
| 2 | Continuous high-frequency tracking | HIGH | GPS polled every 4 seconds — excessive for friend-sharing use case |
| 3 | No data retention limits | HIGH | Location history (latest) persists indefinitely in Firebase |
| 4 | Background tracking without notification | HIGH | `LocationService` runs as background service with no persistent notification (required on Android 8+) |
| 5 | Location shared with all Firebase users | CRITICAL | If Firebase security rules are permissive, any authenticated user could read any other user's location |

## Source Files
- `LocationService.java` — Background location service (GPS + Network providers, 4s interval)
- `MapsActivity.java` — Foreground map with FusedLocationApi (5-15s interval), writes to Firebase
