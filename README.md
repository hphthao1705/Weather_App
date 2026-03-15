# 🌤️ Weather App

An Android weather application built with **Jetpack Compose**, **Hilt**, and **Clean Architecture**. Search any country, view real-time weather conditions, and explore a beautiful carousel-style weather detail screen.

---

## 📸 Screenshots

> _Add your screenshots here_

---

## 🏗️ Architecture

This project follows **Clean Architecture** with an **MVVM** pattern.

```
app/
├── api/          # Retrofit clients, API helpers, error models
├── data/         # Raw API response models
├── di/           # Hilt dependency injection modules
├── google/       # Google Auth client
├── network/      # Network monitor (connectivity check)
├── repository/   # Data layer (CountryRepository, WeatherRepository)
├── usecase/      # Business logic (CountryUseCase, WeatherUseCase)
├── ui/
│   ├── home/         # Home screen + ViewModel
│   ├── onboarding/   # Onboarding screen
│   ├── search/       # Search screen + ViewModel + UI states
│   ├── login/        # Login bottom sheet
│   ├── loading/      # Shimmer loading components
│   └── weatherDetails/ # Weather detail carousel screen + ViewModel
└── util/         # Extensions, PrefsUtils, NetworkUtils, FontFamily
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| UI | Jetpack Compose, Material 3 |
| Navigation | Navigation Compose |
| DI | Hilt + KSP |
| Networking | Retrofit2 + OkHttp |
| Image Loading | Coil |
| Serialization | Kotlin Serialization + Gson |
| Auth | Firebase Auth + Google Identity |
| Local Storage | SharedPreferences |
| Async | Kotlin Coroutines + Flow |
| Build | Gradle KTS + Version Catalog |

---

## 🌐 APIs Used

- **[WeatherAPI](https://www.weatherapi.com/)** — Real-time weather data (`api.weatherapi.com/v1`)
- **[API Countries](https://www.apicountries.com/)** — Country list with flags and metadata

---

## ⚙️ Setup & Installation

### Prerequisites

- Android Studio Hedgehog or later
- JDK 17
- Android SDK 30+
- A [WeatherAPI](https://www.weatherapi.com/) key
- A [API Ninjas](https://api-ninjas.com/) key (for countries)
- Firebase project with Google Sign-In enabled

---

## 📦 Project Dependencies

```toml
# Key versions
kotlin          = "2.1.20"
ksp             = "2.1.20-1.0.32"
hilt            = "2.56.2"
compose         = "1.7.8"
retrofit2       = "2.9.0"
coil            = "2.7.0"
firebase-auth   = "24.0.1"
```
