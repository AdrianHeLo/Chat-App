package com.adrianhelo.whatsappclone.presentation.ChatActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrianhelo.whatsappclone.R
import com.adrianhelo.whatsappclone.databinding.RowChatBinding
import com.adrianhelo.whatsappclone.domain.model.ChatModel

class ChatAdapter(private var chatMessageList: List<ChatModel>): RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    inner class ViewHolder(val itemBinding: RowChatBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflater: RowChatBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_chat, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val getPosition = chatMessageList[position]
        getPosition.checkIsMine()
       holder.itemBinding.chatMessage = getPosition
        holder.itemBinding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return chatMessageList.size
    }

    fun updateList(newList: List<ChatModel>) {
        this.chatMessageList = newList
        notifyDataSetChanged()
    }
}