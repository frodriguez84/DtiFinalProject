package com.example.proyectofinal.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.Config.DTI_ADD
import com.example.proyectofinal.entities.Config.DTI_ELIMINATED
import com.example.proyectofinal.entities.Config.DTI_NOT_IN_FAV
import com.example.proyectofinal.entities.Config.DTI_REPEATER
import com.example.proyectofinal.entities.Config.FAVS
import com.example.proyectofinal.entities.Config.GOOGLE_MAPS
import com.example.proyectofinal.entities.Config.USERS
import com.example.proyectofinal.entities.UserRepository
import com.example.proyectofinal.entities.UserRepository.ListDti
import com.example.proyectofinal.entities.UserRepository.listOfFavs
import com.example.proyectofinal.entities.UserRepository.userMailLogin
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.protobuf.FloatValue
import me.tankery.lib.circularseekbar.CircularSeekBar
import kotlin.properties.Delegates
@SuppressLint("StaticFieldLeak")
class BeachViewModel : ViewModel() {


    private val db = FirebaseFirestore.getInstance()

    private lateinit var nameView : TextView
    private lateinit var pcAforo : CircularSeekBar
    private lateinit var aforoView: TextView

    private lateinit var pcTemp : CircularSeekBar
    private lateinit var tempView: TextView

    private lateinit var pcPark : CircularSeekBar
    private lateinit var parkView: TextView

    private lateinit var imageFlag : ImageView
    private lateinit var flagView : TextView

    private lateinit var pcUvs : CircularSeekBar
    private lateinit var uvView: TextView

    private lateinit var dirV : TextView
    private lateinit var velV : TextView
    private lateinit var altO : TextView

    private lateinit var aforo : String
    private var temp : Float = 0F
    private var park : Float = 0F
    private var uvs : Float = 0F
    private var lugDispo : Int = 0

    private lateinit var bandera : String
    private lateinit var rayosUv : String

    private lateinit var bAddToFavs : Button
    private lateinit var bRemoveFavs : Button


    fun showDataBeach(idPlaya: String, v: View) {

        var posDti = ListDti[idPlaya.toInt()]

        nameView = v.findViewById(R.id.nameBeach)

        aforoView = v.findViewById(R.id.aforoTextView)

        pcAforo = v.findViewById(R.id.pcaforo)

        tempView = v.findViewById(R.id.tempTextView)
        pcTemp = v.findViewById(R.id.pctemp)

        parkView = v.findViewById(R.id.parkTextView)
        pcPark = v.findViewById(R.id.pcpark)

        imageFlag = v.findViewById(R.id.flagImage)
        flagView = v.findViewById(R.id.flagTextView)

        uvView = v.findViewById(R.id.uvRaystextView)
        pcUvs = v.findViewById(R.id.pcUv)

        altO = v.findViewById(R.id.altOlasTV)
        velV = v.findViewById(R.id.windVelTV)
        dirV = v.findViewById(R.id.windDirTV)



            pcPark.max = posDti.maxParking
            aforo = posDti.aforo
            temp =  posDti.temperatura
            park = posDti.parking
            uvs = posDti.uv.toFloat()

            nameView.text = posDti.name

            tempView.text = posDti.temperatura.toString()+"°"

            bandera = posDti.bandera
            rayosUv = posDti.uv

            altO.text = posDti.altOla.toString()+ "mts"
            velV.text = posDti.velViento + "km/h"
            dirV.text = posDti.dirViento.uppercase()

        lugDispo = (posDti.maxParking - posDti.parking).toInt()

        parkView.text = lugDispo.toString()+" Disponibles"


            when(bandera){
                "bueno" -> {
                    imageFlag.setImageDrawable(v.context.getDrawable(R.drawable.bueno))
                    flagView.text = "BUENO"
                }
                "dudoso" ->{
                    imageFlag.setImageDrawable(v.context.getDrawable(R.drawable.dudoso))
                    flagView.text = "DUDOSO"
                }
                "peligroso" -> {
                    imageFlag.setImageDrawable(v.context.getDrawable(R.drawable.peligroso))
                    flagView.text = "PELIGROSO"
                }
                "niñoper" -> {
                    imageFlag.setImageDrawable(v.context.getDrawable(R.drawable.ninoextr))
                    flagView.text = "NIÑO PERDIDO"
                }
                "prohibido" -> {
                    imageFlag.setImageDrawable(v.context.getDrawable(R.drawable.prohibido))
                    flagView.text = "PROHIBIDO BAÑARSE"
                }
                "rayos" -> {
                    imageFlag.setImageDrawable(v.context.getDrawable(R.drawable.rayos))
                    flagView.text = "RAYOS - EVACUAR"
                }
            }

            when(rayosUv){

                "1" -> uvView.text = "1 - Bajo"
                "2" -> uvView.text = "2 - Bajo"
                "3" -> uvView.text = "3 - Moderado"
                "4" -> uvView.text = "4 - Moderado"
                "5" -> uvView.text = "5 - Moderado"
                "6" -> uvView.text = "6 - Alto"
                "7" -> uvView.text = "7 - Alto"
                "8" -> uvView.text = "8 - Muy Alto"
                "9" -> uvView.text = "9 - Muy Alto"
                "10"-> uvView.text = "10 - Muy Alto"

            }
            when(aforo){
                "bajo"-> {
                    aforoView.text = "Bajo"
                    pcAforo.progress = 25F
                }
                "medio"-> {
                    aforoView.text = "Medio"
                    pcAforo.progress = 50F
                }
                "altos"-> {
                    aforoView.text = "Alto"
                    pcAforo.progress = 75F
                }
                "lleno"-> {
                    aforoView.text = "Lleno"
                    pcAforo.progress = 100F
                }
            }

            pcTemp.progress = temp
            pcPark.progress = park
            pcUvs.progress = uvs

    }

    fun showButtons(v: View, docDti: String) {
        bAddToFavs = v.findViewById(R.id.btnAddFavoritos)
        bRemoveFavs = v.findViewById(R.id.btnRemoveFavoritos)

        if(!esFavorito(docDti)) {
            bRemoveFavs.visibility = View.INVISIBLE
        }else{
            bAddToFavs.visibility = View.INVISIBLE
        }
    }

    fun esFavorito(docDti: String): Boolean {
        val x = listOfFavs.find { f -> f == docDti }
        return x != null
    }

    fun dtiNotInList(v: View, context : Context) {

        Toast.makeText(context,DTI_NOT_IN_FAV , Toast.LENGTH_LONG).show()
    }

    fun favRemoved(v : View, context : Context) {
        Toast.makeText(context, DTI_ELIMINATED , Toast.LENGTH_LONG).show()
    }

    fun removeFavorite(x: String) {
        var favoritos = db.collection(USERS).document(userMailLogin)
        favoritos.update(FAVS, FieldValue.arrayRemove(x))
        listOfFavs.remove(listOfFavs.find { f -> f == x })
    }

    fun addFavotite(x: String) {
        var favoritos = db.collection(USERS).document(userMailLogin)
        favoritos.update(FAVS, FieldValue.arrayUnion(x))
        listOfFavs.add(x)

    }

    fun favAdded(v : View, context : Context) {

        Toast.makeText(context, DTI_ADD , Toast.LENGTH_LONG).show()
    }

    fun favInList(v : View, context : Context) {

        Toast.makeText(context, DTI_REPEATER , Toast.LENGTH_LONG).show()
    }

    fun goMap(idPlaya: String , context : Context) {

        val playa = ListDti[idPlaya.toInt()]
        val latitud = playa.location.coordinates[0]
        val longitud = playa.location.coordinates[1]

        val gmmIntentUri = Uri.parse("geo:"+ latitud+"," +longitud+"?q=playa "+playa.name)

        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage(GOOGLE_MAPS)

        startActivity(context , mapIntent , null)

    }


}