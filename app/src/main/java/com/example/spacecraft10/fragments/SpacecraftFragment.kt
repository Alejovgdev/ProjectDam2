package com.example.spacecraft10.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacecraft10.R
import com.example.spacecraft10.adapters.SpacecraftAdapter
import com.example.spacecraft10.data.responseSpacecraft.RetrofitService
import com.example.spacecraft10.data.responseSpacecraft.SpacecraftRepository
import com.example.spacecraft10.databinding.FragmentSpacecraftBinding
import com.example.spacecraft10.viewModel.ViewModelSelf
import kotlinx.coroutines.launch


class SpacecraftFragment : Fragment() {

    private lateinit var binding: FragmentSpacecraftBinding
    private lateinit var viewModel: ViewModelSelf
    private lateinit var adapter: SpacecraftAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSpacecraftBinding.inflate(inflater, container, false)
        adapter = SpacecraftAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        viewModel = ViewModelProvider(requireActivity())[ViewModelSelf::class.java]
        viewModel.lista.observe(viewLifecycleOwner){lista ->
            // Cuando la lista cambia, se actualiza el adaptador con la nueva lista
            adapter.submitList(lista)
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SpacecraftFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}