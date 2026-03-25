package com.adrianhelo.whatsappclone.presentation.ChatActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianhelo.whatsappclone.R
import com.adrianhelo.whatsappclone.databinding.ActivityChatBinding
import com.adrianhelo.whatsappclone.domain.model.ChatModel
import com.adrianhelo.whatsappclone.presentation.viewmodel.RepositoryViewModel

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter

    private val repositoryViewModel: RepositoryViewModel by viewModels()
    private var messagesList = mutableListOf<ChatModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        setupRecyclerView()
        var groupName: String = intent.getStringExtra("Group Name").toString()
        getMessageList(groupName)
        binding.sendBTN.setOnClickListener {
            sendMessage(groupName)
        }
    }

    private fun getMessageList(groupName: String) {
        repositoryViewModel.getChatMessageList(groupName).observe(this){ chatMessages ->
            messagesList.clear()
            messagesList.addAll(chatMessages)

            // Inicializamos el adaptador
            adapter = ChatAdapter(messagesList)
            binding.recyclerView.adapter = adapter

            // Scroll al último mensaje
            val latestPosition = adapter.itemCount - 1
            if (latestPosition >= 0) {
                binding.recyclerView.smoothScrollToPosition(latestPosition)
            }

            chatMessages?.let {
                adapter.updateList(chatMessages)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.fitsSystemWindows = true
    }

    private fun sendMessage(groupName: String){
        val message = binding.edittextChatMessage.text.toString()
        if(message.isNotBlank()){
            repositoryViewModel.sendMessage(message, groupName)
            binding.edittextChatMessage.text.clear()
        }
    }

}