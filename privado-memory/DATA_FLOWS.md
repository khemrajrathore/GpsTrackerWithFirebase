# Data Flows

## Flow Diagram

```
┌──────────────┐       HTTP (plaintext!)        ┌─────────────────────┐
│              │ ──── Registration ──────────►   │  buckleup.esy.es    │
│              │ ──── Sign-in ──────────────►    │  (PHP Backend)      │
│              │ ──── Password Reset ───────►    │                     │
│   Android    │ ──── Feedback ────────────►     │  MySQL Database     │
│   Device     │ ──── Location Update ─────►    └─────────────────────┘
│              │ ──── OTP Update ──────────►
│              │
│              │       HTTPS (encrypted)         ┌─────────────────────┐
│              │ ──── User Profile ─────────►    │  Firebase Realtime  │
│              │ ──── Location Coords ─────►     │  Database           │
│              │ ──── OTP Data ────────────►     │  (Google Cloud)     │
│              │ ──── Feedback ────────────►     └─────────────────────┘
│              │
│              │       Local Storage
│              │ ──── Session Data ─────────►    SharedPreferences
│              │ ──── GPS Coords ──────────►     (on device)
└──────────────┘
```

## Detailed Data Flows

### 1. User Registration Flow
- **Source**: User input (Register Activity)
- **Data**: Name, Email, Phone Number, Password, OTP, OTP Validity
- **Destinations**:
  - PHP Backend via HTTP (`myregister.php`) — name, email, phone, password
  - Firebase Realtime Database via HTTPS — all fields stored under `/Register/{userId}`

### 2. Sign-in Flow
- **Source**: User input (SignIn Activity)
- **Data**: Email, Password
- **Destinations**:
  - PHP Backend via HTTP (`Login.php`) — email, password
  - SharedPreferences — email, name, otp stored locally on success

### 3. Location Tracking Flow
- **Source**: Device GPS sensor (LocationService)
- **Data**: Latitude, Longitude
- **Destinations**:
  - Firebase Realtime Database via HTTPS — stored under `/Register/{userId}/latitude` and `/longitude`
  - PHP Backend via HTTP (`latlongupdate.php`) — email, latitude, longitude
  - SharedPreferences — cached locally

### 4. Friend Location Sharing Flow
- **Source**: Firebase Realtime Database
- **Data**: Friend's Latitude, Longitude
- **Trigger**: User enters friend's email and valid OTP
- **Destinations**:
  - PHP Backend via HTTP (`retrievelatlong.php`) — friend's email
  - Google Maps display on device

### 5. Feedback Flow
- **Source**: User input (Feedback Activity)
- **Data**: Name, Feedback text
- **Destinations**:
  - Firebase Realtime Database via HTTPS — under `/Feedback/{id}`
  - PHP Backend via HTTP (`SendFeedback.php`) — name, feedback

### 6. Password Reset Flow
- **Source**: User input (ForgetPassword Activity)
- **Data**: Email, New Password
- **Destinations**:
  - PHP Backend via HTTP (`MyForgetPassword.php`) — email, new password
  - PHP Backend via HTTP (`SendEmail.php`) — email for recovery notification
