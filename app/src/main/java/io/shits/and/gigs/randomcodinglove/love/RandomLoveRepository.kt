package io.shits.and.gigs.randomcodinglove.love

import Love
import io.shits.and.gigs.randomcodinglove.utils.retryWithExponentialBackoff
import java.lang.Exception


interface RandomLoveRepository {
    suspend fun getMoreLove() : Love

}


class RandomLoveRepositoryImpl(private val service: RandomLoveService) : RandomLoveRepository {
    override suspend fun getMoreLove() : Love {
        return retryWithExponentialBackoff {
            service.getMoreLove() ?: throw Exception("Boom null")
        }
    }

}