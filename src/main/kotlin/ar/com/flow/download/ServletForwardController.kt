package ar.com.flow.download

import kong.unirest.Unirest
import org.apache.commons.io.IOUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.OutputStream
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/servlet/forward")
class ServletForwardController {
    private val log = LoggerFactory.getLogger(ServletFileController::class.java)

    // TODO extract to application.properties
    val baseForwardUrl = "http://localhost:8000"
    val baseDestinationPath = "/tmp"

    @GetMapping("/servlet-response/{fileName}")
    fun unirestForwardToServletResponse(@PathVariable("fileName") fileName: String, response: HttpServletResponse) {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${fileName}\"")

        forwardDownload(fileName, response)

        log.info("Completed file: $fileName")
        log.trace("JVM used memory: ${usedMemoryInMB()} MB")
    }

    private fun forwardDownload(fileName: String, response: HttpServletResponse) {
        Unirest.get("$baseForwardUrl/$fileName").asObject{ rawResponse ->
            IOUtils.copyLarge(rawResponse.content, response.outputStream)
        }
    }

    private fun usedMemoryInMB(): Long {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1014)
    }
}