package com.roman.mainsearch.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.roman.core.CustomItemDecorationHorizontal
import com.roman.core.LoadingState
import com.roman.core.MyKeyListener
import com.roman.mainsearch.bottomSheet.MyBottomSheetFragment
import com.roman.mainsearch.databinding.FragmentMainSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainSearchFragment : Fragment() {
    private var _binding: FragmentMainSearchBinding? = null
    private val binding get() = _binding!!
    private val adapter = MusicFlyAdapter()
    private val viewModel: MainSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.stateLoading.collect {
                when (it) {
                    LoadingState.Loading -> {}
                    LoadingState.Loaded -> {
                        setAdapter()
                        binding.editTextFrom.setText(viewModel.getSharedPreference())
                        deleteFocus()
                        binding.editTextTo.setOnClickListener {
                            clickEditTo()
                        }
                    }
                    is LoadingState.Error -> {}
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        binding.editTextTo.setText(viewModel.inputTo)
    }


    private fun clickEditTo(){
        val textFrom = binding.editTextFrom.text.toString()
        viewModel.setFrom(textFrom)
        if (textFrom != "") {
            viewModel.setSharedPreference(textFrom)
             val bottomSheet = MyBottomSheetFragment(viewModel)
            bottomSheet.show(parentFragmentManager, "bottomSheet")

        }else
        {
            binding.editTextFrom.error = getString(com.roman.core.R.string.input_from)
        }
    }
    private fun deleteFocus() {
        binding.editTextFrom.setOnKeyListener(
            MyKeyListener.setListener(requireContext(),binding.editTextFrom) {  editDeleteFocus() }
        )
    }

    private fun editDeleteFocus() {
        viewModel.setFrom(binding.editTextFrom.text.toString())
        binding.editTextFrom.clearFocus()
    }


    private fun setAdapter() {
        binding.listMusicFly.adapter = adapter

        AppCompatResources.getDrawable(
            requireContext(),
            com.roman.core.R.drawable.decoration_list
        )?.let {
            binding.listMusicFly.addItemDecoration(
                CustomItemDecorationHorizontal(
                    it
                )
            )
        }

        val data = viewModel.musicFlay
        adapter.setData(data)
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}