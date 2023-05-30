package io.shits.and.gigs.randomcodinglove

import Love
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.flesh.dataaboutapp.dataaboutapp.DataAboutAppDialogFragment
import com.flesh.dataaboutapp.dataaboutapp.bindShake
import com.squareup.seismic.ShakeDetector
import io.shits.and.gigs.randomcodinglove.databinding.ActivityMainBinding
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ShakeDetector.Listener {

    private val _viewModel: MoreLoveViewModel by viewModels()

    private val _binding: ActivityMainBinding by lazy {
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        bindClickListeners()
        bindShake(this)
        lifecycleScope.launch {
            _viewModel.loveState.flowWithLifecycle(lifecycle).collectLatest { state ->
                renderLoadingForState(state)
                when (state) {
                    is MoreLoveViewModel.MoreLoveState.Content -> {
                        renderContent(state.love)
                    }
                    MoreLoveViewModel.MoreLoveState.Error -> {
                        renderError()
                    }

                    else -> {
                        // Do nothing because loading is handled else where.
                    }
                }
            }
        }
    }

    private fun bindClickListeners() {
        with(_binding){
            loveImage.setOnClickListener {
                _viewModel.getMoreLove()
            }
            loveFooter.setOnClickListener {
                _viewModel.goToTheSource(it)
            }
        }
    }

    private fun renderLoadingForState(state: MoreLoveViewModel.MoreLoveState) {
        val showLoading = state == MoreLoveViewModel.MoreLoveState.Loading
        with(_binding){
            loveLoading.isVisible = showLoading
        }
    }

    private fun renderContent(love: Love) {
        renderScreen(love.title, love.gifUrl)
    }

    private fun renderError() {
        renderScreen(
            "No Love for you",
            "https://media.tenor.com/FZxj4M9HGSwAAAAd/jinsoulery-jinsoulburger.gif"
        )
    }

    private fun renderScreen(title: String, gifUrl: String) {
        with(_binding) {
            textView.text = title
            Glide.with(loveImage)
                .asGif()
                .load(gifUrl)
                .into(loveImage)
        }
    }

        override fun hearShake() {
            DataAboutAppDialogFragment.showDialog(
                supportFragmentManager,
                RandomLoveDataRepository(resources),
                this
            )
        }
    }

