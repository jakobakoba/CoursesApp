package com.bor96dev.coursesapp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.nav_item_color)
        bottomNavigationView.itemTextColor = ContextCompat.getColorStateList(this, R.color.nav_item_color)

        val rootView = findViewById<ViewGroup>(android.R.id.content)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.updatePadding(
                top = systemBars.top,
                bottom = systemBars.bottom
            )

            val navHostFragment = findViewById<View>(R.id.nav_host_fragment)
            navHostFragment?.updatePadding(
                bottom = bottomNavigationView.measuredHeight
            )

            ViewCompat.setOnApplyWindowInsetsListener(view, null)

            insets
        }

        ViewCompat.requestApplyInsets(rootView)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                com.bor96dev.presentation.R.id.loginFragment -> {
                    bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }
}