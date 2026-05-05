# API Endpoints & Network Calls

## PHP Backend Endpoints (buckleup.esy.es)

| # | URL | Method | Purpose | File | Data Sent |
|---|-----|--------|---------|------|----------|
| 1 | `http://buckleup.esy.es/myregister.php` | POST | User registration | `RegisterRequest.java` | name, email, phone, password |
| 2 | `http://buckleup.esy.es/Login.php` | POST | User sign-in | `SigninRequest.java` | email, password |
| 3 | `http://buckleup.esy.es/MyForgetPassword.php` | POST | Password reset | `ForgetPasswordRequest.java` | email, newpassword |
| 4 | `http://buckleup.esy.es/SendEmail.php` | POST | Send recovery email | `SendEmail.java` | email |
| 5 | `http://buckleup.esy.es/SendFeedback.php` | POST | Submit feedback | `FeedbackRequest.java` | name, feedback |
| 6 | `http://buckleup.esy.es/latlongupdate.php` | POST | Update lat/long | `LatLonToDB.java` | email, latitude, longitude |
| 7 | `http://buckleup.esy.es/otpupdate.php` | POST | Update OTP | `otpupdaterequest.java` | email, otp, otpvalidity |
| 8 | `http://buckleup.esy.es/retrievelatlong.php` | POST | Get friend location | `FriendLatLongRequest.java` | email |

## Firebase Endpoints
| Service | URL | Protocol |
|---------|-----|----------|
| Realtime Database | `https://gpstrackernew.firebaseio.com` | HTTPS |

## ⚠️ Security Warning
All PHP backend calls use **HTTP** (unencrypted). Credentials (email, password) are transmitted in plaintext over the network.
