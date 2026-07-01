package jp.ahoashi.guitarchord.topbar

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.ump.ConsentInformation
import com.google.android.ump.UserMessagingPlatform
import jp.ahoashi.guitarchord.ChordScreenViewModel
import jp.ahoashi.guitarchord.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChordScreenSettingsButton(
    modifier: Modifier = Modifier,
    viewModel: ChordScreenViewModel = koinViewModel(),
    onLicensesClick: () -> Unit = {},
) {
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }
    val setting by viewModel.getSettingStream().collectAsState()

    val consentInformation = UserMessagingPlatform.getConsentInformation(context)
    val showPrivacyEntry =
        consentInformation.privacyOptionsRequirementStatus ==
            ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED

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
                text = { Text(text = stringResource(R.string.setting_lefty)) },
                leadingIcon = {
                    RadioButton(
                        selected = setting.lefty,
                        onClick = null,
                    )
                },
                onClick = { viewModel.setLefty(!setting.lefty) },
            )
            DropdownMenuItem(
                text = { Text(text = stringResource(R.string.licenses)) },
                onClick = {
                    isExpanded = false
                    onLicensesClick()
                },
            )
            if (showPrivacyEntry) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(R.string.privacy_options)) },
                    onClick = {
                        isExpanded = false
                        UserMessagingPlatform.showPrivacyOptionsForm(context as Activity) {}
                    },
                )
            }
        }
    }
}
