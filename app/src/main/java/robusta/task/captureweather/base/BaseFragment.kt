package robusta.task.captureweather.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import robusta.task.captureweather.common.Event

open class BaseFragment<VE> : Fragment() {
    protected val viewEvent = MutableLiveData<Event<VE>>()
    protected fun postEvent(event: VE) {
        viewEvent.value = Event(event)
    }
}