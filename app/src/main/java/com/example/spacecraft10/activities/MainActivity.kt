package com.example.spacecraft10.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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

        viewModel = ViewModelProvider(this)[ViewModelSelf::class.java]

        val retrofitService = RetrofitService.RetrofitServiceFactory.makeRetrofitService()

        val repository = SpacecraftRepository(retrofitService)

        lifecycleScope.launch {

            val allSpacecrafts = repository.getAllSpacecrafts()

            viewModel.actualizarLista(allSpacecrafts)

        }

        if (savedInstanceState == null) {
            val spacecraftFragment = SpacecraftFragment.newInstance()
            replaceFragment(spacecraftFragment)
        }


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
            }

            false
        }



    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}