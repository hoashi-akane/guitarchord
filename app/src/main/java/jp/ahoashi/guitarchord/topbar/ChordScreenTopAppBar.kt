package jp.ahoashi.guitarchord.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import jp.ahoashi.guitarchord.ChordScreenViewModel
import jp.ahoashi.guitarchord.R
import jp.ahoashi.guitarchord.core.SettingsRepository.Setting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChordScreenTopAppBar(viewModel: ChordScreenViewModel = hiltViewModel()) {
    var isIconSelected by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(text = "Guitar Chord")
        },
        navigationIcon = {
        },
        actions = {
            // TODO: 表示切り替えなど入れる
            IconButton(onClick = { isIconSelected = !isIconSelected }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                )
            }

            val setting by viewModel.settingsRepository.getSettingStream().collectAsState(Setting())
            DropdownMenu(
                expanded = isIconSelected,
                onDismissRequest = { isIconSelected = false },
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.setting_lefty))
                    },
                    leadingIcon = {
                        RadioButton(
                            selected = setting.lefty,
                            onClick = null,
                        )
                    },
                    onClick = {
                        viewModel.setLefty(!setting.lefty)
                    },
                )
            }
        },
    )
}
