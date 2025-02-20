package com.example.spacecraft10.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.spacecraft10.R
import com.example.spacecraft10.databinding.FragmentMapsBinding
import com.example.spacecraft10.db.AgenciaMarkerApplication
import com.example.spacecraft10.db.AgenciaMarkerDao
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mMap: GoogleMap
    private lateinit var database: AgenciaMarkerApplication.AppDatabase
    private lateinit var agenciaDao: AgenciaMarkerDao

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener la instancia de la base de datos
        database = AgenciaMarkerApplication.database
        agenciaDao = database.agenciaDao()

        // Configurar el fragmento del mapa
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
            ?: SupportMapFragment.newInstance().also {
                childFragmentManager.beginTransaction().replace(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

        // Inicializar FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        cargarMarcadores()
        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        // Verificar y mostrar la ubicación actual
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
            obtenerUbicacionActual()
        } else {
            // Solicitar permisos si no se han otorgado
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    private fun obtenerUbicacionActual() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Si la ubicación está disponible
                location?.let {
                    val ubicacionActual = LatLng(it.latitude, it.longitude)

                    // Agregar un marcador para la ubicación actual si deseas
                    mMap.addMarker(
                        MarkerOptions()
                            .position(ubicacionActual)
                            .title("Ubicación Actual")
                    )
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(ubicacionActual, 12f),
                        4000,
                        null
                    )
                }
            }
    }

    private fun cargarMarcadores() {
        lifecycleScope.launch(Dispatchers.IO) {
            val agencias = agenciaDao.getAllAgencias() // Obtenemos todas las agencias de la base de datos
            withContext(Dispatchers.Main) {
                agencias.forEach { agencia ->
                    Log.d("Agencia", agencia.nombre)
                    val posicion = LatLng(agencia.latitud, agencia.longitud)
                    // Añadimos un marcador en el mapa para cada agencia
                    mMap.addMarker(
                        MarkerOptions()
                            .position(posicion)
                            .title(agencia.nombre)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
