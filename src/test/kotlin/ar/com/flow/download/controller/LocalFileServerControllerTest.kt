package ar.com.flow.download.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.ActiveProfiles
import java.io.File

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class LocalFileServerControllerTest {
    @Value(value = "\${local.server.port}")
    private var port = 0

    @Value(value = "\${local.source.path}")
    private lateinit var localServerDirectory: String

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @BeforeEach
    fun setUp() {
        File("src/test/resources/hello_world.txt").copyTo(File("$localServerDirectory/hello_world.txt"), overwrite = true)
    }

    @Test
    @Throws(Exception::class)
    fun serveByteArray() {
        val url = "http://localhost:$port/files/bytearray/hello_world.txt"
        assertThat(restTemplate.getForObject(url, String::class.java)).contains("Hello, World")
    }
}
