package com.example.proyectofinal.entities

object Config {

    var URL_CIUDADES = "https://ciudadesdelfuturo.org.ar/"
    var URL_REDDIT = "https://www.reddti-ar.com.ar/"
    var MAIL_CONTACTO = "contacto@reddti-ar.com.ar"
    var URL_BD ="https://6180891b8bfae60017adfb16.mockapi.io/"
    var GOOGLE_MAPS : String = "com.google.android.apps.maps"
    var USERS : String = "users"
    var FAVS : String = "favs"
    var FORMS : String = "forms"



    ///MENSAJES///
    //Login ViewModel
    var SUCCESS : String = "Registro exitoso, inicie sesion"
    var ERROR : String = "Error: Se ha producido un error registrando al usuario"
    var USER_PASS_WRONG : String = "USUARIO or PASSWORD incorrecto"

    //Beach ViewModel
    var DTI_NOT_IN_FAV : String = "Dti no se encuentra en lista de favoritos"
    var DTI_ELIMINATED : String = "Dti ha sido eliminado de su lista de favoritos"
    var DTI_ADD : String = "Dti agregado correctamente a su lista de favoritos"
    var DTI_REPEATER : String = "El Dti ya se encuentra en su lista de favoritos"

    //RecuMail ViewModel
    var MAIL_SENT : String = "Se ha enviado un correo para restablecer tu contraseña"
    var MAIL_FAIL : String = "No se puedo enviar un correo para restablecer tu contraseña"

    //Formulario ViewModel
    var FORM_SENT : String = "Formulario enviado - Gracias"
    var FORM_FIELDS_INCOMPLETE : String = "Complete todos los campos del formulario, Gracias"

    //RecuMail Fragment
    var MAIL_REQUIRED : String = "Debe ingresar el mail"

    //Home Fragment
    var GEO_OFF : String = "No tiene activado Geolocalizacion"
    var TIME_OUT : String = "No se ha podido acceder al servidor, Intentelo mas tarde"

    //Perfil Fragment
    var NOTIF : String = "notif"
    var INFO : String = "info"

    //Consulta ViewModel
    var EMPTY_SUGGESTION : String = "La consulta no puede enviarse vacia"


}