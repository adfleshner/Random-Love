package io.shits.and.gigs.randomcodinglove.utils

import kotlinx.coroutines.delay

// retry logic.
suspend fun <T> retryWithExponentialBackoff(
    maxRetries: Int = 3,
    initialDelayMillis: Long = 100,
    maxDelayMillis: Long = 1000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {
    var currentDelay = initialDelayMillis
    repeat(maxRetries) { retryCount ->
        try {
            return block()
        } catch (e: Exception) {
            if (retryCount == maxRetries - 1) {
                throw e // No more retries left, propagate the exception
            }
            delay(currentDelay)
            // Increase delay exponentially, with a maximum cap
            currentDelay = (currentDelay * factor).coerceAtMost(maxDelayMillis.toDouble()).toLong()
        }
    }
    // This line should never be reached, as we either return successfully or throw an exception
    throw IllegalStateException("Unexpected code path reached")
}