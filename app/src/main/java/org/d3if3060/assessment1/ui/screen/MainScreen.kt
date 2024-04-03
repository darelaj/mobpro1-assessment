package org.d3if3060.assessment1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.d3if3060.assessment1.R
import org.d3if3060.assessment1.ui.theme.Assessment1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier) {

    var rupiah by remember {
        mutableStateOf("")
    }

    var rupiahError by remember {
        mutableStateOf(false)
    }

    var hasilUangAsing by remember {
        mutableFloatStateOf(0f)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val mataUangAsing = listOf(
        stringResource(id = R.string.dolar_amerika),
        stringResource(id = R.string.euro),
        stringResource(id = R.string.ringgit),
        stringResource(id = R.string.yen),
        stringResource(id = R.string.dolar_singapur)
    )

    var dropdownOptions by remember {
        mutableStateOf(mataUangAsing[0])
    }

    var kodeMataUang by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.konverter_intro),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = rupiah,
            onValueChange = {rupiah = it},
            label = { Text(text = stringResource(id = R.string.rupiah))},
            isError = rupiahError,
            leadingIcon = { Text(text = "Rp.")},
            trailingIcon = { IconPicker(isError = rupiahError, unit = "") },
            supportingText = { ErrorHint(isError = rupiahError) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {expanded = it},
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                value = dropdownOptions,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.mata_uang_asing)) },
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            )
            {
                mataUangAsing.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            dropdownOptions = item
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        Row {
            Button(
                onClick = {
                    rupiahError = (rupiah == "" || rupiah == "0")
                    if (rupiahError) return@Button

                    when(dropdownOptions) {
                        mataUangAsing[0] -> kodeMataUang = "USD"
                        mataUangAsing[1] -> kodeMataUang = "EUR"
                        mataUangAsing[2] -> kodeMataUang = "MYR"
                        mataUangAsing[3] -> kodeMataUang = "JPY"
                        mataUangAsing[4] -> kodeMataUang = "SGD"
                    }

                    hasilUangAsing = konversi(rupiah.toFloat(), kodeMataUang)
                }
            ) {
                Text(text = stringResource(id = R.string.hitung))
            }
        }

        if(hasilUangAsing != 0f) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            when(kodeMataUang) {
                "USD" -> Text(text = "US$" + String.format("%.2f", hasilUangAsing), style = MaterialTheme.typography.headlineLarge)
                "EUR" -> Text(text = "€" + String.format("%.2f", hasilUangAsing), style = MaterialTheme.typography.headlineLarge)
                "MYR" -> Text(text = "RM " + String.format("%.2f", hasilUangAsing), style = MaterialTheme.typography.headlineLarge)
                "JPY" -> Text(text = "¥" + String.format("%.2f", hasilUangAsing), style = MaterialTheme.typography.headlineLarge)
                "SGD" -> Text(text = "S$" + String.format("%.2f", hasilUangAsing), style = MaterialTheme.typography.headlineLarge)
            }
        }

    }
}

private fun konversi(rupiah: Float, mataUangAsing: String): Float {
    return when (mataUangAsing) {
        "USD" -> rupiah / 15942
        "EUR" -> rupiah / 17163
        "MYR" -> rupiah / 3370
        "JPY" -> rupiah / 105
        "SGD" -> rupiah / 11794
        else -> rupiah
    }
}
@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(
            imageVector = Icons.Filled.Warning,
            contentDescription = null
        )
    } else {
        Text(text = unit)
    }
}

@Composable
fun ErrorHint(isError: Boolean) {
    if(isError) {
        Text(text = stringResource(id = R.string.input_invalid))
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    Assessment1Theme {
        MainScreen()
    }
}