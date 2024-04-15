package com.atilsamancioglu.besinkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.atilsamancioglu.besinkitabi.R
import com.atilsamancioglu.besinkitabi.adapter.BesinRecyclerAdapter
import com.atilsamancioglu.besinkitabi.databinding.FragmentBesinListesiBinding
import com.atilsamancioglu.besinkitabi.viewmodel.BesinListesiViewModel


class BesinListesiFragment : Fragment() {

    private var _binding: FragmentBesinListesiBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : BesinListesiViewModel
    private val recyclerBesinAdapter = BesinRecyclerAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBesinListesiBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BesinListesiViewModel::class.java]
        viewModel.refreshData()

        binding.besinListRecycler.layoutManager = LinearLayoutManager(context)
        binding.besinListRecycler.adapter = recyclerBesinAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.besinYukleniyor.visibility = View.VISIBLE
            binding.besinHataMesaji.visibility = View.GONE
            binding.besinListRecycler.visibility = View.GONE
            viewModel.refreshFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()
    }

    fun observeLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner) {
                binding.besinListRecycler.visibility = View.VISIBLE
                recyclerBesinAdapter.besinListesiniGuncelle(it)
        }

        viewModel.besinHataMesaji.observe(viewLifecycleOwner) {
                if (it) {
                    binding.besinHataMesaji.visibility = View.VISIBLE
                    binding.besinListRecycler.visibility = View.GONE
                } else {
                    binding.besinHataMesaji.visibility = View.GONE
                }
        }

        viewModel.besinYukleniyor.observe(viewLifecycleOwner) {
                if (it) {
                    binding.besinListRecycler.visibility = View.GONE
                    binding.besinHataMesaji.visibility = View.GONE
                    binding.besinYukleniyor.visibility = View.VISIBLE
                } else {
                    binding.besinYukleniyor.visibility = View.GONE
                }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}