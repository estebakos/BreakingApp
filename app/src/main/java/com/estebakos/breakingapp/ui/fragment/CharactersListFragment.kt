package com.estebakos.breakingapp.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.estebakos.breakingapp.R
import com.estebakos.breakingapp.base.Constants
import com.estebakos.breakingapp.base.UIState
import com.estebakos.breakingapp.ui.adapter.CharactersController
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import com.estebakos.breakingapp.ui.state.CharactersUIState
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel.CharacterView.CharacterDetailFragment
import com.estebakos.breakingapp.ui.viewmodel.ActivityViewModel.CharacterView.CharacterListFragment
import com.estebakos.breakingapp.ui.viewmodel.CharactersViewModel
import com.estebakos.breakingapp.ui.viewmodel.CharactersViewModelFactory
import com.estebakos.breakingapp.util.EndlessRecyclerViewScrollListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_character_list.*
import kotlinx.android.synthetic.main.layout_empty.*
import javax.inject.Inject


@AndroidEntryPoint
class CharactersListFragment : Fragment(R.layout.fragment_character_list),
    CharactersController.CharacterListener {

    private val activityViewModel: ActivityViewModel by activityViewModels()
    private lateinit var charactersViewModel: CharactersViewModel
    private lateinit var controller: CharactersController
    private lateinit var loadMoreScrollListener: EndlessRecyclerViewScrollListener
    private var characters: MutableList<CharacterItemUI> = mutableListOf()

    @Inject
    lateinit var charactersViewModelFactory: CharactersViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller = CharactersController(this)

        val layoutManager = LinearLayoutManager(requireContext())

        loadMoreScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int) {
                if (controller.hasMoreToLoad) {
                    charactersViewModel.loadCharacterList()
                }
            }
        }

        recycler_characters.layoutManager = layoutManager
        recycler_characters.addOnScrollListener(loadMoreScrollListener)
        recycler_characters.adapter = controller.adapter

        button_retry.setOnClickListener {
            charactersViewModel.loadFavorites()
            charactersViewModel.loadCharacterList()
        }
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
            is UIState.Error -> showError(uiState.message)
            is UIState.Empty -> {
                empty_layout.visibility = View.VISIBLE
            }
            is UIState.Data -> onDataReceived(uiState.data)
        }

        if (uiState !is UIState.Loading) {
            loading_layout.visibility = View.GONE
        }

        if (uiState !is UIState.Empty) {
            empty_layout.visibility = View.GONE
        }
    }

    private fun onDataReceived(uiState: CharactersUIState) {
        when (uiState) {
            is CharactersUIState.CharactersLoadedState -> onCharacterListReceived(uiState.characters)
            is CharactersUIState.FavoritesLoadedState -> onFavoriteListReceived(uiState.favorites)
            is CharactersUIState.CharactersResetState -> setCharactersData(uiState.characters)
        }
    }

    private fun showError(@StringRes message: Int) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
            .setAction(
                getString(
                    R.string.snack_bar_close
                )
            ) {
                charactersViewModel.loadCharacterList()
                charactersViewModel.loadFavorites()
            }
            .setActionTextColor(requireContext().getColor(R.color.colorAccent))
            .show()
    }

    private fun onCharacterListReceived(list: List<CharacterItemUI>) {
        if (characters.isEmpty()) {
            setCharactersData(list)
        } else {
            controller.addData(list.toMutableList())
            characters.addAll(list)
        }

        controller.hasMoreToLoad = charactersViewModel.hasMoreData()
    }

    private fun setCharactersData(list: List<CharacterItemUI>) {
        controller.setData(list.toMutableList())
        characters = list.toMutableList()
    }

    private fun onFavoriteListReceived(list: List<CharacterItemUI>) {
        recycler_characters.scrollToPosition(0)
        controller.setFavorites(list.toMutableList())
    }

    override fun onCharacterSelected(character: CharacterItemUI) {
        activityViewModel.navigateTo(
            CharacterListFragment,
            CharacterDetailFragment,
            character
        )
    }

    override fun onCharacterFavorite(character: CharacterItemUI) {
        charactersViewModel.favoriteCharacter(character)
    }

}