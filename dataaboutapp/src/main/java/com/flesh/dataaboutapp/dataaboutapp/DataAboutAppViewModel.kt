package com.flesh.dataaboutapp.dataaboutapp

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class DataAboutAppViewModel(private val dataAboutAppRepository: DataAboutAppRepository) :
    ViewModel() {

    sealed class DataState {
        object Loading : DataState()
        class Content(val title: String, val list: List<String>) : DataState()

        object Error : DataState()
    }

    private val _state = MutableStateFlow<DataState>(DataState.Loading)
    val state = _state.asStateFlow()


    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ALL DATA fetch", dataAboutAppRepository.list.joinToString { item -> item })
            _state.update {
                DataState.Content(dataAboutAppRepository.title, dataAboutAppRepository.list)
            }
        }
    }


    // Factory Methods
    @Suppress("UNCHECKED_CAST")
    class Factory(private val dataAboutAppRepository: DataAboutAppRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DataAboutAppViewModel(dataAboutAppRepository) as T
        }
    }

}

