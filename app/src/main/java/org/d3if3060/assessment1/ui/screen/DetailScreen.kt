package org.d3if3060.assessment1.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3060.assessment1.R
import org.d3if3060.assessment1.database.LaporanDb
import org.d3if3060.assessment1.ui.theme.Assessment1Theme
import org.d3if3060.assessment1.util.ViewModelFactory

const val KEY_ID_LAPORAN = "idLaporan"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {

    val context = LocalContext.current
    val db = LaporanDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember {
        mutableStateOf("")
    }
    var jumlah by remember {
        mutableStateOf("")
    }
    val jenisTransaksi = listOf(
        stringResource(R.string.elektronik),
        stringResource(R.string.kecantikan),
        stringResource(R.string.makanan),
        stringResource(R.string.kesehatan),
        stringResource(R.string.pulsa),
        stringResource(R.string.kebutuhan_harian),
        stringResource(R.string.tagihan),
        stringResource(R.string.travel),
        stringResource(R.string.perlengkapan_rumah),
        stringResource(R.string.fashion),
        stringResource(R.string.lainnya)
    )
    var jenis by remember {
        mutableStateOf("")
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    if(id != null) {
        val data = viewModel.getLaporan(id)
        nama = data.nama
        jenis = data.jenis
        jumlah = data.jumlah
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if(id == null)
                        Text(text = stringResource(R.string.tambah_laporan))
                    else
                        Text(text = stringResource(R.string.edit_laporan))
                        },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            if (nama == "" || jenis == "" || jumlah == "") {
                                Toast.makeText(context, R.string.invalid, Toast.LENGTH_LONG).show()
                                return@IconButton
                            }

                            if (id == null) {
                                viewModel.insert(nama, jenis, jumlah)
                            }
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { padding ->
        FormLaporan(
            transaksi = nama,
            onTransaksiChange = { nama = it },
            isExpanded = expanded,
            onExpandedChange = { expanded = it },
            jenis = jenisTransaksi,
            dropdownOptions = jenis,
            onOptionChange = { jenis = it },
            jumlah = jumlah,
            onJumlahChange = { jumlah = it },
            modifier = Modifier.padding(padding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormLaporan(
    transaksi: String, onTransaksiChange: (String) -> Unit,
    isExpanded: Boolean, onExpandedChange: (Boolean) -> Unit,
    jenis: List<String>, dropdownOptions: String, onOptionChange: (String) -> Unit,
    jumlah: String, onJumlahChange: (String) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = transaksi,
            onValueChange = { onTransaksiChange(it) },
            label = { Text(text = stringResource(R.string.nama_laporan)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded =  isExpanded,
            onExpandedChange = { onExpandedChange(it) },
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
                label = { Text(stringResource(id = R.string.jenis_transaksi)) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { onExpandedChange(false) },
                modifier = Modifier.fillMaxWidth()
            ) {
                jenis.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            onOptionChange(item)
                            onExpandedChange(false)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
        OutlinedTextField(
            value = jumlah,
            onValueChange = { onJumlahChange(it)},
            label = { Text(text = stringResource(R.string.jumlah_pengeluaran)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.copyright))
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailPreview() {
    Assessment1Theme {
        DetailScreen(rememberNavController())
    }
}