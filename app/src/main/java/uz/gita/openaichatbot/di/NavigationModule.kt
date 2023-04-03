package uz.gita.openaichatbot.di
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.openaichatbot.navigation.AppNavigation
import uz.gita.openaichatbot.navigation.NavigationDispatcher
import uz.gita.openaichatbot.navigation.NavigationHandler


@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {

    @Binds
    fun appNavigator(dispatcher: NavigationDispatcher): AppNavigation

    @Binds
    fun navigationHandler(dispatcher: NavigationDispatcher): NavigationHandler
}