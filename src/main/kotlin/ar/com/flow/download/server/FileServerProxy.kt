package ar.com.flow.download.server

import ar.com.flow.download.AttachmentContentDisposition
import jakarta.servlet.http.HttpServletResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.io.IOUtils

class FileServerProxy(private val remoteSourceUrl: String) {
    fun read(fileName: String): RemoteFileToServe {
        return RemoteFileToServe(remoteSourceUrl, fileName)
    }

    companion object {
        fun from(basePath: String): FileServerProxy {
            return FileServerProxy(basePath)
        }
    }
}

class RemoteFileToServe(remoteSourceUrl: String, private val fileName: String) {
    private val filePath = "$remoteSourceUrl/$fileName"

    fun attachTo(response: HttpServletResponse) {
        AttachmentContentDisposition(fileName).setTo(response)

        val client = OkHttpClient()
        val request = Request.Builder().url(filePath).build()

        client.newCall(request).execute().use { remoteResponse ->
            IOUtils.copyLarge(remoteResponse.body!!.byteStream(), response.outputStream)
        }
    }
}