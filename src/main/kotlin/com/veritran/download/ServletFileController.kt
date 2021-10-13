package com.veritran.download

import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.http.*
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/servlet/file")
class ServletFileController {
    private val log = LoggerFactory.getLogger(ServletFileController::class.java)

    // TODO extract to application.properties
    val basePath = "/home/emenendez/Downloads"

    /**
     * WARNING: stores the whole file in memory, therefore it might throw Java Heap Space exception.
     */
    @GetMapping("/in-inputstream/out-bytearray/{fileName}")
    fun inInputStreamOutByteArray(@PathVariable("fileName") fileName: String): ResponseEntity<ByteArray> {
        val fileToServe = inputStreamOf(fileName)

        // here the whole file is stored in memory
        val outputResource = IOUtils.toByteArray(fileToServe)
        log.trace("Completed byte array. Used JVM memory: ${usedMemoryInMB()} MB")

        return ResponseEntity.ok()
            .header(CONTENT_DISPOSITION, attachmentContentDisposition(fileName))
            .body(outputResource)
    }

    @GetMapping("/in-inputstream/out-servlet-response/{fileName}")
    fun inInputStreamOutServletResponse(@PathVariable("fileName") fileName: String, response: HttpServletResponse) {
        response.setHeader(CONTENT_DISPOSITION, "attachment; filename=\"${fileName}\"")

        val fileToServe = inputStreamOf(fileName)

        IOUtils.copyLarge(fileToServe, response.outputStream)

        log.trace("Completed OutputStream of file: $fileName")
        log.trace("JVM used memory: ${usedMemoryInMB()} MB")
    }

    private fun inputStreamOf(fileName: String): InputStream {
        val filePath = "$basePath/${fileName}"
        val inputStream = FileInputStream(File(filePath))
        log.trace("Created InputStream of file: $fileName")
        log.trace("JVM used memory: ${usedMemoryInMB()} MB")
        return inputStream
    }

    private fun usedMemoryInMB(): Long {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1014)
    }

    fun attachmentContentDisposition(fileName: String): String {
        return ContentDisposition.attachment()
            .filename(fileName)
            .build()
            .toString()
    }
}