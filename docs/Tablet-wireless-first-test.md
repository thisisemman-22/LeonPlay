# First wireless CarPlay test

**Receiver:** Samsung Galaxy Tab S10 Lite, Wi-Fi-only model  
**Phone:** iPhone 11, iOS 27.0.1 (user-reported)  
**Requirement:** No dongle, extra receiver or computer needed during use. Wireless first; USB is optional later.

## What this package is

`DiPlay-0.2.8.apk` is the unmodified developer-published DiPlay prerelease. It is a baseline experiment, not our custom app and not a confirmed Samsung-compatible release. Its developer currently supports BYD head units. This exact tablet/phone/iOS combination has not been tested here.

The release contains the developer's experimental software authentication identity. It does not require an external MFi dongle for its intended operation, but future iOS acceptance and compatibility remain uncertain. Our own xcertplay source prototype does not include an authentication identity and is not the APK to use for this test.

## Install on the tablet

1. Transfer `DiPlay-0.2.8.apk` to the tablet or download it from the official release linked below.
2. Open the APK in My Files and allow installation from that source if Android asks. If Samsung blocks it, record the exact message so we can address that specific restriction.
3. Open DiPlay. Allow Nearby devices/Bluetooth and nearby Wi-Fi permissions it requests. Microphone access is needed for Siri/calls. Allow notifications if requested for connection controls.
4. Close other projection/receiver apps on the tablet before testing.

## Connect wirelessly

1. Turn on Wi-Fi and Bluetooth on both the tablet and iPhone. Leave the iPhone unlocked for first pairing.
2. In DiPlay, open **Settings → Connection setup** and select **Wi-Fi Direct**. Save the selection. The app may use car/head-unit wording even though it is running on a tablet.
3. Pair the iPhone and tablet through their normal Bluetooth settings, accepting the matching pairing code on both devices.
4. Return to DiPlay. Use **Choose iPhone** if needed, then **Connect phone**. Accept the CarPlay prompt on the iPhone if one appears.
5. Let the app establish the Wi-Fi connection. This is not a manual pairing operation in an iPhone “Wi-Fi Direct” menu. Do not turn on the iPhone's Personal Hotspot for this test.
6. Start with **30 fps**, **Efficient video/HEVC off**, and **Default icon/text size**. Use **Apply and reconnect** if changing these while connected.

Wi-Fi carries the projected session; Bluetooth assists setup. There is no Bluetooth-only video mode planned. The Wi-Fi-only tablet does not need a SIM for this test. Whether this device's firmware and this iOS version complete the session remains to be established.

## First success criteria

Test these in order and note the first failure:

1. App opens and Wi-Fi Direct can be selected.
2. Bluetooth pairing succeeds and DiPlay lists the iPhone.
3. The iPhone offers CarPlay / the app completes authentication.
4. CarPlay appears and touch controls work.
5. Music plays through the tablet speaker for two minutes.
6. Siri can hear you through the tablet microphone.
7. Reopening the app reconnects successfully.

Use the tablet's own speakers and microphone initially. Car audio integration, steering controls and the friend's ESSGOO unit are later work. Leave BYD dashboard/HUD, vehicle battery/speed and parked-video features disabled; they are unnecessary for this test. No ADB setup is required for core projection.

## If it fails

Record the visible error or the last successful step. Then use **Settings → Diagnostics → Save diagnostic report**. The project documents reports under **Downloads/DiPlay** on Android 10+. Send the report back in this chat together with the tablet's Android/One UI version. Review the report before sharing; do not include your passwords.

If the app specifically reports a stale or busy Wi-Fi Direct group, close other projection apps and try **Settings → Wireless connection help → Reset CarPlay Wi-Fi**, then reconnect. Do not repeatedly reinstall: that can discard useful state and logs. A failure can be due to pairing, Wi-Fi group setup, authentication, media negotiation or Samsung firmware; the report helps identify which one.

## Verification performed on Windows

- Downloaded the APK from the official GitHub v0.2.8 release.
- SHA-256 matched the release asset's published digest.
- Android APK signature verification passed (v2 signing).
- Package ID: `com.shihab.diplay`; version 0.2.8, version code 27.
- APK includes ARM64 native code.
- No live tablet/iPhone test or comprehensive security audit was performed. Integrity checks do not establish device compatibility.

SHA-256:
`9b36a0866608244e422053d6027706b4672d8f2f4a8be9eb7e612411ffb6bf48`

## Sources and license

- [Official release and corresponding source](https://github.com/shihabal3amri/DiPlay/releases/tag/v0.2.8)
- [Installation guide](https://github.com/shihabal3amri/DiPlay/blob/main/docs/INSTALL.md)
- [Compatibility notes](https://github.com/shihabal3amri/DiPlay/blob/main/docs/COMPATIBILITY.md)
- [Project, authentication notice and licensing](https://github.com/shihabal3amri/DiPlay)

The upstream project identifies GPL-3.0 for the xcertplay base and AGPL-3.0 notices for adapted UI/site components. The official release supplies the corresponding source; the APK supplied here is unchanged.
