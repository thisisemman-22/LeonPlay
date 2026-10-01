package com.shilapi.xcertplay.network

import android.net.wifi.p2p.WifiP2pGroup
import com.shilapi.xcertplay.transport.Iap2WirelessSecurity
import java.io.IOException

/**
 * API 36 can return UNKNOWN when the vendor cannot report the group's AKM.
 * For our explicit legacy SSID/passphrase creation request, retain WPA2 semantics
 * only if the returned owner and credentials identify that same group. Never
 * guess security for a system-generated, foreign, or explicitly WPA3 group.
 */
internal object P2pSecurityPolicy {
    fun resolve(
        reportedType: Int,
        isGroupOwner: Boolean,
        actualSsid: String?,
        actualPassphrase: String?,
        requestedSsid: String?,
        requestedPassphrase: String?,
    ): Iap2WirelessSecurity = when (reportedType) {
        WifiP2pGroup.SECURITY_TYPE_WPA2_PSK -> Iap2WirelessSecurity.WPA_WPA2
        WifiP2pGroup.SECURITY_TYPE_WPA3_COMPATIBILITY -> Iap2WirelessSecurity.WPA3_TRANSITION
        WifiP2pGroup.SECURITY_TYPE_WPA3_SAE -> Iap2WirelessSecurity.WPA3_ONLY
        WifiP2pGroup.SECURITY_TYPE_UNKNOWN -> {
            val matchesExplicitLegacyGroup = isGroupOwner &&
                !requestedSsid.isNullOrBlank() && actualSsid == requestedSsid &&
                requestedPassphrase != null && requestedPassphrase.length in 8..63 &&
                requestedPassphrase.all { it.code in 32..126 } &&
                (actualPassphrase.isNullOrBlank() || actualPassphrase == requestedPassphrase)
            if (!matchesExplicitLegacyGroup) {
                throw IOException("Wi-Fi Direct security could not be determined for this group; reset CarPlay Wi-Fi and retry")
            }
            Iap2WirelessSecurity.WPA_WPA2
        }
        else -> throw IOException("Unsupported Wi-Fi P2P security type: $reportedType")
    }
}
