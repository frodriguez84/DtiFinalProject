package com.example.proyectofinal.fragments


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.APIService
import com.example.proyectofinal.entities.Config.GEO_OFF
import com.example.proyectofinal.entities.Config.TIME_OUT
import com.example.proyectofinal.entities.Dti
import com.example.proyectofinal.entities.RestEngine
import com.example.proyectofinal.entities.UserRepository
import com.example.proyectofinal.entities.UserRepository.ListDti
import com.example.proyectofinal.entities.UserRepository.ListDtiNombres
import com.example.proyectofinal.entities.UserRepository.dtiDocument
import com.example.proyectofinal.entities.UserRepository.userBeachSelect
import com.example.proyectofinal.entities.UserRepository.userLatitud
import com.example.proyectofinal.entities.UserRepository.userLongitud
import com.example.proyectofinal.viewmodels.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var v : View
    private val vm : HomeViewModel by viewModels()
    private lateinit var goBeachButton: Button
    private lateinit var bOut : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) { vm.populateFavs() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       v =inflater.inflate(R.layout.fragment_home, container, false)

        goBeachButton = v.findViewById(R.id.goBeachBtn)
        bOut = v.findViewById(R.id.btnLogout)

        return v
    }


    override fun onStart() {
        super.onStart()
        if(ListDti.isNotEmpty()) {
            if (userLatitud.isNotBlank() && userLongitud.isNotBlank()) {
                //Nos va a mostrar el DTI que se encuentra mas cerca a nuestra posicion por Geolocalizacion
                vm.dtiCercano(v)
            } else {
                vm.showData(dtiDocument.toInt(), v)
                Toast.makeText(context, GEO_OFF, Toast.LENGTH_SHORT)
                    .show()
            }

            vm.showDti(v, requireContext())

        } else {

            AlertDialog.Builder(requireContext())
                .setMessage(TIME_OUT)
                .setCancelable(false)
                .setPositiveButton("Cerrar App") { dialog, whichButton ->
                    FirebaseAuth.getInstance().signOut()
                    vm.cleanLogUser()
                    activity?.finish()

                }
                .setNegativeButton("Contacto") { dialog, whichButton ->
                    val action = HomeFragmentDirections.actionHomeFragmentToContactoFragment()
                    v.findNavController().navigate(action)
                }
                .show()
        }

        goBeachButton.setOnClickListener {

            val action = HomeFragmentDirections.actionHomeFragmentToBeachFragment(dtiDocument)
            v.findNavController().navigate(action)
        }

        bOut.setOnClickListener {
            vm.dialog(requireContext(),requireActivity())
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            vm.dialog(requireContext() , requireActivity())
        }

    }





}




