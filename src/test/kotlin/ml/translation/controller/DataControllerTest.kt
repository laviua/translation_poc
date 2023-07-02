package ml.translation.controller

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import ml.translation.TranslationApp
import ml.translation.model.ProductAttribute
import ml.translation.model.ProductAttributeForm
import ml.translation.model.ProductAttributeType
import ml.translation.model.Translation
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.*

@SpringBootTest(properties = ["spring.main.allow-bean-definition-overriding=true"],
    classes = [TranslationApp::class, PostgresContainerConfiguration::class],
    webEnvironment = WebEnvironment.RANDOM_PORT)
class DataControllerTest {

    @LocalServerPort val port: Int = 0
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    fun test() {
        val sizeAttributes = listOf(
            ProductAttributeForm(key = "shoes.size", value = "36", type = ProductAttributeType.NUMBER),
            ProductAttributeForm(key = "shoes.size", value = "37", type = ProductAttributeType.NUMBER),
            ProductAttributeForm(key = "shoes.size", value = "38", type = ProductAttributeType.NUMBER),
            ProductAttributeForm(key = "shoes.size", value = "39", type = ProductAttributeType.NUMBER),
            ProductAttributeForm(key = "shoes.size", value = "40", type = ProductAttributeType.NUMBER)
        )
        val colorAttributes = listOf(
            ProductAttributeForm(key = "shoes.color", value = "shoes.color.red", type = ProductAttributeType.STRING),
            ProductAttributeForm(key = "shoes.color", value = "shoes.color.green", type = ProductAttributeType.STRING),
        )

        val translations = listOf(
            Translation(key = "shoes.size", value = "Size", locale = Locale("en", "EN")),
            Translation(key = "shoes.size", value = "Maat", locale = Locale("nl", "NL")),

            Translation(key = "shoes.color", value = "Color", locale = Locale("en", "EN")),
            Translation(key = "shoes.color", value = "Kleur", locale = Locale("nl", "NL")),

            Translation(key = "shoes.color.red", value = "Red", locale = Locale("en", "EN")),
            Translation(key = "shoes.color.red", value = "Rood", locale = Locale("nl", "NL")),

            Translation(key = "shoes.color.green", value = "Green", locale = Locale("en", "EN")),
            Translation(key = "shoes.color.green", value = "Groente", locale = Locale("nl", "NL")),
        )

        sizeAttributes.forEach { attr ->
            val responseEntity: ResponseEntity<ProductAttribute> = restTemplate.postForEntity("http://localhost:$port/attributes", attr, ProductAttribute::class.java)
            assertEquals(HttpStatus.OK, responseEntity.statusCode)
            assertNotNull(responseEntity.body)
        }

        colorAttributes.forEach { attr ->
            val responseEntity: ResponseEntity<ProductAttribute> = restTemplate.postForEntity("http://localhost:$port/attributes", attr, ProductAttribute::class.java)
            assertEquals(HttpStatus.OK, responseEntity.statusCode)
            assertNotNull(responseEntity.body)
        }

        translations.forEach { translation ->
            val responseEntity: ResponseEntity<Translation> = restTemplate.postForEntity("http://localhost:$port/translations", translation, Translation::class.java)
            assertEquals(HttpStatus.OK, responseEntity.statusCode)
            assertNotNull(responseEntity.body)
        }


        // Get all attributes
        val response: ResponseEntity<List<ProductAttribute>> = restTemplate.exchange(
            "http://localhost:$port/attributes",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<ProductAttribute>>() {}
        )

        for (attribute in response.body!!) {
            val nlTranslatedAttribute: ProductAttribute = restTemplate.getForEntity("http://localhost:$port/attributes/${attribute.id}/translate?locale=nl_NL", ProductAttribute::class.java).body
            val enTranslatedAttribute: ProductAttribute = restTemplate.getForEntity("http://localhost:$port/attributes/${attribute.id}/translate?locale=en_EN", ProductAttribute::class.java).body
            println(enTranslatedAttribute)
            println(nlTranslatedAttribute)
        }
    }


}
