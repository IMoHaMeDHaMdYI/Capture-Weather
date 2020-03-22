package robusta.task.captureweather.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import robusta.task.captureweather.common.Event

open class BaseActivity<VE> : AppCompatActivity() {
    val viewEvent = MutableLiveData<Event<VE>>()
}