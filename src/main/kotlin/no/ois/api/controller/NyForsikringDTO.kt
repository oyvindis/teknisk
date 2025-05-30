package no.ois.api.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class NyForsikringDTO(
    @field:Valid
    val forsikringstaker: Person,

    @field:Valid
    val forsikringsdetaljer: ForsikringsDetaljerDTO
) {
    data class Person(
        @field:NotBlank @field:Size(min = 1, max = 100)
        val navn: String,

        @field:NotBlank @field:Size(min = 1, max = 100)
        val adresse: String,

        @field:NotBlank @field:Size(min = 1, max = 100)
        val poststed: String,

        @field:Pattern(regexp = "\\d{4}", message = "Postnummer 4 siffer")
        val postnummer: String,

        @field:Email @field:NotBlank @field:Size(min = 1, max = 100)
        val email: String,

        @field:Pattern(regexp = "\\d{11}")
        val foedselsnummer: String,
    )
}