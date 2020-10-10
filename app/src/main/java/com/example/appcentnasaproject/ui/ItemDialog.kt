package com.example.appcentnasaproject.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.bumptech.glide.Glide
import com.example.appcentnasaproject.R
import com.example.appcentnasaproject.data.entities.NasaData
import kotlinx.android.synthetic.main.item_pop_up.*

class ItemDialog(
    context : Context,
    private val item : NasaData
) : Dialog(context){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.item_pop_up)

        Glide.with(context).load(item.nasaDataImage).into(ivPopUpImage)
        tvPopupDate.text = item.earth_date
        tvPopupRoverName.text = item.rover!!.roverName
        tvPopupCamera.text = item.camera!!.full_name
        tvPopupRoverStatus.text = item.rover.status
        tvPopupLandingDate.text = item.rover.landing_date
        tvPopupLaunchDate.text = item.rover.launch_date
    }
}