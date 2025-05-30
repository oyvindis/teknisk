package no.ois.api.clients.brevtjeneste

import org.springframework.boot.context.properties.ConfigurationProperties
import java.net.URI

@ConfigurationProperties(prefix = "brevtjeneste-service")
data class BrevtjenesteServiceProperties(
    val url: URI
)