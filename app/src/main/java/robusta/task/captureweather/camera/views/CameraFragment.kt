package robusta.task.captureweather.camera.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.PictureResult
import kotlinx.android.synthetic.main.fragment_camera.*
import robusta.task.captureweather.R
import robusta.task.captureweather.base.BaseFragment
import robusta.task.captureweather.common.extenstions.TAG
import robusta.task.captureweather.common.extenstions.makeGone
import robusta.task.captureweather.common.extenstions.makeVisible
import robusta.task.captureweather.image.ImageFragment
import java.io.File


class CameraFragment : BaseFragment<CameraViewEvent>() {
    private val permissionsRequest = 0
    private var permissionGranted = false
    private var imageFragment: ImageFragment? = null
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
        cameraView.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                result.toFile(File.createTempFile("temp", "png")) {
                    it?.let {
                        finishLoading()
                        openFragment(it.path)
                        return@toFile
                    }
                }
            }
        })
        viewCapture.setOnClickListener {
            if (permissionGranted) {
                startLoading()
                takePicture()
            } else {
                askForPermission()
            }
        }
    }


    private fun openFragment(path: String) {
        imageFragment = ImageFragment.createWithPath(path)
        requireActivity().supportFragmentManager.beginTransaction().add(
            R.id.fragment_container, imageFragment!!, imageFragment!!.TAG
        ).addToBackStack(imageFragment!!.TAG)
            .commit()
    }

    private fun takePicture() {
        cameraView.takePicture();
    }


    private fun startLoading() {
        viewCapture.makeGone()
        pb.makeVisible()
    }

    private fun finishLoading() {
        viewCapture.makeVisible()
        pb.makeGone()
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
        if (requestCode == permissionsRequest && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true
        }
    }
}