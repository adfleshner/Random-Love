package io.shits.and.gigs.randomcodinglove

import android.os.Bundle
import io.shits.and.gigs.randomcodinglove.base.BaseActivity
import io.shits.and.gigs.randomcodinglove.databinding.ActivityMainBinding
import io.shits.and.gigs.randomcodinglove.utils.viewBinding

class MainActivity : BaseActivity() {

    private val _binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, RandomLoveFragment.newInstance(), RandomLoveFragment.TAG)
                .commit()
        }
    }
}
