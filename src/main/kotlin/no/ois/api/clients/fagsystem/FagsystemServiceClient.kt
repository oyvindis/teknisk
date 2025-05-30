package no.ois.api.clients.fagsystem

import no.ois.api.clients.fagsystem.dtos.AvtaleResponseDTO
import no.ois.api.clients.fagsystem.dtos.KundeResponseDTO
import no.ois.api.controller.NyForsikringDTO

interface FagsystemServiceClient {
    fun opprettAvtale(kundenummer: String): AvtaleResponseDTO
    fun opprettKunde(kunde: NyForsikringDTO.Person): KundeResponseDTO

}