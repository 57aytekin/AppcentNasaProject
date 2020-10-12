package com.example.appcentnasaproject.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.appcentnasaproject.R
import com.example.appcentnasaproject.data.entities.NasaData
import com.example.appcentnasaproject.util.ItemClickListener
import kotlinx.android.synthetic.main.nasa_data_row_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NasaDataAdapter(
    private var rovers : List<NasaData>,
    private val context : Context,
    private val itemClick : ItemClickListener
) : RecyclerView.Adapter<NasaDataAdapter.RoversViewHolder>() {

    inner class RoversViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoversViewHolder {
        return RoversViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.nasa_data_row_item, parent, false)
        )
    }

    override fun getItemCount() = rovers.size

    override fun onBindViewHolder(holder: RoversViewHolder, position: Int) {
        val nasaRover = rovers[position]
        Glide.with(context).load(nasaRover.nasaDataImage).apply(RequestOptions().override(800)).into(holder.itemView.ivPhoto)
        holder.itemView.tvCameraFullName.text = nasaRover.nasaCamera!!.full_name
        holder.itemView.card_view.setOnClickListener {
            itemClick.onItemClickListener(nasaRover)
        }
    }

    fun addData( data : List<NasaData>){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                for (newValue in data){
                    if (!rovers.contains(newValue)){
                        rovers += newValue
                    }
                }
            }catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}