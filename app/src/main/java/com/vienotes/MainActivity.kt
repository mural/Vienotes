package com.vienotes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.smartledge.vienotes.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FABBehavior {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_ListFragment_to_DetailFragment)
        }
    }

    //No menu options yet
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_update -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    override fun showFAB(show: Boolean) {
        fab.visibility = if (show) View.VISIBLE else View.GONE
    }
}

interface FABBehavior {
    fun showFAB(show: Boolean)
}