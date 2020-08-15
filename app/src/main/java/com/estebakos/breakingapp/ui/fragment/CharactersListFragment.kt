package com.estebakos.sunbelt.test.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.MergeAdapter
import com.estebakos.sunbelt.test.R
import com.estebakos.sunbelt.test.ui.adapter.AnimeRecyclerViewAdapter
import com.estebakos.sunbelt.test.ui.adapter.WelcomeRecyclerViewAdapter
import com.estebakos.sunbelt.test.ui.model.AnimeListUI
import com.estebakos.sunbelt.test.ui.model.WelcomeListUI
import com.estebakos.sunbelt.test.ui.viewmodel.AnimeViewModel
import com.estebakos.sunbelt.test.ui.viewmodel.AnimeViewModel.AnimeView
import com.estebakos.sunbelt.test.ui.viewmodel.AnimeViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_anime_list.*
import javax.inject.Inject

class AnimeListFragment : Fragment(R.layout.fragment_anime_list),
    WelcomeRecyclerViewAdapter.WelcomeListener {

    @Inject
    lateinit var animeViewModelFactory: AnimeViewModelFactory
    private lateinit var animeViewModel: AnimeViewModel
    private var welcomeAdapter: WelcomeRecyclerViewAdapter? = null
    private var animeAdapter: AnimeRecyclerViewAdapter? = null
    private var mergedAdapter: MergeAdapter? = null
    private var listUI = mutableListOf<AnimeListUI>()
    private var welcomeListUI = mutableListOf<WelcomeListUI>()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        animeViewModel = ViewModelProvider(
            requireActivity(), animeViewModelFactory
        ).get(AnimeViewModel::class.java)

        welcomeListUI = mutableListOf(
            WelcomeListUI(
                getString(R.string.welcome_title), getString(R.string.welcome_description)
            )
        )

        observeLiveData()
        animeViewModel.loadAnimeList()
    }

    override fun onResume() {
        super.onResume()
        if (animeAdapter != null && mergedAdapter != null && mergedAdapter!!.adapters.size == 1) {
            mergedAdapter?.addAdapter(1, animeAdapter!!)
        }
    }

    private fun observeLiveData() {
        animeViewModel.animeList.observe(requireActivity(), Observer(::onAnimeListReceived))
        animeViewModel.loading.observe(requireActivity(), Observer(::onLoadingStateReceived))
        animeViewModel.empty.observe(requireActivity(), Observer(::onEmptyReceived))
        animeViewModel.error.observe(requireActivity(), Observer(::onErrorReceived))
    }

    private fun onAnimeListReceived(list: List<AnimeListUI>) {
        listUI.clear()
        listUI.addAll(list)

        if (animeAdapter == null || mergedAdapter!!.adapters.size == 1) {
            animeAdapter = AnimeRecyclerViewAdapter(listUI) {
                animeViewModel.navigateTo(
                    AnimeView.AnimeListFragment,
                    AnimeView.AnimeDetailFragment,
                    it
                )
            }

            if (mergedAdapter != null && mergedAdapter!!.adapters.size <= 1) {
                mergedAdapter?.addAdapter(1, animeAdapter!!)
            }
        } else {
            animeAdapter!!.notifyDataSetChanged()
        }
    }

    private fun onLoadingStateReceived(isLoading: Boolean) {
        if (loadingLayout != null) {
            loadingLayout.apply {
                visibility = if (isLoading) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }

    private fun onErrorReceived(hasInternet: Boolean) {
        if(hasInternet) {
            Toast.makeText(requireContext(), getString(R.string.no_results), Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun onEmptyReceived(hasInternet: Boolean) {
        Toast.makeText(requireContext(), getString(R.string.no_results), Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_main_data.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        welcomeAdapter = WelcomeRecyclerViewAdapter(welcomeListUI, this)

        mergedAdapter = MergeAdapter(welcomeAdapter)

        rv_main_data.run {
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

            adapter = mergedAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onCardRemoved(item: WelcomeListUI) {
        val position = welcomeListUI.indexOf(item)
        welcomeListUI.remove(item)
        if (welcomeListUI.isEmpty()) {
            mergedAdapter?.removeAdapter(welcomeAdapter!!)
        } else {
            welcomeAdapter?.notifyItemRemoved(position)
        }
    }
}