package com.example.android_laba3

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Math.min

class myAdapter(var data: JSONObject?): RecyclerView.Adapter<myAdapter.MyViewHolder>() {
    @SuppressLint("QueryPermissionsNeeded")
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var category: TextView
        var link: TextView
        var description: TextView

        init {
            title = view.findViewById(R.id.articleTitleData)
            category = view.findViewById(R.id.catName)
            link = view.findViewById(R.id.url)
            description = view.findViewById(R.id.description)
            link.setOnClickListener {
                val intent = Intent(ACTION_VIEW, Uri.parse(link.text.toString()))
                intent.addCategory(CATEGORY_BROWSABLE)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                if (intent.resolveActivity(view.context.packageManager) != null) {
                    view.context.startActivity(intent)
                } else {
                    Toast.makeText(view.context, "Произошла ошибка. Проверьте интернет-подключение", Toast.LENGTH_SHORT).show()
                }
            }
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
        holder.category.text = getCategories(jsonObj?.getJSONArray("category"))
        holder.link.text = stringToNotNull(jsonObj?.getString("link"))
        holder.description.text = stringToNotNull(jsonObj?.getString("description"))
    }

    fun updateData(newData: JSONObject?) { data = newData }

    fun stringToNotNull(smth: String?): String {
        val len = smth?.length?:0
        return if (smth != "null" && smth != null) {
            val target_string = smth.substring(0, min(len, 300))
            if (target_string.length == 300) "$target_string..."
            else target_string }
        else "-"
    }

    fun getCategories(arr: JSONArray?): String {
        return if (arr != null) {
            var categories = ""
            for (i in 0 until arr.length() - 1) {
                val category = arr.get(i)
                categories += "$category, "
            }
            categories + arr[arr.length() - 1]
        } else "-"
    }
}