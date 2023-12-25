package com.example.android_laba3

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity: Activity() {
    private val MY_API_KEY = "pub_3536723bcac7b54645c8af4e56dad660040f1"
    private lateinit var search: EditText
    private lateinit var deleteReq: ImageView
    private lateinit var startSearch: ImageButton
    private lateinit var text: TextView
    private lateinit var myAdapter: myAdapter

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
        deleteReq = findViewById(R.id.clear)
        deleteReq.setOnClickListener { search.setText("") }
        startSearch = findViewById(R.id.startSearch)
        startSearch.setOnClickListener {
            val text = search.text.toString()
            if (text != "") {
                sendRequest(text)
            }
            else {
                Toast.makeText(applicationContext, "Please enter a topic or keyword", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sendRequest(keyWords: String) {
        val myURL = "https://newsdata.io/api/1/news?apikey=$MY_API_KEY&q=$keyWords"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(myURL, null, {
            response ->
            val success = (response.getString("status") == "success") &&
                    response.getInt("totalResults") != 0
            if (success) {
                myAdapter.updateData(response)
                myAdapter.notifyDataSetChanged()
                emptyListOfNews(null)
            } else {
                emptyListOfNews(false)
                myAdapter.updateData(null)
                myAdapter.notifyDataSetChanged()
            }
        }, {
            error ->
            emptyListOfNews(false)
            myAdapter.updateData(null)
            myAdapter.notifyDataSetChanged()
            Toast.makeText(applicationContext, "An error has occurred. Check your internet connection", Toast.LENGTH_SHORT).show()
        })

        queue.add(request)
    }

    fun emptyListOfNews(flag: Boolean? = null) {
        when (flag) {
            true -> text.text = "The news feed is empty now"
            false -> text.text = "0 results was found for your query"
            else -> text.text = ""
        }
    }
}