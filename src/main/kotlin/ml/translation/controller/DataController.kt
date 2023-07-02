package ml.translation.controller

import ml.translation.model.ProductAttribute
import ml.translation.model.ProductAttributeForm
import ml.translation.model.Translation
import ml.translation.service.ProductAttributeService
import ml.translation.service.TranslationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Locale

@RestController
@RequestMapping("")
open class DataController(
    private val translationService: TranslationService,
    private val productAttributeService: ProductAttributeService) {

    @PostMapping("/translations")
    fun createTranslation(@RequestBody translation: Translation): ResponseEntity<Translation> {
        return ResponseEntity.ok(translationService.createTranslation(translation))
    }

    @PostMapping("/attributes")
    fun createAttribute(@RequestBody form: ProductAttributeForm): ResponseEntity<ProductAttribute> {
        return ResponseEntity.ok(productAttributeService.createProductAttribute(form))
    }

    @GetMapping("/attributes")
    fun getAttributes(): ResponseEntity<List<ProductAttribute>> {
        return ResponseEntity.ok(productAttributeService.getProductAttributes())
    }

    @GetMapping("/attributes/{id}")
    fun getAttribute(@PathVariable id: String): ResponseEntity<ProductAttribute> {
        return ResponseEntity.ok(productAttributeService.getProductAttribute(id))
    }
    @GetMapping("/attributes/{id}/translate")
    fun translateAttribute(@PathVariable id: String, @RequestParam(required = false) locale: Locale): ResponseEntity<ProductAttribute> {
        return ResponseEntity.ok(productAttributeService.getTranslateAttribute(id, locale))
    }

}
