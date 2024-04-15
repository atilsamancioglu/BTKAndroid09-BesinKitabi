package com.atilsamancioglu.besinkitabi.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atilsamancioglu.besinkitabi.model.Besin
import com.atilsamancioglu.besinkitabi.roomdb.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : AndroidViewModel(application) {

    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid : Int){
        viewModelScope.launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin
        }
    }
}