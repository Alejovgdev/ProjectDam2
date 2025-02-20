package com.example.spacecraft10.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacecraft10.R
import com.example.spacecraft10.adapters.ChatAdapter
import com.example.spacecraft10.data.responseChat.ChatMessage
import com.example.spacecraft10.databinding.FragmentChatBotBinding
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.launch

class ChatBotFragment : Fragment() {

    private lateinit var generativeModel: GenerativeModel // Modelo de IA para generar respuestas
    private lateinit var binding: FragmentChatBotBinding
    private val chatMessages = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cargarModelo()
        Log.d("**************", "ENNTRA EM ONCREATE")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBotBinding.inflate(inflater, container, false)


        chatAdapter = ChatAdapter(chatMessages)
        binding.rvChat.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = chatAdapter
            visibility = View.VISIBLE
        }

        agregarMensaje("¿En que puedo ayudarte?", false)

        binding.btnEnviar.setOnClickListener {
            val userMessage = binding.etPrompt.text.toString()
            if (userMessage.isNotBlank()) {   // Verificamos que el mensaje no esté vacío
                agregarMensaje(userMessage, true)
                binding.etPrompt.text.clear()

                lifecycleScope.launch {
                    enviarTexto(userMessage)
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        Log.d("!!!!!!!!!!!!!", "ENNTRA EM ON RESUM")
    }

    // Función para agregar un mensaje al chat
    private fun agregarMensaje(text: String, isUser: Boolean) {
        chatMessages.add(ChatMessage(text, isUser))
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        binding.rvChat.scrollToPosition(chatMessages.size - 1)
    }

    // Función para enviar el texto al modelo y obtener una respuesta
    private suspend fun enviarTexto(pregunta: String) {
        val response = generativeModel.generateContent(pregunta)
        response.text?.let { respuesta ->
            agregarMensaje(respuesta, false)
        }
    }

    // Función para cargar el modelo generativo con la configuración necesaria
    private fun cargarModelo() {
        generativeModel = GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = getString(R.string.chat_bot_key)
        )
    }
}




