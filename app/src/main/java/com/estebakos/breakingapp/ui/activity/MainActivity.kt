package com.estebakos.breakingapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel.AnimeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val activityViewModel: ActivityViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_anime_list))
        setupActionBarWithNavController(navController, appBarConfiguration)

        activityViewModel.currentViewLiveData.observe(this) {
            onViewChange(it)
        }
    }

    private fun onViewChange(navigationEntry: Triple<AnimeView, AnimeView, Any?>) {

    }
}