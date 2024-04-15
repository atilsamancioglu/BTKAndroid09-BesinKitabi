package com.atilsamancioglu.besinkitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.atilsamancioglu.besinkitabi.databinding.BesinRecyclerRowBinding
import com.atilsamancioglu.besinkitabi.model.Besin
import com.atilsamancioglu.besinkitabi.util.gorselIndir
import com.atilsamancioglu.besinkitabi.util.placeholderYap
import com.atilsamancioglu.besinkitabi.view.BesinListesiFragmentDirections

class BesinRecyclerAdapter(val besinListesi : ArrayList<Besin>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {

    class BesinViewHolder(var view : BesinRecyclerRowBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val recyclerRowBinding: BesinRecyclerRowBinding = BesinRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BesinViewHolder(recyclerRowBinding)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }


    fun besinListesiniGuncelle(yeniBesinListesi: List<Besin>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {

        holder.view.isim.text = besinListesi.get(position).besinIsim
        holder.view.kalori.text = besinListesi.get(position).besinKalori

        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.imageView.gorselIndir(besinListesi.get(position).besinGorsel, placeholderYap(holder.itemView.context))

    }


}