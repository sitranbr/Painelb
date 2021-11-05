package br.com.painelb.ui.main

import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import br.com.painelb.R
import br.com.painelb.base.ui.BaseActivity
import br.com.painelb.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    companion object {
        private val TOP_LEVEL_DESTINATIONS = setOf(R.id.home_fragment)
    }

    private val mNavController by lazy { findNavController(R.id.main_nav_host_fragment) }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        val appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS)
        toolbar.setupWithNavController(mNavController, appBarConfiguration)
    }
}
