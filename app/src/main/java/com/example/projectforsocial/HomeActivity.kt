package com.example.projectforsocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var mAut:FirebaseAuth
    lateinit var mDatabase:FirebaseDatabase
     var curentUser: FirebaseUser?=null
    lateinit var users:ArrayList<User>
    lateinit var adapter: UsersRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
          FirebaseApp.initializeApp(this@HomeActivity)

          mAut = FirebaseAuth.getInstance()
          mDatabase = FirebaseDatabase.getInstance()
          curentUser = FirebaseAuth.getInstance().currentUser
          users = arrayListOf()
          adapter = UsersRecyclerViewAdapter(users)
          usersRecyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)


         signOutBtn.setOnClickListener {
             mAut.signOut()
             val intent = Intent(this@HomeActivity,SignUpActivity::class.java)
             startActivity(intent)
         }

        if(curentUser == null){
              Log.e("isSign","no")
              val intent = Intent(this@HomeActivity,SignUpActivity::class.java)
              startActivity(intent)
          }else{
              mDatabase.reference.child("users").addValueEventListener(object: ValueEventListener{
                  override fun onCancelled(p0: DatabaseError) {

                  }

                  override fun onDataChange(p0: DataSnapshot) {
                      p0.children.forEach {
                          val user:User? = it.getValue(User::class.java)
                          if(!user!!.id.equals(curentUser!!.uid)){
                              Log.e("users","user:${user.id},name:${user.name}")
                              users.add(user)
                          }
                      }
                      usersRecyclerView.adapter = adapter
                  }

              })

          }

        mDatabase.reference.child("chat").addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                p0.children.forEach {
                    val message = it.getValue(Message::class.java)

                    if (message != null) {
                        if(message!!.receiver.equals(curentUser?.uid)){
                            Toast.makeText(this@HomeActivity,"You have a message|messageText${message.message}",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Log.e("null","message is null")
                    }

                }

            }

        })

    }


}
