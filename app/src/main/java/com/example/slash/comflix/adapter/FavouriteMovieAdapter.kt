package com.example.slash.comflix.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.slash.comflix.R
import com.example.slash.comflix.entities.Movie


class FavouriteMovieAdapter : RecyclerView.Adapter<FavouriteMovieAdapter.MyViewHolder> {
    var mcontext: Context
    var movieList: ArrayList<Movie>
    var layout: Int

    constructor(mcontext: Context, movieList: ArrayList<Movie>, layout: Int) : super() {
        this.mcontext = mcontext
        this.movieList = movieList
        this.layout = layout
    }


    inner class MyViewHolder : RecyclerView.ViewHolder {
        var title: TextView
        var time: TextView
        var thumbnail: ImageView
        var movieId:TextView
        var delete:ImageView
        constructor(itemView: View) : super(itemView) {
            this.title = itemView.findViewById<TextView>(R.id.title) as TextView
            this.time = itemView.findViewById<TextView>(R.id.time) as TextView
            this.thumbnail = itemView.findViewById<ImageView>(R.id.cover) as ImageView
            this.movieId = itemView.findViewById<TextView>(R.id.movieId) as TextView
            this.delete= itemView.findViewById<ImageView>(R.id.delete) as ImageView

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (!movieList.isEmpty()) {
            var movie = movieList.get(position)
            holder.title.text = movie.title
            holder.time.text = movie.time
            holder.movieId.text = movie.movieId.toString()
            Glide.with(mcontext).load(movie.cover).into(holder.thumbnail)

        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        var itemView = LayoutInflater.from(parent!!.context)
                .inflate(layout, parent, false)
        val holder = MyViewHolder(itemView)
        holder.delete.setOnClickListener{

            val id:Int=holder.movieId.text.toString().toInt()
            if(movieList.size!=1) {
                movieList.removeAt(id)

            }else{
                movieList= ArrayList()
            }
            notifyDataSetChanged()
        }
        return MyViewHolder(itemView)

    }
}