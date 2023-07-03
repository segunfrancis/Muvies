package com.czech.muvies.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.czech.muvies.R
import com.czech.muvies.databinding.ActivityMainBinding
import com.czech.muvies.utils.makeGone
import com.czech.muvies.utils.makeVisible
import com.czech.muvies.utils.showMessage
import com.segunfrancis.muvies.common.viewBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private var backPressedOnce = false
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.customToolbar)

        binding.bottomNav.setupWithNavController(navController)
        val appBarConfig = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.moviesFragment,
                R.id.tvShowsFragment,
                R.id.favoritesFragment,
                R.id.searchFragment
            )
        )
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig)

        navController.addOnDestinationChangedListener { _, destination, args ->
            when (destination.id) {
                R.id.moviesFragment,
                R.id.tvShowsFragment,
                R.id.searchFragment,
                R.id.favoritesFragment -> {
                    showBottomNavigation()
                    setToolbarTitle(getString(R.string.app_name))
                }
                R.id.castDetailsFragment -> {
                    hideBottomNavigation()
                    setToolbarTitle("${args?.get("castName")}")
                }
                R.id.detailsFragment -> {
                    hideBottomNavigation()
                    setToolbarTitle("${args?.get("movieTitle")}")
                }
                R.id.tvShowsDetailsFragment -> {
                    hideBottomNavigation()
                    setToolbarTitle("${args?.get("tvShowTitle")}")
                }
                R.id.allFragment -> {
                    setToolbarTitle("${args?.get("movie_title")} movies")
                    hideBottomNavigation()
                }
                else -> {
                    hideBottomNavigation()
                }
            }
        }
        binding.bottomNav.setOnItemReselectedListener { }

        /*setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, route = "/", startDestination = "") {

            }
        }*/
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

    private fun setToolbarTitle(title: String) {
        binding.allTitle.text = title
    }

    override fun onBackPressed() {
        if (navController.currentDestination?.id == navController.graph.getStartDestination()) {
            if (backPressedOnce) {
                super.onBackPressed()
                return
            }
            backPressedOnce = true
            binding.root.showMessage("Press BACK again to exit")
            runBlocking {
                delay(2000)
                backPressedOnce = false
            }
        } else {
            super.onBackPressed()
        }
    }
}
