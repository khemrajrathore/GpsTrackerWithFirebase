# PA-002: User Authentication (Sign-In)

## Overview
| Field | Value |
|-------|-------|
| **PA ID** | PA-002 |
| **Name** | User Authentication (Sign-In) |
| **Description** | Processing of user credentials to authenticate and grant access to the application. On successful sign-in, the app fetches the user's full profile from Firebase Realtime Database and automatically starts the background GPS location tracking service. |
| **Processing Type** | Collection, Verification, Automated Decision-Making |
| **Lawful Basis** | Contract performance (necessary for service access) |
| **Data Controller** | App Operator (BuckleUp / GpsTracker) |

## Data Subjects
| Subject | Description |
|---------|-------------|
| Registered App Users | Users who have an existing account and are signing in |

## Data Elements Processed
| # | Data Element | Category | Sensitivity | Source | Evidence |
|---|-------------|----------|-------------|--------|----------|
| 1 | Email Address | Authentication | HIGH | User input (EditText `logphnumber`) | `SignIn.java:100` |
| 2 | Password | Authentication | CRITICAL | User input (EditText `logpassword`) | `SignIn.java:101` |
| 3 | Firebase UID | Identity | HIGH | Firebase Auth response | `SignIn.java:115` |
| 4 | Full Profile (name, email, phone, OTP, OTP validity) | Identity | HIGH | Firebase Realtime DB read | `SignIn.java:120-132` |

## Data Flows
```
User Input (Sign-In Form)
    │
    ├──► Firebase Authentication (HTTPS)
    │      • signInWithEmailAndPassword(email, password)
    │      • Returns UID on success
    │
    ├──► Firebase Realtime Database (HTTPS) — READ
    │      • Path: /Register/{uid}/
    │      • Reads: name, email, phonenumber, otp, otpvalidity
    │
    └──► Android Services (Local)
           • Starts LocationService (background GPS tracking)
           • Passes phonenumber & uid to the service
```

## Data Storage & Retention
| Storage Location | Data Stored | Protocol | Retention |
|-----------------|-------------|----------|-----------|
| Device Memory (Intent extras) | name, email, phonenumber, otp, password, uid | Local | Session duration |
| Firebase Authentication | Auth session token | HTTPS | Until sign-out |

## Third Parties / Processors
| Third Party | Role | Data Shared | Legal Basis |
|------------|------|-------------|-------------|
| Google Firebase Authentication | Processor | Email, password (for verification) | Contract |

## Privacy Risks & Issues
| # | Risk | Severity | Description |
|---|------|----------|-------------|
| 1 | Password passed via Intent extras | HIGH | Password is passed between activities in plaintext via Intent extras (`SignIn.java:138`) |
| 2 | Auto-start of location tracking | HIGH | LocationService starts immediately upon sign-in without separate location consent |
| 3 | No session timeout | MEDIUM | No automatic session expiry mechanism |

## Source Files
- `SignIn.java` — Sign-in activity, Firebase Auth, profile fetch, LocationService start
- `SigninRequest.java` — (Legacy) Volley request to PHP backend (commented out)
