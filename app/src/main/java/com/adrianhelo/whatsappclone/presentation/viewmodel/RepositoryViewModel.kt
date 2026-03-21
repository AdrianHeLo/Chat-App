package com.adrianhelo.whatsappclone.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.adrianhelo.whatsappclone.domain.model.ChatGroup
import com.adrianhelo.whatsappclone.data.remote.Repository

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

    fun getGroupsList(): MutableLiveData<List<ChatGroup>> {
        return repository.getChatGroups()
    }

    fun createNewGroup(groupName: String){
        return repository.createNewGroup(groupName)
    }

}