package pasantia.sbu.utn.sbuapp.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pasantia.sbu.utn.sbuapp.Inteface.ItemClickListener
import pasantia.sbu.utn.sbuapp.Model.RootObject
import pasantia.sbu.utn.sbuapp.R

class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    var txtTitle: TextView
    var txtPubDate: TextView
    var txtContent: TextView
    private var itemClickListener : ItemClickListener?=null

    init {
        txtTitle = itemView.findViewById(R.id.txtTitle) as TextView
        txtPubDate = itemView.findViewById(R.id.txtPubDate) as TextView
        txtContent = itemView.findViewById(R.id.txtContent) as TextView

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener (itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
       itemClickListener!!.onClick(v, adapterPosition, false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v, adapterPosition, true)
        return true
    }
}

class FeedAdapter(private val rssObject : RootObject, private val mContext: Context ):RecyclerView.Adapter<FeedViewHolder>()
{
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.row, parent, false)
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.txtTitle.text = rssObject.items[position].title
        holder.txtContent.text =  setTextHTML(rssObject.items[position].description)
        holder.txtPubDate.text = rssObject.items[position].pubDate

        holder.setItemClickListener(ItemClickListener{view, position, isLongClick ->
            if(!isLongClick)
            {

                    //show article content inside a dialog
                    val articleView = WebView(view.context)

                    articleView.settings.loadWithOverviewMode = true

                    articleView.settings.javaScriptEnabled = true
                    articleView.isHorizontalScrollBarEnabled = false
                    articleView.webChromeClient = WebChromeClient()
                    articleView.loadDataWithBaseURL(null, "<style>img{display: inline; height: auto; max-width: 100%;} " +

                            "</style>\n" + "<style>iframe{ height: auto; width: auto;}" + "</style>\n" +rssObject.items[position].content, null, "utf-8", null)

                    val alertDialog = androidx.appcompat.app.AlertDialog.Builder(view.context).create()
                    alertDialog.setTitle(rssObject.items[position].title)
                    alertDialog.setView(articleView)
                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK"
                    ) { dialog, _ -> dialog.dismiss() }
                    alertDialog.show()

                    (alertDialog.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()


            }
        })
    }
    fun setTextHTML(html: String): Spanned
    {
        val result: Spanned = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }
        return result
    }

}