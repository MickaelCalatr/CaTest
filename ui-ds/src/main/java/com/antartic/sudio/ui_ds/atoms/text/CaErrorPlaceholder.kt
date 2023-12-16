package com.antartic.sudio.ui_ds.atoms.text

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.antartic.sudio.ui_ds.theme.CATheme
import com.antartic.sudio.ui_ds.theme.bold22
import com.antartic.sudio.ui_ds.theme.margin28

@Composable
fun CaErrorPlaceholder(text: String) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .padding(margin28()),
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = bold22()
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun Preview() {
    CATheme {
        CaErrorPlaceholder(
            text = "An error occurred"
        )
    }
}
