# PA-004: Friend Location Sharing via OTP

## Overview
| Field | Value |
|-------|-------|
| **PA ID** | PA-004 |
| **Name** | Friend Location Sharing via OTP |
| **Description** | Allows a user to share their real-time GPS location with a friend. The sharing user generates a time-limited 6-digit OTP and shares it (via any messaging channel). The receiving friend enters the sharing user's phone number and OTP to validate access, then views the friend's location on a Google Map. The OTP validation reads ALL registered users' data from Firebase to find the matching phone number. |
| **Processing Type** | Collection, Disclosure to Third Parties (friend), Access Control |
| **Lawful Basis** | Consent (user-initiated sharing) |
| **Data Controller** | App Operator (BuckleUp / GpsTracker) |

## Data Subjects
| Subject | Description |
|---------|-------------|
| Sharing User | User who generates the OTP and whose location will be viewed |
| Receiving User | Friend who enters the OTP to view the sharing user's location |

## Data Elements Processed
| # | Data Element | Category | Sensitivity | Source | Evidence |
|---|-------------|----------|-------------|--------|----------|
| 1 | OTP Code (6 digits) | Authentication | HIGH | `Random.nextInt(9)` × 6 | `OtpTimePicker.java:72-76` |
| 2 | OTP Validity (milliseconds) | Temporal | MEDIUM | User-selected date/time picker | `OtpTimePicker.java:59-67` |
| 3 | Friend's Phone Number | Identity | HIGH | User input (EditText `friendmobile`) | `FriendValidation.java:141` |
| 4 | Friend's OTP | Authentication | HIGH | User input (EditText `friendotp`) | `FriendValidation.java:142` |
| 5 | Friend's Latitude | Precise Location | CRITICAL | Firebase DB read | `FriendValidation.java:153` |
| 6 | Friend's Longitude | Precise Location | CRITICAL | Firebase DB read | `FriendValidation.java:154` |
| 7 | Device Contacts | Contact Information | HIGH | Android Contacts Provider | `FriendValidation.java:95-119` |

## Data Flows
```
OTP Generation Flow:
    User picks date/time → generates 6-digit OTP
        │
        ├──► Firebase Realtime Database (HTTPS)
        │      • /Register/{uid}/otp = generated OTP
        │      • /Register/{uid}/otpvalidity = expiry timestamp
        │
        └──► Share Intent (External)
               • OTP shared via SMS/WhatsApp/etc. as plain text

OTP Validation & Location Retrieval Flow:
    Friend enters phone number + OTP
        │
        ├──► Firebase Realtime Database (HTTPS) — READ ALL
        │      • Reads ENTIRE /Register/ node (all users!)
        │      • Iterates all children to match phonenumber
        │      • Checks OTP match and validity
        │
        └──► FriendMapsActivity (Local)
               • Displays friend's lat/long on Google Map
```

## Data Storage & Retention
| Storage Location | Data Stored | Protocol | Retention |
|-----------------|-------------|----------|-----------|
| Firebase Realtime Database | OTP, OTP validity | HTTPS | Until overwritten by new OTP |

## Third Parties / Processors
| Third Party | Role | Data Shared | Legal Basis |
|------------|------|-------------|-------------|
| Google Firebase Realtime Database | Processor | OTP, OTP validity, location coordinates | Contract |
| Google Maps SDK | Processor | Friend's GPS coordinates (rendering) | Contract |
| Android Contacts Provider | System API | Contact names, phone numbers (read-only) | Consent (contacts permission) |

## Privacy Risks & Issues
| # | Risk | Severity | Description |
|---|------|----------|-------------|
| 1 | Reads ALL users' data for validation | CRITICAL | `FriendValidation.getFriendLocation()` downloads the entire `/Register/` node — every user's name, email, phone, OTP, and location — to find one match. Massive over-fetching. |
| 2 | Weak OTP generation | HIGH | Uses `java.util.Random` (predictable PRNG), not `SecureRandom` |
| 3 | No brute-force protection | HIGH | No rate limiting on OTP validation attempts |
| 4 | Contacts access without clear purpose | MEDIUM | Reads device contacts to auto-fill friend's phone number, but no consent dialog specific to contacts |
| 5 | OTP shared in plaintext | MEDIUM | OTP is shared via Android Share Intent as plain text message |

## Source Files
- `OtpTimePicker.java` — OTP generation and Firebase write
- `OtpDatePicker.java` — Date picker for OTP validity
- `FriendValidation.java` — OTP validation, Firebase read-all, contacts access
- `FriendMapsActivity.java` — Display friend's location on map
