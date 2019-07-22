package com.example.projectforsocial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase:FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()

        val messagesList = ArrayList<Message>()
        val manager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        messagesRecyclerView.layoutManager = manager

        val intent = getIntent()

        val receiverUser = intent.getSerializableExtra("receiverUser") as User
        val curentUser = mAuth.currentUser

        Log.e("receiverUser","${receiverUser.name}")

        sendMessageBtn.setOnClickListener {

            val messageText = messageInputView.text.toString()

            if(messageText.isNotEmpty()){
                mDatabase.reference.child("chat").push().setValue(Message(messageText,receiverUser.id,curentUser!!.uid)).addOnCompleteListener {
                    if(it.isSuccessful){
                        Log.e("sending","message is send")
                    }else{
                        Log.e("sending","cant sand ${it.exception}")
                    }
                }
            }


        }

        mDatabase.reference.child("chat").addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                messagesList.clear()
                p0.children.forEach {
                    val message = it.getValue(Message::class.java)
                    if(message != null){
                        if(message.sender == curentUser?.uid && message.receiver == receiverUser.id || message.sender == receiverUser.id && message.receiver == curentUser?.uid){

                            messagesList.add(message)
                        }
                    }
                }
                messagesRecyclerView.adapter = MessageRecyclerViewAdapter(messagesList)

            }

        })



    }
}
