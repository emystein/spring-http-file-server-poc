package ar.com.flow.download

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletResponse

class RemoteFileServerTest {
    private lateinit var mockRemoteServer: MockRemoteServer

    @BeforeEach
    fun setup() {
        mockRemoteServer = MockRemoteServer()
    }

    @Test
    fun download() {
        val downloadedContent = "some file content"

        mockRemoteServer.enqueueContent(downloadedContent)

        val fileServer = RemoteFileServer(mockRemoteServer.rootUrl())
        val fileToServe = fileServer.read("vtnet.log")
        val response = MockHttpServletResponse()
        fileToServe.attachTo(response)

        assertThat(response.contentAsString).isEqualTo(downloadedContent)
    }
}