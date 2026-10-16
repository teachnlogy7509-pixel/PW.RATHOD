# Preview verification

Web UI tested in Chromium at 390 × 844 and 1440 × 1000.

Passed: 11 routes without horizontal document overflow or uncaught JavaScript errors; subject filter; note reader and marking read; save shelf; complete quiz scoring; local doubts; HTML escaping; profile persistence; focus timer start, pause and reset; daily task persistence; mobile menu; classroom tabs.

24 route/dialog views rendered and reviewed. Dark mode, mobile menu, no-results state and offline app-shell/notes navigation were also checked. Mobile batch artwork and SVG close controls adjusted after visual review.

Android native source requires the included build workflow; its generated APK is available as an Actions artifact. GitHub Actions compiled the Android debug APK successfully in run 38854434554. No physical Android device test or Play Store readiness certification has been performed. Live/backend integration and data migration are deferred.
