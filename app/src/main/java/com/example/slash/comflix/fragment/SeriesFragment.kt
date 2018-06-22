package com.example.slash.comflix.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import com.example.slash.comflix.*
import com.example.slash.comflix.R.id.add
import com.example.slash.comflix.entities.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_series.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.slash.comflix.R.id.progressbar
import com.example.slash.comflix.adapter.SerieAdapter
import com.example.slash.comflix.entities.GridSpacingItemDecoration
import com.example.slash.comflix.entities.Serie
import com.example.slash.comflix.entities.dpToPx
import kotlinx.android.synthetic.*


class SeriesFragment : Fragment() {


    // TODO: Rename and change types of parameters
    var listSerie:ArrayList<Serie>?=null
    var adapter:SerieAdapter?=null
    private var mListener: OnFragmentInteractionListener? = null
    val progressBar:ProgressBar by lazy { view?.findViewById<ProgressBar>(R.id.progressbar)!! }

    var currentPage = 1

    var isLoading:Boolean = false

    fun loadData()
    {
        if(isLoading)
            return
        isLoading = true
        RetrofitBuilder.serieApi.getPopluareSeries(currentPage).enqueue(object : Callback<PopularSerieDTO>
        {
            override fun onFailure(call: Call<PopularSerieDTO>?, t: Throwable?)
            {
                isLoading = false
                Toast.makeText(activity,"Problem",Toast.LENGTH_LONG).show()
                Log.d("SeriesFragment",t?.message)
            }

            override fun onResponse(call: Call<PopularSerieDTO>?, response: Response<PopularSerieDTO>?)
            {
                //Toast.makeText(activity,"Response",Toast.LENGTH_LONG).show()
                isLoading = false

                progressBar.visibility = ProgressBar.GONE
                if(response?.isSuccessful!!)
                {
                    currentPage++
                    Toast.makeText(activity,"Success",Toast.LENGTH_LONG).show()

                    listSerie = response.body()?.results
                    adapter?.serieList = listSerie?.toList()!!
                    adapter?.notifyDataSetChanged()
                }else
                {
                    //Toast.makeText(activity,response.code().toString() +"   "+response.errorBody() ,Toast.LENGTH_LONG).show()
                    Log.d("SeriesFragment",response.code().toString() +"   "+response.errorBody())
                }
            }

        })
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view= inflater!!.inflate(R.layout.fragment_series, container, false)
        var serieList=ArrayList<Serie>()
        var recyclerView=view.findViewById<RecyclerView>(R.id.recyclerView) as RecyclerView
        var serieAdapter= SerieAdapter(this.context,serieList,R.layout.serie_card)
        var mLayoutManager= GridLayoutManager(this.context, calculateCardNum(this.context))
        recyclerView.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10),true))
        recyclerView.layoutManager=mLayoutManager
        recyclerView.itemAnimator= DefaultItemAnimator()
        recyclerView.adapter=serieAdapter
        //prepareSeries(this.context,serieList,serieAdapter)
        //listSerie=serieList
        adapter=serieAdapter
        loadData()

        return view
    }


    fun addData()
    {
        if(isLoading)
            return
        isLoading = true
        progressBar.visibility = ProgressBar.VISIBLE
        RetrofitBuilder.serieApi.getPopluareSeries(currentPage).enqueue(object : Callback<PopularSerieDTO>
        {
            override fun onFailure(call: Call<PopularSerieDTO>?, t: Throwable?)
            {
                isLoading = false

                Toast.makeText(activity,"Problem",Toast.LENGTH_LONG).show()
                Log.d("SeriesFragment",t?.message)
            }

            override fun onResponse(call: Call<PopularSerieDTO>?, response: Response<PopularSerieDTO>?)
            {
                //Toast.makeText(activity,"Response",Toast.LENGTH_LONG).show()
                isLoading = false

                progressBar.visibility = ProgressBar.GONE
                if(response?.isSuccessful!!)
                {
                    currentPage++
                    Toast.makeText(activity,"Success",Toast.LENGTH_LONG).show()

                    listSerie = response.body()?.results

                    adapter?.serieList = adapter?.serieList!! + listSerie?.toList()!!
                    adapter?.notifyDataSetChanged()
                }else
                {
                    //Toast.makeText(activity,response.code().toString() +"   "+response.errorBody() ,Toast.LENGTH_LONG).show()
                    Log.d("SeriesFragment",response.code().toString() +"   "+response.errorBody())
                }
            }

        })
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
         //   throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        fun newInstance(param1: String, param2: String): SeriesFragment {
            val fragment = SeriesFragment()
            return fragment
        }
    }

}// Required empty public constructor
