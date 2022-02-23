package com.dt.composedatepicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarMonthViewOneColumn(
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
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight(0.85f)
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = monthList) { item ->
            MonthItemOneColumn(
                month = item,
                index = item.index,
                selectedMonth = selectedMonth.name,
                setMonth = setMonth,
                minMonth = minMonth,
                maxMonth = maxMonth,
                minYear = minYear,
                maxYear = maxYear,
                selectedYear = selectedYear,
                setShowMonths = setShowMonths,
                showOnlyMonth = showOnlyMonth,
                themeColor = themeColor,
            )
        }
    }
}

@Composable
fun MonthItemOneColumn(
    month: MonthData,
    selectedMonth: String,
    setMonth: (MonthData) -> Unit,
    index: Int,
    minMonth: Int,
    maxMonth: Int,
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    setShowMonths: (Boolean) -> Unit,
    showOnlyMonth: Boolean,
    themeColor: Color,
) {
    val enabled = checkDate(
        minYear = minYear,
        maxYear = maxYear,
        selectedYear = selectedYear,
        maxMonth = maxMonth,
        minMonth = minMonth,
        numberOfElement = index
    )

    Box(modifier = Modifier
        .padding(vertical = 6.dp)
        .clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            enabled = enabled
        ) {
            setMonth(month)
            if (!showOnlyMonth) {
                setShowMonths(false)
            }
        }) {
        Text(
            text = index.plus(1).toString(),
            fontSize = if (month.name == selectedMonth) 35.sp else 30.sp,
            color = if (enabled && month.name == selectedMonth) themeColor
            else if (enabled) Color.Black
            else Color.Gray
        )
    }
}

private fun checkDate(
    minYear: Int,
    maxYear: Int,
    selectedYear: Int,
    minMonth: Int,
    maxMonth: Int,
    numberOfElement: Int,
): Boolean {

    if (minYear == maxYear) return numberOfElement in minMonth..maxMonth
    if (selectedYear == minYear) {
        return numberOfElement >= minMonth
    } else if (selectedYear == maxYear) {
        if (numberOfElement > maxMonth) return false
    }
    return true
}