package com.adrianhelo.whatsappclone.presentation.viewmodel

import android.app.Application
import android.os.Message
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adrianhelo.whatsappclone.domain.model.ChatGroupModel
import com.adrianhelo.whatsappclone.data.remote.Repository
import com.adrianhelo.whatsappclone.domain.model.ChatModel

class RepositoryViewModel(application: Application): AndroidViewModel(application) {

    private val repository: Repository = Repository()

    fun signUpAnonymousUser(){
        return repository.firebaseAnonymousAuth(getApplication<Application>().applicationContext)
    }

    fun getUserID(): String? {
        return repository.getUserID()
    }

    fun signOut(){
        return repository.signOut()
    }

    fun getGroupsList(): MutableLiveData<List<ChatGroupModel>> {
        return repository.getChatGroups()
    }

    fun createNewGroup(groupName: String){
        return repository.createNewGroup(groupName)
    }

    fun getChatMessageList(groupName: String): MutableLiveData<List<ChatModel>>{
        return repository.getChatMessageList(groupName)
    }

    fun sendMessage(message: String, groupName: String){
        return repository.sendMessage(message, groupName)
    }

}