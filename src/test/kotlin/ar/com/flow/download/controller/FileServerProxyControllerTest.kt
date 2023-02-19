package ar.com.flow.download.controller

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class FileServerProxyControllerTest: DownloadControllerTest() {
    private val mockRemoteServer = MockWebServer()

    @BeforeEach
    fun setUp() {
        val (host, port) = remoteHostAndPort()
        mockRemoteServer.start(host, port)
    }

    @Test
    fun serveServletResponse() {
        val remoteFileContent = "Hello, World"
        val remoteResponse = MockResponse().setBody(remoteFileContent)
        mockRemoteServer.enqueue(remoteResponse)

        val url = "http://localhost:$port/proxy/servlet-response/hello_world.txt"
        assertThat(restTemplate.getForObject(url, String::class.java)).contains(remoteFileContent)
    }
}
