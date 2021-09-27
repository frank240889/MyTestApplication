package com.franco.mytestapplication.presentation.map

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.franco.mytestapplication.R
import com.franco.mytestapplication.dagger.AndroidViewModelFactory
import com.franco.mytestapplication.databinding.MapFragmentBinding
import com.franco.mytestapplication.utils.getWindowHeight
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Screen to show the map and the registered locations.
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 01:10 p.m.
 */
class MapFragment : BottomSheetDialogFragment(), OnMapReadyCallback, HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: AndroidViewModelFactory

    companion object {
        fun newInstance() = MapFragment()
    }

    private lateinit var viewModel:MapViewModel

    private var _viewBinding: MapFragmentBinding? = null

    private val viewBinding: MapFragmentBinding get() = _viewBinding!!

    private var map: GoogleMap? = null

    private val locationsPermissions =if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    } else {
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }
            setupButton(viewBinding.mbMapFragmentLogNow)
        }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = obtainViewBinding().apply {
        _viewBinding = this
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun androidInjector() = injector

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMap()
        setListeners()
    }

    private fun setListeners() {
        viewBinding.apply {
            ibMapFragmentClose.setOnClickListener {
                dismiss()
            }
            setupButton(mbMapFragmentLogNow)
        }
    }

    private fun setupButton(materialButton: MaterialButton) {
        materialButton.apply {
            text = if (!hasLocationPermissions()) {
                setOnClickListener {
                    requestLocationPermission()
                }
                getString(R.string.request_location)
            } else {
                setOnClickListener {
                    logLocation()
                }
                getString(R.string.log_now)
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                val layoutParams = bottomSheet.layoutParams
                bottomSheet.setBackgroundResource(android.R.color.transparent)

                val windowHeight = getWindowHeight()
                if (layoutParams != null) {
                    layoutParams.height = windowHeight
                }
                bottomSheet.layoutParams = layoutParams

                BottomSheetBehavior.from(bottomSheet).apply {

                    state = BottomSheetBehavior.STATE_EXPANDED
                    skipCollapsed = true
                    isFitToContents = false
                }
            }
        }
    }

    private fun initMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        onLoading(true)
        mapFragment.getMapAsync { map ->
            onLoading(false)
            this.map = map
        }
    }

    private fun obtainViewModel() =  ViewModelProvider(
        this,
        viewModelFactory
    )[MapViewModel::class.java]

    private fun onLoading(loading: Boolean) {
        viewBinding.pbLoading.visibility = if (loading) VISIBLE else GONE
    }

    private fun obtainViewBinding() = MapFragmentBinding.inflate(layoutInflater)

    private fun logLocation() {

    }

    private fun requestLocationPermission() {

    }

    private fun hasLocationPermissions() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContextCompat
                .checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat
                    .checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat
                .checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        } else {
                ContextCompat
                    .checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat
                    .checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        }
}