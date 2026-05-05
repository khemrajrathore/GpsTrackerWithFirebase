# Third-Party Services

## Detected Third Parties

### 1. Firebase Authentication
- **Type**: authentication
- **Evidence**: app/build.gradle (compile 'com.google.firebase:firebase-auth:10.0.1') and SignIn.java / Register.java usage of FirebaseAuth
- **Data Shared**: User email, password (hashed by Firebase), UID

### 2. Firebase Realtime Database
- **Type**: database
- **Evidence**: app/build.gradle (compile 'com.google.firebase:firebase-database:10.0.1') and multiple DatabaseReference usages (e.g., Register.java, MapsActivity.java, Feedback.java)
- **Data Shared**: User profile (name, email, phone, OTP, OTP validity), location coordinates (latitude, longitude), feedback messages

### 3. Google Maps
- **Type**: maps
- **Evidence**: android manifest meta‑data com.google.android.geo.API_KEY and usage of com.google.android.gms.maps in MapsActivity.java and FriendMapsActivity.java
- **Data Shared**: Device latitude/longitude for map rendering

### 4. Google Play Services (Location)
- **Type**: location
- **Evidence**: LocationService.java uses LocationManager and FusedLocationApi; MapsActivity/ FriendMapsActivity use GoogleApiClient and LocationRequest
- **Data Shared**: Raw GPS coordinates

### 5. Volley
- **Type**: network
- **Evidence**: app/build.gradle (compile 'com.android.volley:volley:1.0.0') and all *Request classes (e.g., FeedbackRequest, RegisterRequest)
- **Data Shared**: Various request parameters such as phone number, OTP, latitude, longitude, feedback text

## Backend Server
- **Domain**: `buckleup.esy.es` (free hosting on Hostinger/000webhost)
- **Protocol**: ⚠️ **HTTP** (unencrypted)
- **Technology**: PHP
- **Endpoints**: See API_ENDPOINTS.md
