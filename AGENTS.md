# AGENTS.md — About You (com.premium.aboutyou)

Android app: tells you about a person from their name and date of birth.

## Build

JDK 17+ and Android SDK (platform 34, build-tools 34.0.0) are required.
This sandbox has them at:

- `JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64`
- `ANDROID_HOME=/workspace/android-sdk`
- Gradle 8.7 at `/workspace/gradle-8.7`

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
export ANDROID_HOME=/workspace/android-sdk ANDROID_SDK_ROOT=$ANDROID_HOME
export PATH=/workspace/gradle-8.7/bin:$JAVA_HOME/bin:$PATH

gradle testDebugUnitTest assembleDebug assembleRelease --no-daemon
```

`./build.sh [task]` wraps the above. Builds take ~1-2 min cold, ~20-50 s warm.
Long builds: run in background and poll the log; foreground commands are capped.

## Layout

- `MainActivity.kt` — screen state machine (`Screen` enum), Material 3 date/time picker.
- `MainViewModel.kt` — `LoadState` sealed interface + StateFlows per screen.
- `net/Repo.kt` — all live data. `net/Http.kt` — OkHttp wrapper, sets User-Agent.
- `data/LocalNameData.kt` — curated offline meanings; `generated()` is the deterministic fallback.
- `data/Zodiac.kt` — 12 signs with cut-off dates, traits and image URLs.
- `util/AgeCalc.kt` — exact age maths on `java.time` (desugared for API 24).

## Conventions

- UI strings are English; user-facing chat/answers are Urdu.
- No API keys. Wikimedia requires a descriptive User-Agent — reuse `Http.UA`
  (Coil does this via `AboutYouApp`).
- Cleartext HTTP is blocked; upgrade every URL to HTTPS (`Repo.fileToUrl`).
- Wikimedia thumbnail sizes are constrained: only certain px sizes return 200
  (e.g. `500px-...` works, `512px-...` returns HTTP 400). Verify new image URLs with curl.
- Tests live in `app/src/test/java/...` and run on the JVM (no device needed).
