package ar.com.flow.download

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FileDownloadApplication

fun main(args: Array<String>) {
	runApplication<FileDownloadApplication>(*args)
}
