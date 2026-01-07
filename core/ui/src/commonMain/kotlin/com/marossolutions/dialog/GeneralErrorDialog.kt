package com.marossolutions.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorDialog(closeDialog: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = closeDialog) {
                Text("OK")
            }
        },
        title = { Text("Error Dialog") },
        text = { Text("An error occurred") },
    )
}