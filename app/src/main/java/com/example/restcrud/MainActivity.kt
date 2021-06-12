package com.example.restcrud

import android.app.ProgressDialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.restcrud.model.ResponseAlta
import com.example.restcrud.utils.HttpsResolveSSL
import com.example.restcrud.utils.ValidationTabletOrPhone
import com.google.gson.Gson
import mx.kondiun.condominio.ui.login.model.ResponseLogin
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var  btnInsert : Button
    lateinit var  btnUpdate : Button
    lateinit var  btnSelect: Button
    lateinit var  btnDelete : Button
    lateinit var modelResponseLogin : ResponseLogin
    lateinit var modelResponseAlta : ResponseAlta


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!validar()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            setContentView(R.layout.activity_main)
        }else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            setContentView(R.layout.activity_main_tablet)
        }
        btnDelete = findViewById(R.id.main_activity_btn_delete)
        btnInsert = findViewById(R.id.main_activity_btn_insert)
        btnSelect = findViewById(R.id.main_activity_btn_select)
        btnUpdate = findViewById(R.id.main_activity_btn_update)

        btnSelect.setOnClickListener {

            restSelect()
        }
        btnInsert.setOnClickListener {
            restInsert()
        }
        btnUpdate.setOnClickListener {
            restUpdate()
        }
    }

    fun restSelect() {

        var urlSelect = "https://bitsybytes.mx/hsPbs/api/hsS_index.php"
         HttpsResolveSSL.handleSSLHandshake()

        var progresDialog = ProgressDialog(this)
        progresDialog.setTitle("Select Rest")
        progresDialog.setMessage("Descargando informacion")
        progresDialog.isIndeterminate = true
        progresDialog.setCancelable(true) // bloquea la pantalla principal y evita clicks
        progresDialog.show()

        val jsonRequest = JSONObject() // json de envio de datos
        try {

            var correo = "majduarte@yahoo.com.mx"
            var pass = "1958betoPbs"
            jsonRequest.put("correo", correo)
            jsonRequest.put("pass", pass)


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        // generacion de request para servidor
        var queue = Volley.newRequestQueue(this)
        val request: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST,
                urlSelect,
                jsonRequest,
                Response.Listener {

                    Log.e("jsonResponse", "jsonResponse: " + it.toString())
                    var responseLogin = it.toString()
                    var gson = Gson()
                    modelResponseLogin = gson.fromJson(responseLogin, ResponseLogin::class.java)

                    if (progresDialog.isShowing) {
                        progresDialog.cancel()

                    }

                },
                Response.ErrorListener {
                    if (progresDialog.isShowing) {
                        progresDialog.hide()
                    }
                    Log.e("error", "error volley" + it.cause.toString())
                }){
            // para los header mandar hash maps
        }
        queue.add(request)
    }

    fun restInsert() {

        var urlInsert = "https://bitsybytes.mx/hsPbs/api/hsS_accesoAlt.php"
        HttpsResolveSSL.handleSSLHandshake()

        var progresDialog = ProgressDialog(this)
        progresDialog.setTitle("Insert Rest")
        progresDialog.setMessage("Ingresando información")
        progresDialog.isIndeterminate = true
        progresDialog.setCancelable(true) // bloquea la pantalla principal y evita clicks
        progresDialog.show()

        val jsonRequest = JSONObject() // json de envio de datos
        try {


            jsonRequest.put("jwt", modelResponseLogin.jwt)
            jsonRequest.put("id", modelResponseLogin.dat!!.id)
            jsonRequest.put("nomVis", "jose alberto martinez")
            jsonRequest.put("feAcce", "13/03/2021")
            jsonRequest.put("horAcc", "13:00")
            jsonRequest.put("accesA", "M")


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        // generacion de request para servidor
        var queue = Volley.newRequestQueue(this)
        val request: JsonObjectRequest = object : JsonObjectRequest(
                Method.POST,
                urlInsert,
                jsonRequest,
                Response.Listener {

                    Log.e("jsonResponse", "jsonResponse: " + it.toString())

                    var responseAlta = it.toString()
                    var gson = Gson()
                    modelResponseAlta = gson.fromJson(responseAlta, ResponseAlta::class.java)
                    if (progresDialog.isShowing) {
                        progresDialog.cancel()

                    }

                },
                Response.ErrorListener {
                    if (progresDialog.isShowing) {
                        progresDialog.hide()
                    }
                    Log.e("error", "error volley" + it.cause.toString())
                }){
            // para los header mandar hash maps
        }
        queue.add(request)
    }

    fun restUpdate() {

        var urlUpdate= "https://bitsybytes.mx/hsPbs/api/hsS_accesoMod.php"
        HttpsResolveSSL.handleSSLHandshake()

        var progresDialog = ProgressDialog(this)
        progresDialog.setTitle("Actualizando Rest")
        progresDialog.setMessage("Actualizando información")
        progresDialog.isIndeterminate = true
        progresDialog.setCancelable(true) // bloquea la pantalla principal y evita clicks
        progresDialog.show()

        val jsonRequest = JSONObject() // json de envio de datos
        try {


            jsonRequest.put("jwt", modelResponseLogin.jwt)
            jsonRequest.put("id", modelResponseLogin.dat!!.id)
            jsonRequest.put("numPri", modelResponseLogin.dat!!.numPri)
            jsonRequest.put("consec", modelResponseAlta.consec)
            jsonRequest.put("nomVis", "Ricardo lopez")
            jsonRequest.put("feAcce", "13/09/2021")
            jsonRequest.put("horAcc", "17:00")
            jsonRequest.put("accesA", "E")


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        // generacion de request para servidor
        var queue = Volley.newRequestQueue(this)
        val request: JsonObjectRequest = object : JsonObjectRequest(
                Method.PUT,
                urlUpdate,
                jsonRequest,
                Response.Listener {

                    Log.e("jsonResponse", "jsonResponse: " + it.toString())
                    if (progresDialog.isShowing) {
                        progresDialog.cancel()

                    }

                },
                Response.ErrorListener {
                    if (progresDialog.isShowing) {
                        progresDialog.hide()
                    }
                    Log.e("error", "error volley" + it.cause.toString())
                }){
            // para los header mandar hash maps
        }
        queue.add(request)
    }


    fun validar() : Boolean{
        val validation = ValidationTabletOrPhone()
        return validation.validation(this)
    }


    // select  Login
    // insert Alta
    // update  alta (tiempo hora)
    // delete alta
}
