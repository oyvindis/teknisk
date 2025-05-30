package no.ois.api.controller

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class NyForsikringDTO(
    @field:Valid
    val forsikringstaker: Person,
) {
    data class Person(
        @field:NotBlank @field:Size(min = 2, max = 100)
        val navn: String,

        @field:Pattern(regexp = "\\d{11}")
        val foedselsnummer: String,
    )
}