package io.shits.and.gigs.randomcodinglove

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveViewModel

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:text_response")
    fun setTextResponse(textView: TextView, response: MoreLoveViewModel.Response){
        textView.text = when(response){
            MoreLoveViewModel.Response.Failed ->  "No Love for you"
            is MoreLoveViewModel.Response.Success -> response.love.title
        }
    }

    @JvmStatic
    @BindingAdapter("app:image_response")
    fun setImageResponse(image: ImageView, response: MoreLoveViewModel.Response){
        val gif = when(response){
            MoreLoveViewModel.Response.Failed -> "https://media.tenor.com/FZxj4M9HGSwAAAAd/jinsoulery-jinsoulburger.gif"
            is MoreLoveViewModel.Response.Success -> response.love.gifUrl
        }
        Glide.with(image)
            .asGif()
            .load(gif)
            .into(image)
    }

    @JvmStatic
    @BindingAdapter("isVisibleGone")
    fun setVisibilityBetweenVisibleAndGone(view: View,isVisibleGone:Boolean){
        view.visibility = if(isVisibleGone){
            View.VISIBLE
        }else{
            View.GONE
        }
    }

}