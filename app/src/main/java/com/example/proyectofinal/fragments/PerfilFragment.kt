package com.example.proyectofinal.fragments


import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController

import com.example.proyectofinal.R
import com.example.proyectofinal.entities.Config.INFO
import com.example.proyectofinal.entities.Config.NOTIF
import com.example.proyectofinal.entities.Config.USERS
import com.example.proyectofinal.entities.UserRepository.infoOk
import com.example.proyectofinal.entities.UserRepository.notifOk
import com.example.proyectofinal.entities.UserRepository.userMailLogin
import com.example.proyectofinal.viewmodels.PerfilViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@Suppress("DEPRECATION")
class PerfilFragment : Fragment() {

    private lateinit var v: View
    private lateinit var editBtn: Button
    private lateinit var switchOscuro: Switch
    private lateinit var switchNotif: Switch
    private lateinit var switchInfo: Switch

    private val db = FirebaseFirestore.getInstance()
    private val vm: PerfilViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_perfil, container, false)

        editBtn = v.findViewById(R.id.editBtn)
        switchOscuro = v.findViewById(R.id.switchDark)
        switchNotif = v.findViewById(R.id.switchNotif)
        switchInfo = v.findViewById(R.id.switchCompInfo)

        vm.setSwitch(v)
        return v
    }

    override fun onStart() {
        super.onStart()

        vm.showData(v)

        editBtn.setOnClickListener {

            val action = PerfilFragmentDirections.actionPerfilFragmentToEditProfileFragment()
            v.findNavController().navigate(action)

        }

        switchOscuro.setOnCheckedChangeListener { button, isChecked ->

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            } else {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }

        switchNotif.setOnCheckedChangeListener { compoundButton, isChecked ->

            if (isChecked) {
                notifOk = true
                db.collection(USERS).document(userMailLogin).set(
                    hashMapOf(
                        NOTIF to true,
                    ),
                    SetOptions.merge()
                )
            } else {
                notifOk = false
                db.collection(USERS).document(userMailLogin).set(
                    hashMapOf(
                        NOTIF to false,
                    ),
                    SetOptions.merge()
                )
            }
        }

        switchInfo.setOnCheckedChangeListener { compoundButton, isChecked ->

            if (isChecked) {
                infoOk = true
                db.collection(USERS).document(userMailLogin).set(
                    hashMapOf(
                        INFO to true,
                    ),
                    SetOptions.merge()
                )
            } else {
                infoOk = false
                db.collection(USERS).document(userMailLogin).set(
                    hashMapOf(
                        INFO to false,
                    ),
                    SetOptions.merge()
                )
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            vm.dialog(requireContext(), requireActivity())
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}