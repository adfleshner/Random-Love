package com.flesh.dataaboutapp.dataaboutapp

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class DataAboutAppViewModel(private val dataAboutAppRepository: DataAboutAppRepository) : ViewModel() {

    private val _data: MutableSharedFlow<List<String>> by lazy {
        MutableSharedFlow()
    }

    val data : SharedFlow<List<String>> = _data.asSharedFlow()

    val title = dataAboutAppRepository.title

    private inner class DataAboutAppLifeCycleObserver : DefaultLifecycleObserver {

        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            fetchData()
        }

    }

    // Add the observer to the lifecycle
    fun addObserverToLifeCycle(lifecycle: Lifecycle){
        lifecycle.addObserver(DataAboutAppLifeCycleObserver())
    }


    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO){
            _data.emit(dataAboutAppRepository.list)
        }
    }


    // Factory Methods
    @Suppress("UNCHECKED_CAST")
    class Factory(private val dataAboutAppRepository: DataAboutAppRepository) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DataAboutAppViewModel(dataAboutAppRepository) as T
        }
    }

}

