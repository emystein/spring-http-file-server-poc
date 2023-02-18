package ar.com.flow.download.controller

import ar.com.flow.download.AttachmentResponse
import ar.com.flow.download.server.LocalFileServer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/local")
class LocalFileServerController(
    @Value("\${local.source.path}") private val basePath: String
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    private val fileServer = LocalFileServer.withBasePath(basePath)

    /**
     * Returns a ResponseEntity with a byte array body.
     *
     * WARNING: stores the whole file in memory, therefore it might throw Java Heap Space exception.
     */
    @GetMapping("/byte-array/{fileName}")
    fun serveByteArray(@PathVariable("fileName") fileName: String): ResponseEntity<ByteArray> {
        val fileToServe = fileServer.read(fileName)

        log.trace("Completed File: $fileName")

        return AttachmentResponse
            .fileName(fileName)
            .body(fileToServe)
    }

    /**
     * Writes input file to HttpServletResponse output stream.
     */
    @GetMapping("/servlet-response/{fileName}")
    fun serveServletResponse(@PathVariable("fileName") fileName: String, servletResponse: HttpServletResponse) {
        val fileToServe = fileServer.read(fileName)

        fileToServe.attachTo(servletResponse)

        log.trace("Completed File: $fileName")
    }
}
