package io.shits.and.gigs.randomcodinglove

import android.content.res.Resources
import com.flesh.dataaboutapp.dataaboutapp.DataAboutAppRepository

class RandomLoveDataRepository(resources: Resources) : DataAboutAppRepository() {

    init {
        title = BuildConfig.app_name
        addDataToList(resources.getString(R.string.git_branch_wrapper, resources.getString(R.string.git_branch)))
        addDataToList(resources.getString(R.string.version_name_wrapper, BuildConfig.VERSION_NAME))
        addDataToList(resources.getString(R.string.build_type_wrapper, BuildConfig.BUILD_TYPE))
    }


}