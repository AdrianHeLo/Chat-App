package com.adrianhelo.whatsappclone.presentation.GroupActivity

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrianhelo.whatsappclone.R
import com.adrianhelo.whatsappclone.databinding.ActivityGroupsBinding
import com.adrianhelo.whatsappclone.databinding.DialogAddNewGroupBinding
import com.adrianhelo.whatsappclone.domain.model.ChatGroup
import com.adrianhelo.whatsappclone.presentation.viewmodel.RepositoryViewModel

class GroupsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGroupsBinding
    private lateinit var groupAdapter: GroupAdapter
    private lateinit var chatGroupDialog: Dialog

    private val repositoryViewModel: RepositoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_groups)
        getGroupsList()
        binding.addGroupFloatButton.setOnClickListener{
            setAddGroupDialog()
        }
    }

    private fun setupRecyclerView(){
        groupAdapter = GroupAdapter(mutableListOf()){ selectItem ->
            listItemClicked(selectItem)
        }
        binding.recyclerContainer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerContainer.adapter = groupAdapter
    }

    private fun getGroupsList(){
        setupRecyclerView()
        repositoryViewModel.getGroupsList().observe(this){ it ->
            it?.let {
                groupAdapter.updateList(it)
            }
        }
    }

    private fun listItemClicked(selectItem: ChatGroup) {
        Toast.makeText(this, "Selected name is ${selectItem.groupName}", Toast.LENGTH_LONG).show()
    }

    private fun setAddGroupDialog(){
        chatGroupDialog = Dialog(this)
        chatGroupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        var view: View = LayoutInflater.from(this).inflate(R.layout.dialog_add_new_group, null)
        chatGroupDialog.setContentView(view)

        var groupNameTextView: TextView = view.findViewById(R.id.dialog_edit_text)
        var submitBtn: Button = view.findViewById(R.id.dialog_submit_button)

        chatGroupDialog.show()

        submitBtn.setOnClickListener {
            var groupName: String = groupNameTextView.text.toString()
            Toast.makeText(this, "Group $groupName Created!", Toast.LENGTH_LONG).show()
            repositoryViewModel.createNewGroup(groupName)
            chatGroupDialog.hide()
        }
    }

}