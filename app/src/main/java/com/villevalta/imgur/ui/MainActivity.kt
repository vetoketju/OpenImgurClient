package com.villevalta.imgur.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.transaction
import com.google.android.material.navigation.NavigationView
import com.villevalta.imgur.R
import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Section
import com.villevalta.imgur.model.Sort
import com.villevalta.imgur.model.Window
import com.villevalta.imgur.ui.fragment.PostsRecyclerFragment
import com.villevalta.imgur.ui.fragment.TagsRecyclerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

  // “NOT AFFILIATED WITH OR APPROVED BY IMGUR”
  // https://help.imgur.com/hc/en-us/articles/202062878-Trademark-Use-Policy

  var activeViewId: Int? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setSupportActionBar(toolbar)

    val toggle = ActionBarDrawerToggle(
      this, drawer_layout, toolbar,
      R.string.navigation_drawer_open,
      R.string.navigation_drawer_close
    )
    setActiveView(R.id.nav_viral)
    drawer_layout.addDrawerListener(toggle)
    toggle.syncState()

    nav_view.setNavigationItemSelectedListener(this)

  }

  override fun onBackPressed() {
    val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    return when (item.itemId) {
      R.id.action_settings -> true
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    setActiveView(item.itemId)
    drawer_layout.closeDrawer(GravityCompat.START)
    return true
  }

  private fun setActiveView(@IdRes menuitemId: Int) {
    if (menuitemId == activeViewId) return
    activeViewId = menuitemId

    val fragment = when (menuitemId) {
      R.id.nav_viral -> PostsRecyclerFragment(
        ListFilter(
          Section.hot, // TODO: save users filter
          Sort.viral,
          Window.day,
          showViral = true,
          mature = false, // TODO get this from settings
          albumPreviews = true
        )
      )
      R.id.nav_tags -> TagsRecyclerFragment()
      else -> throw Exception("not found")
    }

    nav_view.setCheckedItem(menuitemId)

    supportFragmentManager.transaction {
      replace(R.id.content, fragment)
    }

  }

}
