package ar.com.flow.download.controller

import ar.com.flow.download.AttachmentResponse
import ar.com.flow.download.FileServer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/webflux/file")
class WebFluxFileController(
    @Value("\${local.source.path}") private val basePath: String
) {
    private val log = LoggerFactory.getLogger(WebFluxFileController::class.java)

    private val fileServer = FileServer.from(basePath)

    /**
     * Returns a ResponseEntity with a byte array body.
     *
     * WARNING: stores the whole file in memory, therefore it might throw Java Heap Space exception.
     */
    @GetMapping("/resource/{fileName}")
    fun serveResource(@PathVariable("fileName") fileName: String): ResponseEntity<Mono<Resource>> {
        val fileToServe = fileServer.read(fileName)
        val resourceToServe = InputStreamResource(fileToServe.inputStream())

        log.trace("Completed File: $fileName")

        return AttachmentResponse.fileName(fileName)
            .body(Mono.just(resourceToServe))
    }
}
