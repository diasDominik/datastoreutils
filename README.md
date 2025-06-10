# datastoreutils

`datastoreutils` is a Kotlin Multiplatform utility library designed to simplify interaction with Jetpack DataStore Preferences, offering convenient APIs for common preference operations and seamless integration with Jetpack Compose.

## Table of Contents

- [About](#about)
- [Features](#features)
- [Multiplatform Support](#multiplatform-support)
- [Installation](#installation)
- [Usage](#usage)

## About

`datastoreutils` aims to provide a robust and easy-to-use wrapper around Android's Jetpack DataStore Preferences. It abstracts away the complexities of DataStore, offering a clear `IPreferencesRepository` interface to manage individual preferences (`IPreference`) across different platforms. Additionally, it provides extensions specifically for Jetpack Compose, enabling reactive observation of preferences directly within your UI.

## Features

-   **Simplified Preferences Management:** Provides `IPreferencesRepository` to create and manage `IPreference` instances for various data types.
-   **Comprehensive Preference Operations:** Each `IPreference` supports:
    -   Asynchronous saving of values (`save`).
    -   Deletion of preferences (`delete`).
    -   Reactive observation of preference changes via `Flow` (`get`).
    -   Atomic updates based on current state (`update`).
    -   Synchronous fetching of the current value (`currentValue`).
    -   Observation of key existence (`exists`).
-   **Clear All Preferences:** Ability to clear all preferences managed by a repository (`clearAllPreferences`).
-   **Jetpack Compose Integration:** The `compose` module offers `@Composable` extension functions to collect preference values as `State` objects, integrating smoothly with the Compose lifecycle.
    -   `collectAsState`: Collects `Flow` values as Compose `State`.
    -   `collectAsStateWithLifecycle`: Collects `Flow` values as Compose `State` respecting a `LifecycleOwner` and minimum active state.

## Multiplatform Support

This library is designed for Kotlin Multiplatform projects and supports the following targets:
-   **Android** (minSdk 23)
-   **iOS** (x64, arm64, simulatorArm64)
-   **JVM**
-   **macOS** (x64, arm64)

## Installation

Add the `datastoreutils` modules to your `build.gradle.kts` file.

```kotlin
include("uk.dominikdias.datastoreutils:core")
include("uk.dominikdias.datastoreutils:compose") // If you need Compose integrations
```

## Usage
````kotlin
class MyRepository(dataStore: DataStore<Preferences>): PreferencesRepository(dataStore) {
    val foo = createPreference(stringPreferencesKey("foo"), "default_value")
    val bar = createPreference(booleanPreferencesKey("bar"), true)
}
````