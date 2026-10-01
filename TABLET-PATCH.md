# Tablet Wi-Fi Direct patch 1 — 2026-10-01

Based on the published DiPlay 0.2.8 source archive. This is a local experimental derivative, not an upstream release. License and third-party notices are retained.

## Reproduction

Samsung SM-X400 / Galaxy Tab S10 Lite Wi-Fi, Android 16/API 36, build BP4A.251205.006.X400XXS7CZH6. Phone: iPhone 11/iOS 27.0.1. The supplied log repeatedly reaches Wi-Fi P2P group creation and then fails with `Unsupported Wi-Fi P2P security type: -1`. USB subsequently authenticates, renders H.264 video and plays media audio.

## Changes

- `shared/.../network/P2pSecurityPolicy.kt`: preserve explicit WPA2/WPA3 mappings; map API 36 UNKNOWN to the known legacy WPA2 creation request only when the tablet is group owner, the returned SSID matches the requested SSID, a valid creation passphrase exists, and any returned passphrase matches. Reject unknown security for foreign or system-generated groups and conflicting credentials. No open-network fallback.
- `shared/.../network/WifiP2pGroupManager.kt`: pass creation context into that policy and log the compatibility path without credentials.
- `shared/.../network/P2pSecurityPolicyTest.kt`: regression coverage for the reported failure and rejection cases.
- `common/.../CarPlayHostActivity.kt` and English strings: a security-reporting error no longer becomes a blanket unsupported-device message.
- `common/.../AirPlayPersistence.kt`: a fresh installation defaults to Wi-Fi Direct on Android 10+.
- `mobile/build.gradle.kts` and app label: distinct debug package `com.shihab.diplay.tablettest`, version `0.2.8-tablet1`, label `DiPlay Tablet Test`, allowing installation beside the original.

Android defines -1 as SECURITY_TYPE_UNKNOWN when the framework cannot derive security from the supplicant's authentication information. It does not mean P2P_UNSUPPORTED. The fallback relies on the explicit legacy SSID/passphrase group request; it does not prove a wireless CarPlay session will succeed. Later Bluetooth, addressing or media issues may become visible after this blocker is removed.

Reference: https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pGroup#SECURITY_TYPE_UNKNOWN

## Runtime authentication and signing

The local test APK retains the two experimental runtime authentication assets from the checksum-verified original DiPlay 0.2.8 APK that the user tested successfully over USB. Their contents are not part of this source tree. They are supplied to the existing upstream standalone build through `DIPLAY_AUTH_ASSETS_DIR`. No remote authentication service or additional hardware is introduced. The original project's authentication limitations still apply.

The new APK uses a local Android debug signing certificate and a different package ID. It cannot update the upstream signed package and does not share its settings or pair records. Future local updates should use the same local debug keystore.

## Windows build

Requirements: JDK 25, Android SDK 37, NDK 28.2.13676358, included Gradle wrapper. Set JAVA_HOME and ANDROID_HOME. Set DIPLAY_AUTH_ASSETS_DIR to the private local asset directory containing `offline-mfi/identity.pk8` and `offline-mfi/certificate.p7b`.

```powershell
.\gradlew.bat :shared:testDebugUnitTest :common:testDebugUnitTest :mobile:lintDebug :mobile:assembleStandaloneDebug --console=plain
```

The source archive intentionally excludes runtime assets, signing keys, local.properties, caches and build output. The build result and live testing status are documented in the delivered tablet test guide.
