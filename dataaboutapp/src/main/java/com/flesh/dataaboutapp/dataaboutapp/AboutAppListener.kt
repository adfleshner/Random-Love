package com.flesh.dataaboutapp.dataaboutapp

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.squareup.seismic.ShakeDetector

class AboutAppListener(
    context: Context,
    private val fragmentManager: FragmentManager,
    private val dataAboutAppRepository: DataAboutAppRepository
) : ShakeDetector.Listener {

    init {
        context.bindShake(this)
    }

    override fun hearShake() {
        DataAboutAppDialogFragment.showDialog(
            fragmentManager,
            dataAboutAppRepository
        )
    }

}