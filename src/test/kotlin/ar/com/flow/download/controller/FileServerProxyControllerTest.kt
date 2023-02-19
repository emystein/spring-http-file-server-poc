package ar.com.flow.download.controller

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Reads src/test/resources/application-test.properties
class FileServerProxyControllerTest {
    @Value(value = "\${local.server.port}")
    private var port = 0

    @Value(value = "\${remote.source.url}")
    private var remoteSourceUrl = ""

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private val mockServer = MockWebServer()

    @BeforeEach
    fun setUp() {
        val portRegex = Regex(":(?<port>[0-9]+)")
        val port = portRegex.find(remoteSourceUrl)!!.groups[1]!!.value.toInt()
        mockServer.start(port)
    }

    @Test
    fun serveServletResponse() {
        val content = "Hello, World"
        val response = MockResponse().setBody(content)
        mockServer.enqueue(response)
        val url = "http://localhost:$port/proxy/servlet-response/hello_world.txt"
        assertThat(restTemplate.getForObject(url, String::class.java)).contains(content)
    }
}
