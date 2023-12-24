package com.example.android_laba3

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class MainActivity: Activity() {
    private lateinit var search: EditText
    private lateinit var startSearch: ImageButton
    private val MY_API_KEY = 12345

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        search = findViewById(R.id.search)
        startSearch = findViewById(R.id.startSearch)
        startSearch.setOnClickListener {
            val text = search.text.toString()
            if (text != "") getNews(text)
            else Toast.makeText(applicationContext, "Please enter a topic or keyword", Toast.LENGTH_SHORT).show()
        }
    }

    fun getNews(keyWords: String) {
        val myURL = "https://newsdata.io/api/1/news?apikey=$MY_API_KEY&q=$keyWords"
    }
}