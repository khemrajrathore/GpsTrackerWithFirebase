# Domains & URLs

## Application Domains
| Domain | Protocol | Purpose | Risk |
|--------|----------|---------|------|
| `buckleup.esy.es` | ⚠️ HTTP | PHP backend API server | **HIGH** — all data sent unencrypted |
| `gpstrackernew.firebaseio.com` | HTTPS | Firebase Realtime Database | Low |

## Backend API URLs
| URL | Purpose |
|-----|---------|
| `http://buckleup.esy.es/myregister.php` | User registration |
| `http://buckleup.esy.es/Login.php` | Authentication |
| `http://buckleup.esy.es/MyForgetPassword.php` | Password reset |
| `http://buckleup.esy.es/SendEmail.php` | Email recovery |
| `http://buckleup.esy.es/SendFeedback.php` | Feedback submission |
| `http://buckleup.esy.es/latlongupdate.php` | Location update |
| `http://buckleup.esy.es/otpupdate.php` | OTP update |
| `http://buckleup.esy.es/retrievelatlong.php` | Friend location retrieval |

## Firebase Configuration
- **Project ID**: gpstrackernew
- **Database URL**: https://gpstrackernew.firebaseio.com
- **Storage Bucket**: gpstrackernew.appspot.com
- **API Key**: Found in `google-services.json`

## External References
| URL | Context |
|-----|---------|
| `https://github.com/khemrajrathore/GpsTrackerWithFirebase.git` | Repository URL |
| `https://t.ent.code.privado.ai` | Privado tracking |
