package com.example.spacecraft10.data.responseTranslation

data class TranslationRequest(
    val q: String,        // Texto a traducir
    val target: String    // Idioma de destino
)
