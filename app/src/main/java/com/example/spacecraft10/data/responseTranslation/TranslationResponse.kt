package com.example.spacecraft10.data.responseTranslation

data class TranslationResponse(
    val data: TranslationData
)

data class TranslationData(
    val translations: List<TranslatedText>
)

data class TranslatedText(
    val translatedText: String
)
