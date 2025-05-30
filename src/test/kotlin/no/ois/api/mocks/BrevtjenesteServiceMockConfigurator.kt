package no.ois.api.mocks

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock

class BrevtjenesteServiceMockConfigurator(val mock: WireMockServer) {
    fun settOpp() {
        mock.stubFor(
            WireMock.post(WireMock.urlPathEqualTo("/brevtjeneste/send"))
                .willReturn(WireMock.okJson(
                    """
                        {
                          "status": "AVTALE_SENDT"
                        }
                    """.trimIndent()
                ))
        )
    }
}