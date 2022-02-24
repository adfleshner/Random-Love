package io.shits.and.gigs.randomcodinglove

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import io.shits.and.gigs.randomcodinglove.databinding.ActivityMainBinding
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val _viewModel: MoreLoveViewModel by viewModels()
    private val _binding: ActivityMainBinding by lazy {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = _viewModel
        binding.lifecycleOwner = this
        binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        lifecycleScope.launch {
            _viewModel.loveResponse.collect {
                BindingAdapters.setTextResponse(_binding.textView,it)
                BindingAdapters.setImageResponse(_binding.imageView,it)
            }
        }
    }
}