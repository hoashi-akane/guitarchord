package jp.ahoashi.guitarchord.licenses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import jp.ahoashi.guitarchord.generated.resources.Res
import jp.ahoashi.guitarchord.generated.resources.licenses
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource

private val jsonParser = Json { ignoreUnknownKeys = true }

@Serializable
private data class GradleLicenseEntry(
    val groupId: String = "",
    val artifactId: String = "",
    val version: String = "",
    val spdxLicenses: List<SpdxLicense> = emptyList(),
    val unknownLicenses: List<UnknownLicense> = emptyList(),
    val scm: Scm? = null,
)

@Serializable
private data class SpdxLicense(
    val identifier: String = "",
    val name: String = "",
    val url: String = "",
)

@Serializable
private data class UnknownLicense(
    val name: String? = null,
    val url: String? = null,
)

@Serializable
private data class Scm(
    val url: String? = null,
)

@Serializable
private data class IosSpmLicense(
    val name: String = "",
    val version: String = "",
    val url: String? = null,
    val license: String = "Unknown",
)

data class LibraryLicense(
    val name: String,
    val version: String,
    val license: String,
    val url: String?,
)

private suspend fun loadAllLicenses(): List<LibraryLicense> {
    val gradleLicenses = runCatching {
        val bytes = Res.readBytes("files/oss_licenses.json")
        jsonParser.decodeFromString<List<GradleLicenseEntry>>(bytes.decodeToString())
            .map { entry ->
                LibraryLicense(
                    name = "${entry.groupId}:${entry.artifactId}",
                    version = entry.version,
                    license = entry.spdxLicenses.firstOrNull()?.name
                        ?: entry.unknownLicenses.firstOrNull()?.name
                        ?: "Unknown",
                    url = entry.scm?.url ?: entry.spdxLicenses.firstOrNull()?.url,
                )
            }
    }.getOrDefault(emptyList())

    val iosLicenses = runCatching {
        val bytes = Res.readBytes("files/ios_oss_licenses.json")
        jsonParser.decodeFromString<List<IosSpmLicense>>(bytes.decodeToString())
            .map { entry ->
                LibraryLicense(
                    name = entry.name,
                    version = entry.version,
                    license = entry.license,
                    url = entry.url,
                )
            }
    }.getOrDefault(emptyList())

    return (gradleLicenses + iosLicenses)
        .distinctBy { it.name.lowercase() }
        .sortedBy { it.name.lowercase() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicensesScreen(onBack: () -> Unit) {
    var licenses by remember { mutableStateOf<List<LibraryLicense>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        licenses = loadAllLicenses()
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.licenses)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues,
            ) {
                items(licenses) { license ->
                    LicenseItem(license)
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
private fun LicenseItem(license: LibraryLicense) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = license.url != null) {
                license.url?.let { uriHandler.openUri(it) }
            }
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            text = license.name,
            style = MaterialTheme.typography.bodyMedium,
        )
        if (license.version.isNotEmpty()) {
            Text(
                text = license.version,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            text = license.license,
            style = MaterialTheme.typography.bodySmall,
            color = if (license.url != null) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
        )
    }
}
