package es.utad.entreactividades.dialogs

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import es.utad.entreactividades.MainActivity
import es.utad.entreactividades.R
import es.utad.entreactividades.model.User
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class LoginDialog : DialogFragment() {

    var usuario = User()
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var viewDialog = inflater.inflate(R.layout.login_dialog, container, false)

        var botonLogin = viewDialog.findViewById<Button>(R.id.buttonLogin)
        botonLogin.setOnClickListener { view -> login(view) }
        return viewDialog
    }

    fun login(view: View) {


        var et_user = dialog!!.findViewById<EditText>(R.id.et_username)
        var et_password = dialog!!.findViewById<EditText>(R.id.et_password)

        var user = et_user.text.toString()
        var pass = et_password.text.toString()

        var gson = Gson()

        var recorrerArray = gson.fromJson(leer(mainActivity), Array<User>::class.java).toList()


        recorrerArray.forEach { usuario ->

            if (user.equals(usuario.usuario, true) && pass.equals(usuario.password)) {

                mainActivity.activateInformation()
                mainActivity.usuario = usuario

                dismiss()
                return
            }
        }


        Toast.makeText(this.activity, "Usuario/Contraseña incorrecto", Toast.LENGTH_LONG).show()

    }

    private fun leer(activity: Activity): String {

        val stringBuilder: StringBuilder = StringBuilder()
        try {
            var bufferedReader = BufferedReader(InputStreamReader(activity.openFileInput("JSON.json")))


            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            bufferedReader.close()
            return stringBuilder.toString()
        } catch (e: IOException) {
            throw e
        }


    }


}