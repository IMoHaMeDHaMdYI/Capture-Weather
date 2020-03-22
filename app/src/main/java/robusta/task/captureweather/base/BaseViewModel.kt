package robusta.task.captureweather.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import robusta.task.captureweather.common.Event

abstract class BaseViewModel<VS, VE> : ViewModel(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext = Dispatchers.IO + job


    private val _viewState = MutableLiveData<Event<VS>>()
    val viewState: LiveData<Event<VS>> get() = _viewState
    private fun postState(state: Event<VS>) {
        _viewState.postValue(state)
    }

    private fun viewStateValue() = _viewState.value?.peekContent()


    abstract fun postEvent(event: Event<VE>)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}