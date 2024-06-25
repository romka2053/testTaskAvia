package com.roman.search

import android.app.Dialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.roman.core.LoadingState
import com.roman.core.MyLinearLayoutManager
import com.roman.core.ParcelableSearchSetting
import com.roman.search.databinding.CalendarBinding
import com.roman.search.databinding.FragmentSearchBinding
import com.roman.search.di.SearchToAllTickets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val adapter = DirectFlightsAdapter()
    private val viewModel: SearchFragmentViewModel by viewModels()
    private val timeNow = Calendar.getInstance().time.time
    private var calendarThere = timeNow
    private var calendarHere: Long = 0L



    @Inject
    lateinit var navigate: SearchToAllTickets

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(FROM_TEXT)?.let { text -> viewModel.setRoutFrom(text)   }
            it.getString(TO_TEXT)?.let { text -> viewModel.setRouteTo(text)}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.stateLoading.collect {
                when (it) {
                    LoadingState.Loading -> {}
                    LoadingState.Loaded -> {
                        setText()
                        setAdapter()
                        setDateFromViewModel()
                        setListener()
                    }

                    is LoadingState.Error -> {}
                }
            }
        }
    }

    private fun setText() {
        binding.editTextFromSearchFragment.setText(viewModel.routFrom)
        binding.editTextToSearchFragment.setText(viewModel.routTo)
    }


    private fun setListener() {
        binding.backImage.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.dateChip.setOnClickListener {
            openCalendar(TypeDate.THERE)
        }
        binding.hereChip.setOnClickListener {
            if (calendarHere != 0L) clearCalendarHere()
            else openCalendar(TypeDate.HERE)
        }
        binding.replaceIcon.setOnClickListener {
            viewModel.reverse(
                binding.editTextFromSearchFragment.text.toString(),
                binding.editTextToSearchFragment.text.toString()
            )
            setText()
        }
        binding.clearIcon.setOnClickListener {
            viewModel.clearTo()
            setText()
        }
        binding.buttonViewAll.setOnClickListener {
            clickAllTickets()
        }

    }

    private fun clickAllTickets() {
        if (binding.editTextFromSearchFragment.text.toString() == "") {
            binding.editTextFromSearchFragment.error= getString(com.roman.core.R.string.input_from)
        }else if (binding.editTextToSearchFragment.text.toString()=="")
        {
            binding.editTextToSearchFragment.error= getString(com.roman.core.R.string.input_to)
        }else
        {
            viewModel.setRoutFrom( binding.editTextFromSearchFragment.text.toString())
            viewModel.setRouteTo(binding.editTextToSearchFragment.text.toString())
            val parcelableSetting = ParcelableSearchSetting(
                routeFrom = viewModel.routFrom,
                routeTo = viewModel.routTo,
                dateFrom = viewModel.getFromDayAndMouth(),
                dateTo = viewModel.getToDayAndMouth(),
                countPeople = 1
            )
            navigate.navigate(findNavController(), parcelableSetting)
        }

    }

    private fun setDateFromViewModel() {
        if (viewModel.dateFrom != 0L) calendarThere = viewModel.dateFrom
        if (viewModel.dateTo != 0L) calendarHere = viewModel.dateTo

        setDateThere(calendarThere, TypeDate.THERE)
        setDateThere(calendarHere, TypeDate.HERE)
    }

    private fun setAdapter() {
        binding.recommendationsList.adapter = adapter
        val listItem = viewModel.ticketsRecommendationsList
        adapter.setData(listItem)
        binding.recommendationsList.layoutManager = MyLinearLayoutManager(requireContext())
    }

    private fun openCalendar(type: TypeDate) {
        var time: Long = when (type) {
            TypeDate.THERE -> {
                calendarThere
            }

            TypeDate.HERE -> {
                if (calendarHere == 0L) {
                    Calendar.getInstance().time.time
                } else {
                    calendarHere
                }
            }
        }
        val dialog = Dialog(requireContext(), com.roman.core.R.style.CustomAlertDialog)
        dialog.setCancelable(true)
        val bindingCalendar = CalendarBinding.inflate(layoutInflater, binding.root, false)
        bindingCalendar.calendarView.minDate = if (type == TypeDate.HERE) calendarThere else timeNow
        bindingCalendar.calendarView.setDate(time, true, true)
        bindingCalendar.buttonCancel.setOnClickListener {
            dialog.cancel()
        }
        bindingCalendar.calendarView.setOnDateChangeListener { view, i, i2, i3 ->
            view.date = Calendar.getInstance()
                .apply {
                    set(i, i2, i3)
                }.timeInMillis
        }
        bindingCalendar.buttonChoose.setOnClickListener {
            time = bindingCalendar.calendarView.date
            setDateThere(time, type)
            dialog.cancel()
        }

        dialog.setContentView(bindingCalendar.root)
        dialog.show()
    }

    private fun setDateThere(calendar: Long, type: TypeDate) {

        val formatDate = SimpleDateFormat("d MMM, EE", Locale.getDefault())
        val date = formatDate.format(calendar).replace(".", "")
        val text = SpannableStringBuilder(date)
        val bias = if (Locale.getDefault().language == "en") 5 else 4
        val style = ForegroundColorSpan(requireContext().getColor(com.roman.core.R.color.grey6))
        text.setSpan(style, date.count() - bias, date.count(), Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        when (type) {
            TypeDate.THERE -> {
                calendarThere = calendar
                binding.dateChip.text = text
                viewModel.setDateFrom(calendar)
                calendarHere.let {
                    if (calendar / 86400 > it / 86400 && it != 0L) clearCalendarHereError()
                }
            }

            TypeDate.HERE -> {
                if (calendarThere <= calendar) {
                    calendarHere = calendar
                    binding.hereChip.text = text
                    binding.hereChip.setChipIconResource(com.roman.core.R.drawable.close_chip_icon)
                    viewModel.setDateTo(calendar)
                } else
                    if (calendar == 0L) {
                        clearCalendarHere()
                    } else {
                        clearCalendarHereError()
                    }
            }
        }
    }

    private fun clearCalendarHere() {
        calendarHere = 0L
        viewModel.setDateTo(0L)
        binding.hereChip.text = getString(R.string.plus_here)
        binding.hereChip.setChipIconResource(com.roman.core.R.drawable.plus_icon)

    }

    private fun clearCalendarHereError() {
        clearCalendarHere()
        val snack =
            Snackbar.make(binding.root, getString(R.string.here_error), Snackbar.LENGTH_LONG)
        snack.show()
    }

    companion object {
        private enum class TypeDate {
            THERE,
            HERE
        }

        const val FROM_TEXT = "from_text"
        const val TO_TEXT = "to_text"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}