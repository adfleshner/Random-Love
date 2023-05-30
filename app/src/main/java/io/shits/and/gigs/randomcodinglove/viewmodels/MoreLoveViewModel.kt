package io.shits.and.gigs.randomcodinglove.viewmodels

import Love
import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.View
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.shits.and.gigs.randomcodinglove.R
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

const val MORE_LOVE_ALLOWED_DELAY = 500L
const val MORE_LOVE_ALLOWED_RETRIES = 5

class MoreLoveViewModel : ViewModel() {

    sealed class MoreLoveState {

        object Loading : MoreLoveState()
        class Content(val love: Love) : MoreLoveState()

        object Error : MoreLoveState()

    }

    private val _loveState = MutableStateFlow<MoreLoveState>(MoreLoveState.Loading)
    var loveState = _loveState.asStateFlow()

    private var tries = 0
    private var running = false

    init {
        getMoreLove()
    }

    fun getMoreLove() = viewModelScope.launch(IO) {
        if (!running) {
            tries = 0 // reset tries
            var tempLove: Love? = null
            while (tempLove == null && tries <= MORE_LOVE_ALLOWED_RETRIES) { //keep trying to get a more love until you find it or give up after 5 tries.
                tries++
                running = true
                _loveState.update {
                    MoreLoveState.Loading
                }
                tempLove = Parser.findRandomCodingLoveGif()
                delay(MORE_LOVE_ALLOWED_DELAY) // if you fail wait 500ms before trying again
            }
            val state =  if (tempLove == null) {
                MoreLoveState.Error
            } else {
                MoreLoveState.Content(tempLove)
            }

            _loveState.update {
                state
            }
            running = false
        }
    }

    fun goToTheSource(view: View){
        try {
            view.context.startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = view.context.getString(R.string.the_url).toUri()
            })
        }catch (_:ActivityNotFoundException){}
    }

}