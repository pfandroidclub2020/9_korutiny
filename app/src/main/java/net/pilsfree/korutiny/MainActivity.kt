package net.pilsfree.korutiny

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bgScope.launch {
            val doc = Jsoup.parse(URL("https://www.abclinuxu.cz/clanky/programovani"), 5000)
            val nadpisy = doc.select("#st .st_nadpis")

            val list = nadpisy.map { Pair<String,String>(
                it.text(),
                "https://www.abclinuxu.cz"+it.select("a").first().attr("href")
            ) }

            val adapter  = ABCAdapter(list) {
                    url : String ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
            }

            uiScope.launch {
                recycler.adapter = adapter
                recycler.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
                recycler.addItemDecoration(DividerItemDecoration(this@MainActivity,LinearLayoutManager.VERTICAL))
            }

            val link = doc.select("#st a").filter { it.text() == "Starší články" }.lastOrNull()

            link?.also {
                val doc2 = Jsoup.parse(URL("https://www.abclinuxu.cz"+link.attr("href")),5000)
                val nadpisy2 = doc2.select("#st .st_nadpis")
                val list2 = nadpisy2.map { Pair<String,String>(
                    it.text(),
                    "https://www.abclinuxu.cz"+it.select("a").first().attr("href")
                ) }
                val joined = arrayListOf<Pair<String,String>>()
                joined.addAll(list)
                joined.addAll(list2)
                uiScope.launch {
                    adapter.list = joined
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}
