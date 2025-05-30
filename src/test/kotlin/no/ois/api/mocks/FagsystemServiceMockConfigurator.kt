package no.ois.api.mocks

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock

class FagsystemServiceMockConfigurator(val mock: WireMockServer) {
    fun settOpp() {
        mock.stubFor(
            WireMock.post(WireMock.urlPathEqualTo("/fagsystem/avtale/opprett"))
                .willReturn(WireMock.okJson(
                    """
                        {
                          "avtalenummer": "A-100"
                        }
                    """.trimIndent()
                ))
        )

        mock.stubFor(
            WireMock.post(WireMock.urlPathEqualTo("/fagsystem/kunde/opprett"))
                .willReturn(WireMock.okJson(
                    """
                        {
                          "kundenummer": "K-100"
                        }
                    """.trimIndent()
                ))
        )
    }
}