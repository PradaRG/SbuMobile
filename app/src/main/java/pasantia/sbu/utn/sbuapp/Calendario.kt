package pasantia.sbu.utn.sbuapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import kotlinx.android.synthetic.main.activity_autogestion.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.imageView_opcion

class Calendario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)
        imageView_opcion.setOnClickListener {
            llamarmenu(it)
        }
        webview.webChromeClient = object  : WebChromeClient(){}
        webview.webViewClient = object : WebViewClient() {}
        webview.loadUrl("https://calendar.google.com/calendar/embed?src=totoelguan%40gmail.com&ctz=America%2FArgentina%2FCordoba")
       // webview.loadUrl("file:///android_asset/calendario.html")
        val settings = webview.settings
        settings.javaScriptEnabled = true




    }

    override fun onBackPressed() {
        webview.goBack()
    }

    private fun llamarmenu(b: View) {
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
                }
                else -> false

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
