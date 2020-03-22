package robusta.task.captureweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import robusta.task.captureweather.camera.views.CameraFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CameraFragment(), "camera")
            .commit()
    }
}
