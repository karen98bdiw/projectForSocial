package com.example.projectforsocial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mAuth = FirebaseAuth.getInstance()

        signInBtn.setOnClickListener {

            val signInMail = signInMailInputView.text.toString()
            val signInPassword = signPasswordInputView.text.toString()

            if(validateEmailPassword(signInMail,signInPassword)){
                mAuth.signInWithEmailAndPassword(signInMail,signInPassword)
                    .addOnCompleteListener(this@SignInActivity,OnCompleteListener<AuthResult>(){
                        if(it.isSuccessful){
                            Log.e("signIn","yes|mail:${it.result.user.email}|")
                            val intent = Intent(this@SignInActivity,HomeActivity::class.java)
                            startActivity(intent)

                        }else{
                            Log.e("signIn","no")
                        }
                    })
            }
        }

    }

    fun validateEmailPassword(email:String,password:String):Boolean{
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this@SignInActivity,"Please write email",Toast.LENGTH_LONG)
                .show()
            return false
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this@SignInActivity,"Please write password",Toast.LENGTH_LONG)
                .show()
            return false
        }else{
            return true
        }
    }
}
