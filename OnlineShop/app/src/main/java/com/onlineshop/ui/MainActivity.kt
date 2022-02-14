package com.onlineshop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.onlineshop.R
import com.onlineshop.databinding.ActivityMainBinding
import com.onlineshop.utils.setupWithNavController

/**
 * Base activity of application
 */
class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupBottomNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    /**
     * Configures the bottom navigation bar
     */
    private fun setupBottomNavigation() {
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
//        val navController = navHostFragment.navController
//        val bottomNav = binding.bottomNavigation
//
//        bottomNav.setupWithNavController(navController)
        val bottomNavigationView = binding.bottomNavigation

        val navGraphIds = listOf(
            R.navigation.products_navigation,
            R.navigation.cart_navigation,
            R.navigation.history_navigation,
            R.navigation.login_navigation
        )
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = binding.navHostFragmentContainer.id,
            intent = intent
        )
        currentNavController = controller
    }
}
