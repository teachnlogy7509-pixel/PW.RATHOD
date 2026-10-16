# Rathod Hub 3.0 — Study App Preview

An independent, mobile-first study application for **PW.RATHOD**. Responsive web/PWA frontend plus an Android WebView application that bundles the same frontend locally.

**This is a design/navigation preview, not a data migration or an official Physics Wallah app.** It does not unlock, extract or include third-party course content, accounts, enrollment or batch access. The supplied compiled Android ZIP cannot be merged into editable web source; this is a new implementation inspired by common education-app navigation, not an exact reconstruction of that APK.

## Included
- Responsive dashboard, subject shortcuts, three sample batch classrooms.
- Classroom tabs: Overview, Lessons, Notes and Practice.
- Searchable/filterable study library, four original sample notes, saved shelf and read progress.
- Four-question original sample quiz with explanations and local result history.
- Device-only doubts and community posts, editable local profile, daily checklist.
- Working 25-minute focus timer, reset/pause, announcements and mobile navigation.
- Light/dark appearance based on device preferences, keyboard navigation and labelled controls.
- Installable PWA with cached app shell and bundled sample notes.
- Android project with local HTTPS assets, restricted WebView, native back handling and APK build workflow.

## Explicitly deferred
No backend login, payment, enrollment, real videos, teacher answers, live chat, real leaderboard, uploads, server notifications or existing Rathod Hub data is connected. The original RATHOD-HUB-3.0 repository and its databases are unchanged. All preview edits use localStorage (`rhpw_` prefix), not a backend; they do not sync between devices or between web and Android. Clearing browser/app data removes them. The focus timer requires the app to remain open.

## Run web locally
```sh
node scripts/generate-icons.mjs
python3 -m http.server 8080
# Open http://localhost:8080
```
PWA installation requires HTTPS (or localhost). Automatic service-worker registration is enabled on HTTPS sites and localhost. Cached offline mode becomes available after the first successful online visit. No package installation is needed.

## Deploy the website
The `Deploy Web App` GitHub Actions workflow packages only public web assets and deploys GitHub Pages on pushes to `main`. GitHub repository Settings → Pages → Source must be **GitHub Actions**; a repository administrator may need to enable Pages. The workflow attempts Pages enablement but repository policy may require this one-time manual step.

Expected URL after successful deployment:
https://teachnlogy7509-pixel.github.io/PW.RATHOD/

## Build and download the Android APK
GitHub → **Actions → Build Android APK → latest successful run → Artifacts → Rathod-Hub-3.0-debug-APK**. Download and unzip the artifact to find `app-debug.apk`.

The workflow runs on pushes to `main` and can also be started with **Run workflow**. It uses Java 17, Android SDK 35 and Gradle 8.9. A successful workflow is required before an APK is available. The Android project is source, not a prebuilt APK.

Local build (with Android SDK and Gradle 8.9 installed):
```sh
node scripts/sync-android.mjs
gradle --no-daemon -p android :app:assembleDebug
```
The sync step must run before each Android build. Android minimum version: 7.0 / API 24; install an up-to-date Android System WebView. Application ID: `com.rathodhub.study`.

The generated APK is **debug-signed for testing**, not a Play Store release. CI debug signing keys are ephemeral and may differ across builds; uninstall a prior preview if Android rejects an update (this clears local data). Production needs a stable signing key, release build, privacy review, real backend authorization and device testing. Never commit signing keys or passwords.

## Layout
```
index.html / styles.css / app.js   shared responsive app
icons/ / manifest.webmanifest     PWA icons & installation
scripts/generate-icons.mjs        produce raster icons from embedded originals
sw.js                            same-origin offline cache
android/                         native Android wrapper
scripts/sync-android.mjs          copy shared app into Android assets
.github/workflows/                web deployment + Android APK build
```

No original APK binaries, third-party logos, signing material, Firebase config, Supabase credentials or private user data are copied into this project. Backend integration is a later, explicit task.
