package com.dt.composedatepicker

import java.util.*

interface SelectDateListener {
    fun onDateSelected(date: Date)
    fun onCanceled()
}