package com.example.oxeanbits_challenge.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oxeanbits_challenge.R
import com.example.oxeanbits_challenge.ui.theme.Typography

@Composable
fun OxeanbitsSnackBar(
    imagePainter: Painter? = null,
    imageVector: ImageVector? = null,
    message: String,
    backgroundColor: Color = Color.Red,
    textButton: String? = null,
    textButtonColor: Color = Color.White,
    onClick: () -> Unit,
    dismiss: () -> Unit,
    duration: SnackbarDuration = SnackbarDuration.Short
){

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarHostState) {
        when(snackbarHostState.showSnackbar(message, duration = duration)){
            SnackbarResult.ActionPerformed -> {}
            SnackbarResult.Dismissed -> {dismiss()}
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        SnackbarHost (
            hostState = snackbarHostState,
            modifier = Modifier
        ){ _ ->
            Snackbar(
                modifier = Modifier.padding(8.dp),
                containerColor = backgroundColor,
                contentColor = Color.White,
                action = {
                    if (textButton != null) {
                        Text(
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .clickable { onClick() },
                            text = textButton,
                            style = Typography.headlineSmall,
                            color = textButtonColor
                        )
                    }
                }
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (imageVector != null) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp),
                            imageVector = imageVector,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    } else if (imagePainter != null){
                        Image(
                            modifier = Modifier
                                .size(32.dp),
                            painter = imagePainter,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        modifier = Modifier
                            .testTag(stringResource(id = R.string.test_tag_text_snackbar)),
                        text = message,
                        style = Typography.headlineSmall,
                        color = Color.White
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun OxeanbitsSnackBarPreview() {
    OxeanbitsSnackBar(
        message = stringResource(id = R.string.lorem_ipsum_dolor),
        backgroundColor = Color.Red,
        textButton = stringResource(id = R.string.preview_variable_button1),
        onClick = {},
        dismiss = {}
    )
}