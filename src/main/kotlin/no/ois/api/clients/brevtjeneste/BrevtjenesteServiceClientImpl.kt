package no.ois.api.clients.brevtjeneste

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class BrevtjenesteServiceClientImpl(
    private val properties: BrevtjenesteServiceProperties,
    restClientBuilder: RestClient.Builder,
) : BrevtjenesteServiceClient {

    private val restClient = restClientBuilder.baseUrl(properties.url.toString()).build()

    override fun sendAvtale(avtale: String): BrevtjenesteResponseDTO {
        try {
            return restClient.post()
                .uri("/brevtjeneste/send")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body("mock")
                .retrieve()
                .body(BrevtjenesteResponseDTO::class.java)!!
        } catch (ex: Exception) {
            throw ex
        }
    }
}