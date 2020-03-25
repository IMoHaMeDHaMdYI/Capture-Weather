package robusta.task.captureweather.history

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.android.viewmodel.ext.android.viewModel
import robusta.task.captureweather.R
import robusta.task.captureweather.image.ImageFragment

class HistoryActivity : AppCompatActivity() {
    private val viewModel: HistoryViewModel by viewModel()
    private val permissionsRequest = 0
    private var permissionGranted = false
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

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
        askForPermission()
    }

    private fun askForPermission() {
        val neededPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) != PackageManager.PERMISSION_GRANTED
        }
        if (neededPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                neededPermissions.toTypedArray(),
                permissionsRequest
            )
        } else {
            permissionGranted = true
            viewModel.getFiles()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionsRequest && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true
            viewModel.getFiles()
        } else {
            askForPermission()
        }
    }


}
