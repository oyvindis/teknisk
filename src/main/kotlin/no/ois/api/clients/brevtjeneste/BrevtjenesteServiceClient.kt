package no.ois.api.clients.brevtjeneste

import no.ois.api.controller.ForsikringsDetaljerDTO


interface BrevtjenesteServiceClient {
    fun sendAvtale(kundenummer: String, avtalenummer: String, detaljer: ForsikringsDetaljerDTO): BrevtjenesteResponseDTO
}