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
            else -> { "Finding more love"}
        }
    }

    @JvmStatic
    @BindingAdapter("app:image_response")
    fun setImageResponse(image: ImageView, response: MoreLoveViewModel.Response){
        val gif = when(response){
            MoreLoveViewModel.Response.Failed -> "https://www.google.com/url?sa=i&url=https%3A%2F%2Ftenor.com%2Fsearch%2Fsad-dog-gifs&psig=AOvVaw2PFThXpvVIDEhb31YWNMr7&ust=1645763873700000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCNDAuarCl_YCFQAAAAAdAAAAABAD"
            is MoreLoveViewModel.Response.Success -> response.love.gifUrl
            else -> { null }
        }
        if(gif != null){
            Glide.with(image)
                .asGif()
                .load(gif)
                .into(image)
        }
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