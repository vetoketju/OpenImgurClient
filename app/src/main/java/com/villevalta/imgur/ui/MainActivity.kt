package com.villevalta.imgur.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.villevalta.imgur.R
import com.villevalta.imgur.utils.visibleOrGone
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  lateinit var navController: NavController
  var topLevelMenuItems: Set<Int>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    //setSupportActionBar(mainToolbar)

    // collect all items from menu for configuration
    topLevelMenuItems = nav_view.menu
      .iterator()
      .asSequence()
      .map { it.itemId }
      .toSet()

    navController = findNavController(R.id.nav_host_fragment)
    nav_view.setupWithNavController(navController)

    // All menuitems should be treated as top navigational items
    // this prevents toolbar up arrow for the items
    /*topLevelMenuItems?.let {
      mainToolbar.setupWithNavController(navController, AppBarConfiguration(it))
    }*/

    navController.addOnDestinationChangedListener { controller, destination, arguments ->
      // If id is found in the bottom menu, keep it visible.
      val topLevelVisible = topLevelMenuItems?.contains(destination.id) ?: false
      nav_view.visibleOrGone(topLevelVisible)
      //mainToolbar.visibleOrGone(topLevelVisible)
    }

  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }

}
