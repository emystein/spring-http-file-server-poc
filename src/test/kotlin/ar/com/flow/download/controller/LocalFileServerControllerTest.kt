package ar.com.flow.download.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class LocalFileServerControllerTest: DownloadControllerTest() {
    @BeforeEach
    fun setUp() {
        File("src/test/resources/hello_world.txt").copyTo(File("$localServerDirectory/hello_world.txt"), overwrite = true)
    }

    @Test
    fun serveByteArray() {
        val url = "http://localhost:$port/local/response-entity/hello_world.txt"
        assertThat(restTemplate.getForObject(url, String::class.java)).contains("Hello, World")
    }

    @Test
    fun serveServletResponse() {
        val url = "http://localhost:$port/local/servlet-response/hello_world.txt"
        assertThat(restTemplate.getForObject(url, String::class.java)).contains("Hello, World")
    }
}
