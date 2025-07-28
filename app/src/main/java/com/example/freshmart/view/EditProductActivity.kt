package com.example.freshmart.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.freshmart.model.ProductModel
import com.example.freshmart.repository.ProductRepositoryImpl
import com.example.freshmart.viewmodel.ProductViewModel

class EditProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productId = intent.getStringExtra("productId") ?: ""

        setContent {
            FreshMartTheme {
                EditProductScreen(productId = productId)
            }
        }
    }
}

@Composable
fun EditProductActivity(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF4CAF50),
            secondary = Color(0xFF8BC34A),
            tertiary = Color(0xFFFF9800),
            background = Color(0xFFF1F8E9),
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF2E7D32),
            onSurface = Color(0xFF2E7D32)
        ),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(productId: String) {
    val context = LocalContext.current
    val activity = context as? Activity

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    // Observe current product
    val currentProduct by viewModel.products.observeAsState()

    // Form states
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var productName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("🥕 Vegetables") }
    var isLoading by remember { mutableStateOf(false) }
    var isUpdating by remember { mutableStateOf(false) }
    var expandedCategory by remember { mutableStateOf(false) }
    var hasImageChanged by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        hasImageChanged = uri != null
    }

    // Theme colors
    val primaryColor = MaterialTheme.colorScheme.primary
    val tertiaryColor = MaterialTheme.colorScheme.tertiary
    val backgroundColor = MaterialTheme.colorScheme.background

    val categories = listOf(
        "🥕 Vegetables", "🍎 Fruits", "🥛 Dairy Products", "🍞 Bakery Items",
        "🥩 Meat & Poultry", "🐟 Seafood", "🌾 Grains & Cereals", "🥜 Nuts & Seeds",
        "🧄 Herbs & Spices", "🥤 Beverages", "🍯 Organic Products", "🥫 Canned Goods"
    )

    // Load product data
    LaunchedEffect(productId) {
        if (productId.isNotEmpty()) {
            isLoading = true
            viewModel.getProductByID(productId)
        }
    }

    // Update form when product data is loaded
    LaunchedEffect(currentProduct) {
        currentProduct?.let { product ->
            productName = product.productName
            price = product.price.toString()
            description = product.description
            selectedCategory = product.category
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✏️", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Edit Product",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                )
            )
        }
    ) { paddingValues ->
        if (isLoading) {
            // Loading state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(backgroundColor, Color(0xFFE8F5E8))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(color = primaryColor, strokeWidth = 4.dp)
                    Text(
                        "Loading product details...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = primaryColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(backgroundColor, Color(0xFFE8F5E8))
                        )
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Header Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(tertiaryColor, Color(0xFFFFB74D))
                                    )
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🔄", fontSize = 40.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    "✏️ Update Product Details ✏️",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    "Edit your fresh produce information",
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.9f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Image Comparison Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 16.dp)
                            ) {
                                Text("📷", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Product Images",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Current Image
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        "Current Image",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = primaryColor,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(140.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .border(
                                                1.dp,
                                                Color.Gray.copy(alpha = 0.3f),
                                                RoundedCornerShape(12.dp)
                                            )
                                    ) {
                                        currentProduct?.let { product ->
                                            AsyncImage(
                                                model = product.image,
                                                contentDescription = "Current Image",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(RoundedCornerShape(12.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                    }
                                }

                                // New Image or Upload Button
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        "New Image (Optional)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (hasImageChanged) primaryColor else Color.Gray,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(140.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .border(
                                                2.dp,
                                                if (selectedImageUri != null) primaryColor else Color.Gray.copy(alpha = 0.3f),
                                                RoundedCornerShape(12.dp)
                                            )
                                            .clickable(
                                                indication = null,
                                                interactionSource = remember { MutableInteractionSource() }
                                            ) { imagePickerLauncher.launch("image/*") },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (selectedImageUri != null) {
                                            AsyncImage(
                                                model = selectedImageUri,
                                                contentDescription = "New Image",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .clip(RoundedCornerShape(12.dp)),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(40.dp)
                                                        .background(primaryColor.copy(alpha = 0.1f), CircleShape),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Icon(
                                                        Icons.Default.PhotoCamera,
                                                        contentDescription = "Change Image",
                                                        tint = primaryColor,
                                                        modifier = Modifier.size(20.dp)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(8.dp))
                                                Text(
                                                    "Tap to change",
                                                    fontSize = 12.sp,
                                                    color = primaryColor,
                                                    fontWeight = FontWeight.Medium
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            if (hasImageChanged) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            primaryColor.copy(alpha = 0.1f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Info,
                                        contentDescription = null,
                                        tint = primaryColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        "New image will replace the current one",
                                        fontSize = 12.sp,
                                        color = primaryColor,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    // Product Details Form
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text("📝", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Product Details",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Product Name
                            OutlinedTextField(
                                value = productName,
                                onValueChange = { productName = it },
                                label = { Text("Product Name") },
                                placeholder = { Text("e.g. Fresh Organic Carrots") },
                                leadingIcon = {
                                    Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = primaryColor)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = primaryColor,
                                    focusedLabelColor = primaryColor,
                                    cursorColor = primaryColor
                                ),
                                shape = RoundedCornerShape(12.dp),
                                enabled = !isUpdating
                            )

                            // Price
                            OutlinedTextField(
                                value = price,
                                onValueChange = { price = it },
                                label = { Text("Price (₹)") },
                                placeholder = { Text("0.00") },
                                leadingIcon = {
                                    Icon(Icons.Default.AttachMoney, contentDescription = null, tint = tertiaryColor)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = primaryColor,
                                    focusedLabelColor = primaryColor,
                                    cursorColor = primaryColor
                                ),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                enabled = !isUpdating
                            )

                            // Category Dropdown
                            ExposedDropdownMenuBox(
                                expanded = expandedCategory,
                                onExpandedChange = { expandedCategory = !expandedCategory }
                            ) {
                                OutlinedTextField(
                                    value = selectedCategory,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Category") },
                                    leadingIcon = {
                                        Icon(Icons.Default.Category, contentDescription = null, tint = primaryColor)
                                    },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = primaryColor,
                                        focusedLabelColor = primaryColor
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),
                                    enabled = !isUpdating
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedCategory,
                                    onDismissRequest = { expandedCategory = false }
                                ) {
                                    categories.forEach { category ->
                                        DropdownMenuItem(
                                            text = { Text(category) },
                                            onClick = {
                                                selectedCategory = category
                                                expandedCategory = false
                                            }
                                        )
                                    }
                                }
                            }

                            // Description
                            OutlinedTextField(
                                value = description,
                                onValueChange = { description = it },
                                label = { Text("Description") },
                                placeholder = { Text("Describe freshness, origin, benefits...") },
                                leadingIcon = {
                                    Icon(Icons.Default.Description, contentDescription = null, tint = primaryColor)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3,
                                maxLines = 5,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = primaryColor,
                                    focusedLabelColor = primaryColor,
                                    cursorColor = primaryColor
                                ),
                                shape = RoundedCornerShape(12.dp),
                                enabled = !isUpdating
                            )
                        }
                    }

                    // Update Button
                    Button(
                        onClick = {
                            when {
                                productName.isBlank() -> {
                                    Toast.makeText(context, "🏷️ Please enter product name", Toast.LENGTH_SHORT).show()
                                }
                                price.isBlank() -> {
                                    Toast.makeText(context, "💰 Please enter price", Toast.LENGTH_SHORT).show()
                                }
                                description.isBlank() -> {
                                    Toast.makeText(context, "📝 Please enter description", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    try {
                                        val priceValue = price.toDouble()
                                        isUpdating = true

                                        if (hasImageChanged && selectedImageUri != null) {
                                            // Upload new image first
                                            viewModel.uploadImage(context,
                                                selectedImageUri!!
                                            ) { imageUrl ->
                                                if (imageUrl != null) {
                                                    val updateData = mutableMapOf<String, Any?>(
                                                        "productName" to productName,
                                                        "price" to priceValue,
                                                        "description" to description,
                                                        "category" to selectedCategory,
                                                        "image" to imageUrl
                                                    )
                                                    viewModel.updateProduct(productId, updateData) { success, message ->
                                                        isUpdating = false
                                                        Toast.makeText(context, if (success) "✅ $message" else "❌ $message", Toast.LENGTH_LONG).show()
                                                        if (success) activity?.finish()
                                                    }
                                                } else {
                                                    isUpdating = false
                                                    Toast.makeText(context, "❌ Failed to upload new image", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            // Update without changing image
                                            val updateData = mutableMapOf<String, Any?>(
                                                "productName" to productName,
                                                "price" to priceValue,
                                                "description" to description,
                                                "category" to selectedCategory
                                            )
                                            viewModel.updateProduct(productId, updateData) { success, message ->
                                                isUpdating = false
                                                Toast.makeText(context, if (success) "✅ $message" else "❌ $message", Toast.LENGTH_LONG).show()
                                                if (success) activity?.finish()
                                            }
                                        }
                                    } catch (e: NumberFormatException) {
                                        Toast.makeText(context, "💰 Please enter a valid price", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !isUpdating
                    ) {
                        if (isUpdating) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "🔄 Updating Product...",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Icon(Icons.Default.Check, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Update Product",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Info Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF2196F3).copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text("ℹ️", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    "💡 Update Tips:",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "✏️ Edit any field you want to change\n📷 Update image only if needed\n💰 Ensure price reflects current market value\n📝 Keep description accurate and appealing",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}
