package com.adrianhelo.whatsappclone.domain.model

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ChatModel(
    var senderId: String = "",
    var message: String = "",
    var isMine: Boolean = false,
    var timestamp: Long = 0
){

    // Lógica para determinar si el mensaje es del usuario actual
    fun checkIsMine(): Boolean {
        val currentUid = FirebaseAuth.getInstance().currentUser?.uid
        this.isMine = (senderId == currentUid)
        return this.isMine
    }

    fun getFormattedDate(): String{
        val sdf = SimpleDateFormat("HH/mm", Locale.getDefault())
        val date = Date(timestamp)
        return sdf.format(date)
    }
}
