package com.joseantoniofernandezverdugo.rastreadog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ComprarPerroViewModel : ViewModel()  {
    private val listaPerros = MutableLiveData<MutableMap<String, Any?>?>()

    fun getListaPerros(): LiveData<MutableMap<String, Any?>?> = listaPerros
    fun getPerros() {
        viewModelScope.launch(Dispatchers.IO){
            PerroProvider.getTotalPerros { perrosMap ->
                listaPerros.postValue(perrosMap)
            }
        }
    }
}