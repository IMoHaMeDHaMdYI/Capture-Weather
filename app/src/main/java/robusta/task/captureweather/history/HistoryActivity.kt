package robusta.task.captureweather.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.viewmodel.ext.android.viewModel
import robusta.task.captureweather.R
import robusta.task.captureweather.common.extenstions.TAG
import robusta.task.captureweather.common.utils.FileHelper
import robusta.task.captureweather.image.ImageFragment

class HistoryActivity : AppCompatActivity() {
    private val viewModel: HistoryViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val adapter = HistoryAdapter {
            val fragment = ImageFragment.createWithPath(it.path, false)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment.TAG)
                .addToBackStack(fragment.TAG).commit()
        }
        rvThumbnails.adapter = adapter
        viewModel.pathes.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
