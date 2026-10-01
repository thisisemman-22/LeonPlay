package com.shilapi.xcertplay.network

import android.net.wifi.p2p.WifiP2pGroup
import com.shilapi.xcertplay.transport.Iap2WirelessSecurity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.io.IOException

class P2pSecurityPolicyTest {
    private fun resolve(type: Int = -1, owner: Boolean = true,
                        actualSsid: String? = "DIRECT-dp-test",
                        actualPassphrase: String? = "test-passphrase-123",
                        requestedSsid: String? = "DIRECT-dp-test",
                        requestedPassphrase: String? = "test-passphrase-123") =
        P2pSecurityPolicy.resolve(type, owner, actualSsid, actualPassphrase, requestedSsid, requestedPassphrase)

    @Test fun samsungApi36UnknownUsesOurKnownLegacyGroup() {
        assertEquals(Iap2WirelessSecurity.WPA_WPA2, resolve())
    }
    @Test fun omittedFrameworkPassphraseUsesOurExplicitCreationInput() {
        assertEquals(Iap2WirelessSecurity.WPA_WPA2, resolve(actualPassphrase = null))
    }
    @Test fun neverDowngradesKnownWpa3() {
        assertEquals(Iap2WirelessSecurity.WPA3_ONLY, resolve(type = WifiP2pGroup.SECURITY_TYPE_WPA3_SAE))
        assertEquals(Iap2WirelessSecurity.WPA3_TRANSITION, resolve(type = WifiP2pGroup.SECURITY_TYPE_WPA3_COMPATIBILITY))
        assertEquals(Iap2WirelessSecurity.WPA_WPA2, resolve(type = WifiP2pGroup.SECURITY_TYPE_WPA2_PSK))
    }
    @Test fun unknownSystemGeneratedGroupIsRejected() {
        assertThrows(IOException::class.java) { resolve(requestedSsid = null, requestedPassphrase = null) }
    }
    @Test fun foreignGroupIsRejected() {
        assertThrows(IOException::class.java) { resolve(actualSsid = "DIRECT-other-app") }
    }
    @Test fun clientRoleIsRejected() {
        assertThrows(IOException::class.java) { resolve(owner = false) }
    }
    @Test fun missingReturnedSsidIsRejected() {
        assertThrows(IOException::class.java) { resolve(actualSsid = null) }
    }
    @Test fun differentReturnedPassphraseIsRejected() {
        assertThrows(IOException::class.java) { resolve(actualPassphrase = "a-different-password") }
    }
    @Test fun invalidRequestedCredentialsAreRejected() {
        for (passphrase in listOf(null, "", "short", "x".repeat(64), "non-ascii-\u00e9")) {
            assertThrows(IOException::class.java) { resolve(actualPassphrase = null, requestedPassphrase = passphrase) }
        }
    }
    @Test fun futureUnrecognizedTypeIsNotGuessed() {
        assertThrows(IOException::class.java) { resolve(type = 99) }
    }
}
