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


    private val _binding: ActivityMainBinding by lazy {
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        binding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        bindShake(this)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.content,RandomLoveFragment() , RandomLoveFragment.TAG).commit()
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

