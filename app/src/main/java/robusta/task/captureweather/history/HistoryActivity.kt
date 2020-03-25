package robusta.task.captureweather.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.viewmodel.ext.android.viewModel
import robusta.task.captureweather.R
import robusta.task.captureweather.image.ImageFragment

class HistoryActivity : AppCompatActivity() {
    private val viewModel: HistoryViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val adapter = HistoryAdapter {
            val fragment = ImageFragment.createWithPath(it.path, false)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, fragment::class.java.simpleName)
                .addToBackStack(fragment::class.java.simpleName).commit()
        }
        rvThumbnails.adapter = adapter
        viewModel.paths.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
