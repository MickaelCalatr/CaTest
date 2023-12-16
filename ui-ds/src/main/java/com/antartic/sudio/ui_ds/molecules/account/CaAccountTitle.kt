package com.antartic.sudio.ui_ds.molecules.account

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antartic.sudio.ui_ds.R
import com.antartic.sudio.ui_ds.theme.CATheme
import com.antartic.sudio.ui_ds.theme.iconSize20
import com.antartic.sudio.ui_ds.theme.margin12
import com.antartic.sudio.ui_ds.theme.margin40
import com.antartic.sudio.ui_ds.theme.margin8
import com.antartic.sudio.ui_ds.theme.regular14


@Composable
fun CaAccountTitle(
    modifier: Modifier = Modifier,
    accountName: String,
    amount: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = margin40())
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(
                top = margin12(),
                bottom = margin8(),
                end = margin8()
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = accountName,
                style = regular14(),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = amount,
                style = regular14(),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Icon(
                modifier = Modifier.width(iconSize20()),
                painter = painterResource(R.drawable.ic_arrow_right),
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                contentDescription = "Account Icon"
            )
        }
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun Preview() {
    CATheme {
        CaAccountTitle(
            accountName = "Credit Agricole PEL",
            amount = "13450,50 €",
            onClick = {}
        )
    }
}
