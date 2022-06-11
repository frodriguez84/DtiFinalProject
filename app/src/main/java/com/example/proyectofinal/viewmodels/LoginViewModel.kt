package com.example.proyectofinal.viewmodels

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.Favoritos
import com.example.proyectofinal.entities.UserRepository.userMailLogin
import com.example.proyectofinal.fragments.LoginFragmentDirections
import com.google.android.material.snackbar.Snackbar
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
                } else {
                    registerFail(c)
                }
            }
        } else{
            registerFail(c)
        }
    }

    fun cleanUser(){
        email.text =""
        pass.text =""
    }

    fun registerOK(c: Context) {

        db.collection("users").document(userMailLogin).set(
            hashMapOf(
                "favs" to mutableListOf<Favoritos>(),
                "notif" to false,
                "info" to false
            ))
        cleanUser()
        Toast.makeText(c, "Registro exitoso, inicie sesion", Toast.LENGTH_SHORT).show()

    }
    fun registerFail(c: Context){
        Toast.makeText(c, "Error: Se ha producido un error registrando al usuario", Toast.LENGTH_SHORT).show()
    }

    fun loginFail(c: Context){
        Toast.makeText(c, "USUARIO or PASSWORD incorrecto", Toast.LENGTH_SHORT).show()

    }
}