package com.dt.composedatepicker

import android.util.DisplayMetrics
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun CalendarYearView(
    selectedYear: Int,
    setYear: (Int) -> Unit,
    minYear: Int,
    maxYear: Int,
    height: Int,
    themeColor:Color
) {
    val years = IntRange(minYear, maxYear).toList()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val selectedIndex = years.indexOf(selectedYear)
    val metrics = LocalContext.current.resources.displayMetrics

    val mHeight = (if (height==0) 5*(metrics.heightPixels)/10 else height) / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    LazyColumn(state = listState, modifier = Modifier
        .fillMaxWidth()
        .requiredHeight((mHeight + 40).dp)  //40dp for padding in MonthView
        ,horizontalAlignment = Alignment.CenterHorizontally) {
        items(years) { year ->
            Text(text = year.toString(),
                fontSize = if (year == selectedYear) 35.sp else 30.sp,
                color = if (year == selectedYear) themeColor else Color.Black,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .clickable {
                        setYear(year)
                    })
        }
        scope.launch {
            listState.animateScrollToItem(selectedIndex)
        }
    }
}