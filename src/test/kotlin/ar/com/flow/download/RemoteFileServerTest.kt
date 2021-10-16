package ar.com.flow.download

import assertk.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletResponse

class RemoteFileServerTest {
    private lateinit var mockRemoteServer: MockRemoteServer
    private lateinit var fileServer: RemoteFileServer

    @BeforeEach
    fun setup() {
        mockRemoteServer = MockRemoteServer()
        fileServer = RemoteFileServer(mockRemoteServer.rootUrl())
    }

    @Test
    fun download() {
        val downloadedContent = "some file content"

        mockRemoteServer.enqueueContent(downloadedContent)

        val fileToServe = fileServer.read("vtnet.log")
        val response = MockHttpServletResponse()
        fileToServe.attachTo(response)

        assertThat(response).hasContent(downloadedContent)
    }
}