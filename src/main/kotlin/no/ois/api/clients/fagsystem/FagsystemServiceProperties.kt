package no.ois.api.clients.fagsystem

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

@ConfigurationProperties(prefix = "fagsystem-service")
data class FagsystemServiceProperties(
    val url: URI
)