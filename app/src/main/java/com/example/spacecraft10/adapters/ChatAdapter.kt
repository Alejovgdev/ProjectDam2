package com.example.spacecraft10.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.example.spacecraft10.R
import com.example.spacecraft10.data.responseChat.ChatMessage

class ChatAdapter(private val messages: List<ChatMessage>) :

    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardMessage: CardView = view.findViewById(R.id.cardMessage)
        val tvMessage: TextView = view.findViewById(R.id.tvMessage)
        val rootLayout: ConstraintLayout = view.findViewById(R.id.rootLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = messages[position]
        holder.tvMessage.text = message.text

        // Alinear mensaje a la derecha si es del usuario, a la izquierda si es de la IA
        val constraintSet = ConstraintSet()
        constraintSet.clone(holder.rootLayout)

        if (message.isUser) {
            constraintSet.clear(R.id.cardMessage, ConstraintSet.START)
            constraintSet.connect(R.id.cardMessage, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 10)
            holder.cardMessage.setCardBackgroundColor(holder.itemView.context.getColor(R.color.colorGreenMio)) // Mensaje de la IA
        } else {
            constraintSet.clear(R.id.cardMessage, ConstraintSet.END)
            constraintSet.connect(R.id.cardMessage, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 10)
        }

        constraintSet.applyTo(holder.rootLayout)
    }

    override fun getItemCount() = messages.size
}
