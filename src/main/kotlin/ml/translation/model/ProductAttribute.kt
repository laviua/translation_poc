package ml.translation.model

enum class ProductAttributeType {
    STRING,
    NUMBER,
    BOOLEAN
}
data class ProductAttributeForm(val key: String,
                                val value: String,
                                val type: ProductAttributeType)
data class ProductAttribute(val id: String,
                            val key: String,
                            val value: String,
                            val type: ProductAttributeType)