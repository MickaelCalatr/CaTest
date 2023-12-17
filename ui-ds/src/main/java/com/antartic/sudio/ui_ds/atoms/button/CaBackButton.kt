package com.antartic.sudio.ui_ds.atoms.button

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.antartic.sudio.ui_ds.R
import com.antartic.sudio.ui_ds.theme.CATheme
import com.antartic.sudio.ui_ds.theme.iconSize20
import com.antartic.sudio.ui_ds.theme.margin8
import com.antartic.sudio.ui_ds.theme.regular14

@Composable
fun CaBackButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Row(modifier = modifier) {
        TextButton(onClick = onClick) {
            Icon(
                modifier = Modifier.width(iconSize20()),
                painter = painterResource(R.drawable.ic_arrow_back),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Back Button"
            )
            Text(
                modifier = Modifier.padding(start = margin8()),
                text = text,
                style = regular14(),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun Preview() {
    CATheme {
        CaBackButton(
            text = "My Account"
        ) {

        }
    }
}
