package robusta.task.captureweather.camera.views

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.impl.ImageCaptureConfig
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.otaliastudios.cameraview.BitmapCallback
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import kotlinx.android.synthetic.main.fragment_camera.*
import org.koin.android.viewmodel.ext.android.viewModel
import robusta.task.captureweather.R
import robusta.task.captureweather.base.BaseFragment
import robusta.task.captureweather.camera.viewmodels.CameraViewModel
import robusta.task.captureweather.common.extenstions.TAG
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : BaseFragment<CameraViewEvent>() {
    private val viewModel: CameraViewModel by viewModel()
    private val permissionsRequest = 0
    private var permissionGranted = false
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        askForPermission()
        cameraView.setLifecycleOwner(viewLifecycleOwner)
        viewEvent.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { event ->
                viewModel.postEvent(event)
            }
        })
        cameraView.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                result.toBitmap {
                    img.setImageBitmap(it)
                }
            }
            // And much more
        })
        viewCapture.setOnClickListener {
            if (permissionGranted) {
                takePicture()
            } else {
                askForPermission()
            }
        }
    }

    private fun takePicture() {
        cameraView.takePicture();

    }

    private fun askForPermission() {
        val neededPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (neededPermissions.isNotEmpty()) {
            requestPermissions(neededPermissions.toTypedArray(), permissionsRequest)
        } else {
            permissionGranted = true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionsRequest && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true
        }
    }
}