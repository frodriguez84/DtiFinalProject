package com.example.proyectofinal.viewmodels

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.Config.ERROR
import com.example.proyectofinal.entities.Config.SUCCESS
import com.example.proyectofinal.entities.Config.USERS
import com.example.proyectofinal.entities.Config.USER_PASS_WRONG
import com.example.proyectofinal.entities.Favoritos
import com.example.proyectofinal.entities.UserRepository.userMailLogin
import com.example.proyectofinal.fragments.LoginFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    private lateinit var email : TextView
    private lateinit var pass : TextView

    var db = FirebaseFirestore.getInstance()

    fun login(v : View, c: Context){
        email = v.findViewById(R.id.emailText)
        pass = v.findViewById(R.id.passText)
        if ((email.text.isNotEmpty() && pass.text.isNotEmpty()))  {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email.text.toString(),
                pass.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    userMailLogin = email.text.toString()

                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    v.findNavController().navigate(action)

                    cleanUser()
                } else {
                    loginFail(c)
                }
            }
        } else {
            loginFail(c)
        }
    }

    fun register(v : View, c: Context){
        email = v.findViewById(R.id.emailText)
        pass = v.findViewById(R.id.passText)
        if (email.text.isNotEmpty() && pass.text.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                email.text.toString(),
                pass.text.toString()
            ).addOnCompleteListener {

                if (it.isSuccessful) {
                    userMailLogin = email.text.toString()
                    registerOK(c)
                    cleanUser()
                } else {
                    registerFail(c)
                }
            }
        } else{
            registerFail(c)
        }
    }

    private fun cleanUser(){
        email.text =""
        pass.text =""
    }

    private fun registerOK(c: Context) {

        db.collection(USERS).document(userMailLogin).set(
            hashMapOf(
                "favs" to mutableListOf<Favoritos>(),
                "notif" to false,
                "info" to false
            ))
        cleanUser()
        Toast.makeText(c, SUCCESS, Toast.LENGTH_SHORT).show()

    }
    private fun registerFail(c: Context){
        Toast.makeText(c, ERROR, Toast.LENGTH_SHORT).show()
    }

    private fun loginFail(c: Context){
        Toast.makeText(c, USER_PASS_WRONG, Toast.LENGTH_SHORT).show()

    }
}