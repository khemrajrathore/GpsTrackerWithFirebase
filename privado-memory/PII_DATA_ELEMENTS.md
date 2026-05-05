# PII & Data Elements

## Data Elements Inventory

| # | Element | Category | Sensitivity | Source | Storage | Evidence Files |
|---|---------|----------|-------------|--------|---------|----------------|
| 1 | Email Address | Authentication | CRITICAL | user input | Firebase Realtime Database | `Register.java`, `SignIn.java` |
| 2 | Password | Authentication | CRITICAL | user input | Firebase Realtime Database | `Register.java`, `SignIn.java` |
| 3 | Location | Location | MEDIUM | device sensor | Firebase Realtime Database | `LocationService.java`, `MapsActivity.java` |
| 4 | Phone Number | Identity | MEDIUM | user input | Firebase Realtime Database | `Register.java`, `SignIn.java` |
| 5 | GPS Latitude | Location | HIGH | device sensor | Firebase Realtime Database, SharedPreferences | `LocationService.java`, `MapsActivity.java` |
| 6 | GPS Longitude | Location | HIGH | device sensor | Firebase Realtime Database, SharedPreferences | `LocationService.java`, `MapsActivity.java` |
| 7 | OTP Code | Authentication | HIGH | app-generated | Firebase Realtime Database, SharedPreferences | `OtpTimePicker.java`, `Register.java`, `FriendValidation.java` |
| 8 | OTP Validity | Authentication | MEDIUM | app-generated | Firebase Realtime Database | `OtpTimePicker.java`, `Register.java` |
| 9 | User Name | Identity | MEDIUM | user input | Firebase Realtime Database, SharedPreferences | `Register.java`, `SignIn.java`, `Feedback.java` |
| 10 | Feedback Text | Behavioral | LOW | user input | Firebase Realtime Database | `Feedback.java` |

## SharedPreferences Keys
| Key | Data Type | Files |
|-----|-----------|-------|
| `email` | String | `ForgetPassword.java`, `SignIn.java` |
| `name` | String | `SignIn.java` |
| `otp` | String | `SignIn.java` |
| `password` | String | `ForgetPassword.java` |
| `latitude` | String | `FriendValidation.java` |
| `longitude` | String | `FriendValidation.java` |
| `userlog` | String | `FriendValidation.java` |

## Firebase Database Schema
| Node Path | Fields | Purpose |
|-----------|--------|---------|
| `/Register/{userId}` | name, email, phonenumber, otp, otpvalidity, latitude, longitude | User profile + location |
| `/Feedback/{feedbackId}` | name, feedback | User feedback submissions |
