package com.dt.composedatepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalendarHeader(
    selectedMonth: MonthData,
    selectedYear: Int,
    showMonths: Boolean,
    setShowMonths: (Boolean) -> Unit,
    title: String,
    calendarType: CalendarType,
    themeColor:Color,
    monthViewType: MonthViewType?
) {
    val monthAsNumber = String.format("%02d",selectedMonth.index.plus(1))
    val monthText = if (monthViewType==MonthViewType.ONLY_MONTH) selectedMonth.name.uppercase() else monthAsNumber
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(themeColor),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 16.dp),
            color = Color.White)
        Row() {
            if (calendarType != CalendarType.ONLY_YEAR) {
                Text(
                    text = monthText,
                    fontSize = 35.sp,
                    modifier = Modifier
                        .padding(bottom = 20.dp,
                            start = if (calendarType == CalendarType.ONLY_MONTH) 0.dp else 30.dp,
                            end = if (calendarType == CalendarType.ONLY_MONTH) 0.dp else 10.dp)
                        .clickable { setShowMonths(true) },
                    color = if (showMonths) Color.White else Color.LightGray)
            }
            if (calendarType != CalendarType.ONLY_MONTH && calendarType != CalendarType.ONLY_YEAR){
                Text(text = "/",fontSize = 35.sp,color = Color.White)
            }
            if (calendarType != CalendarType.ONLY_MONTH ) {
                Text(text = selectedYear.toString(),
                    fontSize = 35.sp,
                    modifier = Modifier
                        .padding(bottom = 20.dp,
                            start = if (calendarType == CalendarType.ONLY_YEAR) 0.dp else 10.dp,
                            end = if (calendarType == CalendarType.ONLY_YEAR) 0.dp else 30.dp)
                        .clickable { setShowMonths(false) },
                    color = if (showMonths) Color.LightGray else Color.White)
            }
        }
    }
}