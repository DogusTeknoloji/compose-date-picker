package com.dt.calendarwork

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dt.calendarwork.ui.theme.CalendarWorkTheme
import com.dt.composedatepicker.ComposeCalendar
import com.dt.composedatepicker.MonthViewType
import com.dt.composedatepicker.SelectDateListener
import java.util.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarWorkTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, 2022)
    calendar.set(Calendar.MONTH, 1)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val calendarMax = Calendar.getInstance()
    calendarMax.set(Calendar.YEAR, 2032)
    calendarMax.set(Calendar.MONTH, 9)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val (open, setOpen) = remember {
        mutableStateOf(true)
    }

    Box(Modifier
        .fillMaxSize()
        .background(color = Color.Gray), contentAlignment = Alignment.Center) {
        if (open) {
            ComposeCalendar(
                minDate = calendar.time,
                maxDate = calendarMax.time,
                locale = Locale("en"),
                title = "Select Date",
                monthViewType = MonthViewType.ONLY_NUMBER_ONE_COLUMN,
                listener = object : SelectDateListener {
                    override fun onDateSelected(date: Date) {
                        Log.i("DENEME", date.toString())
                    }

                    override fun onCanceled() {
                        setOpen(false)
                    }
                })
        }
    }
}
