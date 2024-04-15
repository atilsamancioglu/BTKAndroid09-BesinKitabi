package com.atilsamancioglu.besinkitabi.service

import com.atilsamancioglu.besinkitabi.model.Besin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BesinAPIServis {

    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    // BASE_URL -> https://raw.githubusercontent.com/
    // atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json


    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BesinAPI::class.java)

    suspend fun getData() : List<Besin> {
        return api.getBesin()
    }

}