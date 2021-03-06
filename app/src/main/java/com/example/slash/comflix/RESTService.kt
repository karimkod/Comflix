package com.example.slash.comflix

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import com.example.slash.comflix.adapter.*
import com.example.slash.comflix.entities.*
import com.example.slash.comflix.fragment.MovieDetailsFragment
import com.example.slash.comflix.fragment.PersonDetailsFragment
import com.example.slash.comflix.room.MovieDB
import com.example.slash.comflix.room.MovieEntity
import com.example.slash.comflix.room.MovieEntityDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

var num_page_movies=1
var num_page_popular_movies=1
var num_page_series=1
var num_page_persons=1
var popular:ArrayList<Person>?=null
var total_pages_now_palying=1
var total_pages_popular=1
var total_pages_series=1
var total_pages_person=1
fun calculateCardNum(context: Context):Int{
    val n=context.resources.displayMetrics
    val num=n.widthPixels/n.density
    return (num/160).toInt()

}
fun getListMoviesNowPlaying(movieAdapter: MovieAdapter,movieList: ArrayList<Movie>){
   if(num_page_movies <= total_pages_now_palying ) {
       RetrofitBuilder.movieApi.getMoviesNowPalying(num_page_movies)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(
                       { result ->
                           movieList.addAll(result.results)
                           movieAdapter.updateListMovie(movieList)
                           total_pages_now_palying = result.total_pages
                       },
                       { error -> Log.e("ERROR", error.message) }
               )
       num_page_movies++
   }
}
fun getListMoviesPopular(movieAdapter: MovieAdapter,movieList: ArrayList<Movie>){
if(num_page_popular_movies<= total_pages_popular) {
    RetrofitBuilder.movieApi.getPopularMovies(num_page_popular_movies)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { result ->
                        movieList.addAll(result.results)
                        movieAdapter.updateListMovie(movieList)
                        total_pages_popular = result.total_pages
                    },
                    { error -> Log.e("ERROR", error.message) }
            )
    num_page_popular_movies++
}
}
fun getListSeries(serieAdapter: SerieAdapter,serieList: ArrayList<Serie>){
if(num_page_series <= total_pages_series) {
    RetrofitBuilder.serieApi.getSeriesNowPalying(num_page_series)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { result ->
                        serieList.addAll(result.results)
                        serieAdapter.updateListSerie(serieList)
                        total_pages_series = result.total_page
                    },
                    { error -> Log.e("ERROR", error.message) }
            )
    num_page_movies++
}
}
fun getMovieDetails(movie_id:Int,fragment: MovieDetailsFragment){

    RetrofitBuilder.movieApi.getMovieDetails(movie_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { result -> fragment.onQueryResponse(result)
                    },
                    { error -> Log.e("ERROR", error.message) }
            )

}
fun getSimilarMovies(movie_id:Int, adapter: MovieAdapter){
    RetrofitBuilder.movieApi.getSimilarMovies(movie_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { result -> adapter.updateListMovie(result.results)
                    },
                    { error -> Log.e("ERROR", error.message) }
            )

}

fun getSimilarSeries(id:Int, adapter: SerieAdapter){
    RetrofitBuilder.serieApi.getSimilarSeries(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { result -> adapter.updateListSerie(result.results)
                    },
                    { error -> Log.e("ERROR", error.message) }
            )

}
fun getCastCrewSeries(id:Int, credistAdapter: PersonAdapter){
    RetrofitBuilder.serieApi.getCreditsSeries(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { result -> result.cast.addAll(result.crew)
                        credistAdapter.updateListPerson(result.cast)
                    },
                    { error -> Log.e("ERROR", error.message) }
            )


}

fun getCastCrew(movie_id:Int, credistAdapter: PersonAdapter){
    RetrofitBuilder.movieApi.getCredits(movie_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { result -> result.cast.addAll(result.crew)
                        credistAdapter.updateListPerson(result.cast)
                    },
                    { error -> Log.e("ERROR", error.message) }
            )


}
fun getPersonDetails(person_id:Int, personDetailsFragment: PersonDetailsFragment){
    RetrofitBuilder.personApi.getPersonDetails(person_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { result -> personDetailsFragment.onQueryResponse(result)
                    },
                    { error -> Log.e("ERROR", error.message) }
            )

}
fun getPopularPerson( personList:ArrayList<Person>,personAdapter: PersonAdapter){
  if(num_page_persons <= total_pages_person) {
      RetrofitBuilder.personApi.getPopularPersons(num_page_persons)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(
                      { result ->
                          personList.addAll(result.results)
                          personAdapter.updateListPerson(personList)
                          popular = result.results
                          total_pages_person=result.total_pages
                      },
                      { error -> Log.e("ERROR", error.message) }
              )
      num_page_persons++
  }

}


fun chargerScoll(recyclerView: RecyclerView, layoutManager: GridLayoutManager, loadDataFun:()->Unit):Boolean{
    var charger=false
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        var pastVisiblesItems: Int = 0
        var visibleItemCount: Int = 0
        var totalItemCount: Int = 0

        override fun onScrolled(recyclerView: RecyclerView?,
                                dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            visibleItemCount = layoutManager.childCount
            totalItemCount = layoutManager.itemCount
            pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

            if (visibleItemCount + pastVisiblesItems >= totalItemCount) charger=true

            if(charger)
                loadDataFun()
        }
    })
    return  charger

}

