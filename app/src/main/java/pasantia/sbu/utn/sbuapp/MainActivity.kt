package pasantia.sbu.utn.sbuapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
private val TAG = ' '
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        imageView_opcion.setOnClickListener {
           llamarmenu(it)
//conseguir toquen de prueba
               FirebaseInstanceId.getInstance().instanceId
               .addOnCompleteListener(OnCompleteListener { task ->
                     if (!task.isSuccessful) {
                        Log.w(TAG.toString(), "getInstanceId failed", task.exception)
                        return@OnCompleteListener }
//
                    //Get new Instance ID token
                    val token = task.result?.token
//
                   // Log and toast
                   // val msg = getString (R.string.msg_token_fmt, token)
                    Log.d(TAG.toString(), token)
                   //Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
               })
        }


    }

    private fun llamarmenu(b:View) {
        val popupMenu = PopupMenu(this,b)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.Noticias -> {
                    Toast.makeText(this, " Noticias", Toast.LENGTH_SHORT).show()
                    //prueba
                    val abrirgestion = Intent (this,MainActivity::class.java)
                    startActivity(abrirgestion)
                    true
                    true
                }
                R.id.Autogestion ->{
                    Toast.makeText(this, " Autogestion", Toast.LENGTH_SHORT).show()
                    //prueba
                    val abrirgestion = Intent (this,Autogestion::class.java)
                    startActivity(abrirgestion)
                    true
                }
                R.id.CalendarioAcademico ->{
                    Toast.makeText(this, " Calendario academico", Toast.LENGTH_SHORT).show()
                    //prueba
                    val abrirgestion = Intent (this,Calendario::class.java)
                    startActivity(abrirgestion)
                    true
                }
                R.id.Campus ->{
                    Toast.makeText(this, " Campus Virtual", Toast.LENGTH_SHORT).show()
                    //prueba
                    val abrirgestion = Intent (this,CampuVirtual::class.java)
                    startActivity(abrirgestion)
                    true
                }else -> false

            }


        }

        popupMenu.inflate(R.menu.menu_main)
        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popupMenu)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception) {
            Log.e("Main", "Error showing menu icons.", e)
        } finally {
            popupMenu.show()
        }
    }
}