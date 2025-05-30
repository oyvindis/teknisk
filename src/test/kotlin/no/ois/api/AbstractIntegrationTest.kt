package no.ois.api

import com.github.tomakehurst.wiremock.WireMockServer
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock
import com.maciejwalkowiak.wiremock.spring.EnableWireMock
import com.maciejwalkowiak.wiremock.spring.InjectWireMock
import no.ois.api.mocks.BrevtjenesteServiceMockConfigurator
import no.ois.api.mocks.FagsystemServiceMockConfigurator
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.BasicJsonTester
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpResponse
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.client.RestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock(
    ConfigureWireMock(name = "fagsystem-service", property = "fagsystem-service.url"),
    ConfigureWireMock(name = "brevtjeneste-service", property = "brevtjeneste-service.url")
)
@ActiveProfiles("junit")
abstract class AbstractIntegrationTest {

    @InjectWireMock("fagsystem-service")
    protected lateinit var fagsystemMock: WireMockServer

    @InjectWireMock("brevtjeneste-service")
    protected lateinit var brevtjenesteMock: WireMockServer

    @LocalServerPort
    private var localServerPort: Int = 0

    val baseUrl by lazy { "http://localhost:$localServerPort" }

    private val restClient by lazy {
        RestClient.builder()
            .baseUrl(baseUrl)
            .build()
    }

    protected val jsonTester = BasicJsonTester(javaClass)

    @BeforeEach
    fun settOppFagsystemMockConfigurator() {
        FagsystemServiceMockConfigurator(fagsystemMock).settOpp()
    }

    @BeforeEach
    fun settOppBrevtjenesteMock() {
        BrevtjenesteServiceMockConfigurator(brevtjenesteMock).settOpp()
    }

    protected fun post(url: String, jsonBody: String) =
        restClient.post().execute(url = url, jsonBody = jsonBody)

    private fun RestClient.RequestBodyUriSpec.execute(url: String, jsonBody: String): ResponseWithStringBody? =
        uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonBody)
            .execute()



    private fun RestClient.RequestHeadersSpec<*>.execute(): ResponseWithStringBody? =
        exchange { request, response ->
            val body = response.bodyTo(String::class.java)

            ResponseWithStringBody(
                response = response,
                bodyAsString = body
            )
        }
}

data class ResponseWithStringBody(val response: ClientHttpResponse, val bodyAsString: String?)

