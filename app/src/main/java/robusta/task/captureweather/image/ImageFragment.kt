package robusta.task.captureweather.image

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_image.*
import robusta.task.captureweather.R
import robusta.task.captureweather.common.utils.getBitmap

class ImageFragment : Fragment() {
    private val permissionsRequest = 0
    private var permissionGranted = false
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
        return inflater
            .inflate(R.layout.fragment_image, container, false)
    }

    lateinit var path: String

    private var fusedLocation: FusedLocationProviderClient? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        path =
            requireArguments().getString(IMAGE_PATH) ?: throw IllegalStateException(
                "This Fragment must be initialized using ImageFragment.createWithFile(path: String)"
            )
        askForPermission()
        img.bitmap = getBitmap(path)
        img.text = "Hello Bitches"
        setCancellationListener()
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private fun setCancellationListener() {
        imgCancel.setOnClickListener {
            requireActivity().onBackPressed()
        }
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

    companion object {
        const val IMAGE_PATH = "path"
        fun createWithPath(path: String) = ImageFragment().apply {
            arguments = Bundle().apply {
                putString(IMAGE_PATH, path)
            }
        }
    }
}