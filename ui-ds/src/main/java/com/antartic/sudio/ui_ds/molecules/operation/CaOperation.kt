package com.antartic.sudio.ui_ds.molecules.operation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antartic.sudio.ui_ds.theme.CATheme
import com.antartic.sudio.ui_ds.theme.bold14
import com.antartic.sudio.ui_ds.theme.margin16
import com.antartic.sudio.ui_ds.theme.margin40
import com.antartic.sudio.ui_ds.theme.margin8
import com.antartic.sudio.ui_ds.theme.regular12
import com.antartic.sudio.ui_ds.theme.regular14


@Composable
fun CaOperationTitle(
    modifier: Modifier = Modifier,
    operationName: String,
    amount: String,
    date: String?
) {
    Column(
        modifier = modifier.padding(start = margin16())
    ) {
        Row(
            modifier = Modifier.padding(
                top = margin16(),
                bottom = margin8(),
                end = margin8()
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = margin8())
                    .weight(1f)
            ) {
                Text(
                    text = operationName,
                    style = bold14(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(margin8()))
                date?.let {
                    Text(
                        modifier = Modifier.padding(start = margin16()),
                        text = date,
                        style = regular12(),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            Text(
                modifier = Modifier.padding(end = margin40()),
                text = amount,
                style = regular14(),
                color = MaterialTheme.colorScheme.secondaryContainer
            )
        }
        Divider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondaryContainer
        )
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun Preview() {
    CATheme {
        Surface {
            CaOperationTitle(
                operationName = "Restaurant Via Pilat",
                amount = "13450,50 €",
                date = "24/12/2023"
            )
        }
    }
}
