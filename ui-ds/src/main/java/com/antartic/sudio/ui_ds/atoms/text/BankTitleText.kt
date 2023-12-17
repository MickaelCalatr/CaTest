package com.antartic.sudio.ui_ds.atoms.text

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.antartic.sudio.ui_ds.theme.CATheme
import com.antartic.sudio.ui_ds.theme.margin10
import com.antartic.sudio.ui_ds.theme.margin16
import com.antartic.sudio.ui_ds.theme.margin4
import com.antartic.sudio.ui_ds.theme.regular14

@Composable
fun CABankTitleText(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = margin10())
            .fillMaxWidth(),
    ) {
        Text(
            modifier = modifier.padding(horizontal = margin16()),
            style = regular14(),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            text = text,
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun Preview() {
    CATheme {
        CABankTitleText(
            text = "Credit Agricole"
        )
    }
}

