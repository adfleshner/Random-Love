package io.shits.and.gigs.randomcodinglove.love

import io.shits.and.gigs.randomcodinglove.R
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveEvent

interface RandomLoveEventRepository {

    fun createGoToSourceEvent(): MoreLoveEvent

}

class RandomLoveEventRepositoryImpl : RandomLoveEventRepository {

    override fun createGoToSourceEvent(): MoreLoveEvent =
        MoreLoveEvent.goToSource(R.string.the_url)

}