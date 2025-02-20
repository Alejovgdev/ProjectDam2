package com.example.spacecraft10.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacecraft10.R
import com.example.spacecraft10.adapters.SpacecraftAdapter
import com.example.spacecraft10.data.responseSpacecraft.RetrofitService
import com.example.spacecraft10.data.responseSpacecraft.SpacecraftRepository
import com.example.spacecraft10.databinding.ActivityMainBinding
import com.example.spacecraft10.fragments.ChatBotFragment
import com.example.spacecraft10.fragments.MapsFragment
import com.example.spacecraft10.fragments.SpacecraftFragment
import com.example.spacecraft10.viewModel.ViewModelSelf
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModelSelf

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización del ViewModel para gestionar los datos de forma eficiente.
        viewModel = ViewModelProvider(this)[ViewModelSelf::class.java]

        // Se crea un servicio Retrofit para realizar solicitudes HTTP a la API.
        val retrofitService = RetrofitService.RetrofitServiceFactory.makeRetrofitService()

        // Se crea un repositorio para interactuar con la API y obtener los datos de las naves espaciales.
        val repository = SpacecraftRepository(retrofitService)

        // Llamada asíncrona a la API para obtener las naves espaciales.
        lifecycleScope.launch {

            val allSpacecrafts = repository.getAllSpacecrafts()

            viewModel.actualizarLista(allSpacecrafts)

        }

        if (savedInstanceState == null) {
            val spacecraftFragment = SpacecraftFragment.newInstance()
            replaceFragment(spacecraftFragment)
        }

        // Configuración del Bottom Navigation para navegar entre diferentes secciones.
        binding.bottomNav.setOnNavigationItemSelectedListener {
            it.isChecked = true
            when(it.itemId){
                R.id.nav_naves->{
                    val spacecraftFragment = SpacecraftFragment.newInstance()
                    replaceFragment(spacecraftFragment)
                }
                R.id.nav_maps->{
                    val mapsFragment = MapsFragment()
                    replaceFragment(mapsFragment)
                }
                R.id.nav_chatBot->{
                    val chatBotFragment = ChatBotFragment()
                    replaceFragment(chatBotFragment)
                }
                R.id.nav_settings->{
                    val intent = Intent(this, PreferencesActivity::class.java)
                    startActivity(intent)
                }
            }

            false
        }



    }
    // Método que ajusta la configuración de la fuente según las preferencias del usuario.
    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase)
        val fontSize = sharedPreferences.getString("font_size", "16")?.toFloatOrNull() ?: 16f

        val config = Configuration(newBase.resources.configuration)
        config.fontScale = fontSize / 16f // Ajustamos el escalado
        val newContext = newBase.createConfigurationContext(config)

        super.attachBaseContext(newContext)
    }

    // Método que reemplaza el fragmento actual por uno nuevo.
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}