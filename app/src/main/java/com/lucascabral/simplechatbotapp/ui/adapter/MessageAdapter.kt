package com.lucascabral.simplechatbotapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucascabral.simplechatbotapp.data.Message
import com.lucascabral.simplechatbotapp.databinding.MessageItemBinding
import com.lucascabral.simplechatbotapp.ui.adapter.MessageAdapter.*
import com.lucascabral.simplechatbotapp.utils.Constants.RECEIVE_ID
import com.lucascabral.simplechatbotapp.utils.Constants.SEND_ID

class MessageAdapter : RecyclerView.Adapter<MessageViewHolder>() {

    var messageList = mutableListOf<Message>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messageList[position]

        when (currentMessage.id) {
            SEND_ID -> {
                holder.binding.messageTextView.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.binding.botMessageTextView.visibility = View.GONE
            }
            RECEIVE_ID -> {
                holder.binding.botMessageTextView.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.binding.messageTextView.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = messageList.size

    inner class MessageViewHolder(
        val binding: MessageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                messageList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    fun insertMessage(message: Message) {
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
        notifyDataSetChanged()
    }
}