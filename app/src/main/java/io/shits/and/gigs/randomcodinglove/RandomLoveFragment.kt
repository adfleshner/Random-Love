package io.shits.and.gigs.randomcodinglove

import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import io.shits.and.gigs.randomcodinglove.databinding.FragmentRandomLoveBinding
import io.shits.and.gigs.randomcodinglove.utils.IntentUtils
import io.shits.and.gigs.randomcodinglove.utils.viewBinding
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveEvent
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveState
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RandomLoveFragment : Fragment(R.layout.fragment_random_love) {

    private val binding by viewBinding(FragmentRandomLoveBinding::bind)

    private val _viewModel: MoreLoveViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindClickListeners()
        lifecycleScope.launch {
            _viewModel.loveState.flowWithLifecycle(lifecycle).collectLatest { state ->
                renderLoadingForState(state)
                when (state) {
                    is MoreLoveState.Content -> {
                        renderContent(state.title, state.url)
                    }

                    MoreLoveState.Error -> {
                        renderError()
                    }

                    else -> {
                        // Do nothing because loading is handled else where.
                    }
                }
            }
        }
        lifecycleScope.launch {
            _viewModel.events.flowWithLifecycle(lifecycle).collectLatest { event ->
                when (event) {
                    is MoreLoveEvent.goToSource -> {
                        val uri = requireContext().getString(event.urlRes).toUri()
                        IntentUtils.openUri(requireView(), uri)
                    }

                    is MoreLoveEvent.share -> {
                        IntentUtils.shareText(
                            requireView(),
                            requireContext().getString(
                                R.string.share_gifs,
                                event.title, event.gif
                            )
                        )
                    }
                }
            }
        }
    }

    private fun bindClickListeners() {
        with(binding) {
            loveImage.setOnClickListener {
                _viewModel.getMoreLove()
            }
            loveFooter.setOnClickListener {
                _viewModel.goToTheSource()
            }
            loveShare.setOnClickListener {
                _viewModel.shareTheLove()
            }
        }
    }

    private fun renderLoadingForState(state: MoreLoveState) {
        val showLoading = state == MoreLoveState.Loading
        with(binding) {
            loveLoading.isVisible = showLoading
        }
    }

    private fun renderContent(title: String, gifUrl: String) {
        renderScreen(title, gifUrl)
    }

    private fun renderError() {
        renderScreen(
            "No Love for you",
            "https://media.tenor.com/FZxj4M9HGSwAAAAd/jinsoulery-jinsoulburger.gif"
        )
    }

    private fun renderScreen(title: String, gifUrl: String) {
        with(binding) {
            textView.text = title
            com.bumptech.glide.Glide.with(loveImage)
                .asGif()
                .load(gifUrl)
                .into(loveImage)
        }
    }

    companion object {
        val TAG: String = RandomLoveFragment::class.java.simpleName
        fun newInstance(): RandomLoveFragment =
            RandomLoveFragment()
    }
}
