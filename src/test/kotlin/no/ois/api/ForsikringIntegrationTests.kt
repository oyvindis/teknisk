package no.ois.api

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.assertj.core.api.Assertions.assertThat

class ForsikringIntegrationTests : AbstractIntegrationTest() {

    @Test
    fun `POST på opprette ny forsikringsavtale`() {
        val response = post(
            url = "/forsikring/avtale",
            jsonBody = """
                {
                    "forsikringstaker": {
                        "navn": "Test Testesen",
                        "adresse": "Hovedveien 1",
                        "poststed": "OSLO",
                        "postnummer": "1234",
                        "email": "a@a.no",
                        "foedselsnummer": "12345678912"
                    },
                    "forsikringsdetaljer": {
                        "forsikringstype": "BILFORSIKRING",
                        "registreringsnummer": "AB12345",
                        "kilometerstand": "15000",
                        "kjoerelengde": "KM8000"
                    }
                }
            """.trimIndent()
        )

        assertEquals(201, response?.response?.statusCode?.value())
        assertEquals("${baseUrl}/forsikring/avtale/A-100", response?.response?.headers?.get("Location")?.single())
        assertThat(jsonTester.from(response?.bodyAsString)).isStrictlyEqualToJson(
            """
                {
                    "avtalenummer": "A-100",
                    "status": "AVTALE_SENDT"
                }
            """.trimIndent()
        )
    }

    @Test
    fun `POST opprette ny forsikringsavtale med feil input`() {
        val response = post(
            url = "/forsikring/avtale",
            jsonBody = """
                {
                    "forsikringstaker": {
                        "navn": "",
                        "foedselsnummer": "12345678912"
                    }
                }
            """.trimIndent()
        )
        assertEquals(400, response?.response?.statusCode?.value())
    }
}