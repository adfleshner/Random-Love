package io.shits.and.gigs.randomcodinglove.viewmodels

import Love
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.shits.and.gigs.randomcodinglove.R
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MoreLoveViewModel : ViewModel() {

    sealed class Response {
        object Failed : Response()
        class Success(val love: Love) : Response()
    }

    private val _loveResponse = MutableSharedFlow<Response>()
    var loveResponse = _loveResponse.asSharedFlow()

    private val _loading = MutableLiveData(false)
    val loading : LiveData<Boolean> get() = _loading

    private var tries = 0
    private var running = false

    init {
        getMoreLove()
    }

    fun getMoreLove() = viewModelScope.launch(IO) {
        if (!running) {
            tries = 0 // reset tries
            var tempLove: Love? = null
            while (tempLove == null && tries <= 5) { //keep trying to get a more love until you find it or give up after 5 tries.
                tries++
                running = true
                _loading.postValue(true)
                tempLove = Parser.findRandomCodingLoveGif()
                delay(500) // if you fail wait 500ms before trying again
            }
            _loveResponse.emit(
                if (tempLove == null) {
                    Response.Failed
                } else {
                    Response.Success(tempLove)
                }
            )
            _loading.postValue(false)
            running = false
        }
    }

    fun goToTheSource(view: View){
        try {
            view.context.startActivity(Intent(view.context.getString(R.string.the_url)))
        }catch (ex:ActivityNotFoundException){}
    }

}