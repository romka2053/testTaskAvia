package com.roman.mainsearch.bottomSheet

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.roman.core.CustomItemDecorationVertical
import com.roman.core.MyKeyListener
import com.roman.core.MyLinearLayoutManager
import com.roman.mainsearch.R
import com.roman.mainsearch.databinding.BottomSheetDialogBinding
import com.roman.mainsearch.di.MainSearchToEmpty
import com.roman.mainsearch.di.MainSearchToSearch
import com.roman.mainsearch.main.MainSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class MyBottomSheetFragment(private val viewModel: MainSearchViewModel) :
    BottomSheetDialogFragment() {

    @Inject
    lateinit var navigateToSearch: MainSearchToSearch

    @Inject
    lateinit var navigateToEmpty: MainSearchToEmpty

    private var _binding: BottomSheetDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val adapter = BottomSheetAdapter { text -> endEdit(text) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        binding.root.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
        binding.root.minimumWidth = Resources.getSystem().displayMetrics.widthPixels
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        setAdapter()
        setInputs()
        addListeners()
    }


    private fun addListeners() {
        binding.clearToText.setOnClickListener {
            binding.editTextToBottomDialog.setText("")
        }
        binding.editTextToBottomDialog.setOnKeyListener(
            MyKeyListener.setListener(requireContext(), binding.editTextToBottomDialog) {
                endEdit(binding.editTextToBottomDialog.text.toString())
            }
        )

        binding.route.setOnClickListener {
            navToEmpty()
        }

        binding.anywhere.setOnClickListener {
            val baseRes = resources
            val config = Configuration(baseRes.configuration)
            config.locale = Locale("ru")
            val localRes = Resources(baseRes.assets, baseRes.displayMetrics, config)
            val text = localRes.getString(R.string.anywhere_text)
            endEdit(text)
        }

        binding.weekend.setOnClickListener {
            navToEmpty()
        }

        binding.hotTickets.setOnClickListener {
            navToEmpty()
        }
    }

    private fun navToEmpty() {
        navigateToEmpty.navigate(findNavController())
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setInputs() {
        binding.editTextFromBottomDialog.setText(viewModel.inputFrom)
        binding.editTextToBottomDialog.setText(viewModel.inputTo)
    }


    private fun setAdapter() {
        binding.recommendationsList.adapter = adapter
        binding.recommendationsList.layoutManager = MyLinearLayoutManager(requireContext())
        AppCompatResources.getDrawable(
            requireContext(),
            com.roman.core.R.drawable.decoration_list_recommedation
        )?.let {
            binding.recommendationsList.addItemDecoration(
                CustomItemDecorationVertical(
                    it
                )
            )
        }
    }


    private fun endEdit(text: String) {
        Log.d("1111", "34356789")
        binding.editTextToBottomDialog.setText(text)
        binding.editTextToBottomDialog.clearFocus()
        if (text != "") {
            lifecycleScope.launch {
                navigateToSearch.navigate(findNavController(), viewModel.inputFrom, text)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                viewModel.setTo(text)
                Log.d("1111", text)
            }

        }
    }


}