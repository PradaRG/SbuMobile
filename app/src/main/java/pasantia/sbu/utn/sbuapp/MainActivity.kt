package pasantia.sbu.utn.sbuapp

import android.app.ProgressDialog

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import pasantia.sbu.utn.sbuapp.Adapter.FeedAdapter
import pasantia.sbu.utn.sbuapp.Common.HTTPDataHandler
import pasantia.sbu.utn.sbuapp.Model.RootObject

class MainActivity : AppCompatActivity() {
private val TAG = ' '
    private val RSS_link = "http://www.frp.utn.edu.ar/info2/?feed=rss2"
    private val RSS_to_JSON_API = "https://api.rss2json.com/v1/api.json?rss_url="


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title="NOTICIAS"
        setSupportActionBar(toolbar)
        val linearLayoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager

        loadRSS()

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

     private fun loadRSS() {
     val loadRSSAsync = object:AsyncTask<String, String, String>(){
            internal var mDialog = ProgressDialog(this@MainActivity)


            override fun onPostExecute(result: String?) {
                mDialog.dismiss()
                var rssObject: RootObject?
                rssObject= Gson().fromJson<RootObject>(result, RootObject::class.java!!)
                val adapter = FeedAdapter(rssObject,baseContext)
                recyclerView.adapter=adapter
                adapter.notifyDataSetChanged()

            }

            override fun doInBackground(vararg params: String): String {
                val result: String
                val http= HTTPDataHandler()
                result = http.GetHTTPDataHandler(params[0])
                return result
            }

            override fun onPreExecute() {
                mDialog.setMessage("Espere...")
                mDialog.show()
            }
        }
        val url_get_data = StringBuilder(RSS_to_JSON_API)
        url_get_data.append(RSS_link)
        loadRSSAsync.execute(url_get_data.toString())

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