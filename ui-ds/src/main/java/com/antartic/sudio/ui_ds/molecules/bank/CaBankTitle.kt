package com.antartic.sudio.ui_ds.molecules.bank

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antartic.sudio.ui_ds.R
import com.antartic.sudio.ui_ds.theme.CATheme
import com.antartic.sudio.ui_ds.theme.iconSize16
import com.antartic.sudio.ui_ds.theme.margin12
import com.antartic.sudio.ui_ds.theme.margin16
import com.antartic.sudio.ui_ds.theme.margin8
import com.antartic.sudio.ui_ds.theme.regular16


@Composable
fun CaBankTitle(
    modifier: Modifier = Modifier,
    accountName: String,
    amount: String,
    onClick: (Boolean) -> Unit
) {
    var isOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .clickable {
                isOpen = !isOpen
                onClick(isOpen)
            }
    ) {
        Row(
            modifier = modifier.padding(
                top = margin12(),
                bottom = margin12(),
                start = margin16(),
                end = margin8()
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                style = regular16(),
                text = accountName,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                modifier = Modifier.padding(horizontal = margin12()),
                text = amount,
                style = regular16(),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Icon(
                modifier = Modifier.width(iconSize16()),
                painter = painterResource(
                    if (isOpen) R.drawable.ic_arrow_up
                    else R.drawable.ic_arrow_down
                ),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                contentDescription = "Bank Icon"
            )
        }
        if (isOpen) {
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun Preview() {
    CATheme {
        CaBankTitle(
            accountName = "Credit Agricole",
            amount = "13450,50 €",
            onClick = {}
        )
    }
}
