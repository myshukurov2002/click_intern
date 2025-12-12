package normal.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import normal.model.Product
import normal.network.RetrofitModule

sealed interface ProductUiState {
    data object Loading : ProductUiState
    data class Success(val items: List<Product>) : ProductUiState
    data class Error(val message: String) : ProductUiState
}

@Composable
fun ProductListScreen() {
    var state by remember { mutableStateOf<ProductUiState>(ProductUiState.Loading) }
    LaunchedEffect(Unit) {
        state = try {
            val products = RetrofitModule.api.getProducts()
            ProductUiState.Success(products)
        } catch (t: Throwable) {
            ProductUiState.Error(t.message ?: "Unknown error")
        }
    }
    when (val s = state) {
        is ProductUiState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        is ProductUiState.Error -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(text = s.message) }
        is ProductUiState.Success -> ProductList(items = s.items)
    }
}

@Composable
private fun ProductList(items: List<Product>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { p -> ProductCard(p) }
    }
}

@Composable
private fun ProductCard(p: Product) {
    Card(Modifier.fillMaxWidth().padding(12.dp)) {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(72.dp), contentAlignment = Alignment.Center) {
                Text(text = "Image")
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(text = p.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "$${p.price}", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
