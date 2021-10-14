package ar.com.flow.download

import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders.CONTENT_DISPOSITION
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletResponse

class AttachmentResponse(fileName: String) {
    companion object {
        fun fileName(fileName: String): AttachmentResponse {
            return AttachmentResponse(fileName)
        }
    }

    private val contentDisposition = AttachmentContentDisposition(fileName)

    private val response = ResponseEntity.ok().header(CONTENT_DISPOSITION, contentDisposition.value)

    fun body(fileToServe: FileToServe): ResponseEntity<ByteArray> {
        return response.body(fileToServe.byteArray())
    }
}

class AttachmentContentDisposition(fileName: String) {
    private val contentDisposition = ContentDisposition.attachment()
        .filename(fileName)
        .build()

    val value: String = contentDisposition.toString()

    fun setTo(response: HttpServletResponse) {
        response.setHeader(CONTENT_DISPOSITION, value)
    }
}
