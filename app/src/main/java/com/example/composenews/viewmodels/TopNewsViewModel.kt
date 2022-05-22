package com.example.composenews.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.composenews.data.local.TopNewsEntity
import com.example.composenews.data.local.remotemediator.PagingNewsEntity
import com.example.composenews.data.remote.models.SourceItem
import com.example.composenews.repository.NewsRepository
import com.example.composenews.utils.SortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopNewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<List<TopNewsEntity>>()
    val news: LiveData<List<TopNewsEntity>> get() = _news

    private val _source = MutableLiveData<List<SourceItem>>()
    val source: LiveData<List<SourceItem>> get() = _source

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _searchQuery = mutableStateOf("bitcoin")
    val searchQuery = _searchQuery

    private val _searchedNews = MutableStateFlow<PagingData<PagingNewsEntity>>(PagingData.empty())
    val searchedNews = _searchedNews

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(TopNewsViewModel::class.simpleName, throwable.message ?: "Unknown error")
        triggerErrorEvent()
        _isLoading.postValue(false)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchNews(query: String, sortBy: SortBy) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            repository.getEverything(query, sortBy).cachedIn(viewModelScope)
                .onEach {
                    _isLoading.postValue(false)
                }
                .collect{
                _searchedNews.value = it
            }
        }
    }

    fun getNews(isRefresh: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            _isLoading.postValue(true)
            val response = repository.getNews(isRefresh)
            _news.postValue(response)
            _isLoading.postValue(false)
        }
    }

    fun getAllSource(isRefresh: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            _isLoading.postValue(true)
            val response = repository.getSources(isRefresh)
            _source.postValue(response)
            _isLoading.postValue(false)
        }
    }

    sealed class CustomEvent {
        object ErrorEvent : CustomEvent()
    }

    private val channel = Channel<CustomEvent>()
    val eventFlow = channel.receiveAsFlow()

    private fun triggerErrorEvent() = viewModelScope.launch {
        channel.send(CustomEvent.ErrorEvent)
    }
}