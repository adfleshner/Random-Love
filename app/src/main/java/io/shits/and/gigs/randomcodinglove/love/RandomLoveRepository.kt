package io.shits.and.gigs.randomcodinglove.love

import Love
import io.shits.and.gigs.randomcodinglove.utils.retryWithExponentialBackoff
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import java.lang.Exception
import java.util.Stack


class NoHistoryException() : Throwable("No history here")

interface RandomLoveRepository {

    //suspend fun getMoreLove() : Love
    suspend fun historyAndLove(wasBackPress: Boolean = false): Love

}


class RandomLoveRepositoryImpl(private val service: RandomLoveService) : RandomLoveRepository {

    private val history = mutableListOf<Love>()


    // internal function that gets more love.
    private suspend fun getMoreLove(): Love {
        return withTimeout(3000) {
            retryWithExponentialBackoff {
                service.getMoreLove() ?: throw Exception("Boom null")
            }
        }
    }

    override suspend fun historyAndLove(wasBackPress: Boolean): Love {
        if (wasBackPress) {
            if (history.isNotEmpty()) {
                history.removeLast() // pop the current one and throw it on the ground
                return history.last() // now return the last one
            } else {
                throw NoHistoryException()
            }
        } else {
            return runCatching {
                getMoreLove()
            }.onSuccess {
                history.add(it)
                it
            }.onFailure {
                throw it
            }.getOrThrow()
        }
    }

}