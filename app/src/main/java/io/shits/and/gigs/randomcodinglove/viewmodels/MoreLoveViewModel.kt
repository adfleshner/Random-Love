package io.shits.and.gigs.randomcodinglove.viewmodels

import Love
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.shits.and.gigs.randomcodinglove.R
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val MORE_LOVE_ALLOWED_DELAY = 500L
const val MORE_LOVE_ALLOWED_RETRIES = 5

sealed class MoreLoveState {

    object Loading : MoreLoveState()
    class Content(val title: String, val url: String) : MoreLoveState()
    object Error : MoreLoveState()
}

sealed class MoreLoveEvent {

    class goToSource(val urlRes: Int) : MoreLoveEvent()
    class share(val title: String, val gif: String) : MoreLoveEvent()
}

class MoreLoveViewModel : ViewModel() {

    private var _currentLove: Love? = null

    private val _loveState = MutableStateFlow<MoreLoveState>(MoreLoveState.Loading)
    val loveState = _loveState.asStateFlow()

    private val _loveEvents: MutableSharedFlow<MoreLoveEvent> = MutableSharedFlow()
    val events = _loveEvents.asSharedFlow()

    private var tries = 0
    private var running = false

    init {
        getMoreLove()
    }

    fun getMoreLove() = viewModelScope.launch(IO) {
        if (!running) {
            tries = 0 // reset tries
            var tempLove: Love? = null
            while (tempLove == null && tries <= MORE_LOVE_ALLOWED_RETRIES) { // keep trying to get a more love until you find it or give up after 5 tries.
                tries++
                running = true
                _loveState.update {
                    MoreLoveState.Loading
                }
                tempLove = Parser.findRandomCodingLoveGif()
                delay(MORE_LOVE_ALLOWED_DELAY) // if you fail wait 500ms before trying again
            }
            val state = if (tempLove == null) {
                MoreLoveState.Error
            } else {
                _currentLove = tempLove

                MoreLoveState.Content(tempLove.title, tempLove.gifUrl)
            }

            _loveState.update {
                state
            }
            running = false
        }
    }

    fun goToTheSource() {
        viewModelScope.launch {
            _loveEvents.emit(MoreLoveEvent.goToSource(R.string.the_url))
        }
    }

    fun shareTheLove() {
        viewModelScope.launch {
            runCatching {
                // Using the (!!) so I know dont need to do any error case for everything
                // and make a catch all dunno if it is something that should be done but
                // Im gonna try it and see if it is something that I like and if I think
                // It makes sense.
                _loveEvents.emit(MoreLoveEvent.share(_currentLove!!.title, _currentLove!!.gifUrl))
            }.onFailure {
                // Do something for either the NPE exception or any other exception.......
                // But its ok to not cause this is my app not yours and I cant be
                // bothered to write errors for this.
            }
        }
    }
}
