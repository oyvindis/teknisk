package no.ois.api.controller

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import no.ois.api.clients.brevtjeneste.BrevtjenesteServiceClient
import no.ois.api.clients.fagsystem.FagsystemServiceClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/forsikring")
class ForsikringController(
    private val fagsystemServiceClient: FagsystemServiceClient,
    private val brevtjenesteServiceClient: BrevtjenesteServiceClient
) {

    @PostMapping("/avtale")
    @Operation(summary = "Opprett ny avtale")
    @ResponseStatus(HttpStatus.CREATED)
    fun opprettForsikring(
        @Valid @RequestBody dto: NyForsikringDTO,
        uriComponentsBuilder: UriComponentsBuilder
    ) : ResponseEntity<OpprettetForsikringResponseDTO> {

        when ( val detaljer = dto.forsikringsdetaljer) {
            is ForsikringsDetaljerBilDTO -> {
                val kundeResponseDTO = fagsystemServiceClient.opprettKunde(dto.forsikringstaker)

                val avtaleResponseDTO = fagsystemServiceClient.opprettAvtale(kundeResponseDTO.kundenummer, detaljer)

                val brevtjenesteResponseDTO = brevtjenesteServiceClient.sendAvtale(
                    kundenummer = kundeResponseDTO.kundenummer,
                    avtalenummer = avtaleResponseDTO.avtalenummer,
                    detaljer = detaljer)

                return ResponseEntity
                    .created(uriComponentsBuilder.path("/forsikring/avtale/${avtaleResponseDTO.avtalenummer}").build().toUri())
                    .body(OpprettetForsikringResponseDTO(avtalenummer = avtaleResponseDTO.avtalenummer, status = brevtjenesteResponseDTO.status))
            }
        }
    }
}