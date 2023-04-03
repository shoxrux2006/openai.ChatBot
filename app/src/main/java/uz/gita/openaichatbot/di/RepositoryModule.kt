package uz.gita.banking.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bellissimo_clone.domain.repository.ChatRepository
import uz.gita.openaichatbot.domain.repository.impl.ChatRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds
    Singleton]
    fun provideChatRepo(mainRepositoryImpl: ChatRepositoryImpl): ChatRepository

}