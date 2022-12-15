package io.shits.and.gigs.randomcodinglove.dataaboutapp

import android.content.res.Resources
import io.shits.and.gigs.randomcodinglove.R

class DataAboutAppRepository(resources : Resources) {

    val gitBranch : String =
        resources.getString(R.string.git_branch)

}