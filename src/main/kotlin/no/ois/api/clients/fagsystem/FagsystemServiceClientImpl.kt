package no.ois.api.clients.fagsystem

import com.fasterxml.jackson.databind.ObjectMapper
import no.ois.api.clients.fagsystem.dtos.AvtaleResponseDTO
import no.ois.api.clients.fagsystem.dtos.KundeResponseDTO
import no.ois.api.controller.ForsikringsDetaljerDTO
import no.ois.api.controller.NyForsikringDTO
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestClient

@Component
class FagsystemServiceClientImpl(
    private val properties: FagsystemServiceProperties,
    restClientBuilder: RestClient.Builder,
    private val objectMapper: ObjectMapper
) : FagsystemServiceClient {

    private val restClient = restClientBuilder
        .messageConverters { it.add(MappingJackson2HttpMessageConverter(objectMapper)) }
        .baseUrl(properties.url.toString()).build()

    override fun opprettAvtale(kundenummer: String, detaljer: ForsikringsDetaljerDTO): AvtaleResponseDTO {
        try {
            return restClient.post()
                .uri("/fagsystem/avtale/opprett")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body("mock")
                .retrieve()
                .body(AvtaleResponseDTO::class.java)!!
        } catch (ex: HttpClientErrorException) {
          //TODO logg feil fra ekstern tjeneste
            throw ex
        } catch (ex: Exception) {
            //TODO logg uventet feil
            throw ex
        }
    }

    override fun opprettKunde(kunde: NyForsikringDTO.Person): KundeResponseDTO {
        try {
            return restClient.post()
                .uri("/fagsystem/kunde/opprett")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body("mock")
                .retrieve()
                .body(KundeResponseDTO::class.java)!!
        } catch (ex: HttpClientErrorException) {
            //TODO logg feil fra ekstern tjeneste
            throw ex
        } catch (ex: Exception) {
            //TODO logg uventet feil
            throw ex
        }
    }
}