## UnsplashTest

An Android project demonstrating a clean, testable multi-module architecture with Jetpack Compose,
Kotlin coroutines/Flow, Room, Hilt, Retrofit. and navigation via Compose Destinations.

### Highlights

- Clean architecture split into domain/data/app/feature/core modules
- UI with Jetpack Compose and immutable UI models
- DI with Hilt
- Strong test coverage with MockK, Turbine, MockWebServer, Room in-memory DB

## Tech stack

- Kotlin, JDK 17
- Coroutines + Flow
- Jetpack Compose 1.9.x + Material 3
- Hilt
- Retrofit 3.x + Kotlinx Serialization
- Room
- Compose Destinations 2.x
- Test: JUnit4, MockK, Turbine, MockWebServer, AndroidX Test, Room Testing, Compose UI Test

## Modules

- `app`: Android application entry, DI wiring, app-level build config
- `feature:imagelist`: Image list feature (ViewModel, pager, screen)
- `data`: Repositories, network (Retrofit service + interceptors), DB (Room)
- `domain`: Pure Kotlin business layer (interactors, models)
- `core:ui`: Shared UI components/themes/base VM/UI events
- `core:common`: Test fixtures and shared utilities

## Architecture overview

- Domain exposes interfaces and interactors; no Android dependencies
- Data implements repositories using Retrofit + Room, mapping to domain DTOs
- Feature depends on domain/core and provides ViewModels + Compose screens
- App wires everything with Hilt and hosts navigation

## Running the project

Prereqs: Android Studio (latest), Android SDK 36, JDK 17.

- Build (all modules):

```bash
./gradlew assemble
```

- Run unit tests (JVM):

```bash
./gradlew test
```

- Run instrumented tests (launched device/emulator required):

```bash
./gradlew connectedAndroidTest
```

## API configuration

`AuthInterceptor` adds the Unsplash Client-ID header. For simplicity, the project uses a hardcoded
demo key suitable for a CV sample. In production, secure storage/obfuscation and CI secrets should
be used.

## Navigation docs

Compose Destinations KSP is configured to export Mermaid/HTML graphs into `navigation-docs/`. Open
those files to preview navigation flows.

## Testing

### Conventions

- Unit tests: backticked function names (readable sentences), e.g. `fun `emits error event on pager
  error`() { ... }`
- Android tests: underscore names, e.g. `fun renders_items_and_triggers_author_click()`
- Prefer Given/When/Then comments for multi-step tests

## Code style & tooling

- Version catalog (`gradle/libs.versions.toml`), plugin aliases, and typesafe project accessors
- KtLint applied to all subprojects (`org.jlleitschuh.gradle.ktlint`)

## Stubs and test data

Many models expose `companion object` `stub` computed properties for quick test instantiation.
Example:

```kotlin
val photo = cc.anisimov.vlad.unsplashtest.domain.model.Photo.stub.copy(id = "43")
```

For more complex scenarios small factory helpers in test sources should be created.

## Why this project

This repository is optimized to communicate how I structure Android apps: layered architecture,
feature isolation, and pragmatic testing.


