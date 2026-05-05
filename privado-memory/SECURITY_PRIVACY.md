# Security & Privacy Concerns

## Critical Issues

### 1. 🔴 Plaintext HTTP for All Backend API Calls
- **Severity**: CRITICAL
- **Description**: All 8 PHP backend endpoints use HTTP (not HTTPS). User credentials (email, password), location data, and OTP codes are transmitted in plaintext, making them vulnerable to man-in-the-middle attacks.
- **Affected Files**: `SigninRequest.java`, `RegisterRequest.java`, `ForgetPasswordRequest.java`, `LatLonToDB.java`, `otpupdaterequest.java`, `FriendLatLongRequest.java`, `FeedbackRequest.java`, `SendEmail.java`
- **Recommendation**: Migrate all endpoints to HTTPS. Use certificate pinning.

### 2. 🔴 Password Stored in Firebase (Likely Plaintext)
- **Severity**: CRITICAL
- **Description**: User passwords appear to be stored directly in Firebase Realtime Database without hashing. The `Register.java` class writes the password directly to the database.
- **Affected Files**: `Register.java`, `SignIn.java`
- **Recommendation**: Never store plaintext passwords. Use bcrypt/scrypt hashing server-side.

### 3. 🟠 Password Stored in SharedPreferences
- **Severity**: HIGH
- **Description**: The ForgetPassword activity reads/writes the password from SharedPreferences, which stores data in plaintext XML on the device.
- **Affected Files**: `ForgetPassword.java`
- **Recommendation**: Use Android Keystore or EncryptedSharedPreferences.

### 4. 🟠 Location Data Continuously Tracked and Stored
- **Severity**: HIGH
- **Description**: LocationService runs as a background service and continuously pushes GPS coordinates to Firebase every ~5 seconds. No user consent dialog or opt-out mechanism is visible.
- **Affected Files**: `LocationService.java`
- **Recommendation**: Add explicit location consent, provide opt-out, implement data retention limits.

### 5. 🟡 Weak OTP Implementation
- **Severity**: MEDIUM
- **Description**: 6-digit OTPs are generated using `Random` (not `SecureRandom`) and stored in Firebase without rate limiting or brute-force protection.
- **Affected Files**: `OtpTimePicker.java`, `Register.java`
- **Recommendation**: Use `SecureRandom`, implement rate limiting, add OTP expiry.

### 6. 🟡 Firebase Database Rules Likely Permissive
- **Severity**: MEDIUM
- **Description**: No evidence of Firebase security rules in the codebase. Default rules may allow any authenticated (or even unauthenticated) user to read/write all data.
- **Recommendation**: Implement strict Firebase security rules with per-user access control.

### 7. 🟡 No Data Encryption at Rest
- **Severity**: MEDIUM
- **Description**: Data stored in SharedPreferences and Firebase is not encrypted at rest by the application.
- **Recommendation**: Use EncryptedSharedPreferences and Firebase-level encryption.

## Android Permissions
| Permission | Purpose | Risk |
|-----------|---------|------|
| `ACCESS_FINE_LOCATION` | GPS location tracking | HIGH |
| `ACCESS_COARSE_LOCATION` | Approximate location | HIGH |
| `INTERNET` | Network communication | MEDIUM |
| `ACCESS_NETWORK_STATE` | Check connectivity | LOW |
