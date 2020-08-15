package com.estebakos.sunbelt.test.ui.viewmodel

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebakos.sunbelt.test.base.NavigationProvider
import com.estebakos.sunbelt.test.base.Output
import com.estebakos.sunbelt.test.domain.usecase.GetAnimeByIdUseCase
import com.estebakos.sunbelt.test.domain.usecase.GetAnimeListUseCase
import com.estebakos.sunbelt.test.domain.usecase.SearchAnimeListUseCase
import com.estebakos.sunbelt.test.ui.model.AnimeDetailUI
import com.estebakos.sunbelt.test.ui.model.AnimeListUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class AnimeViewModel @Inject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
    private val searchAnimeListUseCase: SearchAnimeListUseCase,
    private val getAnimeByIdUseCase: GetAnimeByIdUseCase,
    private val connectivityManager: ConnectivityManager
) : ViewModel(), NavigationProvider<AnimeViewModel.AnimeView> {

    private val animeListLiveData: MutableLiveData<List<AnimeListUI>> = MutableLiveData()
    private val animeDetailLiveData: MutableLiveData<AnimeDetailUI> = MutableLiveData()
    private val emptyItemsLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val errorLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val currentViewMutableLiveData = MutableLiveData<Triple<AnimeView, AnimeView, Any?>>()

    val currentViewLiveData: LiveData<Triple<AnimeView, AnimeView, Any?>>
        get() = currentViewMutableLiveData

    val animeList: LiveData<List<AnimeListUI>>
        get() = animeListLiveData

    val animeDetail: LiveData<AnimeDetailUI>
        get() = animeDetailLiveData

    val empty: LiveData<Boolean>
        get() = emptyItemsLiveData

    val error: LiveData<Boolean>
        get() = errorLiveData

    val loading: LiveData<Boolean>
        get() = loadingLiveData

    fun loadAnimeList() {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            val output = getAnimeListUseCase.execute()
            if (output is Output.Success) {
                onGetAnimeListSuccess(output.data)
            } else {
                onError()
            }
        }
    }

    fun searchAnime(query: String, page: Int = 1) {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            val output = searchAnimeListUseCase.execute(query, page)
            if (output is Output.Success) {
                onGetAnimeListSuccess(output.data)
            } else {
                onError()
            }
        }
    }

    fun getAnimeById(id: Int) {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            val output = getAnimeByIdUseCase.execute(id)
            if (output is Output.Success) {
                onGetAnimeByIdSuccess(output.data)
            } else {
                onError()
            }
        }
    }

    private fun onGetAnimeListSuccess(animeList: List<AnimeListUI>) {
        if (animeList.isEmpty()) {
            emptyItemsLiveData.postValue(true)
        } else {
            animeListLiveData.postValue(animeList)
        }

        loadingLiveData.postValue(false)
    }

    private fun onGetAnimeByIdSuccess(data: AnimeDetailUI) {
        animeDetailLiveData.postValue(data)
        loadingLiveData.postValue(false)
    }

    private fun onError(hasInternet: Boolean = true) {
        viewModelScope.launch {
            delay(300)
            loadingLiveData.postValue(false)
        }.invokeOnCompletion {
            errorLiveData.postValue(hasInternet)
        }
    }

    override fun navigateTo(originView: AnimeView, destinationView: AnimeView, params: Any?) {
        if (hasInternet()) {
            currentViewMutableLiveData.value = Triple(originView, destinationView, params)
        } else {
            onError(false)
        }
    }

    private fun hasInternet(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.run {
                getNetworkCapabilities(activeNetwork)?.run {
                    return when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            true
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
            }
        } else {
            connectivityManager.run {
                activeNetworkInfo?.run {
                    return when (type) {
                        ConnectivityManager.TYPE_WIFI -> {
                            true
                        }
                        ConnectivityManager.TYPE_MOBILE -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
            }
        }

        return false
    }

    sealed class AnimeView {
        object AnimeListFragment : AnimeView()
        object AnimeDetailFragment : AnimeView()
    }
}