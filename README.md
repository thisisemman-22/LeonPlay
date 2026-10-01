# LeonPlay

**Wireless CarPlay for Android** - bring Apple CarPlay to any Android device, no wires needed.

LeonPlay is a wireless CarPlay receiver built on top of [DiPlay](https://github.com/nicedayzhu/DiPlay) (an open-source CarPlay receiver based on xcertplay, licensed under GPL-3.0). This fork adds improved hardware compatibility, audio fixes for MediaTek head units, and a refreshed UI.

## Features

- **Wireless CarPlay** - connect your iPhone to your Android device via Wi-Fi
- **Universal Audio** - automatic codec fallback for maximum hardware compatibility
- **Fine-tuning Controls** - software decoder toggles for audio (AAC) and video (HEVC)
- **Works on Tablets & Head Units** - tested on standard Android tablets and AC8227L head units
- **Custom Theme** - warm amber on charcoal dark UI

## Tested Hardware

| Device | Platform | Android | Status |
|--------|----------|---------|--------|
| AC8227L Head Unit | ARMv7 (MT632L) | 13 (spoofed, API 27) | Working |
| Android Tablet | ARM64 | 13+ | Working |

## Building

```bash
# Set required environment variables
export DIPLAY_AUTH_ASSETS_DIR=/path/to/auth/assets
export JAVA_HOME=/path/to/jdk21
export ANDROID_HOME=/path/to/android-sdk

# Build standalone debug APK
./gradlew :mobile:assembleStandaloneDebug
```

The APK will be at `mobile/build/outputs/apk/debug/mobile-debug.apk`.

## Audio Troubleshooting

If audio doesn't work on your device, go to **Settings > Display and performance** and try:

1. **Software AAC decoder** - forces Google's software AAC decoder instead of the hardware one. Fixes audio on buggy MediaTek units.
2. **Software HEVC decoder** - forces software HEVC video decoding.
3. **Music buffer** - increase to 500ms or 1000ms for more stable audio on slow devices.

The app automatically tries the standard audio configuration first, and falls back to a MediaTek-compatible mode if it fails.

## Credits

- **[DiPlay](https://github.com/nicedayzhu/DiPlay)** by Shihab Al-Amri - the open-source CarPlay receiver that serves as the foundation for this project
- **xcertplay** - the underlying CarPlay protocol engine (GPL-3.0)
- Built by [@thisisemman-22](https://github.com/thisisemman-22)

## License

This project inherits the GPL-3.0 license from its upstream dependencies.
