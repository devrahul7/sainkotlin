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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.freshmart.model.ProductModel
import com.example.freshmart.repository.ProductRepositoryImpl
import com.example.freshmart.viewmodel.ProductViewModel

class AddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreshMartTheme {
                AddProductScreen()
            }
        }
    }
}

@Composable
fun AddProductActivity(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF2E7D32),
            secondary = Color(0xFF81C784),
            background = Color(0xFFF1F8E9),
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF1B5E20),
            onSurface = Color(0xFF1B5E20)
        ),
        content = content
    )
}

@Composable
fun AddProductScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    AddProductBody(
        selectedImageUri = selectedImageUri,
        onPickImage = {
            imagePickerLauncher.launch("image/*")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductBody(
    selectedImageUri: Uri?,
    onPickImage: () -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("🥕 Vegetables") }
    var isLoading by remember { mutableStateOf(false) }
    var expandedCategory by remember { mutableStateOf(false) }

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val context = LocalContext.current
    val activity = context as? Activity

    // FreshMart theme colors
    val primaryColor = Color(0xFF2E7D32) // Fresh Green
    val accentColor = Color(0xFFFF6F00) // Fresh Orange
    val backgroundColor = Color(0xFFF1F8E9) // Very light green
    val lightGreen = Color(0xFF81C784)

    val categories = listOf(
        "🥕 Vegetables", "🍎 Fruits", "🥛 Dairy Products", "🍞 Bakery Items",
        "🥩 Meat & Poultry", "🐟 Seafood", "🌾 Grains & Cereals", "🥜 Nuts & Seeds",
        "🧄 Herbs & Spices", "🥤 Beverages", "🍯 Organic Products", "🥫 Canned Goods"
    )

    Scaffold(
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🛒", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Add Fresh Product",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        activity?.finish()
                    }) {
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
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            backgroundColor,
                            Color(0xFFE8F5E8),
                            Color(0xFFF1F8E9)
                        )
                    )
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    // Header Section
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 12.dp,
                                shape = RoundedCornerShape(20.dp),
                                ambientColor = primaryColor.copy(alpha = 0.3f)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(primaryColor, lightGreen)
                                    )
                                )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("🥕", fontSize = 32.sp)
                                    Text("🥬", fontSize = 32.sp)
                                    Text("🍅", fontSize = 32.sp)
                                    Text("🍎", fontSize = 32.sp)
                                    Text("🥛", fontSize = 32.sp)
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "🌱 Add Fresh Grocery Item 🌱",
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "Share organic & fresh produce with customers",
                                    fontSize = 16.sp,
                                    color = Color.White.copy(alpha = 0.9f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                item {
                    // Image Upload Section
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = primaryColor.copy(alpha = 0.1f)
                            ),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 12.dp)
                            ) {
                                Text("📷", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Product Photo",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1B5E20)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        3.dp,
                                        if (selectedImageUri != null) primaryColor else Color.Gray.copy(alpha = 0.3f),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable(
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) { onPickImage() },
                                contentAlignment = Alignment.Center
                            ) {
                                if (selectedImageUri != null) {
                                    AsyncImage(
                                        model = selectedImageUri,
                                        contentDescription = "Selected Product Image",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(12.dp)),
                                        contentScale = ContentScale.Crop
                                    )

                                    // Edit overlay
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .padding(8.dp)
                                            .size(32.dp)
                                            .background(primaryColor, CircleShape)
                                            .clickable { onPickImage() },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.Edit,
                                            contentDescription = "Change Photo",
                                            tint = Color.White,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                } else {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(60.dp)
                                                .background(primaryColor.copy(alpha = 0.1f), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Add,
                                                contentDescription = "Add Photo",
                                                tint = primaryColor,
                                                modifier = Modifier.size(30.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Text(
                                            text = "📸 Tap to add product photo",
                                            color = primaryColor,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "Show your fresh produce!",
                                            color = Color.Gray,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    // Product Details Form
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = primaryColor.copy(alpha = 0.1f)
                            ),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(18.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                Text("📝", fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Product Details",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1B5E20)
                                )
                            }

                            // Product Name
                            OutlinedTextField(
                                value = productName,
                                onValueChange = { productName = it },
                                label = { Text("Product Name", color = primaryColor) },
                                placeholder = { Text("e.g. Fresh Organic Carrots", color = primaryColor.copy(alpha = 0.7f)) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.ShoppingCart,
                                        contentDescription = null,
                                        tint = primaryColor
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = primaryColor,
                                    unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                    focusedLabelColor = primaryColor,
                                    cursorColor = primaryColor
                                ),
                                shape = RoundedCornerShape(12.dp),
                                enabled = !isLoading
                            )

                            // Price - FIXED: Changed CurrencyRupee to AttachMoney
                            OutlinedTextField(
                                value = price,
                                onValueChange = { price = it },
                                label = { Text("Price (₹)", color = primaryColor) },
                                placeholder = { Text("0.00", color = accentColor.copy(alpha = 0.7f)) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.AttachMoney, // Fixed: This icon exists in standard Material Icons
                                        contentDescription = null,
                                        tint = accentColor
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = primaryColor,
                                    unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                    focusedLabelColor = primaryColor,
                                    cursorColor = primaryColor
                                ),
                                shape = RoundedCornerShape(12.dp),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                enabled = !isLoading
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
                                    label = { Text("Category", color = primaryColor) },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Category,
                                            contentDescription = null,
                                            tint = primaryColor
                                        )
                                    },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategory) },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = primaryColor,
                                        unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                        focusedLabelColor = primaryColor
                                    ),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),
                                    enabled = !isLoading
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
                                label = { Text("Description", color = primaryColor) },
                                placeholder = { Text("Describe freshness, origin, benefits...", color = primaryColor.copy(alpha = 0.7f)) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Description,
                                        contentDescription = null,
                                        tint = primaryColor
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3,
                                maxLines = 5,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = primaryColor,
                                    unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                    focusedLabelColor = primaryColor,
                                    cursorColor = primaryColor
                                ),
                                shape = RoundedCornerShape(12.dp),
                                enabled = !isLoading
                            )
                        }
                    }
                }

                item {
                    // Add Product Button
                    Button(
                        onClick = {
                            when {
                                selectedImageUri == null -> {
                                    Toast.makeText(context, "📷 Please select a product image", Toast.LENGTH_SHORT).show()
                                }
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
                                        isLoading = true

                                        viewModel.uploadImage(context, selectedImageUri!!) { imageUrl ->
                                            if (imageUrl != null) {
                                                val model = ProductModel(
                                                    productName = productName,
                                                    price = priceValue,
                                                    description = description,
                                                    image = imageUrl,
                                                    category = selectedCategory,
                                                    dateAdded = System.currentTimeMillis()
                                                )
                                                viewModel.addProduct(model) { success, message ->
                                                    isLoading = false
                                                    Toast.makeText(context, if (success) "🎉 $message" else "❌ $message", Toast.LENGTH_LONG).show()
                                                    if (success) {
                                                        activity?.finish()
                                                    }
                                                }
                                            } else {
                                                isLoading = false
                                                Toast.makeText(
                                                    context,
                                                    "❌ Failed to upload image. Please try again.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } catch (e: NumberFormatException) {
                                        Toast.makeText(
                                            context,
                                            "💰 Please enter a valid price",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(16.dp),
                                ambientColor = primaryColor.copy(alpha = 0.3f)
                            ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor
                        ),
                        shape = RoundedCornerShape(16.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "🌱 Adding Fresh Product...",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Text("🛒", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Add Fresh Product",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                item {
                    // Tips Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = accentColor.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text("💡", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    "🌟 Fresh Produce Tips:",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1B5E20)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "📸 Use natural lighting for photos\n🥕 Highlight freshness and quality\n🏷️ Include origin/farm details\n⚖️ Mention weight or quantity\n🌱 Choose appropriate category",
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddProductPreview() {
    FreshMartTheme {
        AddProductBody(
            selectedImageUri = null,
            onPickImage = {}
        )
    }
}
