package ml.translation.service

import ml.translation.exception.ResourceNotFoundException
import ml.translation.model.ProductAttribute
import ml.translation.model.ProductAttributeForm
import ml.translation.model.ProductAttributeType
import ml.translation.model.entity.ProductAttributeEntity
import ml.translation.repository.ProductAttributeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class ProductAttributeService(
    private val productAttributeRepository: ProductAttributeRepository,
    private val translationService: TranslationService
) {

    @Transactional
    open fun createProductAttribute(form: ProductAttributeForm): ProductAttribute? {
        val entity = ProductAttributeEntity()
        entity.id = UUID.randomUUID()
        entity.key = form.key
        entity.type = form.type
        entity.value = form.value
        return productAttributeRepository.save(entity).toProductAttribute()
    }


    @Transactional
    open fun getProductAttributes(): List<ProductAttribute> {
        return productAttributeRepository.findAll().map { it.toProductAttribute() }
    }

    @Transactional
    open fun getProductAttribute(id: String): ProductAttribute {
        return productAttributeRepository.findById(UUID.fromString(id)).orElseThrow { ResourceNotFoundException("Product attribute not found") }.toProductAttribute()
    }

    @Transactional
    open fun getTranslateAttribute(id: String, locale: Locale): ProductAttribute? {
        val sourceAttribute = getProductAttribute(id)
        val translatedKey = translationService.translate(sourceAttribute.key, locale)
        if (sourceAttribute.type == ProductAttributeType.STRING) {
            val translatedValue = translationService.translate(sourceAttribute.value, locale)
            return ProductAttribute(id = sourceAttribute.id, key = translatedKey?:sourceAttribute.key, value = translatedValue?:sourceAttribute.value, type = sourceAttribute.type)
        } else {
            return ProductAttribute(id = sourceAttribute.id, key = translatedKey?:sourceAttribute.key, value = sourceAttribute.value, type = sourceAttribute.type)
        }
    }
}