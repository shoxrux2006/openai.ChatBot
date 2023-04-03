package uz.gita.bellissimo_clone.utils

sealed class NetworkResponse<T>(
    val data: T? = null,
    var error: Boolean = false
) {
    class Success<T>(data: T) : NetworkResponse<T>(data = data)
    class Error<T>( val message: String) : NetworkResponse<T>()
    class NoConnection<T> : NetworkResponse<T>()
    class Loading<T>(val isLoading: Boolean) : NetworkResponse<T>()
}