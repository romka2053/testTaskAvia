package com.roman.alltickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.roman.alltickets.databinding.FragmentAllTicketsBinding
import com.roman.core.CustomItemDecorationVertical
import com.roman.core.LoadingState
import com.roman.core.MyLinearLayoutManager
import com.roman.core.ParcelableSearchSetting
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllTicketsFragment : Fragment() {
    private val viewModel: AllTicketsViewModel by viewModels()
    private var _binding: FragmentAllTicketsBinding? = null
    private val binding get() = _binding!!
    private val adapter = TicketsAllAdapter()
    private var parcelableSetting: ParcelableSearchSetting? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            parcelableSetting = if (android.os.Build.VERSION.SDK_INT >= 33) {
                it.getParcelable(RUT_PARCELABLE, ParcelableSearchSetting::class.java)
            } else {
                it.getParcelable(RUT_PARCELABLE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllTicketsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.stateLoading.collect {
                when (it) {
                    LoadingState.Loading -> {
                        //Loader
                    }

                    LoadingState.Loaded -> {
                        setHeader()
                        setAdapter()
                    }

                    is LoadingState.Error -> {}
                }


            }
        }

    }

    private fun setHeader() {
        parcelableSetting?.let {
            val textFromTo = it.routeFrom + "–" + it.routeTo
            val countPeople = it.countPeople
            val dateFrom = it.dateFrom
            val getDateTo = it.dateTo
            val dateTo = if (getDateTo != "") ", назад:" + getDateTo else ""
            val textBottom = dateFrom + ", " + countPeople + " пассажир" + dateTo

            binding.townFromTownTo.text = textFromTo
            binding.infoText.text = textBottom

            binding.imageBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }


    }

    private fun setAdapter() {
        val tickets = viewModel.tickets.tickets
        adapter.setData(tickets)
        binding.fullInformTicketList.adapter = adapter
        binding.fullInformTicketList.layoutManager = MyLinearLayoutManager(requireContext())
        AppCompatResources.getDrawable(
            requireContext(),
            com.roman.core.R.drawable.full_tickets_decoration
        )?.let { draw ->
            binding.fullInformTicketList.addItemDecoration(
                CustomItemDecorationVertical(
                    draw
                )
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val RUT_PARCELABLE = "put_parcelable"
    }

}