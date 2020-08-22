package com.it.venew.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.it.venew.R
import com.it.venew.util.InjectorUtils
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import java.io.File


class MapFragment : Fragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    private var mMapView: MapView? = null
    private var mMapController: MapController? = null

    private lateinit var viewModel: MainViewModel

    private var suppliersOnMap: List<OverlayItem> = listOf()
    private var usersOnMap: List<OverlayItem> = listOf()

    private lateinit var mOverlay: ItemizedOverlayWithFocus<OverlayItem?>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.main_fragment, container, false)

        val factory = InjectorUtils.provideMainViewModelFactory(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), factory).get(MainViewModel::class.java)

        val osmConfig = Configuration.getInstance()
        osmConfig.userAgentValue = requireActivity().packageName
        val basePath = File(requireActivity().cacheDir.absolutePath, "osmdroid")
        osmConfig.osmdroidBasePath = basePath
        val tileCache  = File(osmConfig.osmdroidBasePath, "tile")
        osmConfig.osmdroidTileCache = tileCache

        mMapView = rootView.findViewById(R.id.map)
        mMapView!!.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mMapView!!.setBuiltInZoomControls(true)
        mMapController = mMapView!!.controller as MapController
        mMapController!!.setZoom(13)
        val gPt = GeoPoint(32109333, 34855499)
        mMapController!!.setCenter(gPt)

        mOverlay = ItemizedOverlayWithFocus(requireContext(), listOf(),
            object : OnItemGestureListener<OverlayItem?> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            }
        )
        mOverlay.setFocusItemsOnTap(true)

        viewModel.suppliers.observe(viewLifecycleOwner, Observer{suppliers ->
            val supplierMarker = ContextCompat.getDrawable(requireActivity(), R.drawable.map_supplier_marker)

            suppliersOnMap.forEach{
                mOverlay.removeItem(it)
            }

            suppliersOnMap = suppliers.filter { it.lat != null && it.lon != null }.map { supplier ->
                OverlayItem(supplier.name, supplier.note, GeoPoint(supplier.lat!!, supplier.lon!!)).apply { setMarker(supplierMarker) }
            }

            mOverlay.addItems(suppliersOnMap)
        })

        viewModel.userLocations.observe(viewLifecycleOwner, Observer{userLocations ->
            val userMarker = ContextCompat.getDrawable(requireActivity(), R.drawable.map_user_marker)

            usersOnMap.forEach{
                mOverlay.removeItem(it)
            }

            usersOnMap = userLocations.map { user ->
                OverlayItem("", "", GeoPoint(user.lat, user.lon)).apply { setMarker(userMarker) }
            }

            mOverlay.addItems(usersOnMap)
        })


//        mMapView!!.overlays.add(mOverlay)
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mMapView = null
        mMapController = null
    }
}
