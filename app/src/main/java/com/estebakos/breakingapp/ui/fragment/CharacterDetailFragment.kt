package com.estebakos.breakingapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.estebakos.breakingapp.R
import com.estebakos.breakingapp.base.UIState
import com.estebakos.breakingapp.ui.adapter.CharacterDetailController
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import com.estebakos.breakingapp.ui.state.CharactersUIState
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel
import com.estebakos.breakingapp.ui.viewmodel.CharactersViewModel
import com.estebakos.breakingapp.ui.viewmodel.CharactersViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_character_detail.*
import kotlinx.android.synthetic.main.fragment_character_list.*
import javax.inject.Inject


@AndroidEntryPoint
class CharactersDetailFragment : Fragment(R.layout.fragment_character_list),
    CharacterDetailController.CharacterDetailListener {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private lateinit var charactersViewModel: CharactersViewModel
    private val args: CharacterDetailFragmentArgs by navArgs()

    @Inject
    lateinit var charactersViewModelFactory: CharactersViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        val controller = CharacterDetailController(this)
        recycler_character_detail.adapter = controller.adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        charactersViewModel =
            ViewModelProvider(this, charactersViewModelFactory).get(CharactersViewModel::class.java)

        charactersViewModel.uiStateLiveData.observe(
            requireActivity(),
            Observer(::onUIChange)
        )

        charactersViewModel.loadFavorites()
        charactersViewModel.loadCharacterList()
    }

    private fun onUIChange(uiState: UIState<CharactersUIState>) {
        when (uiState) {
            is UIState.Loading -> loading_layout.visibility = View.VISIBLE
            is UIState.Error -> {
                loading_layout.visibility = View.GONE
                showError(uiState.message)
            }
        }
    }

    private fun showError(@StringRes message: Int) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.snack_bar_close)) { }
            .setActionTextColor(requireContext().getColor(R.color.colorAccent))
            .show()
    }

    override fun onCharacterFavorite(character: CharacterItemUI) {
        charactersViewModel.favoriteCharacter(character)
    }

}