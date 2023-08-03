package com.iggydev.carselection.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iggydev.carselection.common.MyColors

@Composable
fun CustomTextField(
    state: MutableState<String>,
    placeholder: String,
    onValidate: () -> Unit = {},
    valid: Boolean = true,
    vectorResource: Int? = null,
    fontWeight: FontWeight = FontWeight.Medium,
    fontSize: TextUnit = 11.sp,
    modifier: Modifier
) {
    // to clear focus
    val focusManager = LocalFocusManager.current
    BasicTextField(
        value = state.value,
        onValueChange = { entered: String -> state.value = entered },
        modifier = modifier,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontWeight = fontWeight,
            fontSize = fontSize
        ),
        maxLines = 1,
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.Center) {
                if (state.value.isEmpty() && valid) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        fontWeight = fontWeight,
                        fontSize = fontSize
                    )
                } else if (state.value.isEmpty() && !valid) {
                    Text(
                        text = placeholder,
                        color = Color.Red,
                        fontWeight = fontWeight,
                        fontSize = fontSize
                    )
                }
                if (vectorResource != null) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = vectorResource),
                        contentDescription = "inner image",
                        tint = MyColors.davysGray,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(horizontal = 15.dp)
                    )
                }
                innerTextField()
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            onValidate()
        })
    )
}