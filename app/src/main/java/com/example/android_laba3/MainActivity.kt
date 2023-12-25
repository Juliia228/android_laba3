package com.example.android_laba3

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity: Activity() {
    private val MY_API_KEY = ""
    private lateinit var search: EditText
    private lateinit var startSearch: ImageButton
    private lateinit var text: TextView
    private lateinit var myAdapter: myAdapter

//    class Article constructor(var title: String = "-",
//                              var author: String = "-",
//                              var description: String = "-",
//                              var url: String = "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        text = findViewById(R.id.emptyList)
        emptyListOfNews(true)
        myAdapter = myAdapter(null)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerV)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = myAdapter

        search = findViewById(R.id.search)
        startSearch = findViewById(R.id.startSearch)
        startSearch.setOnClickListener {
            var success: Boolean
            val text = search.text.toString()
            if (text != "") success = sendRequest(text)
            else {
                success = false
                Toast.makeText(applicationContext, "Please enter a topic or keyword", Toast.LENGTH_SHORT).show()
            }
            emptyListOfNews(!success)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sendRequest(keyWords: String): Boolean {
        var success = false

        val myURL = "https://newsdata.io/api/1/news?apikey=$MY_API_KEY&q=$keyWords"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(myURL, null, {
            response ->
            success = (response.getString("status") == "success") &&
                    response.getInt("totalResults") != 0
            if (success) {
                myAdapter.updateData(response)
                emptyListOfNews(null)
            }
        }, {
            error ->
            Toast.makeText(applicationContext, "Произошла ошибка. Проверьте интернет-подключение", Toast.LENGTH_SHORT).show()
        })

        queue.add(request)

        return success
    }

    fun emptyListOfNews(flag: Boolean? = null) {
        when (flag) {
            true -> text.text = "The news feed is empty now"
            false -> text.text = "0 results was found for your query"
            else -> text.text = ""
        }
    }
}