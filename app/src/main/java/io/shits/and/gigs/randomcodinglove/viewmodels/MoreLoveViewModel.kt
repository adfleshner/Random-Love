package io.shits.and.gigs.randomcodinglove.viewmodels

import Love
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.shits.and.gigs.randomcodinglove.R
import io.shits.and.gigs.randomcodinglove.love.RandomLoveEventRepository
import io.shits.and.gigs.randomcodinglove.love.RandomLoveRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed class MoreLoveState {

    object Initial : MoreLoveState()

    object Loading : MoreLoveState()
    class Content(val title: String, val url: String) : MoreLoveState()
    class Error(val error: Throwable) : MoreLoveState()
}

sealed class MoreLoveEvent {

    class goToSource(val urlRes: Int) : MoreLoveEvent()
    class share(val title: String, val gif: String) : MoreLoveEvent()
}

class MoreLoveViewModel(
    private val moreLoveRepository: RandomLoveRepository,
    private val eventRepository: RandomLoveEventRepository
) : ViewModel() {



    private var _currentLove: Love? = null

    // will
    private val currentLove : Love get() = requireNotNull(_currentLove)

    private val _loveState = MutableStateFlow<MoreLoveState>(MoreLoveState.Initial)
    val loveState = _loveState.asStateFlow()

    private val _loveEvents: MutableSharedFlow<MoreLoveEvent> = MutableSharedFlow()
    val events = _loveEvents.asSharedFlow()

    init {
        getMoreLove()
    }

    fun getMoreLove(wasBackPress : Boolean = false) {
        viewModelScope.launch(IO) {
            Log.d("MoreLoveViewModel", "Starting More Love")
            if (_loveState.value is MoreLoveState.Loading) {
                // ignore get more love if already in the loading state.
                Log.d("MoreLoveViewModel", "Already in the loading state.")
                return@launch
            }
            _loveState.update {
                MoreLoveState.Loading
            }
            runCatching {
                Log.d("MoreLoveViewModel", "Getting More Love")
                moreLoveRepository.historyAndLove(wasBackPress)
            }.onSuccess { love ->
                Log.d("MoreLoveViewModel", "Got some love $love")
                _currentLove = love
                _loveState.update {
                    MoreLoveState.Content(love.title, love.gifUrl)
                }
            }.onFailure {error ->
                Log.d("MoreLoveViewModel", "No Love for you")
                _loveState.update {
                    MoreLoveState.Error(error)
                }
            }
        }
    }

    fun goToTheSource() {
        viewModelScope.launch {
            _loveEvents.emit(eventRepository.createGoToSourceEvent())
        }
    }

    fun shareTheLove() {
        viewModelScope.launch {
            runCatching {
                _loveEvents.emit(
                    MoreLoveEvent.share(
                        currentLove.title,
                        currentLove.gifUrl
                    )
                )
            }.onFailure {
                // Do something for either the NPE exception or any other exception.......
                // But its ok to not cause this is my app not yours and I cant be
                // bothered to write errors for this.
            }
        }
    }
}
