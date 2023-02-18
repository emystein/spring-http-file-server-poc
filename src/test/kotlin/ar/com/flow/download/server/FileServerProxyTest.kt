package ar.com.flow.download.server

import assertk.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletResponse

class FileServerProxyTest {
    private lateinit var mockRemoteServer: MockRemoteServer
    private lateinit var proxy: FileServerProxy

    @BeforeEach
    fun setup() {
        mockRemoteServer = MockRemoteServer()
        proxy = FileServerProxy(mockRemoteServer.rootUrl())
    }

    @Test
    fun download() {
        val downloadedContent = "some file content"

        mockRemoteServer.enqueueContent(downloadedContent)

        val fileToServe = proxy.read("vtnet.log")
        val response = MockHttpServletResponse()
        fileToServe.attachTo(response)

        assertThat(response).hasContent(downloadedContent)
    }
}