package ar.com.flow.download.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import java.net.InetAddress


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Reads src/test/resources/application-test.properties
abstract class DownloadControllerTest {
    @Value(value = "\${local.server.port}")
    var port = 0

    @Value(value = "\${local.source.path}")
    var localServerDirectory = ""

    @Value(value = "\${remote.source.url}")
    var remoteSourceUrl = ""

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    fun remoteHostAndPort(): Pair<InetAddress, Int> {
        return hostAndPort(remoteSourceUrl)
    }

    fun hostAndPort(url: String): Pair<InetAddress, Int> {
        val urlRegex = Regex("https?://(.*):([0-9]+)")
        val matchResults = urlRegex.find(url)!!.groups
        val host = matchResults[1]!!.value
        val port = matchResults[2]!!.value.toInt()
        return Pair(InetAddress.getByName(host), port)
    }
}