package no.ois.api.clients.fagsystem.dtos

class OpprettKundeRequestDTO(
    val foedselsnummer: String,
    val navn: String,
    val adresse: String,
    val postnummer: String,
    val poststed: String,
    val email: String
)