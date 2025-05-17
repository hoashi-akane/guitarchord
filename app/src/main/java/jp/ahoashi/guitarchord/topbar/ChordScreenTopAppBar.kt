package jp.ahoashi.guitarchord.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChordScreenTopAppBar() {
//    var isIconSelected by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(text = "Guitar Chord")
        },
        navigationIcon = {
        },
        actions = {
            // TODO: 表示切り替えなど入れる
//            IconButton(onClick = { isIconSelected = !isIconSelected }) {
//                Icon(
//                    imageVector = Icons.Filled.Settings,
//                    contentDescription = "Settings",
//                )
//            }
//
//            DropdownMenu(
//                expanded = isIconSelected,
//                onDismissRequest = { isIconSelected = false},
//            ) {
//                DropdownMenuItem(
//                    text = {}
//                )
//            }
        },
    )
}
