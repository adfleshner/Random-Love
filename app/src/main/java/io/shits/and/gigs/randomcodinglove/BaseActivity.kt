package io.shits.and.gigs.randomcodinglove

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import com.flesh.dataaboutapp.dataaboutapp.AboutAppListener

open class BaseActivity : AppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add to Enable About App Dialog
        AboutAppListener(this,supportFragmentManager, RandomLoveDataRepository(resources))
    }

}