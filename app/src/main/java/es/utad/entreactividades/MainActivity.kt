
package es.utad.entreactividades

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.gson.Gson
import es.utad.entreactividades.activities.FormActivity
import es.utad.entreactividades.dialogs.LoginDialog
import es.utad.entreactividades.model.User


class MainActivity : AppCompatActivity() {
    val RESQUEST_CODE_ACTIVTY_FORM:Int = 1000
    var usuario: User = User()

    lateinit var buttonRegister: Button
    lateinit var buttonLogin: Button
    lateinit var buttonInformation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonInformation = findViewById<Button>(R.id.buttonInformacion)
        buttonLogin.isEnabled = false
        buttonInformation.isEnabled = false
    }


    fun onRegister(view: View) {
        var intentRegistro = Intent(this, FormActivity::class.java)
        intentRegistro.putExtra("registro", true)
        startActivityForResult(intentRegistro, RESQUEST_CODE_ACTIVTY_FORM)
    }

    fun onLogin(view: View) {
        var loginDialog = LoginDialog()
        loginDialog.usuario = usuario
        loginDialog.mainActivity = this

        loginDialog.show(supportFragmentManager, "logiDialog_Tag")
    }

    fun onInformation(view: View) {
        var intentInformacion = Intent(this, FormActivity::class.java)
        intentInformacion.putExtra("registro", false)
        intentInformacion.putExtra("usuario", usuario.getBundle())
        startActivity(intentInformacion)
    }

    fun activateInformation(){
        buttonInformation.isEnabled = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == RESQUEST_CODE_ACTIVTY_FORM){
                var bundle = data!!.getBundleExtra("usuario")
                usuario.setBundle(bundle)

                buttonLogin.isEnabled = true
            }
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Mensaje!!")
        builder.setMessage("¿Está seguro que quiere salir?")

        builder.setPositiveButton("Si") { dialog, _ -> finish()}
        builder.setNegativeButton("No") { dialog, which -> }

        builder.show()

        //super.onBackPressed()
    }
}