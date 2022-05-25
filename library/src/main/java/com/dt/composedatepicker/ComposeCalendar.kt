package com.dt.composedatepicker

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import java.text.DateFormatSymbols
import java.util.*

@Composable
fun ComposeCalendar(
    minDate: Date? = null,
    maxDate: Date? = null,
    initialDate: Date? = null,
    locale: Locale = Locale.getDefault(),
    title: String = "",
    listener: SelectDateListener,
    showOnlyMonth: Boolean = false,
    showOnlyYear: Boolean = false,
    themeColor: Color = Color(0xFF614FF0),
    unselectedColor: Color = Color.Black,
    negativeButtonTitle: String = "CANCEL",
    positiveButtonTitle: String = "OK",
    buttonTextSize: TextUnit = TextUnit.Unspecified,
    monthViewType: MonthViewType? = MonthViewType.ONLY_MONTH
) {
    if (showOnlyMonth && showOnlyYear) {
        throw IllegalStateException("'showOnlyMonth' and 'showOnlyYear' states cannot be true at the same time")
    } else {

        var minYear = 1970
        var minMonth = 0
        var maxYear = 2100
        var maxMonth = 11

        var initialCalendar = Calendar.getInstance(locale)
        var currentMonth = initialCalendar.get(Calendar.MONTH)
        var currentYear = initialCalendar.get(Calendar.YEAR)

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
        initialDate?.let {
            initialCalendar.time = initialDate
            currentMonth = initialCalendar.get(Calendar.MONTH)
            currentYear = initialCalendar.get(Calendar.YEAR)
        }


        if (minYear > currentYear) {
            currentYear = minYear
        }
        if (maxYear < currentYear) {
            currentYear = maxYear
        }

        val months = (DateFormatSymbols(locale).shortMonths).toList()
        val monthList = months.mapIndexed { index, name ->
            MonthData(name = name, index = index)
        }
        val (selectedMonth, setMonth) = remember {
            mutableStateOf(
                MonthData(
                    name = DateFormatSymbols(locale).shortMonths[currentMonth],
                    index = currentMonth
                )
            )
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
            calendarDate.set(
                Calendar.DAY_OF_MONTH,
                1
            ) //For example, if the date is 30 march and we click february, then it shows 2March because february does not have 30 days, so we set default day to 1
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

        Card(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                CalendarHeader(
                    selectedMonth = selectedMonth,
                    selectedYear = selectedYear,
                    showMonths = showMonths,
                    setShowMonths = setShowMonths,
                    title = title,
                    showOnlyMonth = showOnlyMonth,
                    showOnlyYear = showOnlyYear,
                    themeColor = themeColor,
                    monthViewType = monthViewType
                )
                Crossfade(targetState = showMonths) {
                    when (it) {
                        true -> {
                            if (monthViewType == MonthViewType.ONLY_NUMBER_ONE_COLUMN) {
                                CalendarMonthViewOneColumn(
                                    selectedMonth = selectedMonth,
                                    setMonth = setMonth,
                                    minMonth = minMonth,
                                    maxMonth = maxMonth,
                                    setShowMonths = setShowMonths,
                                    minYear = minYear,
                                    maxYear = maxYear,
                                    selectedYear = selectedYear,
                                    monthList = monthList,
                                    showOnlyMonth = showOnlyMonth,
                                    themeColor = themeColor,
                                    unselectedColor = unselectedColor
                                )
                            } else {
                                CalendarMonthView(
                                    selectedMonth = selectedMonth,
                                    setMonth = setMonth,
                                    minMonth = minMonth,
                                    maxMonth = maxMonth,
                                    setShowMonths = setShowMonths,
                                    minYear = minYear,
                                    maxYear = maxYear,
                                    selectedYear = selectedYear,
                                    monthList = monthList,
                                    showOnlyMonth = showOnlyMonth,
                                    themeColor = themeColor,
                                    unselectedColor = unselectedColor,
                                    monthViewType = monthViewType
                                )
                            }
                        }
                        false -> CalendarYearView(
                            selectedYear = selectedYear,
                            setYear = setYear,
                            minYear = minYear,
                            maxYear = maxYear,
                            themeColor = themeColor
                        )
                    }
                }
                CalendarBottom(
                    onPositiveClick = { listener.onDateSelected(selectedDate) },
                    onCancelClick = { listener.onCanceled() },
                    themeColor = themeColor,
                    buttonTextSize = buttonTextSize,
                    negativeButtonTitle = negativeButtonTitle,
                    positiveButtonTitle = positiveButtonTitle
                )
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