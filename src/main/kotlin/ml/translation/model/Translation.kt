package ml.translation.model

import java.util.Locale

data class Translation(
    val key: String,
    val locale: Locale,
    val value: String
)