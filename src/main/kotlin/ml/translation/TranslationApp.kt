package ml.translation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class TranslationApp

fun main(args: Array<String>) {
    runApplication<TranslationApp>(*args)
}
