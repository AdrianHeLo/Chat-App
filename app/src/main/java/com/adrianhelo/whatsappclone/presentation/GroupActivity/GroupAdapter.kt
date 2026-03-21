package com.adrianhelo.whatsappclone.presentation.GroupActivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrianhelo.whatsappclone.R
import com.adrianhelo.whatsappclone.databinding.GroupsItemBinding
import com.adrianhelo.whatsappclone.domain.model.ChatGroup

class GroupAdapter(private var groupList: List<ChatGroup>, private val clickListener: (ChatGroup) -> Unit): RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(private val groupsItemBinding: GroupsItemBinding): RecyclerView.ViewHolder(groupsItemBinding.root){
        fun bind(chatGroup: ChatGroup, clickListener: (ChatGroup) -> Unit){
            groupsItemBinding.groupTextviewCardView.text = chatGroup.groupName
            groupsItemBinding.listItemLayout.setOnClickListener {
                clickListener(chatGroup)
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
        holder.bind(getItemPosition, clickListener)
        holder.itemView.setOnClickListener {

        }
    }

    fun updateList(newList: List<ChatGroup>) {
        this.groupList = newList
        notifyDataSetChanged()
    }
}