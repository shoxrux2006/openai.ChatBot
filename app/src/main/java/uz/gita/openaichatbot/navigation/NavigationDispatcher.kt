package uz.gita.openaichatbot.navigation

import cafe.adriel.voyager.androidx.AndroidScreen
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NavigationDispatcher @Inject constructor() : NavigationHandler, AppNavigation {
    override val navStack = MutableSharedFlow<NavigationArgs>()


    private suspend fun navigate(arg: NavigationArgs) {
        navStack.emit(arg)
    }

    override suspend fun back() = navigate {
        pop()
    }

    override suspend fun backAll() = navigate {
        popAll()
    }

    override suspend fun backToRoot() = navigate {
        popUntilRoot()
    }

    override suspend fun navigateTo(screen: AndroidScreen) = navigate {
        push(screen)
    }

    override suspend fun replaceWith(screen: AndroidScreen) = navigate {
        replace(screen)
    }

}