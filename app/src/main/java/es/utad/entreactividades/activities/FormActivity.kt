package es.utad.entreactividades.activities


import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

import es.utad.entreactividades.MainActivity
import es.utad.entreactividades.R
import es.utad.entreactividades.model.User
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

class FormActivity : AppCompatActivity() {
    var registro: Boolean = true
    var usuario = User()
    lateinit var editTextUsuario: EditText
    lateinit var editTextPassword: EditText
    lateinit var editTextNombre: EditText
    lateinit var editTextApellidos: EditText
    lateinit var btnActualizar: Button
    lateinit var btnAcceptar: Button
    lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        editTextUsuario = findViewById<EditText>(R.id.editTextUsuario)
        editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        editTextApellidos = findViewById<EditText>(R.id.editTextApellidos)
        btnAcceptar = findViewById<Button>(R.id.btnAceptar)
        btnActualizar = findViewById<Button>(R.id.btnActualizar)
        btnCancelar = findViewById<Button>(R.id.btnCancelar)
        btnActualizar.visibility = View.INVISIBLE


        registro = intent.getBooleanExtra("registro", true)

        if (!registro) {
            var bundle: Bundle = intent.getBundleExtra("usuario")
            usuario.setBundle(bundle)
            editTextUsuario.setText(usuario.usuario)
            editTextPassword.setText(usuario.password)
            editTextNombre.setText(usuario.nombre)
            editTextApellidos.setText(usuario.apellidos)
            editTextUsuario.isEnabled = false
            btnAcceptar.visibility = View.INVISIBLE
            btnActualizar.visibility = View.VISIBLE
            btnCancelar.visibility = View.VISIBLE
        }
    }

    fun onActualizar(view: View) {

        usuario.usuario = editTextUsuario.text.toString()
        usuario.password = editTextPassword.text.toString()
        usuario.nombre = editTextNombre.text.toString()
        usuario.apellidos = editTextApellidos.text.toString()

        var resultIntent = Intent(this, MainActivity::class.java)
        resultIntent.putExtra("usuario", usuario.getBundle())
        setResult(Activity.RESULT_OK, resultIntent)
        val JsonUpdate = JSONArray(leer())

        var jsonObjeto = JSONObject()

        jsonObjeto.put("usuario", usuario.usuario)
        jsonObjeto.put("password", usuario.password);
        jsonObjeto.put("nombre", usuario.nombre);
        jsonObjeto.put("apellidos", usuario.apellidos);

        for (i in 0 until JsonUpdate.length()) {
            val item = JsonUpdate.getJSONObject(i)

            if (item.get("usuario") == jsonObjeto.get("usuario")) {
                JsonUpdate.put(i, jsonObjeto)
            }
        }
        var nombreFichero = "JSON.json"

        val jsonString: String = JsonUpdate.toString()
        var fileOutput = openFileOutput(nombreFichero, Context.MODE_PRIVATE)
        fileOutput.write(jsonString.toByteArray())
        fileOutput.close()

        finish()
    }


    fun onCancelar(view: View) {
        finish()
    }

    fun onAceptar(view: View) {
        if (registro) {

            usuario.usuario = editTextUsuario.text.toString()
            usuario.password = editTextPassword.text.toString()
            usuario.nombre = editTextNombre.text.toString()
            usuario.apellidos = editTextApellidos.text.toString()

            var resultIntent = Intent(this, MainActivity::class.java)
            resultIntent.putExtra("usuario", usuario.getBundle())
            setResult(Activity.RESULT_OK, resultIntent)
            var JsonUpdate: JSONArray

            var nombreFichero = "JSON.json"
            var file = File(getFilesDir().getAbsolutePath(), nombreFichero)
            var fileExists = file.exists()

            if (fileExists) {
                JsonUpdate = JSONArray(leer())
            } else {
                JsonUpdate = JSONArray()
            }

            var jsonObjeto = JSONObject()

            jsonObjeto.put("usuario", usuario.usuario)
            jsonObjeto.put("password", usuario.password);
            jsonObjeto.put("nombre", usuario.nombre);
            jsonObjeto.put("apellidos", usuario.apellidos);

            JsonUpdate.put(jsonObjeto)

            val jsonString: String = JsonUpdate.toString()
            var fileOutput = openFileOutput(nombreFichero, Context.MODE_PRIVATE)
            fileOutput.write(jsonString.toByteArray())
            fileOutput.close()

            finish()
        } else {
            finish()
        }
    }

    private fun leer(): String {

        val StBl: StringBuilder = StringBuilder()
        try {
            var bufferedReader =
                BufferedReader(InputStreamReader(openFileInput("JSON.json")))

            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                StBl.append(text)
            }
            bufferedReader.close()
            return StBl.toString()
        } catch (e: IOException) {
            throw e
        }

    }

}