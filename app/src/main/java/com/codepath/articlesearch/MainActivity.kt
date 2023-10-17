package com.codepath.articlesearch


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException
import androidx.lifecycle.lifecycleScope
import com.example.codepathmail.WishlistAdapter
import com.example.codepathmail.WishlistFetcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}
class MainActivity : AppCompatActivity() {

    lateinit var emails: List<Wishlist>


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputList = mutableListOf<String>()
        // Lookup the RecyclerView in activity layout
        val emailsRv = findViewById<RecyclerView>(R.id.rvMain)
        // Fetch the list of emails
        emails = WishlistFetcher.getEmails()
        // Create adapter passing in the list of emails
        val adapter = WishlistAdapter(emails)
        val editText = findViewById<EditText>(R.id.itemText)
        val editText2 = findViewById<EditText>(R.id.urlText)
        val editText3 = findViewById<EditText>(R.id.priceText)


        emailsRv.layoutManager = LinearLayoutManager(this)
        // Attach the adapter to the RecyclerView to populate items
        emailsRv.adapter = adapter
        // Set layout manager to position the items
        findViewById<Button>(R.id.loadMoreBtn).setOnClickListener {
            adapter.notifyDataSetChanged()
            val userInput: String = editText.text.toString()
            val userInput2: String = editText2.text.toString()
            val userInput3: String = editText3.text.toString()
            val newEmails = WishlistFetcher.setEmails(userInput, userInput2, userInput3)
            // Add new emails to existidang list of emails
            (emails as MutableList<Wishlist>).addAll(newEmails)
            // Notify the adapter there's new emails so the RecyclerView layout is updated
            adapter.notifyDataSetChanged()
            try {
                val parsedJson = createJson().decodeFromString(
                    SearchNewsResponse.serializer(),
                    ""
                )

                parsedJson.response?.docs?.let { list ->
                    lifecycleScope.launch(IO) {
                        (application as ArticleApplication).db.articleDao().deleteAll()
                        (application as ArticleApplication).db.articleDao().insertAll(list.map {
                            ArticleEntity(
                                headline = userInput,
                                articleAbstract = userInput2,
                                byline = userInput3,
                                mediaImageUrl = ""
                            )
                        })
                    }
                }
                // Fetch next 5 emails

                // Add new emails to existing list of emails


        } catch (e: JSONException) {
            Log.e(TAG, "Exception: $e")
        }

    }

    }
}







