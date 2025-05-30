package no.ois.api.controller

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.PositiveOrZero

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "forsikringstype")
@JsonSubTypes(
    JsonSubTypes.Type(value = ForsikringsDetaljerBilDTO::class, name = "BILFORSIKRING"),
)
sealed interface ForsikringsDetaljerDTO

data class ForsikringsDetaljerBilDTO(

    @field:Pattern(
        regexp = "^[A-Z]{2}\\d{5}$",
        message = "Må være to bokstaver og fem tall"
    )
    val registreringsnummer: String,

    @field:PositiveOrZero(message = "Kilometerstand kan ikke være negativ")
    val kilometerstand: String,

    val kjoerelengde: Distanse,
) : ForsikringsDetaljerDTO

enum class Distanse {
    KM8000
}