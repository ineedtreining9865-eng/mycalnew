package com.example.mycalnew.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycalnew.viewmodel.MainViewModel

@Composable
fun HomeScreen(onNavigateToSettings: () -> Unit) {
    val viewModel: MainViewModel = viewModel()
    val display by viewModel.display.observeAsState("0")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calculator") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Display area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = display,
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Buttons grid
            val buttonModifier = Modifier
                .weight(1f)
                .fillMaxSize()

            val buttonRows = listOf(
                listOf("C", "⌫", "", "/"),
                listOf("7", "8", "9", "*") ,
                listOf("4", "5", "6", "-") ,
                listOf("1", "2", "3", "+") ,
                listOf("0", ".", "=", "")
            )

            buttonRows.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    row.forEach { label ->
                        val weight = if (label == "0") 2f else 1f
                        if (label.isEmpty()) {
                            Spacer(modifier = Modifier.weight(weight))
                        } else {
                            Button(
                                onClick = { viewModel.onButtonClick(label) },
                                modifier = Modifier.weight(weight),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = when (label) {
                                        "C", "⌫" -> MaterialTheme.colorScheme.errorContainer
                                        "=", "/", "*", "-", "+" -> MaterialTheme.colorScheme.primaryContainer
                                        else -> MaterialTheme.colorScheme.surfaceVariant
                                    },
                                    contentColor = when (label) {
                                        "C", "⌫" -> MaterialTheme.colorScheme.onErrorContainer
                                        "=", "/", "*", "-", "+" -> MaterialTheme.colorScheme.onPrimaryContainer
                                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            ) {
                                Text(text = label, fontSize = 20.sp)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
