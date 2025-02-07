package br.com.digital.order.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import br.com.digital.order.R
import br.com.digital.order.ui.theme.Themes
import br.com.digital.order.utils.colorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    selectedValue: String,
    options: List<String>,
    label: String,
    isError: Boolean = false,
    onValueChangedEvent: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(value = false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Description(description = label) },
            isError = isError,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = label,
                    tint = Themes.colors.primary
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = colorTheme(error = isError),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(size = Themes.size.spaceSize16),
            textStyle = Themes.typography.description()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(color = Themes.colors.background)
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Description(description = it) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(it)
                    },
                )
            }
        }
    }
}
