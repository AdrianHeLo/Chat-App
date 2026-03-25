package com.adrianhelo.whatsappclone.presentation.GroupActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrianhelo.whatsappclone.R
import com.adrianhelo.whatsappclone.databinding.GroupsItemBinding
import com.adrianhelo.whatsappclone.domain.model.ChatGroupModel

class GroupAdapter(private var groupList: List<ChatGroupModel>, private val clickListener: (ChatGroupModel) -> Unit): RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(private val groupsItemBinding: GroupsItemBinding): RecyclerView.ViewHolder(groupsItemBinding.root){
        fun bind(chatGroupModel: ChatGroupModel, clickListener: (ChatGroupModel) -> Unit){
            groupsItemBinding.groupTextviewCardView.text = chatGroupModel.groupName
            groupsItemBinding.listItemLayout.setOnClickListener {
                clickListener(chatGroupModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val  binding = LayoutInflater.from(parent.context)
        val itemBinding: GroupsItemBinding = DataBindingUtil.inflate(binding, R.layout.groups_item, parent, false)
        return GroupViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return groupList.size
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        var getItemPosition = groupList[position]
        return holder.bind(getItemPosition, clickListener)
    }

    fun updateList(newList: List<ChatGroupModel>) {
        this.groupList = newList
        notifyDataSetChanged()
    }
}