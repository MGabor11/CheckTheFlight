package com.marossolutions.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/** Validation helper: 2-3 letters, optional space, 1-4 digits (case-insensitive) */
fun isValidFlightNumber(input: String): Boolean {
    val normalized = input.trim().uppercase()
    val regex = Regex("^[A-Z]{2,3}\\s?\\d{1,4}$")
    return regex.matches(normalized)
}

@Composable
fun FlightNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Flight number",
    showValidation: Boolean = true // if false, disables showing error UI
) {
    val focusManager = LocalFocusManager.current

    // Validate current value
    val normalized = value.trim().uppercase()
    val valid = remember(normalized) { isValidFlightNumber(normalized) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { new ->
                // optionally auto-uppercase and remove multiple spaces
                val sanitized = new.uppercase()
                onValueChange(sanitized)
            },
            label = { Text(label) },
            leadingIcon = { Icon(Icons.Default.Flight, contentDescription = "Flight") },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(onClick = { onValueChange("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            isError = showValidation && value.isNotBlank() && !valid,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                keyboardType = KeyboardType.Ascii,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (showValidation && value.isNotBlank() && !valid) Color.Red else MaterialTheme.colorScheme.primary,
                focusedLabelColor = if (showValidation && value.isNotBlank() && !valid) Color.Red else MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier.onFocusChanged { focusState ->
                onFocusChange(focusState.isFocused)
            },
        )

        if (showValidation && value.isNotBlank() && !valid) {
            Text(
                text = "Invalid flight number. Example: “LH123”, “AA 045”",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 6.dp)
            )
        }
    }
}
