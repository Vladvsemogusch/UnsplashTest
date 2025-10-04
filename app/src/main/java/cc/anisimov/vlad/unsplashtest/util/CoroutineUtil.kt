package cc.anisimov.vlad.unsplashtest.util

import kotlin.coroutines.cancellation.CancellationException

suspend fun tryCoroutine(tryFun: suspend () -> Unit, catchFun: suspend (Throwable) -> Unit) {
    try {
        tryFun()
    } catch (e: Throwable) {
        if (e !is CancellationException) {
            catchFun(e)
        } else {
            throw e
        }
    }
}