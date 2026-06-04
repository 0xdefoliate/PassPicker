package se.axelkarlsson.passpicker.ui.route.generator

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import se.axelkarlsson.passpicker.R

@Composable
private fun GeneratePasswordFloatingActionButton() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.refresh_24px),
                contentDescription = "New Password"
            )
        }
    }
}

@Composable
private fun GeneratedPasswordRow(password: String) {
    Row(
        modifier = Modifier
            .padding(28.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .combinedClickable(
                onClick = {},
                onLongClick = {}
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            text = password,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Row(
            modifier = Modifier.wrapContentWidth()
        ) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.visibility_24px),
                    contentDescription = "Toggle Password Visibility"
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.content_copy_24px),
                    contentDescription = "Copy Generated Password"
                )
            }
        }
    }
}

@Composable
fun GeneratorScreen() {
    GeneratePasswordFloatingActionButton()
    GeneratedPasswordRow("[Generated Password]")
}