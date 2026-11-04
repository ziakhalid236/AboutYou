# About You

A premium Android app that tells you everything about a person from their **name** and **date of birth**.

Package: `com.premium.aboutyou`

## What it does

1. **Name insights** — type any name and the app shows its meaning, origin, culture,
   gender, lucky number/colour, personality traits, live name statistics and the
   famous people who share that name.
2. **Live age counter** — pick a date of birth and the app shows a timer that ticks
   every second: years, months, days, hours, minutes and seconds since birth,
   plus totals (total months, weeks, days, hours, minutes, seconds) and a countdown
   to the next birthday.
3. **Star sign** — the zodiac sign for the date, with its element, quality, ruling
   planet, lucky numbers, best matches, strengths, weaknesses and a sign image.
4. **Famous birthdays** — everyone famous born on the same day (any year) **and**
   on the exact same date (day + month + year).

## Data sources

Everything is live and searched across the whole internet — no single platform:

| Source | Used for |
| --- | --- |
| `api.wikimedia.org` on-this-day feed | famous births on a month/day, with year |
| `query.wikidata.org` (SPARQL) | births on an exact date (day + month + year) |
| `en.wikipedia.org` API | name summaries, article extracts, search, thumbnails |
| `commons.wikimedia.org` | portrait images |
| `api.genderize.io` | gender of a name |
| `api.nationalize.io` | countries where a name is most common |

No API keys are required. All requests send a descriptive `User-Agent`.

## Screens

- **Home** — enter a name, choose a date of birth.
- **Name Insights** — meaning, profile, traits, live statistics, encyclopaedia entry, famous namesakes.
- **Your Time Alive** — live counter, totals, next birthday, star sign.
- **Born On Your Day** — exact-date and same-day famous birthdays.

## Design

Dark premium look: deep navy gradient background, gold/violet/cyan accents,
glass-style cards with soft borders, gradient headlines and ambient glows.
Material 3 with edge-to-edge layout and proper system-bar insets.

## Build

Requirements: JDK 17+ and the Android SDK (platform 34, build-tools 34.0.0).

```bash
# Debug APK
./gradlew assembleDebug
# output: app/build/outputs/apk/debug/app-debug.apk

# Release APK (signed)
./gradlew assembleRelease
# output: app/build/outputs/apk/release/app-release.apk

# Unit tests
./gradlew testDebugUnitTest
```

The release build is signed with `keystore/aboutyou.jks`
(alias `aboutyou`, store/key password `aboutyou123`). Replace this keystore
before publishing to Google Play.

## Continuous integration

`.github/workflows/build-apk.yml` builds the app on GitHub:

- runs on every push to `main`, on pull requests, and manually (Actions → *Build APK* → *Run workflow*);
- runs the unit tests, then builds the debug and release APKs;
- uploads both APKs as workflow artifacts (Actions → run → *Artifacts*);
- on a push to `main`, attaches both APKs to a GitHub Release (`v1.0.<run-number>`).

Download the latest APK from the
[Releases page](https://github.com/ziakhalid236/AboutYou/releases).

## Project layout

```
app/src/main/java/com/premium/aboutyou/
  MainActivity.kt        navigation, date/time picker
  MainViewModel.kt       state and loading for every screen
  AboutYouApp.kt         Application + Coil image loader
  data/                  models, offline name meanings, zodiac signs
  net/                   OkHttp wrapper and the live data repository
  util/AgeCalc.kt        exact age maths (java.time)
  ui/components/         glass cards, gradient text, shared widgets
  ui/screens/            Home, Name, Age, Birthday screens
  ui/theme/              colours, typography, theme
app/src/test/java/       unit tests for age maths, zodiac and name data
```

## Notes

- `minSdk 24`; `java.time` is provided on older devices through core library desugaring.
- Cleartext HTTP is blocked; every URL is upgraded to HTTPS.
- The app is data-driven: if a name is not in the curated offline list, a stable
  fallback profile is generated and enriched with live Wikipedia data.
