# Wireless tablet receiver project

## Agreed scope

- First receiver: Samsung Tab S10 Lite, Wi-Fi-only. Android/One UI version not yet provided.
- Phone: iPhone 11, iOS 27.0.1 (user-reported).
- No external hardware, dongle or authentication receiver. Windows is the development computer only.
- Wireless priority: Bluetooth bootstrap + Wi-Fi session. No Bluetooth-only media goal.
- Wired support optional later. Friend's ESSGOO-style head unit postponed; Android 9 is only an assumption for that device.

## Current baseline

Official, unchanged DiPlay 0.2.8 APK downloaded into outputs and verified against the GitHub asset SHA-256 and Android v2 signature. Tablet test instructions are in outputs. Neither tablet nor iPhone is connected here; physical session validation requires user testing.

DiPlay is BYD-focused and unsupported on Samsung by its maintainer. Its published APK includes an experimental recovered accessory identity. Source builds omit that identity. Do not conflate an identity-free build, passive compatibility checks or a passing signature check with a working wireless CarPlay session.

## Preparatory work

`receiver/` contains an xcertplay master snapshot with a new generic readiness launcher, report export, separate package ID and Windows build helper. These changes have not been compiled or device-tested. No authentication identity is included, so it is not a standalone solution. Do not deliver it as one.

Portable JDK 25 and Android SDK/NDK are in work/tools. Gradle configuration (`help`) passed; this was not an APK build or test-suite run. Caches and downloads are under work.

## Next evidence needed

Run the official baseline APK on the tablet in Wi-Fi Direct mode, record the first failure stage or successful session, and export its diagnostics. Use that evidence to decide whether Samsung-specific connection fixes are needed before investing in a custom wireless receiver build. No external authentication hardware is an acceptable fallback under the agreed scope.
