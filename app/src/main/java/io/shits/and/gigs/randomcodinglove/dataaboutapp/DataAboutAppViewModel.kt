package io.shits.and.gigs.randomcodinglove.dataaboutapp

import androidx.lifecycle.*
import io.shits.and.gigs.randomcodinglove.BuildConfig
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
            val gitBranch = "Branch: ${dataAboutAppRepository.gitBranch}"
            val versionName = "Version Name: ${BuildConfig.VERSION_NAME}"
            val buildType = "Build Type: ${BuildConfig.BUILD_TYPE}"
            val list = mutableListOf<String>()
            list.add(gitBranch)
            list.add(versionName)
            list.add(buildType)
            _data.emit(list)
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

