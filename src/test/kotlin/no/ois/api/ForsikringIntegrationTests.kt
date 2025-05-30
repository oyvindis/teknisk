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
                        "foedselsnummer": "12345678912"
                    }
                }
            """.trimIndent()
        )

        assertEquals(201, response?.response?.statusCode?.value())
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