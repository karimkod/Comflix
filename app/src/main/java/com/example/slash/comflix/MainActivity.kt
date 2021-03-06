package com.example.slash.comflix

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.slash.comflix.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity :AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private var fragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        if(isOnline(this)) {
            fragment = HomeFragment()
            supportFragmentManager.beginTransaction()
                    .replace(conteneur_main.id, fragment)
                    .commit()
        }else{
            val intent= Intent(this,FavouriteMovieActivity::class.java)
            this.startActivity(intent)
        }
        return true
    }
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return  activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

       when (item.itemId) {

            R.id.nav_home -> {
                fragment= HomeFragment()
            }
            R.id.nav_movies -> {
                val intent= Intent(this,FavouriteMovieActivity::class.java)
                this.startActivity(intent)
            }

        }
        supportFragmentManager.beginTransaction()
                .replace(conteneur_main.id,fragment)
                .addToBackStack(null)
                .commit()
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
