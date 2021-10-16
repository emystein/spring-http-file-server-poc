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
@RequestMapping("/servlet/forward")
class ServletForwardController(
    @Value("\${remote.source.url}") private val remoteSourceUrl: String
) {
    private val log = LoggerFactory.getLogger(ServletFileController::class.java)

    private val fileServer = FileServerProxy(remoteSourceUrl)

    @GetMapping("/servlet-response/{fileName}")
    fun forwardToServletResponse(@PathVariable("fileName") fileName: String, response: HttpServletResponse) {
        val fileToDownload = fileServer.read(fileName)

        fileToDownload.attachTo(response)

        log.info("Completed file: $fileName")
    }
}