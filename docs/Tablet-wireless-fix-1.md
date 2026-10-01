# Tab S10 Lite wireless fix — test 1

Target: Samsung SM-X400 (Tab S10 Lite Wi-Fi), Android 16/API 36, paired with iPhone 11 on iOS 27.0.1.

## What the diagnostic report establishes

The wireless attempts were already using Wi-Fi Direct, not the tablet's unavailable Mobile Hotspot feature. At 21:49:21, Wi-Fi and permissions were enabled and the app requested a group on 5180 MHz. It then rejected `Unsupported Wi-Fi P2P security type: -1`. Subsequent attempts repeated this result, including retained owned groups.

Android defines -1 as **unknown security type**, which can occur when the framework cannot derive security details from its Wi-Fi driver/service. DiPlay 0.2.8 treats it as unsupported and translates that into a generic message suggesting that the head unit may not support wireless CarPlay. That message is not a hardware compatibility finding.

The USB session later passed authentication, rendered its first H.264 frame at 21:51:54, and received/played media audio. This is concrete evidence that the original experimental identity and the tablet's media path worked for that wired session. It does not yet establish the wireless Bluetooth/Wi-Fi handshake or Siri/call behavior.

## What changed

The patched app recognizes an unknown security report for an explicitly created legacy WPA2 Wi-Fi Direct group belonging to this app. It verifies ownership, matching network name and creation credentials before using that compatibility path. Explicit WPA3 reports retain their original mapping; unrelated groups and conflicting credentials are rejected.

The app also defaults new installations to Wi-Fi Direct and gives a more accurate message when security cannot be determined.

This is a separately signed experimental build named **DiPlay Tablet Test**, package `com.shihab.diplay.tablettest`, version `0.2.8-tablet1`. It can be installed beside the original. It retains the original APK's experimental software authentication assets locally; no dongle, remote authentication service or extra receiver is added.

## Install and retest

1. Disconnect the USB cable from the iPhone. Stop the session in the original DiPlay, then **Settings → Apps → DiPlay → Force stop**. Keep the original installed so its working wired setup remains available.
2. Install `DiPlay-Tablet-Test-0.2.8-tablet1.apk` on the tablet. Open **DiPlay Tablet Test**, not the original DiPlay icon.
3. Allow the requested Nearby devices/Wi-Fi/Bluetooth permissions. Allow microphone access when testing Siri or calls.
4. Verify **Settings → Connection setup → Wi-Fi Direct**. No Mobile Hotspot toggle is required. Keep Wi-Fi and Bluetooth on. Both devices being on the same home network is neither required nor sufficient for this mode.
5. If an old group is reported as busy or needing reset, use **Wireless connection help → Reset CarPlay Wi-Fi** in the test app. Confirm removal of the old CarPlay group when asked, with other projection apps stopped.
6. Use **Choose iPhone**, select the already paired iPhone, and tap **Connect phone**. Accept the iPhone's CarPlay prompt if shown. Start with H.264/30 fps and default display size.
7. Test the first picture, touch and tablet-speaker audio. Then test Siri and reconnect separately.

If it fails again, save a fresh diagnostic report **from DiPlay Tablet Test**. The expected new diagnostic line, if the Samsung compatibility path is used, is:

`Wi-Fi P2P security reported=UNKNOWN resolved=WPA2 source=matching_app_created_legacy_group`

The next important milestones are `Wi-Fi P2P ready`, Bluetooth/iAP2 authentication and an active AirPlay session. A later failure can be a separate issue; avoid repeatedly reinstalling or changing several network settings at once.

## Limits

This patch addresses the error captured in your report. No live wireless test has been performed here. The developer's experimental accessory identity and future iOS compatibility limitations still apply. Wired code was not intentionally changed, but the new package has its own settings and must be tested separately if used for USB.

The corresponding modified source is provided in `DiPlay-Tablet-Test-0.2.8-tablet1-source.zip`. Original GPL/AGPL notices are retained; see `TABLET-PATCH.md` inside for the code changes and local build requirements.

Reference: [Android's SECURITY_TYPE_UNKNOWN definition](https://developer.android.com/reference/android/net/wifi/p2p/WifiP2pGroup#SECURITY_TYPE_UNKNOWN).
