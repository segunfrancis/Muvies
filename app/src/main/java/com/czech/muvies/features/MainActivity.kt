package com.czech.muvies.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.czech.muvies.R
import com.czech.muvies.databinding.ActivityMainBinding
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.czech.muvies.utils.showMessage
import kotlinx.coroutines.*

@Deprecated("use constants from AppConstants class")
const val LANGUAGE = "en-US"

class MainActivity : AppCompatActivity() {

    private var backPressedOnce = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)
        val appBarConfig = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.moviesFragment,
                R.id.tvShowsFragment,
                R.id.favoritesFragment
            )
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.moviesFragment,
                R.id.tvShowsFragment,
                R.id.favoritesFragment -> showBottomNavigation()
                else -> hideBottomNavigation()
            }
        }
        binding.bottomNav.setOnNavigationItemReselectedListener { }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showBottomNavigation() {
        binding.bottomNav.makeVisible()
    }

    private fun hideBottomNavigation() {
        binding.bottomNav.makeGone()
    }

    override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination?.id) {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }
            backPressedOnce = true
            binding.root.showMessage("Press BACK again to exit")
            lifecycleScope.launch {
                delay(2000)
                backPressedOnce = false
            }
        } else {
            super.onBackPressed()
        }
    }
}
