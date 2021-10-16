package ar.com.flow.download

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class MockRemoteServer {
    private val mockServer = MockWebServer()

    init {
        mockServer.start()
    }

    fun rootUrl(): String {
        return mockServer.url("/").toString()
    }

    fun enqueueContent(content: String) {
        val response = MockResponse().setBody(content)

        mockServer.enqueue(response)
    }
}