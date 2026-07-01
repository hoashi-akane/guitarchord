package jp.ahoashi.guitarchord.chordsdb.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing
import jp.ahoashi.guitarchord.entity.TYPE
import jp.ahoashi.guitarchord.generated.resources.Res
import jp.ahoashi.guitarchord.generated.resources.chords_db_debug
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChordsDbVoicingsScreen(
    onBack: () -> Unit,
    viewModel: ChordsDbVoicingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.chords_db_debug)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (alphabet in listOf("C", "D", "E", "F", "G", "A", "B")) {
                    OutlinedButton(
                        onClick = { viewModel.setAlphabet(alphabet) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (uiState.alphabet == alphabet) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onPrimary
                            },
                            contentColor = if (uiState.alphabet == alphabet) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.primary
                            },
                        ),
                    ) {
                        Text(alphabet)
                    }
                }
            }

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                for (type in TYPE.entries) {
                    OutlinedButton(
                        onClick = { viewModel.setType(type) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (uiState.type == type) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onPrimary
                            },
                            contentColor = if (uiState.type == type) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.primary
                            },
                        ),
                    ) {
                        Text(type.displayName)
                    }
                }
            }

            Text(
                text = "${uiState.alphabet} ${uiState.type.displayName}",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp),
            )

            val voicingSet = uiState.voicingSet
            if (voicingSet == null) {
                Text(
                    text = "該当する押さえ方が見つかりませんでした",
                    modifier = Modifier.padding(top = 8.dp),
                )
            } else {
                voicingSet.voicings.forEachIndexed { index, voicing ->
                    VoicingRow(index = index, voicing = voicing)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}

@Composable
private fun VoicingRow(index: Int, voicing: ChordVoicing) {
    Column {
        Text(
            text = "押さえ方${index + 1}: baseFret=${voicing.baseFret} capo=${voicing.isCapoSuggested} barres=${voicing.barres}",
            style = MaterialTheme.typography.bodyMedium,
        )
        val line = voicing.strings
            .sortedByDescending { it.stringNumber }
            .joinToString(separator = "  ") { stringFret ->
                if (stringFret.fret < 0) {
                    "${stringFret.stringNumber}:x"
                } else {
                    "${stringFret.stringNumber}:${stringFret.fret}(finger${stringFret.finger})"
                }
            }
        Text(
            text = line,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
