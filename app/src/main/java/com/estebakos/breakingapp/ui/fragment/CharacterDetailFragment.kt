package com.estebakos.breakingapp.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.estebakos.breakingapp.R
import com.estebakos.breakingapp.base.UIState
import com.estebakos.breakingapp.ui.adapter.CharacterDetailController
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import com.estebakos.breakingapp.ui.state.CharacterDetailUIState
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel.CharacterView
import com.estebakos.breakingapp.ui.viewmodel.CharacterDetailViewModel
import com.estebakos.breakingapp.ui.viewmodel.CharacterDetailViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_character_detail.*
import javax.inject.Inject


@AndroidEntryPoint
class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail),
    CharacterDetailController.CharacterDetailListener {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private lateinit var characterDetailViewModel: CharacterDetailViewModel
    private val args: CharacterDetailFragmentArgs by navArgs()
    private lateinit var controller: CharacterDetailController
    private var favorite = false

    @Inject
    lateinit var characterDetailsViewModelFactory: CharacterDetailViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goToCharacterListFragment()
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        characterDetailViewModel =
            ViewModelProvider(
                this,
                characterDetailsViewModelFactory
            ).get(CharacterDetailViewModel::class.java)

        characterDetailViewModel.uiStateLiveData.observe(
            requireActivity(),
            Observer(::onUIChange)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        controller = CharacterDetailController(this)
        recycler_character_detail.adapter = controller.adapter
        recycler_character_detail.layoutManager = LinearLayoutManager(requireContext())
        favorite = args.character.favorite
        controller.setData(args.character, favorite)
    }

    private fun onUIChange(uiState: UIState<CharacterDetailUIState>) {
        when (uiState) {
            is UIState.Loading -> loading_detail_layout.visibility = View.VISIBLE
            is UIState.Error -> {
                showError(uiState.message)
            }
            is UIState.Data -> {
                Toast.makeText(
                    requireContext(),
                    (uiState.data as CharacterDetailUIState.CharactersLoadedState).message,
                    Toast.LENGTH_SHORT
                ).show()

                favorite = !favorite
                controller.setData(args.character, favorite)
            }
        }

        if (uiState !is UIState.Loading) {
            loading_detail_layout.visibility = View.GONE
        }
    }

    private fun showError(@StringRes message: Int) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.snack_bar_close)) { }
            .setActionTextColor(requireContext().getColor(R.color.colorAccent))
            .show()
    }

    private fun goToCharacterListFragment() {
        activityViewModel.navigateTo(
            originView = CharacterView.CharacterDetailFragment,
            destinationView = CharacterView.CharacterListFragment
        )
    }

    override fun onCharacterFavorite(character: CharacterItemUI) {
        characterDetailViewModel.favoriteCharacter(character, favorite)
    }

}