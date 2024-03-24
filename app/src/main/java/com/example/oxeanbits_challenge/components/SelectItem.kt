package com.example.oxeanbits_challenge.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.oxeanbits_challenge.ui.theme.Typography


@Composable
fun SelectItem(
    modifier : Modifier,
    textWidth : Dp,
    index: MutableState<Int>,
    array: List<String>,
    colorItems: Color,
    click: (Int, Int) -> Unit
){

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    if (index.value > 0) {
                        index.value -= 1
                        click(index.value + 1, index.value)
                    }else {
                        index.value = array.size - 1
                        click(0, index.value)
                    }
                },
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = null,
            colorFilter = ColorFilter.tint(colorItems)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .width(textWidth),
            text = array[index.value],
            textAlign = TextAlign.Center,
            style = Typography.headlineSmall,
            color = colorItems
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    if (index.value < array.size - 1) {
                        index.value += 1
                        click(index.value - 1, index.value)
                    }else {
                        index.value = 0
                        click(array.size - 1, index.value)
                    }
                },
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            colorFilter = ColorFilter.tint(colorItems)
        )

    }

}