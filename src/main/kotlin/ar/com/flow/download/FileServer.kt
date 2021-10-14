package ar.com.flow.download

import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.InputStream
import java.io.OutputStream
import javax.servlet.http.HttpServletResponse

class FileServer(private val basePath: String) {
    fun read(fileName: String): FileToServe {
        return FileToServe(basePath, fileName)
    }

    companion object {
        fun from(basePath: String): FileServer {
            return FileServer(basePath)
        }
    }
}

class FileToServe(basePath: String, private val fileName: String) {
    private val log = LoggerFactory.getLogger(FileToServe::class.java)

    private val filePath = "$basePath/$fileName"

    fun byteArray(): ByteArray {
        log.trace("JVM used memory: ${Memory.usedInMB()} MB")
        // here the whole file is stored in memory

        val byteArray = IOUtils.toByteArray(inputStream())

        log.trace("Loaded byte array of file: $filePath")
        log.trace("JVM used memory: ${Memory.usedInMB()} MB")

        return byteArray
    }

    fun inputStream(): InputStream {
        return FileInputStream(filePath)
    }

    fun copyTo(outputStream: OutputStream) {
        log.trace("JVM used memory: ${Memory.usedInMB()} MB")

        IOUtils.copyLarge(inputStream(), outputStream)

        log.trace("Completed OutputStream of file: $filePath")
        log.trace("JVM used memory: ${Memory.usedInMB()} MB")
    }

    fun attachTo(response: HttpServletResponse) {
        AttachmentContentDisposition(fileName).setTo(response)

        copyTo(response.outputStream)
    }
}
