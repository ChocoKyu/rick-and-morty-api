# Rick & Morty KMP App

Une application multiplateforme basée sur l'API Rick and Morty, utilisant Kotlin Multiplatform, Compose Multiplatform, Clean Architecture, Room et Ktor.

---

## Plateformes supportées

Ce projet Kotlin Multiplatform cible :

- Android
- iOS (entrée dans le dossier `iosApp`)
- Desktop (JVM)
- Web (WASM)

---

## Fonctionnalités

- Liste paginée des personnages
- Fiche détaillée d’un personnage : avatar, statut, genre, épisodes
- Affichage de l’origine et de la dernière location
- Nouvelle feature : écran `LocationDetails` avec liste des résidents
- Son joué au clic sur une carte de location
- Cache local avec Room (Character, Episode, Location)
- Architecture modulaire basée sur Clean Architecture
- MVI : gestion des écrans par `UiState`, `UiAction`, `ViewModel`, `Screen`

---

## Architecture

Le projet suit les principes de Clean Architecture :

- `domain` : logique métier, modèles, interfaces Repository
- `data` : appels API (Ktor), cache local (Room), mapping DTO/Entity/Model
- `ui` : interface utilisateur avec Compose Multiplatform, gérée via MVI

Autres éléments techniques :
- Injection de dépendances avec Koin
- Ktor pour les appels HTTP
- Room (avec SQLite) pour le cache local
- Compose Multiplatform pour l’interface graphique sur Android, Desktop et Web

---

## Structure du projet

```text
composeApp/
├── commonMain/
│   ├── data/
│   │   ├── local/           → DAO, Entities, RoomDatabase
│   │   ├── remote/          → API, DTOs, Ktor
│   │   ├── repositories/    → Repositories implémentés
│   ├── domain/              → Interfaces métier, models, use cases
│   ├── ui/
│   │   ├── screens/         → MVI (characters, details, locations)
│   │   ├── core/            → Composables réutilisables, thème, navigation
├── androidMain/
│   └── MainApplication.kt   → Démarrage et configuration Koin
├── desktopMain/
│   └── Main.kt              → Entrée principale desktop
├── wasmJsMain/
│   └── Fichier principal Web
iosApp/
└── Code Swift/SwiftUI pour l’entrée iOS
