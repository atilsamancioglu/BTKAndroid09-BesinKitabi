package com.atilsamancioglu.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.atilsamancioglu.besinkitabi.R
import com.atilsamancioglu.besinkitabi.databinding.FragmentBesinDetayiBinding
import com.atilsamancioglu.besinkitabi.databinding.FragmentBesinListesiBinding
import com.atilsamancioglu.besinkitabi.util.gorselIndir
import com.atilsamancioglu.besinkitabi.util.placeholderYap
import com.atilsamancioglu.besinkitabi.viewmodel.BesinDetayiViewModel


class BesinDetayiFragment : Fragment() {

    private var _binding: FragmentBesinDetayiBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : BesinDetayiViewModel
    private var besinId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinDetayiBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId
        }

        viewModel = ViewModelProvider(this)[BesinDetayiViewModel::class.java]
        viewModel.roomVerisiniAl(besinId)
        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner) { besin ->
                binding.besinIsim.text = besin.besinIsim
                binding.besinKalori.text = besin.besinKalori
                binding.besinKarbonhidrat.text = besin.besinKarbonhidrat
                binding.besinProtein.text = besin.besinProtein
                binding.besinYag.text = besin.besinYag
                binding.besinImage.gorselIndir(besin.besinGorsel, placeholderYap(requireContext()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}