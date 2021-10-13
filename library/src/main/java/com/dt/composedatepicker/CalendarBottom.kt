package com.dt.composedatepicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CalendarBottom(onPositiveClick: () -> Unit, onCancelClick: () -> Unit,themeColor:Color,negativeButtonTitle:String,positiveButtonTitle:String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 40.dp)
        .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.End) {
        Text(text = negativeButtonTitle,
            color = themeColor,
            modifier = Modifier
                .padding(end = 20.dp)
                .clickable { onCancelClick() })
        Text(text = positiveButtonTitle,
            color = themeColor,
            modifier = Modifier.clickable { onPositiveClick() })
    }
}