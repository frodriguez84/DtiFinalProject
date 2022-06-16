package com.example.proyectofinal.viewmodels

import android.app.ProgressDialog
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.entities.Config.MAIL_FAIL
import com.example.proyectofinal.entities.Config.MAIL_SENT
import com.google.firebase.auth.FirebaseAuth

class RecuMailViewModel : ViewModel() {

    private lateinit var mAuth : FirebaseAuth

    fun resetPassword(emailRecu: String , v : View) {
        mAuth = FirebaseAuth.getInstance()

        mAuth.setLanguageCode("es")
        mAuth.sendPasswordResetEmail(emailRecu).addOnCompleteListener {

            if(it.isSuccessful){
                Toast.makeText( v.context , MAIL_SENT ,
                Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText( v.context , MAIL_FAIL ,
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}