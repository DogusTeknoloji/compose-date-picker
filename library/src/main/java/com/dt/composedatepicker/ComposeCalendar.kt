package com.dt.composedatepicker

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.text.DateFormatSymbols
import java.util.*

@Composable
fun ComposeCalendar(
    minDate: Date? = null,
    maxDate: Date? = null,
    locale: Locale = Locale.getDefault(),
    title: String = "",
    listener: SelectDateListener,
    showOnlyMonth: Boolean = false,
    showOnlyYear: Boolean = false,
    themeColor:Color = Color(0xFF614FF0),
    negativeButtonTitle:String = "CANCEL",
    positiveButtonTitle:String = "OK",
    monthViewType: MonthViewType? = MonthViewType.ONLY_MONTH
) {
    if (showOnlyMonth && showOnlyYear) {
        throw IllegalStateException("'showOnlyMonth' and 'showOnlyYear' states cannot be true at the same time")
    } else {

        var minYear = 1970
        var minMonth = 0
        var maxYear = 2100
        var maxMonth = 11
        minDate?.let {
            val calendarMin = Calendar.getInstance()
            calendarMin.time = minDate
            minMonth = calendarMin.get(Calendar.MONTH)
            minYear = calendarMin.get(Calendar.YEAR)
        }
        maxDate?.let {
            val calendarMax = Calendar.getInstance()
            calendarMax.time = maxDate
            maxMonth = calendarMax.get(Calendar.MONTH)
            maxYear = calendarMax.get(Calendar.YEAR)
        }

        val (height, setHeight) = remember {
            mutableStateOf(0)
        }

        val calendar = Calendar.getInstance(locale)
        val currentMonth = calendar.get(Calendar.MONTH)
        var currentYear = calendar.get(Calendar.YEAR)

        if (minYear>currentYear){
            currentYear = minYear
        }
        if (maxYear<currentYear){
            currentYear = maxYear
        }

        val months = (DateFormatSymbols(locale).shortMonths).toList()
        val monthList = months.mapIndexed { index, name ->
            MonthData(name = name, index = index)
        }
        val (selectedMonth, setMonth) = remember {
            mutableStateOf(MonthData(name = DateFormatSymbols(locale).shortMonths[currentMonth],
                index = currentMonth))
        }
        val (selectedYear, setYear) = remember {
            mutableStateOf(currentYear)
        }
        val (showMonths, setShowMonths) = remember {
            mutableStateOf(!showOnlyYear)
        }

        val calendarDate = Calendar.getInstance()
        var selectedDate by remember {
            mutableStateOf(calendarDate.time)
        }

        LaunchedEffect(key1 = selectedYear, key2 = selectedMonth) {
            calendarDate.set(Calendar.YEAR, selectedYear)
            calendarDate.set(Calendar.MONTH, selectedMonth.index)
            selectedDate = calendarDate.time
        }
        LaunchedEffect(key1 = selectedYear) {
            if (selectedYear == minYear) {
                if (selectedMonth.index < minMonth) {
                    setMonth(monthList[minMonth])
                }
            }
            if (selectedYear == maxYear) {
                if (selectedMonth.index > maxMonth) {
                    setMonth(monthList[maxMonth])
                }
            }
        }

        Card(modifier = Modifier
            .fillMaxWidth(0.9f)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                CalendarHeader(selectedMonth = selectedMonth.name,
                    selectedYear = selectedYear,
                    showMonths = showMonths,
                    setShowMonths = setShowMonths,
                    title = title,
                    showOnlyMonth = showOnlyMonth,
                    showOnlyYear = showOnlyYear,
                    themeColor=themeColor)
                Crossfade(targetState = showMonths) {
                    when (it) {
                        true -> {
                            if (monthViewType == MonthViewType.ONLY_NUMBER_ONE_COLUMN){
                                CalendarMonthViewOneColumn(selectedMonth = selectedMonth,
                                    setMonth = setMonth,
                                    minMonth = minMonth,
                                    maxMonth = maxMonth,
                                    setShowMonths = setShowMonths,
                                    minYear = minYear,
                                    maxYear = maxYear,
                                    selectedYear = selectedYear,
                                    monthList = monthList,
                                    setHeight = setHeight,
                                    showOnlyMonth = showOnlyMonth,
                                    themeColor=themeColor
                                )
                            }
                            else{
                                CalendarMonthView(selectedMonth = selectedMonth,
                                    setMonth = setMonth,
                                    minMonth = minMonth,
                                    maxMonth = maxMonth,
                                    setShowMonths = setShowMonths,
                                    minYear = minYear,
                                    maxYear = maxYear,
                                    selectedYear = selectedYear,
                                    monthList = monthList,
                                    setHeight = setHeight,
                                    showOnlyMonth = showOnlyMonth,
                                    themeColor=themeColor,
                                    monthViewType = monthViewType)
                            }
                        }
                        false -> CalendarYearView(selectedYear = selectedYear,
                            setYear = setYear,
                            minYear = minYear,
                            maxYear = maxYear,
                            height = height,
                            themeColor=themeColor)
                    }
                }
                CalendarBottom(onPositiveClick = { listener.onDateSelected(selectedDate) },
                    onCancelClick = { listener.onCanceled() },
                    themeColor=themeColor,
                    negativeButtonTitle=negativeButtonTitle,
                    positiveButtonTitle=positiveButtonTitle)
            }
        }
    }
}
enum class MonthViewType {
    ONLY_MONTH,
    ONLY_NUMBER,
    ONLY_NUMBER_ONE_COLUMN,
    BOTH_NUMBER_AND_MONTH
}