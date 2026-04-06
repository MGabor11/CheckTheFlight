# AGENTS.md

## Project Context
This is a multi module **Compose Multiplatform (CMP)** project. It targets multiple platforms (Android, iOS) with a focus on shared UI in `commonMain`.

## Tech Stack & Patterns
- **UI:** Compose Multiplatform.
- **Dependency Injection:** Koin. Use `koinViewModel()` to inject ViewModels into Screens.
- **Resources:** JetBrains Compose Resources. Use `Res.string.key` and `stringResource(...)`.
- **Architecture:** MVVM. Screens should generally be `internal` and use a dedicated ViewModel.

## Coding Standards
- **Previews:** Always wrap `@Preview` functions in the project's theme wrapper (e.g., `CheckTheFlightTheme { ... }`).
- **Visibility:** UI Screens in `feature` modules should be `internal` to maintain modularity.

## Useful Commands
- **Build:** `./gradlew build`
- **Clean:** `./gradlew clean`