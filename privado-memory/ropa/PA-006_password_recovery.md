# PA-006: Password Recovery

## Overview
| Field | Value |
|-------|-------|
| **PA ID** | PA-006 |
| **Name** | Password Recovery |
| **Description** | Processes a password recovery request. The user enters their phone number and email. The app sends the phone number to the PHP backend over HTTP to retrieve the stored password, then verifies the email matches, and sends the password via a separate PHP email endpoint — all over unencrypted HTTP. |
| **Processing Type** | Collection, Transmission, Disclosure |
| **Lawful Basis** | Contract performance (account recovery) |
| **Data Controller** | App Operator (BuckleUp / GpsTracker) |

## Data Subjects
| Subject | Description |
|---------|-------------|
| Registered Users | Users who have forgotten their password and request recovery |

## Data Elements Processed
| # | Data Element | Category | Sensitivity | Source | Evidence |
|---|-------------|----------|-------------|--------|----------|
| 1 | Phone Number | Identity | HIGH | User input (EditText `forgetnumber`) | `ForgetPassword.java:36` |
| 2 | Email Address | Contact | HIGH | User input (EditText `forgetemail`) | `ForgetPassword.java:37` |
| 3 | Password | Authentication | CRITICAL | Retrieved from PHP backend response | `ForgetPassword.java:50` |

## Data Flows
```
User enters phone number + email
    │
    ├──► PHP Backend — HTTP (UNENCRYPTED!)
    │      • POST http://buckleup.esy.es/MyForgetPassword.php
    │      • Sends: phone number
    │      • Receives: password (plaintext!), email
    │
    └──► PHP Backend — HTTP (UNENCRYPTED!)
           • POST http://buckleup.esy.es/SendEmail.php
           • Sends: email, password (plaintext!)
           • Triggers: password recovery email to user
```

## Data Storage & Retention
| Storage Location | Data Stored | Protocol | Retention |
|-----------------|-------------|----------|-----------|
| PHP Backend (MySQL on buckleup.esy.es) | Phone number, email, password (likely plaintext) | HTTP | Unknown |
| Network transit | Password transmitted in plaintext over HTTP | HTTP | Transient |

## Third Parties / Processors
| Third Party | Role | Data Shared | Legal Basis |
|------------|------|-------------|-------------|
| buckleup.esy.es (Hostinger/000webhost) | Processor | Phone number, email, password | Contract |
| Hosting provider email service | Sub-processor | Email address, password | Contract |

## Security Measures
| Measure | Status | Notes |
|---------|--------|-------|
| HTTPS | ❌ Not implemented | All calls use HTTP — credentials sent in plaintext |
| Password hashing | ❌ Not implemented | Password returned in plaintext from server |
| Rate limiting | ❌ Not implemented | No brute-force protection |
| Email verification | ⚠️ Partial | Email compared client-side only |

## Privacy Risks & Issues
| # | Risk | Severity | Description |
|---|------|----------|-------------|
| 1 | Password sent over HTTP | CRITICAL | Password transmitted in plaintext, vulnerable to MitM attacks |
| 2 | Password stored in plaintext on server | CRITICAL | Server returns the actual password (not a reset link), proving it's stored unhashed |
| 3 | Password emailed in plaintext | CRITICAL | Password recovery sends the actual password via email instead of a secure reset link |
| 4 | Client-side email verification only | HIGH | Email matching done on client side — easily bypassed |
| 5 | Free hosting provider | HIGH | Sensitive data hosted on free hosting (esy.es/000webhost) with questionable security |

## Source Files
- `ForgetPassword.java` — Password recovery flow
- `ForgetPasswordRequest.java` — Volley POST to MyForgetPassword.php
- `SendEmail.java` — Volley POST to SendEmail.php
