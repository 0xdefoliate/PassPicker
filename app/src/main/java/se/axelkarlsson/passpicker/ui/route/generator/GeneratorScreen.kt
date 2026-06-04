package se.axelkarlsson.passpicker.ui.route.generator

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import se.axelkarlsson.passpicker.R
import se.axelkarlsson.passpicker.ui.component.CheckboxRow
import kotlin.math.roundToInt

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

    val passwordLength = remember { mutableFloatStateOf(32f) }

    val useAlpha = remember { mutableStateOf(true) }
    val useNumber = remember { mutableStateOf(true) }
    val useSpecial = remember { mutableStateOf(true) }

    GeneratePasswordFloatingActionButton()

    Column(
        modifier = Modifier
            .padding(28.dp)
    ) {

        GeneratedPasswordRow("[Generated Password]")

        Column(modifier = Modifier.padding(top = 16.dp)) {
            Text("Options", fontSize = 20.sp)

            Column(modifier = Modifier.padding(top = 16.dp)) {
                Text("Password Length (${passwordLength.floatValue.roundToInt()})")

                Slider(
                    value = passwordLength.floatValue,
                    valueRange = 6f..128f,
                    onValueChange = { passwordLength.floatValue = it })
            }

            CheckboxRow(useAlpha, "Alphabetic")
            CheckboxRow(useNumber, "Numeric")
            CheckboxRow(useSpecial, "Special")
        }
    }
}