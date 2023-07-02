package ml.translation.model.entity

import jakarta.persistence.*
import ml.translation.model.Translation
import java.io.Serializable
import java.util.*

@Embeddable
data class TranslationId(
    var key: String? = null,
    var locale: String? = null): Serializable

@Entity
@Table(name = "translation")
data class TranslationEntity(

    @EmbeddedId
    var id: TranslationId? = null,

    @Column(name = "value")
    var value: String? = null,
) {
    fun toTranslation(): Translation {
        return Translation(
            key = this.id!!.key!!,
            locale = Locale(this.id!!.locale!!),
            value = this.value!!
        )
    }
}