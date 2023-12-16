package com.antartic.sudio.catest.core

import androidx.lifecycle.ViewModel
import com.antartic.sudio.core.MainDispatcher
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel(), CoroutineScope {

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        if (error !is CancellationException) {
            Timber.e(error)
            displayError(error)
        }
    }

    private val parentJob = SupervisorJob() + exceptionHandler
    override val coroutineContext: CoroutineContext
        get() = dispatcher + parentJob

    private val _displayError = MutableSharedFlow<Throwable>()
    val displayError = _displayError.asSharedFlow()

    open fun displayError(error: Throwable) {
        launch {
            _displayError.emit(error)
        }
    }

    override fun onCleared() {
        this.parentJob.cancel()
        super.onCleared()
    }
}
