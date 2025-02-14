package com.example.spacecraft10.data.responseChat

data class ChatMessage(
    val text: String,
    val isUser: Boolean // True si es del usuario, False si es de la IA
)
