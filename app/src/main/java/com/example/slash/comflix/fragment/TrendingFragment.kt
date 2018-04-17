package com.example.slash.comflix.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.slash.comflix.R
import com.example.slash.comflix.adapter.MovieAdapter
import com.example.slash.comflix.adapter.SerieAdapter
import com.example.slash.comflix.entities.*


class TrendingFragment : Fragment() {


    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_trending, container, false)
        var trendingMoviesList=ArrayList<Movie>()
        var trendingseriesList=ArrayList<Serie>()
        var movieRecyclerView=view.findViewById<RecyclerView>(R.id.moviesRecyclerView) as RecyclerView
        var serieRecyclerView=view.findViewById<RecyclerView>(R.id.seriesRecyclerView) as RecyclerView
        var movieAdapter= MovieAdapter(this.context,trendingMoviesList,R.layout.trending_movie_card)
        var serieAdapter= SerieAdapter(this.context,trendingseriesList,R.layout.trending_serie_card)
        var movieLayoutManager: RecyclerView.LayoutManager= GridLayoutManager(this.context,1,GridLayoutManager.HORIZONTAL,false)
        var serieLayoutManager: RecyclerView.LayoutManager= GridLayoutManager(this.context,1,GridLayoutManager.HORIZONTAL,false)

        movieRecyclerView.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10),true))
        serieRecyclerView.addItemDecoration(GridSpacingItemDecoration(2, dpToPx(10),true))

        movieRecyclerView.layoutManager=movieLayoutManager
        movieRecyclerView.itemAnimator= DefaultItemAnimator()
        movieRecyclerView.adapter=movieAdapter
        serieRecyclerView.layoutManager=serieLayoutManager
        serieRecyclerView.itemAnimator= DefaultItemAnimator()
        serieRecyclerView.adapter=serieAdapter
        prepareTrending(trendingMoviesList,movieAdapter,trendingseriesList,serieAdapter)

        return view
    }
    fun prepareTrending(movieTrendingList:ArrayList<Movie>, movieTrendingAdapter: MovieAdapter,serieTrendingList:ArrayList<Serie>, serieTrendingAdapter: SerieAdapter){

        var movieCovers= intArrayOf(
                R.drawable.divergent,
                R.drawable.hungergamescatchingfire,
                R.drawable.mazerunner,
                R.drawable.pirateofthecar,
                R.drawable.themazerunnerdeathcure,
                R.drawable.themazerunnerscorch

        )
        var serieCovers= intArrayOf(
                R.drawable.houseofcards,
                R.drawable.gameofthrones,
                R.drawable.friends,
                R.drawable.suits,
                R.drawable.vikings,
                R.drawable.breakingbad,
                R.drawable.lacasadepapel,
                R.drawable.prisonbreak
        )
        var movieTitles=resources.getStringArray(R.array.movieTitles)
        var movieCinema= resources.getStringArray(R.array.movieCinema)
        var serieTitles= resources.getStringArray(R.array.serieTitles)
        var serieSeasons=resources.getIntArray(R.array.serieSeasons)
        var serieEpisodes=resources.getIntArray(R.array.serieEpisodes)
        for (i in 0 until movieCovers.size){
            var movie=Movie(movieTitles.get(i),movieCovers.get(i),movieCinema.get(i))
            movieTrendingList.add(movie)
        }
        for (i in 0 until serieCovers.size){
            var serie=Serie(serieTitles.get(i),serieCovers.get(i),serieSeasons.get(i),serieEpisodes.get(i))
            serieTrendingList.add(serie)
        }
        movieTrendingAdapter.notifyDataSetChanged()
        serieTrendingAdapter.notifyDataSetChanged()
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
           // throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TrendingFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): TrendingFragment {
            val fragment = TrendingFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
