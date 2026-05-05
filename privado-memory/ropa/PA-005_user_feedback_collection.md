# PA-005: User Feedback Collection

## Overview
| Field | Value |
|-------|-------|
| **PA ID** | PA-005 |
| **Name** | User Feedback Collection |
| **Description** | Collection and storage of user feedback messages. The user's name (auto-populated from their profile) and free-text feedback are stored in Firebase Realtime Database under a dedicated Feedback node. |
| **Processing Type** | Collection, Storage |
| **Lawful Basis** | Consent (user-initiated submission) |
| **Data Controller** | App Operator (BuckleUp / GpsTracker) |

## Data Subjects
| Subject | Description |
|---------|-------------|
| App Users | Authenticated users who submit feedback through the app |

## Data Elements Collected
| # | Data Element | Category | Sensitivity | Source | Evidence |
|---|-------------|----------|-------------|--------|----------|
| 1 | User Name | Identity | MEDIUM | Auto-populated from profile (Intent extra) | `Feedback.java:31` |
| 2 | Feedback Text | Behavioural / Opinion | LOW–MEDIUM | User input (EditText `feedbackmsg`) | `Feedback.java:40` |
| 3 | Firebase UID | Identity | HIGH | Intent extra (used as node key) | `Feedback.java:43` |

## Data Flows
```
User submits feedback
    │
    └──► Firebase Realtime Database (HTTPS)
           • Path: /Feedback/{uid}/name
           • Path: /Feedback/{uid}/feedback
```

## Data Storage & Retention
| Storage Location | Data Stored | Protocol | Retention |
|-----------------|-------------|----------|-----------|
| Firebase Realtime Database (`/Feedback/{uid}/`) | User name, feedback text | HTTPS | Indefinite (no deletion policy) |

## Third Parties / Processors
| Third Party | Role | Data Shared | Legal Basis |
|------------|------|-------------|-------------|
| Google Firebase Realtime Database | Processor | User name, feedback text | Contract |

## Privacy Risks & Issues
| # | Risk | Severity | Description |
|---|------|----------|-------------|
| 1 | No data retention policy | LOW | Feedback persists indefinitely with no purge mechanism |
| 2 | Feedback may contain PII | MEDIUM | Free-text field could contain personal information, health data, or other sensitive content |
| 3 | Feedback keyed by UID | LOW | Feedback is directly linkable to user identity via Firebase UID |

## Source Files
- `Feedback.java` — Feedback activity and Firebase write
- `FeedbackRequest.java` — (Legacy) Volley request to PHP backend (commented out)
