package com.example.proyectofinal.viewmodels

import android.content.Context
import android.telephony.ims.ImsMmTelManager
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.Config.USERS
import com.example.proyectofinal.entities.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class EditProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var nameText : TextView
    private lateinit var lastNameText : TextView
    private lateinit var telText : TextView
    private lateinit var provText : TextView
    private lateinit var cityText : TextView

    private lateinit var name : String
    private lateinit var last : String
    private lateinit var tel : String
    private lateinit var prov : String
    private lateinit var ciu : String


    private var NULL : String = "null"
    private var DATA_SAVED : String = "Los datos fueron guardados"


    fun saveData(v: View, c: Context) {

        nameText = v.findViewById(R.id.nameEdit)
        lastNameText = v.findViewById(R.id.lastEdit)
        telText = v.findViewById(R.id.telEdit)
        provText = v.findViewById(R.id.provEdit)
        cityText = v.findViewById(R.id.cityEdit)

        db.collection(USERS).document(UserRepository.userMailLogin).set(
            hashMapOf(
                "apellido" to lastNameText.text.toString(),
                "nombre" to nameText.text.toString(),
                "telefono" to telText.text.toString(),
                "provincia" to provText.text.toString(),
                "ciudad" to cityText.text.toString()
            ) ,
            SetOptions.merge()
        )
        showMgsSaveData(c)

    }

    fun showMgsSaveData(c: Context){
        Toast.makeText(c, DATA_SAVED, Toast.LENGTH_SHORT).show()
    }

    fun showData(v: View) {

        nameText = v.findViewById(R.id.nameEdit)
        lastNameText = v.findViewById(R.id.lastEdit)
        telText = v.findViewById(R.id.telEdit)
        provText = v.findViewById(R.id.provEdit)
        cityText = v.findViewById(R.id.cityEdit)

        val docRef = db.collection(USERS).document(UserRepository.userMailLogin)

        docRef.get().addOnCompleteListener{ document ->
            if (document != null) {

                name = document.result.get("nombre").toString()
                last = document.result.get("apellido").toString()
                tel = document.result.get("telefono").toString()
                prov = document.result.get("provincia").toString()
                ciu = document.result.get("ciudad").toString()

                if (name != NULL){
                    nameText.text = name
                } else {
                    nameText.text = ""
                }
                if(last != NULL) {
                    lastNameText.text = last
                } else {
                    lastNameText.text = ""
                }
                if(tel != NULL) {
                    telText.text = tel
                } else {
                    telText.text = ""
                }
                if(prov != NULL) {
                    provText.text = prov
                } else {
                    provText.text = ""
                }
                if(ciu != NULL) {
                    cityText.text = ciu
                } else {
                    cityText.text = ""
                }
            }

        }

    }

}