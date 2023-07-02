package ml.translation.service

import ml.translation.model.Translation
import ml.translation.model.entity.TranslationEntity
import ml.translation.model.entity.TranslationId
import ml.translation.repository.TranslationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class TranslationService(private val translationRepository: TranslationRepository) {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Transactional
    open fun createTranslation(translation: Translation): Translation {
        val entity = TranslationEntity()
        entity.id = TranslationId(translation.key, translation.locale.toString())
        entity.value = translation.value
        translationRepository.save(entity)
        log.info("Saved translation: ${translation.key}, ${translation.locale}")
        return entity.toTranslation()
    }

    @Transactional
    open fun translate(key: String, locale: Locale): String? {
        return translationRepository
            .findById(TranslationId(key, locale.toString()))
            .map { it.value }
            .orElse(null)
    }

    @Transactional
    open fun getTranslations(): List<Translation> {
        return translationRepository.findAll().map { it.toTranslation() }.sortedBy { it.key }
    }

}



