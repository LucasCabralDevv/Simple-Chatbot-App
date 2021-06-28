package com.lucascabral.simplechatbotapp.ui.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucascabral.simplechatbotapp.data.Message
import com.lucascabral.simplechatbotapp.databinding.ActivityMainBinding
import com.lucascabral.simplechatbotapp.ui.adapter.MessageAdapter
import com.lucascabral.simplechatbotapp.utils.BotResponse
import com.lucascabral.simplechatbotapp.utils.Constants.OPEN_GOOGLE
import com.lucascabral.simplechatbotapp.utils.Constants.OPEN_SEARCH
import com.lucascabral.simplechatbotapp.utils.Constants.RECEIVE_ID
import com.lucascabral.simplechatbotapp.utils.Constants.SEND_ID
import com.lucascabral.simplechatbotapp.utils.Time
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MessageAdapter
    var messagesList = mutableListOf<Message>()
    private lateinit var binding: ActivityMainBinding
    private val botList = listOf("Lucas", "Diego", "Bruno", "Thiago")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView()
        clickEvents()
        val random = (0..3).random()
        customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?")
    }

    private fun clickEvents() {

        binding.sendButton.setOnClickListener {
            sendMessage()
        }

        binding.messageEditText.setOnClickListener {
            GlobalScope.launch {
                delay(1000)

                withContext(Dispatchers.Main) {
                    binding.messagesRecyclerView.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessageAdapter()
        binding.messagesRecyclerView.adapter = adapter
        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                binding.messagesRecyclerView.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = binding.messageEditText.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            messagesList.add(Message(message, SEND_ID, timeStamp))
            binding.messageEditText.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            binding.messagesRecyclerView.scrollToPosition(adapter.itemCount - 1)
            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val response = BotResponse.basicResponse(message)
                messagesList.add(Message(response, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                binding.messagesRecyclerView.scrollToPosition(adapter.itemCount - 1)

                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECEIVE_ID, timeStamp))
                binding.messagesRecyclerView.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}