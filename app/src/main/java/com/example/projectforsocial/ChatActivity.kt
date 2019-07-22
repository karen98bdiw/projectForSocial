package com.example.projectforsocial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var mDatabase:FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()

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



    }
}
