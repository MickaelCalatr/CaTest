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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.antartic.sudio.ui_ds.theme.regular14

@Composable
fun CaBankTitle(
    modifier: Modifier = Modifier,
    accountName: String,
    balance: String,
    isCollapsed: Boolean,
    onClick: () -> Unit,
    collapseContent: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(start = margin8())
            .clickable {
                onClick()
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
                style = regular14(),
                text = accountName,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                modifier = Modifier.padding(horizontal = margin12()),
                text = balance,
                style = regular14(),
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            Icon(
                modifier = Modifier.width(iconSize16()),
                painter = painterResource(
                    if (isCollapsed) R.drawable.ic_arrow_down
                    else R.drawable.ic_arrow_up
                ),
                tint = MaterialTheme.colorScheme.secondaryContainer,
                contentDescription = "Bank Icon"
            )
        }
        if (!isCollapsed) {
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            collapseContent()
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun Preview() {
    CATheme {
        Surface {
            CaBankTitle(
                accountName = "Credit Agricole",
                balance = "13450,50 €",
                isCollapsed = true,
                onClick = {},
                collapseContent = {}
            )
        }
    }
}
