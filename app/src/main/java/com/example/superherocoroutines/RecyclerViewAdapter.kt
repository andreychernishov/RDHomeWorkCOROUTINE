package com.example.superherocoroutines


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.superherocoroutines.databinding.RcItemLayoutBinding

class RecyclerViewAdapter(var items:MutableList<JsData> = mutableListOf(), val callBack:(result: String) -> Unit, val listener: Listener): RecyclerView.Adapter<RecyclerViewAdapter.RCHolder>() {
    class RCHolder(item: View): RecyclerView.ViewHolder(item){
        private val binding = RcItemLayoutBinding.bind(item)
        fun bind(data: JsData,listener: Listener){
            binding.title.text = data.name
            binding.fullNameTv.text = data.biography.fullName
            binding.image
            itemView.setOnClickListener {
                listener.onClick(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RCHolder {
        val listItemViewHolder = LayoutInflater.from(parent.context).inflate(R.layout.rc_item_layout, parent, false)
        return RCHolder(listItemViewHolder)
    }
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: RCHolder, position: Int) {
        holder.bind(items[position],listener)
        val image = holder.itemView.findViewById<ImageView>(R.id.image)
        Glide.with(image)
            .load(items[position].images.sm)
            .into(image)


//        holder.title.text = items[position].name
//        holder.fullNameTv.text = items[position].biography.fullName
//        Glide.with(holder.imageView)
//            .load(items[position].images.sm)
//            .into(holder.imageView)

//        holder.itemView.setOnClickListener {
//            callBack(position.toString())
//        }
    }
    interface Listener{
        fun onClick(itemClicked: JsData)
    }
//    fun updateItemList(updatedList: MutableList<JsData>) {
//        items = updatedList
//        notifyDataSetChanged()
//    }
}


//class RecyclerViewViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
//
//    val title: TextView = itemView.findViewById(R.id.title)
//    val fullNameTv: TextView = itemView.findViewById(R.id.full_name_tv)
//    val imageView: ImageView = itemView.findViewById(R.id.image)

//}