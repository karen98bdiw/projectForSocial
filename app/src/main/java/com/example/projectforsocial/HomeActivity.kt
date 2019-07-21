package com.example.projectforsocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var mAut:FirebaseAuth
    lateinit var curentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
          FirebaseApp.initializeApp(this@HomeActivity)

          mAut = FirebaseAuth.getInstance()

         signOutBtn.setOnClickListener {
             mAut.signOut()
             val intent = Intent(this@HomeActivity,SignInActivity::class.java)
             startActivity(intent)
         }

          if(mAut.currentUser == null){
              Log.e("isSign","no")
              val intent = Intent(this@HomeActivity,SignUpActivity::class.java)
              startActivity(intent)
          }else{
              Log.e("isSign","yes")
          }

    }


}
