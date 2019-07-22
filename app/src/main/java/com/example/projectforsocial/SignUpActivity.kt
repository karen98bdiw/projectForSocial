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
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var mAuth:FirebaseAuth
    lateinit var mDatabase:FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()

        signUpBtn.setOnClickListener {
            val name = signUpInputNameView.text.toString()
            val surname = signUpInputSurnameView.text.toString()
            val mail = signUpInputMailView.text.toString()
            val password = signUpInputPasswordView.text.toString()



            if(validateNameSurnamMailPassword(name,surname,mail,password)){
                mAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(this@SignUpActivity,OnCompleteListener<AuthResult>(){
                    if(it.isSuccessful){
                        Log.e("registr","registrd")

                        val user = User(it.result!!.user.uid,name, surname, mail, password)
                        mDatabase.reference.child("users").child(it.result!!.user.uid).setValue(user).addOnCompleteListener {
                            if(it.isSuccessful){
                                Log.e("reqister","registredWithDatabase")
                            }else{
                                Log.e("register","cantRegister:${it.exception}")
                            }
                        }


                        val intent = Intent(this@SignUpActivity,HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Log.e("registr","${it.exception}")
                    }
                })
            }

        }

        signInBtnIfHaveAcaunt.setOnClickListener {
            val intent = Intent(this@SignUpActivity,SignInActivity::class.java)
            startActivity(intent)
        }
    }

    fun validateNameSurnamMailPassword(name:String,surname:String,mail:String,password:String):Boolean{

        if(TextUtils.isEmpty(name)){
            Toast.makeText(this@SignUpActivity,"Please write name",Toast.LENGTH_LONG)
                .show()
            return false
        }else if(TextUtils.isEmpty(surname)){
            Toast.makeText(this@SignUpActivity,"Please write surname",Toast.LENGTH_LONG)
                .show()
            return false
        }else if(TextUtils.isEmpty(mail)){
            Toast.makeText(this@SignUpActivity,"Please write mail",Toast.LENGTH_LONG)
                .show()
            return false
        }else if(TextUtils.isEmpty(password)){
            Toast.makeText(this@SignUpActivity,"Please write password",Toast.LENGTH_LONG)
                .show()
            return false
        }
        return true
    }
}
