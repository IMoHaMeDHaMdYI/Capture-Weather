package robusta.task.captureweather.image

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_image.*
import org.koin.android.viewmodel.ext.android.viewModel
import robusta.task.captureweather.R
import robusta.task.captureweather.base.BaseFragment
import robusta.task.captureweather.common.extenstions.TAG
import robusta.task.captureweather.common.extenstions.makeGone
import robusta.task.captureweather.common.extenstions.makeVisible
import robusta.task.captureweather.common.extenstions.toast
import robusta.task.captureweather.common.utils.getBitmap

class ImageFragment : BaseFragment<ViewEvent>() {
    private val viewModel: ImageViewModel by viewModel()
    private val permissionsRequest = 0
    private var permissionGranted = false
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
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

    private lateinit var fusedLocation: FusedLocationProviderClient
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
        path =
            requireArguments().getString(IMAGE_PATH) ?: throw IllegalStateException(
                "This Fragment must be initialized using ImageFragment.createWithFile(path: String)"
            )
        askForPermission()
        setCancellationListener()

        img.bitmap = getBitmap(path)
        img.text = "Loading..."
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.peekContent().let {
                it.weather?.main?.let {
                    val printedText = "Temp: ${it.temp}\nMax: ${it.max}\nMin: ${it.min} "
                    img.text = printedText
                }
                if (it.loadingWeather) {
                    pb.makeVisible()
                } else {
                    pb.makeGone()
                }
                if (it.exception != null) {
                    tvError.makeVisible()
                } else {
                    tvError.makeGone()
                }
            }
        })
        viewEvent.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { viewModel.postEvent(it) }
        })
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
            ) != PackageManager.PERMISSION_GRANTED
        }
        if (neededPermissions.isNotEmpty()) {
            requestPermissions(neededPermissions.toTypedArray(), permissionsRequest)
        } else {
            setLocationListener()
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
            setLocationListener()
        }
    }

    private fun setLocationListener() {
        fusedLocation.lastLocation.addOnSuccessListener {
            it?.let { location ->
                Log.d(TAG, "hereee")
                postEvent(ViewEvent.GetWeather(location.latitude, location.longitude))
                return@addOnSuccessListener
            }
            context?.toast(getString(R.string.error_detecting_location))

        }.addOnFailureListener {
            context?.toast(getString(R.string.error_detecting_location))
            it.printStackTrace()
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