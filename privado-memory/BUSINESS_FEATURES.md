# Business Features

## 1. User registration and sign‑in
- **Description**: Allows a new user to create an account with email/password and existing users to authenticate. On successful sign‑in the user profile is stored in Firebase and a background location service is started.
- **User-facing**: Yes
- **Key Classes**: `Register`, `SignIn`, `RegisterRequest`, `SigninRequest`

## 2. OTP generation and validation
- **Description**: Generates a time‑limited 6‑digit OTP for a user, stores it in Firebase, and validates a friend's OTP to allow location sharing.
- **User-facing**: Yes
- **Key Classes**: `OtpDatePicker`, `OtpTimePicker`, `otpupdaterequest`, `FriendValidation`

## 3. Location sharing between friends
- **Description**: Continuously captures the device's GPS coordinates, writes them to Firebase, and lets a friend retrieve and view those coordinates on a map.
- **User-facing**: Yes
- **Key Classes**: `LocationService`, `MapsActivity`, `FriendMapsActivity`, `LatLonToDB`

## 4. Feedback submission
- **Description**: Collects textual feedback from the user and stores it under a "Feedback" node in Firebase.
- **User-facing**: Yes
- **Key Classes**: `Feedback`

## 5. Profile display
- **Description**: Shows the logged‑in user's profile information (name, email, phone, OTP).
- **User-facing**: Yes
- **Key Classes**: `MyProfile`

## 6. Forget Password
- **Description**: Allows users to reset their password by entering their email. Sends a request to the PHP backend and also supports email-based recovery.
- **User-facing**: Yes
- **Key Classes**: `ForgetPassword`, `ForgetPasswordRequest`, `SendEmail`

## 7. Navigation Drawer
- **Description**: Provides a side navigation menu with links to Home, My Location, Friends Location, My Profile, Feedback, About Us, Share App, and Sign Out.
- **User-facing**: Yes
- **Key Classes**: `Welcome`, `FriendValidation`
