package io.shits.and.gigs.randomcodinglove.di

import io.shits.and.gigs.randomcodinglove.love.RandomLoveEventRepository
import io.shits.and.gigs.randomcodinglove.love.RandomLoveEventRepositoryImpl
import io.shits.and.gigs.randomcodinglove.love.RandomLoveRepository
import io.shits.and.gigs.randomcodinglove.love.RandomLoveRepositoryImpl
import io.shits.and.gigs.randomcodinglove.love.RandomLoveService
import io.shits.and.gigs.randomcodinglove.love.RandomLoveServiceImpl
import io.shits.and.gigs.randomcodinglove.viewmodels.MoreLoveViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loveModule = module {
    // create Random Love Service
    single<RandomLoveService> { RandomLoveServiceImpl() }
    // create Random Love Repo
    single<RandomLoveRepository> { RandomLoveRepositoryImpl(get()) }
    // create Random Love Event Repo
    single<RandomLoveEventRepository> { RandomLoveEventRepositoryImpl() }
    // create viewModel
    viewModel { MoreLoveViewModel(get(),get()) }
}