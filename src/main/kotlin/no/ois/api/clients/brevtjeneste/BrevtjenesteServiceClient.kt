package no.ois.api.clients.brevtjeneste


interface BrevtjenesteServiceClient {
    fun sendAvtale(avtale: String): BrevtjenesteResponseDTO
}