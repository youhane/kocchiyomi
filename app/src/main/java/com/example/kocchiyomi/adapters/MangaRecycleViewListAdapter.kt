package com.example.kocchiyomi.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kocchiyomi.R


class RecycleViewAdapter : RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private var foodList: List<Food>? = null
    private var context: Context? = null

    constructor(foodList: List<Food>?, context: Context?) {
        this.foodList = foodList
        this.context = context
    }

    constructor(foodList: List<Food?>?) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.one_line_food, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tv_foodName.setText(foodList!![position].getName())
        Glide.with(context).load(foodList!![position].getImageURL()).into(holder.iv_foodPicture)
        holder.parentLayout.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("id", foodList!![holder.adapterPosition].getId())
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return foodList!!.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_foodPicture: ImageView
        var tv_foodName: TextView
        var parentLayout: RelativeLayout

        init {
            iv_foodPicture = itemView.findViewById(R.id.iv_foodPicture)
            tv_foodName = itemView.findViewById(R.id.tv_foodName)
            parentLayout = itemView.findViewById(R.id.oneLineFoodLayout)
        }
    }
}