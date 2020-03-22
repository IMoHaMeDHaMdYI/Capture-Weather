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
    init {
        initViewState()
    }

    abstract fun initViewState()

    private val job = SupervisorJob()
    override val coroutineContext = Dispatchers.IO + job


    private val _viewState = MutableLiveData<Event<VS>>()
    val viewState: LiveData<Event<VS>> get() = _viewState
    protected fun postState(state: VS) {
        _viewState.postValue(Event(state))
    }

    protected fun viewStateValue() = _viewState.value!!.peekContent()


    abstract fun postEvent(event: VE)

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}