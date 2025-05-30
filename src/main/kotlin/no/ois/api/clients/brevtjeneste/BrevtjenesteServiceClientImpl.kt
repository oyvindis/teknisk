package no.ois.api.clients.brevtjeneste

import no.ois.api.controller.ForsikringsDetaljerDTO
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient

@Component
class BrevtjenesteServiceClientImpl(
    private val properties: BrevtjenesteServiceProperties,
    restClientBuilder: RestClient.Builder,
) : BrevtjenesteServiceClient {

    private val restClient = restClientBuilder.baseUrl(properties.url.toString()).build()

    override fun sendAvtale(kundenummer: String, avtalenummer: String, detaljer: ForsikringsDetaljerDTO): BrevtjenesteResponseDTO {
        try {
            return restClient.post()
                .uri("/brevtjeneste/send")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body("mock")
                .retrieve()
                .body(BrevtjenesteResponseDTO::class.java)!!
        } catch (ex: HttpClientErrorException) {
            //TODO logg feil fra ekstern tjeneste
            throw ex
        } catch (ex: Exception) {
            //TODO logg uventet feil
            throw ex
        }
    }
}