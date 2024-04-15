package com.atilsamancioglu.besinkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.atilsamancioglu.besinkitabi.model.Besin
import com.atilsamancioglu.besinkitabi.roomdb.BesinDatabase
import com.atilsamancioglu.besinkitabi.service.BesinAPIServis
import com.atilsamancioglu.besinkitabi.util.OzelSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class BesinListesiViewModel(application: Application) : AndroidViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L

    private val besinApiServis = BesinAPIServis()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshData() {

        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            //Sqlite'tan çek
            verileriSQLitetanAl()
        } else {
            verileriInternettenAl()
        }
    }

    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriSQLitetanAl(){
        besinYukleniyor.value = true

        viewModelScope.launch {

            val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Besinleri Room'dan Aldık",Toast.LENGTH_LONG).show()

        }

    }

    private fun verileriInternettenAl(){
        besinYukleniyor.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val besinler = besinApiServis.getData()
            withContext(Dispatchers.Main) {
                besinYukleniyor.value = false
                sqliteSakla(besinler)
                Toast.makeText(getApplication(),"Besinleri Internet'ten Aldık",Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        besinHataMesaji.value = false
        besinYukleniyor.value = false
    }

    private fun sqliteSakla(besinListesi: List<Besin>){

        viewModelScope.launch {

            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinListesi.toTypedArray())
            var i = 0
            while (i < besinListesi.size){
                besinListesi[i].uuid = uuidListesi[i].toInt()
                i = i + 1
            }
            besinleriGoster(besinListesi)
        }

        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }

}