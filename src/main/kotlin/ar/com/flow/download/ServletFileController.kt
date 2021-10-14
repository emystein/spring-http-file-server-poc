package ar.com.flow.download

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/servlet/file")
class ServletFileController(
    @Value("\${local.source.path}") private val basePath: String
) {
    val fileServer = FileServer.from(basePath)

    /**
     * Returns a ResponseEntity with a byte array body.
     *
     * WARNING: stores the whole file in memory, therefore it might throw Java Heap Space exception.
     */
    @GetMapping("/bytearray/{fileName}")
    fun serveByteArray(@PathVariable("fileName") fileName: String): ResponseEntity<ByteArray> {
        val fileToServe = fileServer.read(fileName)

        return AttachmentResponse.fileName(fileName)
            .body(fileToServe)
    }

    /**
     * Writes input file to HttpServletResponse output stream.
     */
    @GetMapping("/servlet-response/{fileName}")
    fun serveServletResponse(@PathVariable("fileName") fileName: String, response: HttpServletResponse) {
        val fileToServe = fileServer.read(fileName)

        fileToServe.attachTo(response)
    }

}
