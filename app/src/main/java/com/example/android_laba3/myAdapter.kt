package com.example.android_laba3

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class myAdapter(var data: JSONObject?): RecyclerView.Adapter<myAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var author: TextView
        var description: TextView

        init {
            title = view.findViewById(R.id.articleTitleData)
            author = view.findViewById(R.id.authorName)
            description = view.findViewById(R.id.description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_view, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.getJSONArray("results")?.length()?:0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val jsonObj = data?.getJSONArray("results")?.getJSONObject(position)
        holder.title.text = stringToNotNull(jsonObj?.getString("title"))
        holder.author.text = stringToNotNull(jsonObj?.getString("creator"))
        holder.description.text = stringToNotNull(jsonObj?.getString("description"))
//        holder.title.text = (jsonObj?.getString("title"))?:"-"
//        holder.author.text = (jsonObj?.getString("creator"))?:"-"
//        holder.description.text = jsonObj?.getString("description")?:"-"
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: JSONObject?) {
        data = newData
        Log.d("omg", data.toString())
        this.notifyDataSetChanged()
    }

    fun stringToNotNull(smth: String?): String {
        return if (smth != "null") smth!!
        else "-"
    }
}