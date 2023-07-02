package ml.translation.model.entity

import jakarta.persistence.*
import ml.translation.model.ProductAttribute
import ml.translation.model.ProductAttributeType
import org.hibernate.annotations.JdbcType
import org.hibernate.type.descriptor.jdbc.UUIDJdbcType
import java.util.*

@Entity
@Table(name = "product_attribute")
data class ProductAttributeEntity(

    @Id
    @JdbcType(UUIDJdbcType::class)
    @Column(name = "id", length = 36, updatable = false, nullable = false) var id: UUID? = null,

    @Column(name = "key")
    var key: String? = null,

    @Column(name = "value")
    var value: String? = null,

    @Enumerated(value = EnumType.STRING) @Column(name = "type")
    var type: ProductAttributeType? = null,
) {
    fun toProductAttribute(): ProductAttribute {
        return ProductAttribute(
            id = this.id!!.toString(),
            key = this.key!!,
            value = this.value!!,
            type = this.type!!,
        )
    }
}