package jp.ahoashi.guitarchord.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jp.ahoashi.guitarchord.ChordScreenViewModel
import jp.ahoashi.guitarchord.core.AppTheme
import jp.ahoashi.guitarchord.core.SettingsRepository.Setting
import jp.ahoashi.guitarchord.generated.resources.Res
import jp.ahoashi.guitarchord.generated.resources.privacy_options
import jp.ahoashi.guitarchord.generated.resources.setting_lefty
import jp.ahoashi.guitarchord.generated.resources.setting_theme_color
import jp.ahoashi.guitarchord.generated.resources.theme_blue
import jp.ahoashi.guitarchord.generated.resources.theme_green
import jp.ahoashi.guitarchord.generated.resources.theme_red
import jp.ahoashi.guitarchord.generated.resources.theme_teal
import jp.ahoashi.guitarchord.generated.resources.theme_yellow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private val themeColors = listOf(
    AppTheme.TEAL to Color(0xFF00696B),
    AppTheme.RED to Color(0xFF8B5E6A),
    AppTheme.BLUE to Color(0xFF5B6B9E),
    AppTheme.GREEN to Color(0xFF5A7A62),
    AppTheme.YELLOW to Color(0xFF7A6E3A),
)

@Composable
fun ChordScreenSettingsButton(
    modifier: Modifier = Modifier,
    viewModel: ChordScreenViewModel = koinViewModel(),
    onPrivacyOptionsClick: (() -> Unit)? = null,
) {
    val setting by viewModel.getSettingStream().collectAsState(Setting())
    var isExpanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { isExpanded = !isExpanded }) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings",
            )
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            DropdownMenuItem(
                text = { Text(text = stringResource(Res.string.setting_lefty)) },
                leadingIcon = {
                    RadioButton(selected = setting.lefty, onClick = null)
                },
                onClick = { viewModel.setLefty(!setting.lefty) },
            )

            HorizontalDivider()

            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(Res.string.setting_theme_color),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                onClick = {},
                enabled = false,
            )

            val themeLabels = listOf(
                stringResource(Res.string.theme_teal),
                stringResource(Res.string.theme_red),
                stringResource(Res.string.theme_blue),
                stringResource(Res.string.theme_green),
                stringResource(Res.string.theme_yellow),
            )

            themeColors.forEachIndexed { index, (theme, color) ->
                DropdownMenuItem(
                    text = { Text(themeLabels[index]) },
                    leadingIcon = {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    },
                    trailingIcon = {
                        if (setting.theme == theme) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                            )
                        }
                    },
                    onClick = {
                        viewModel.setTheme(theme)
                        isExpanded = false
                    },
                )
            }

            if (onPrivacyOptionsClick != null) {
                HorizontalDivider()
                DropdownMenuItem(
                    text = { Text(text = stringResource(Res.string.privacy_options)) },
                    onClick = {
                        isExpanded = false
                        onPrivacyOptionsClick()
                    },
                )
            }
        }
    }
}
