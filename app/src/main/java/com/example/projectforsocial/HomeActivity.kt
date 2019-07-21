package com.example.projectforsocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
          FirebaseApp.initializeApp(this@HomeActivity)

          mAut = FirebaseAuth.getInstance()
          mDatabase = FirebaseDatabase.getInstance()
          curentUser = FirebaseAuth.getInstance().currentUser

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
              Log.e("isSign","yes")
              mDatabase.reference.child("users").child(curentUser!!.uid).addListenerForSingleValueEvent(object: ValueEventListener{

                  override fun onDataChange(p0: DataSnapshot) {

                      val user = p0.getValue(User::class.java)
                      Log.e("signedDrowInfo","name:${user!!.name}|surname:${user.surname},mail:${user.mail},password:${user.password},")

                  }

                  override fun onCancelled(p0: DatabaseError) {
                      Log.e("onCanceled","canceled${p0.toException()}")
                  }



              })

          }

    }




}
