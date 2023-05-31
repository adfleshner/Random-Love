package io.shits.and.gigs.randomcodinglove

import Love
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import io.shits.and.gigs.randomcodinglove.databinding.FragmentRandomLoveBinding
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RandomLoveFragment : Fragment() {

    private val _viewModel: MoreLoveViewModel by viewModels()

    private var _binding : FragmentRandomLoveBinding? = null
    private val binding get() = _binding as FragmentRandomLoveBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomLoveBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindClickListeners()
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
        with(binding){
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
        with(binding){
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
    }


}