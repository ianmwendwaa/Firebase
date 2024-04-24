package com.mwendwa.morningfirebase.data

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mwendwa.morningfirebase.models.User
import com.mwendwa.morningfirebase.navigation.ROUTE_HOME
import com.mwendwa.morningfirebase.navigation.ROUTE_LOGIN
import com.mwendwa.morningfirebase.navigation.ROUTE_REGISTER

class AuthViewModel(var navController:NavHostController,var context: Context){

    var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val progress: ProgressDialog

    init {
        progress=ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("PLease Wait.....")
    }
    fun signup(email:String,pass:String,confpass:String){
        progress.show()

        if (email.isBlank() || pass.isBlank() ||confpass.isBlank()){
            progress.dismiss()
            Toast.makeText(context,"Please email and password cannot be blank",Toast.LENGTH_LONG).show()
            return
        }else if (pass != confpass){
            Toast.makeText(context,"Password do not match",Toast.LENGTH_LONG).show()
            return
        }else{
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                if (it.isSuccessful){
                    val userdata= User(email,pass,mAuth.currentUser!!.uid)
                    val regRef= FirebaseDatabase.getInstance().getReference()
                        .child("Users/"+mAuth.currentUser!!.uid)
                    regRef.setValue(userdata).addOnCompleteListener {

                        if (it.isSuccessful){
                            Toast.makeText(context,"Registered Successfully",Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_LOGIN)

                        }else{
                            Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
                            navController.navigate(ROUTE_REGISTER)
                        }
                    }
                }else{
                    navController.navigate(ROUTE_REGISTER)
                }

            } }

    }
    fun login(email: String,pass: String){
        progress.show()

        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
            progress.dismiss()
            if (it.isSuccessful){
                Toast.makeText(context,"Successfully Logged in",Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_HOME)
//                navController.navigate(ROUTE_REGISTER)TO TAKE YOU TO A DIFFERENT PAGE
            }else{
                Toast.makeText(context,"${it.exception!!.message}",Toast.LENGTH_LONG).show()
                navController.navigate(ROUTE_LOGIN)
            }
        }

    }
    fun logout(){
        mAuth.signOut()
        navController.navigate(ROUTE_LOGIN)
    }
    fun isloggedin():Boolean{
        return mAuth.currentUser !=null
    }

}