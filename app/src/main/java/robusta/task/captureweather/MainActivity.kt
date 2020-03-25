package robusta.task.captureweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import robusta.task.captureweather.camera.views.CameraFragment
import robusta.task.captureweather.common.extenstions.TAG

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CameraFragment(), "camera")
            .commit()
        Log.d(TAG, "home: $filesDir")
    }
}
