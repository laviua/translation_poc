package ml.translation.repository

import ml.translation.model.entity.ProductAttributeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductAttributeRepository  : JpaRepository<ProductAttributeEntity, UUID>, JpaSpecificationExecutor<ProductAttributeEntity>