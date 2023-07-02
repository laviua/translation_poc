package ml.translation.repository

import ml.translation.model.entity.TranslationEntity
import ml.translation.model.entity.TranslationId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface TranslationRepository : JpaRepository<TranslationEntity, TranslationId>, JpaSpecificationExecutor<TranslationEntity>
