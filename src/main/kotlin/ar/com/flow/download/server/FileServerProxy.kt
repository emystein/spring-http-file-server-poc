package ar.com.flow.download.server

import ar.com.flow.download.AttachmentContentDisposition
import kong.unirest.Unirest
import org.apache.commons.io.IOUtils
import javax.servlet.http.HttpServletResponse

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

        Unirest.get(filePath).asObject { rawResponse ->
            IOUtils.copyLarge(rawResponse.content, response.outputStream)
        }
    }
}