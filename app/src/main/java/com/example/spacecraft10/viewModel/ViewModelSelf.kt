package com.example.spacecraft10.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacecraft10.data.responseSpacecraft.Results

class ViewModelSelf : ViewModel() {
    private val _lista = MutableLiveData<List<Results>>()
    val lista: LiveData<List<Results>> get() = _lista

    fun actualizarLista(nuevaLista: List<Results>) {
        _lista.value = nuevaLista
    }
}
