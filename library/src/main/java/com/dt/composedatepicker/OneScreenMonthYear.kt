package com.dt.composedatepicker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OneScreenMonthYear(
    monthList: List<MonthData>,
    selectedMonth: MonthData,
    setMonth: (MonthData) -> Unit,
    minMonth: Int,
    maxMonth: Int,
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    setShowMonths: (Boolean) -> Unit,
    showOnlyMonth: Boolean,
    themeColor: Color,
    unselectedColor: Color,
    setYear: (Int) -> Unit,
) {
    Row() {
        CalendarMonthViewOneColumn(
            monthList = monthList,
            selectedMonth = selectedMonth,
            setMonth = setMonth,
            minMonth = minMonth,
            maxMonth = maxMonth,
            minYear = minYear,
            maxYear = maxYear,
            selectedYear = selectedYear,
            setShowMonths = setShowMonths,
            showOnlyMonth = showOnlyMonth,
            themeColor = themeColor,
            unselectedColor = unselectedColor,
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth(0.5f)
                .padding(vertical = 10.dp),
        )
        CalendarYearView(
            selectedYear = selectedYear,
            setYear = setYear,
            minYear = minYear,
            maxYear = maxYear,
            themeColor = themeColor
        )
    }
}