package no.ois.api

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import no.ois.api.mocks.BrevtjenesteServiceMockConfigurator
import no.ois.api.mocks.FagsystemServiceMockConfigurator
import org.springframework.boot.runApplication


fun main(args: Array<String>) {

    val fagsystemMock = WireMockServer(WireMockConfiguration.options().dynamicPort())
        .also(WireMockServer::start)
    FagsystemServiceMockConfigurator(fagsystemMock).settOpp()

    val brevtjenesteMock = WireMockServer(WireMockConfiguration.options().dynamicPort())
        .also(WireMockServer::start)
    BrevtjenesteServiceMockConfigurator(brevtjenesteMock).settOpp()


    runApplication<ApiApplication>(*args) {
        setAdditionalProfiles("local")
        setDefaultProperties(
            mapOf(
                "fagsystem-service.url" to fagsystemMock.baseUrl(),
                "brevtjeneste-service.url" to brevtjenesteMock.baseUrl()
            )
        )
    }
}
