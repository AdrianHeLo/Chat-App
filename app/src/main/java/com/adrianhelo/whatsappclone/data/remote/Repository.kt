package com.adrianhelo.whatsappclone.data.remote

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.adrianhelo.whatsappclone.domain.model.ChatGroupModel
import com.adrianhelo.whatsappclone.domain.model.ChatModel
import com.adrianhelo.whatsappclone.presentation.GroupActivity.GroupsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Repository {

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseReference: DatabaseReference
    private lateinit var groupReference: DatabaseReference

    private val groupMutableLiveData = MutableLiveData<List<ChatGroupModel>>()
    private val messageListMutableLiveData = MutableLiveData<List<ChatModel>>()

    fun firebaseAnonymousAuth(context: Context){
        FirebaseAuth.getInstance().signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val i = Intent(context, GroupsActivity::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(i)
                }
            }.addOnFailureListener{
                Toast.makeText(context, "Authentication Failure!!!", Toast.LENGTH_LONG).show()
            }
    }

    private fun getFirebaseInstance(){
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseReference = firebaseDatabase.reference
    }

    fun getUserID(): String? {
        return FirebaseAuth.getInstance().uid
    }

    fun signOut(){
        return FirebaseAuth.getInstance().signOut()
    }

    fun getChatGroups(): MutableLiveData<List<ChatGroupModel>> {
        val groupsList = ArrayList<ChatGroupModel>()
        // Obtenemos el acceso a Firebase Database
        getFirebaseInstance()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 1. Limpiar la lista para evitar duplicados
                groupsList.clear()
                // 2. Iterar sobre los hijos
                for (snapshot in dataSnapshot.children){
                    // Obtenemos la clave del grupo actual
                    val groupId = snapshot.key ?: ""
                    // Creamos el objeto ChatGroupModel (asumiendo que tu constructor recibe el ID)
                    val group = ChatGroupModel(groupId)
                    groupsList.add(group)
                }
                // 3. Actualizar el LiveData (Equivalente al return en LiveData)
                groupMutableLiveData.value = groupsList
            }
            override fun onCancelled(error: DatabaseError) {
                // Es obligatorio implementar este método aunque esté vacío
                Log.e("Firebase", "Error: ${error.message}")
            }
        }

        // Asignar el listener a nuestro FirebaseReference
        firebaseReference.addValueEventListener(postListener)
        return groupMutableLiveData
    }

    fun createNewGroup(groupName: String){
        getFirebaseInstance()
        firebaseReference.child(groupName).setValue(groupName)
    }

    fun getChatMessageList(groupName: String): MutableLiveData<List<ChatModel>>{
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseReference = firebaseDatabase.reference
        groupReference = firebaseReference.database.reference.child(groupName)
        val messageList = ArrayList<ChatModel>()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messageList.clear()
                for (snapshot in dataSnapshot.children){
                    val message = dataSnapshot.getValue(ChatModel::class.java)
                    if (message != null) {
                        messageList.add(message)
                    }
                }
                messageListMutableLiveData.postValue(messageList)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error: ${error.message}")
            }
        }
        groupReference.addValueEventListener(postListener)
        return messageListMutableLiveData
    }

    fun sendMessage(message: String, groupName: String){
        val chatReference =  firebaseDatabase.getReference(groupName)
        if (message.isNotBlank()){
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return
            var msg = ChatModel(
                senderId = currentUserId,
                message = message,
                timestamp = System.currentTimeMillis()
            )
            chatReference.push().apply {
                key?.let { randomKey ->
                    child(randomKey).setValue(msg)
                }
            }
        }
    }

}