package pasantia.sbu.utn.sbuapp.Model

import java.util.*

data class Item(val title : String, val pubDate: String, val link : String, val guid : String, val author : String, val thumbnail : String, val description : String, val content : String, val enclosure : Objects?, val categories: List<String>) {
constructor(): this("","","","","","","","",null, emptyList())
}