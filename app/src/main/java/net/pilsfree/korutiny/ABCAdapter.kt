package net.pilsfree.korutiny

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ABCAdapter(var list: List<Pair<String, String>>, val onClick: (String) -> Unit) : RecyclerView.Adapter<ABCAdapter.ItemViewHolder>(){
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article,parent,false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val layout = holder.itemView
        val label = layout.findViewById<TextView>(R.id.label)
        label.text = list[position].first
        label.setOnClickListener {
            onClick(list[position].second)
        }
    }
}