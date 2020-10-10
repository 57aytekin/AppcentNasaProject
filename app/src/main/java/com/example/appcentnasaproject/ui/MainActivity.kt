package com.example.appcentnasaproject.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentnasaproject.R
import com.example.appcentnasaproject.data.adapter.NasaDataAdapter
import com.example.appcentnasaproject.data.api.NasaApi
import com.example.appcentnasaproject.data.entities.NasaData
import com.example.appcentnasaproject.data.response.NasaDataResponse
import com.example.appcentnasaproject.repository.NasaDataRepository
import com.example.appcentnasaproject.util.*
import com.example.appcentnasaproject.viewmodel.NasaDataViewModel
import com.example.appcentnasaproject.viewmodel.NasaDataViewModelFactory
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {
    private val viewModel: NasaDataViewModel by viewModels()
    private lateinit var adapter : NasaDataAdapter
    private lateinit var dialog : ItemDialog

    //pagination items
    private var pastVisibleItems : Int? = null
    private var visibleItemCount : Int? = null
    private var totalItemCount : Int? = null
    private var previouesTotal = 0
    private var viewThreshold = 10
    private var pageNumber = 1

    private var roverName : String? = null
    private var cameraName : String? = null

    private var layoutManager : LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roverName = "Curiosity"
        cameraName = SortType.All.name
        layoutManager = LinearLayoutManager(this)
        adapter = NasaDataAdapter(listOf(), this, this)

        getNasaData()
        selectedSpinner()
        tabSelected()

        rvRoversPhoto.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager!!.childCount
                totalItemCount = layoutManager!!.itemCount
                pastVisibleItems = layoutManager!!.findFirstVisibleItemPosition()
                if (dy > 0){
                    if (totalItemCount!! - visibleItemCount!! <= pastVisibleItems!! + viewThreshold){
                        if (totalItemCount!! - 24*pageNumber >= 1){
                            tvAlert.visibility = View.GONE
                            pageNumber++
                            performPagination(roverName!!, pageNumber, cameraName!!)
                        }else{
                            if ((totalItemCount!! - (visibleItemCount!! + pastVisibleItems!!)) == 0 ){
                                tvAlert.visibility = View.VISIBLE
                                tvAlert.text = "End of page!"
                            }
                        }
                    }
                    if (totalItemCount!! > previouesTotal){
                        previouesTotal = totalItemCount as Int
                    }
                }else{
                    tvAlert.visibility = View.GONE
                }
            }
        })
    }

    private fun tabSelected() {
        tabLayoutRover.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
                Log.d("RESELECT","RESELECT")
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                Log.d("UNSELECT","UNSELECT")
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tvAlert.visibility = View.GONE
                when(tab?.position){
                    0 ->{
                        progressMain.visibility = View.VISIBLE
                        roverName = "Curiosity"
                        getNasaData()
                    }
                    1 -> {
                        progressMain.visibility = View.VISIBLE
                        roverName = "opportunity"
                        getNasaData()
                    }
                    2 -> {
                        progressMain.visibility = View.VISIBLE
                        roverName = "Spirit"
                        getNasaData()
                    }
                }
            }

        })
    }

    private fun performPagination(roverName: String, pageNumber: Int, cameraName: String) {
        Coroutines.main {
            try {
                val data : NasaDataResponse = if (cameraName == SortType.All.name){
                    viewModel.getAllCamera(roverName, pageNumber)
                }else{
                    viewModel.getNasaData(roverName, pageNumber, cameraName)
                }

                if (data.photoList.isNotEmpty()){
                    rvRoversPhoto.apply {
                        (adapter as? NasaDataAdapter)?.addData(data.photoList)
                        adapter!!.notifyDataSetChanged()
                        rvRoversPhoto.layoutManager = layoutManager
                    }
                }
            }catch (e : NoInternetException){
                Log.d("HATAA", e.message!!)
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }catch (e : Exception){
                Log.d("HATA",e.message!!)
            }
        }
    }

    private fun selectedSpinner() {
        cameraSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, v: View?, p: Int, id: Long) {
                when(p){
                    0 ->{ cameraName = SortType.All.name }
                    1 ->{ cameraName = SortType.FHAZ.name }
                    2 ->{ cameraName = SortType.RHAZ.name }
                    3 ->{ cameraName = SortType.MAST.name }
                    4 ->{ cameraName = SortType.CHEMCAM.name }
                    5 ->{ cameraName = SortType.MAHLI.name }
                    6 ->{ cameraName = SortType.MARDI.name }
                    7 ->{ cameraName= SortType.NAVCAM.name }
                    8 ->{ cameraName = SortType.PANCAM.name }
                    9 ->{ cameraName = SortType.MINITES.name }
                }
                getNasaData()
                tvAlert.visibility = View.GONE
            }
        }
    }

    private fun getNasaData() {
        progressMain.visibility = View.VISIBLE
        pageNumber = 1
        Coroutines.main {
            try{
                val data : NasaDataResponse = if (cameraName == SortType.All.name){
                    viewModel.getAllCamera(roverName!!, pageNumber)
                }else{
                    viewModel.getNasaData(roverName!!, pageNumber, cameraName!!)
                }
                rvRoversPhoto.also {
                    progressMain.visibility = View.GONE
                    adapter = NasaDataAdapter(data.photoList,this, this)
                    it.adapter = adapter
                    adapter.notifyDataSetChanged()
                    it.layoutManager = layoutManager
                    it.setHasFixedSize(true)
                }
            }catch (e : NoInternetException){
                progressMain.visibility = View.GONE
                Toast.makeText(this, "İnternet bağlantısı bulunamadı", Toast.LENGTH_SHORT).show()
            }catch (e :Exception){
                progressMain.visibility = View.GONE
                Toast.makeText(this, ""+e.printStackTrace(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onItemClickListener(nasaData: NasaData) {
        dialog = ItemDialog(this, nasaData)
        dialog.show()
    }
}