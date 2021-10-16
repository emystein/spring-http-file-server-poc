package ar.com.flow.download.controller

import ar.com.flow.download.FileServerProxy
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/proxy")
class ProxyController(
    @Value("\${remote.source.url}") private val remoteUrl: String
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    private val fileServer = FileServerProxy(remoteUrl)

    @GetMapping("/servlet-response/{fileName}")
    fun writeToServletResponse(@PathVariable("fileName") fileName: String, response: HttpServletResponse) {
        val fileToDownload = fileServer.read(fileName)

        fileToDownload.attachTo(response)

        log.info("Completed file: $fileName")
    }
}