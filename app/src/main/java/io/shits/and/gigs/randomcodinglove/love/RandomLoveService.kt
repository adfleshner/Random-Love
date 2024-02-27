package io.shits.and.gigs.randomcodinglove.love

import Love
import Parser

interface RandomLoveService{
    suspend fun getMoreLove() : Love?
}

class RandomLoveServiceImpl : RandomLoveService    {
    override suspend fun getMoreLove(): Love? {
        return Parser.findRandomCodingLoveGif()
    }

}