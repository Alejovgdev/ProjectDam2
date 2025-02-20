package com.example.spacecraft10.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.spacecraft10.R
import com.example.spacecraft10.activities.MainActivity


class SharedPrefFragment : PreferenceFragmentCompat() {

    // Este método se llama cuando el fragmento se crea y se configura la interfaz de preferencias
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        // Buscamos la preferencia de tipo ListPreference con la clave "font_size"
        val fontSizePreference = findPreference<ListPreference>("font_size")
        fontSizePreference?.setOnPreferenceChangeListener { _, newValue ->
            val newSize = newValue as String
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

            // Guardamos el nuevo tamaño de fuente en las SharedPreferences
            with(sharedPreferences.edit()) {
                putString("font_size", newSize)
                apply()
            }


            restartMainActivity()
            true
        }
    }

    private fun restartMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().finish()
        startActivity(intent)
    }
}
