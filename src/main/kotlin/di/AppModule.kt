package org.example.di

import org.example.data.repositories.ActivityRepository
import org.example.data.repositories.UserRepository
import org.koin.dsl.module

val module = module {
    single { UserRepository() }
    single { ActivityRepository() }
}