package io.shits.and.gigs.randomcodinglove

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.flesh.dataaboutapp.dataaboutapp.DataAboutAppDialogFragment
import com.flesh.dataaboutapp.dataaboutapp.bindShake
import com.squareup.seismic.ShakeDetector
import io.shits.and.gigs.randomcodinglove.databinding.ActivityMainBinding
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ShakeDetector.Listener {

    private val _viewModel: MoreLoveViewModel by viewModels()

    private val _binding: ActivityMainBinding by lazy {
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = _viewModel
        binding.lifecycleOwner = this
        binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        bindShake(this)
        lifecycleScope.launch {
            _viewModel.loveResponse.collect {
                BindingAdapters.setTextResponse(_binding.textView, it)
                BindingAdapters.setImageResponse(_binding.imageView, it)
            }
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

