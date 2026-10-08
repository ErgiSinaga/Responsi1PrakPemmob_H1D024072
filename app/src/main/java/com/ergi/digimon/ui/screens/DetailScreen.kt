package com.ergi.digimon.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ergi.digimon.data.model.Digimon
import com.ergi.digimon.viewmodel.DetailViewModel
import com.ergi.digimon.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBack: () -> Unit,
    viewModel: DetailViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Digimon", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val s = state) {
                is UiState.Loading -> CircularProgressIndicator()

                is UiState.Error -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = s.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = viewModel::loadDetail) { Text("Coba Lagi") }
                }

                is UiState.Success -> DetailContent(s.data)
            }
        }
    }
}

@Composable
private fun DetailContent(digimon: Digimon) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = digimon.imageUrl,
            contentDescription = digimon.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(200.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(digimon.name, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))

        // Data minimal: Nama, Level, Attribute, Type
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoRow("Nama", digimon.name)
                InfoRow("Level", digimon.level)
                InfoRow("Attribute", digimon.attribute)
                InfoRow("Type", digimon.type)
                InfoRow("Rilis", digimon.releaseDate)
            }
        }

        if (digimon.fields.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            SectionTitle("Fields")
            FlowRowChips(digimon.fields)
        }

        Spacer(Modifier.height(16.dp))
        SectionTitle("Deskripsi")
        Text(digimon.description, style = MaterialTheme.typography.bodyMedium)

        if (digimon.skills.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            SectionTitle("Skills")
            digimon.skills.take(8).forEach { skill ->
                Text(skill.name, style = MaterialTheme.typography.titleMedium)
                if (skill.description.isNotBlank()) {
                    Text(
                        skill.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth()) {
        Text(
            label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.width(100.dp)
        )
        Text(value, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FlowRowChips(items: List<String>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { AssistChip(onClick = {}, label = { Text(it) }) }
    }
}
