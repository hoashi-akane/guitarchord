package jp.ahoashi.guitarchord

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.ahoashi.guitarchord.ChordScreenViewModel.Companion.Sharp

@Composable
fun ChordScreen(
    modifier: Modifier = Modifier,
    viewModel: ChordScreenViewModel = viewModel(),
) {
    val text = rememberTextMeasurer(8)
    val uiState by viewModel.uiState.collectAsState()
    Column(modifier = modifier.fillMaxHeight().padding(horizontal = 20.dp, vertical = 100.dp)) {
        Row {
            Column(modifier = Modifier.height(200.dp), verticalArrangement = Arrangement.SpaceAround) {
                //            Text("6")
                //            Text("5")
                //            Text("4")
                //            Text("3")
                //            Text("2")
                //            Text("1")
            }
            Canvas(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(0f, 200.dp.toPx()),
                    strokeWidth = 2.dp.toPx(),
                )
                val offsetY = size.height / 6f
                val offsetX = size.width / 4f
                for (i in 0..6) {
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(0f, offsetY * i),
                        end = Offset(x = size.width, offsetY * i),
                        strokeWidth = 2.dp.toPx(),
                    )
                }

                for (i in 1..4) {
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(x = offsetX * i, y = 0f),
                        end = Offset(x = offsetX * i, y = size.height),
                        strokeWidth = 2.dp.toPx(),
                    )

                    drawText(text.measure(i.toString()), color = Color.Black, topLeft = Offset(offsetX * i - (offsetX / 2), -20.dp.toPx()))
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 100.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ChordOutlineButton(
                alphabet = "C",
                uiState = uiState,
            ) { viewModel.setAlphabet(it) }
            ChordOutlineButton(
                alphabet = "D",
                uiState = uiState,
            ) { viewModel.setAlphabet(it) }
            ChordOutlineButton(
                alphabet = "E",
                uiState = uiState,
            ) { viewModel.setAlphabet(it) }
            ChordOutlineButton(
                alphabet = "F",
                uiState = uiState,
            ) { viewModel.setAlphabet(it) }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            ChordOutlineButton(
                alphabet = "G",
                uiState = uiState,
            ) { viewModel.setAlphabet(it) }
            ChordOutlineButton(
                alphabet = "A",
                uiState = uiState,
            ) { viewModel.setAlphabet(it) }
            ChordOutlineButton(
                alphabet = "B",
                uiState = uiState,
            ) { viewModel.setAlphabet(it) }
            SharpOutlineButton(
                uiState = uiState,
            ) {
                viewModel.setSharp(Sharp.UNSET)
            }
        }
    }
}

@Composable
fun RowScope.ChordOutlineButton(
    alphabet: String,
    uiState: ChordScreenViewModel.ChordScreenUiState,
    setAlphabet: (String) -> Unit,
) {
    OutlinedButton(
        onClick = { setAlphabet(alphabet) },
        modifier = Modifier.weight(1f),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor =
                    if (uiState.alphabet == alphabet) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                contentColor =
                    if (uiState.alphabet == alphabet) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
            ),
    ) {
        Text(alphabet)
    }
}

@Composable
fun RowScope.SharpOutlineButton(
    uiState: ChordScreenViewModel.ChordScreenUiState,
    setSharp: (Sharp) -> Unit,
) {
    OutlinedButton(
        onClick = { setSharp(Sharp.SET) },
        modifier = Modifier.weight(1f),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor =
                    if (uiState.sharp == Sharp.SET) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                contentColor =
                    if (uiState.sharp == Sharp.SET) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
            ),
    ) {
        Text("#")
    }
}

@Composable
@Preview
private fun ChordScreenPreview() {
    ChordScreen(modifier = Modifier.fillMaxSize())
}
