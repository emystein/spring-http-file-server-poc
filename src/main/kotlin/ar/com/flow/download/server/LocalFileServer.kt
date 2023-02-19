package ar.com.flow.download.server

import ar.com.flow.download.AttachmentContentDisposition
import ar.com.flow.download.AttachmentResponse
import org.apache.commons.io.IOUtils
import org.springframework.http.ResponseEntity
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import jakarta.servlet.http.HttpServletResponse

class LocalFileServer(private val basePath: String) {
    fun read(fileName: String): LocalFileToServe {
        return LocalFileToServe(basePath, fileName)
    }

    companion object {
        fun withBasePath(basePath: String): LocalFileServer {
            return LocalFileServer(basePath)
        }
    }
}

class LocalFileToServe(basePath: String, private val fileName: String) {
    private val filePath = "$basePath/$fileName"

    fun byteArray(): ByteArray {
        // here the whole file is stored in memory
        return IOUtils.toByteArray(inputStream())
    }

    fun inputStream(): InputStream {
        return FileInputStream(filePath)
    }

    fun copyTo(outputStream: OutputStream) {
        IOUtils.copyLarge(inputStream(), outputStream)
    }

    fun attachTo(response: HttpServletResponse) {
        AttachmentContentDisposition(fileName).setTo(response)

        copyTo(response.outputStream)
    }
}
