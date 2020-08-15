package com.estebakos.breakingapp.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.estebakos.breakingapp.R
import com.estebakos.breakingapp.ui.fragment.CharacterDetailFragmentDirections
import com.estebakos.breakingapp.ui.fragment.CharactersListFragmentDirections
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel.CharacterView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val activityViewModel: ActivityViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_character_list))
        setupActionBarWithNavController(navController, appBarConfiguration)

        activityViewModel.currentViewLiveData.observe(this) {
            onViewChange(it)
        }
    }

    private fun onViewChange(navigationEntry: Triple<CharacterView, CharacterView, Any?>) {
        val (_, destination, params) = navigationEntry
        when (destination) {
            is CharacterView.CharacterListFragment -> showCharacterListFragment()
            is CharacterView.CharacterDetailFragment -> showCharacterDetailFragment(params)
        }
    }

    private fun showCharacterDetailFragment(params: Any?) {
        val character = params as CharacterItemUI
        val action =
            CharactersListFragmentDirections.actionNavigationCharacterListToNavigationCharacterDetail(
                character,
                character.name
            )
        navController.navigate(action)
    }

    private fun showCharacterListFragment() {
        navController.navigate(CharacterDetailFragmentDirections.actionNavigationCharacterDetailToNavigationCharacterList())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return true
    }
}