package com.example.freshmart.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.freshmart.R
import com.example.freshmart.model.UserModel
import com.example.freshmart.repository.UserRepositoryImpl
import com.example.freshmart.viewmodel.UserViewModel

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreshMartTheme {
                DashboardScreen()
            }
        }
    }
}

// Fixed FreshMartTheme composable
@Composable
fun FreshMartTheme(content: @Composable () -> Unit) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    val context = LocalContext.current
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    // State management
    var selectedTab by remember { mutableIntStateOf(0) }
    var userName by remember { mutableStateOf("User") }
    var userEmail by remember { mutableStateOf("") }
    var userPhone by remember { mutableStateOf("") }
    var userAddress by remember { mutableStateOf("") }
    var isUserDataLoaded by remember { mutableStateOf(false) }

    // Fresh grocery theme colors
    val primaryColor = Color(0xFF2E7D32) // Fresh Green
    val accentColor = Color(0xFFFF6F00) // Fresh Orange
    val backgroundColor = Color(0xFFF1F8E9) // Very light green
    val lightGreen = Color(0xFF81C784)

    // Data loading
    LaunchedEffect(Unit) {
        val currentUser = userViewModel.getCurrentUser()
        if (currentUser != null) {
            userViewModel.getUserByID(currentUser.uid)

            repo.getUserByID(currentUser.uid) { user, success, message ->
                if (success && user != null) {
                    userName = user.fullName.ifEmpty { "Fresh Shopper" }
                    userEmail = user.email
                    userPhone = user.phoneNumber
                    userAddress = user.address
                    isUserDataLoaded = true
                }
            }
        }
    }

    fun navigateToAddProduct() {
        try {
            val intent = Intent(context, AddProductActivity::class.java)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Add Product feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    fun navigateToViewAddedProduct() {
        try {
            val intent = Intent(context, ViewProductActivity::class.java)
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "View Products feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            when (selectedTab) {
                                0 -> "🛒"
                                1 -> "🔍"
                                2 -> "👤"
                                else -> "🛒"
                            },
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            when (selectedTab) {
                                0 -> "FreshMart"
                                1 -> "Search Products"
                                2 -> "My Profile"
                                else -> "FreshMart"
                            },
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                },
                actions = {
                    if (selectedTab == 0) {
                        IconButton(onClick = {
                            Toast.makeText(context, "🔔 No new notifications", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {
                            Toast.makeText(context, "🛒 Cart is empty", Toast.LENGTH_SHORT).show()
                        }) {
                            Icon(
                                Icons.Default.ShoppingCart,
                                contentDescription = "Shopping Cart",
                                tint = Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                contentColor = primaryColor
            ) {
                NavigationBarItem(
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Home, contentDescription = "Home")
                            if (selectedTab == 0) {
                                Text("🏠", fontSize = 8.sp)
                            }
                        }
                    },
                    label = { Text("Home") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryColor,
                        selectedTextColor = primaryColor,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                            if (selectedTab == 1) {
                                Text("🔍", fontSize = 8.sp)
                            }
                        }
                    },
                    label = { Text("Search") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryColor,
                        selectedTextColor = primaryColor,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
                NavigationBarItem(
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Person, contentDescription = "Profile")
                            if (selectedTab == 2) {
                                Text("👤", fontSize = 8.sp)
                            }
                        }
                    },
                    label = { Text("Profile") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = primaryColor,
                        selectedTextColor = primaryColor,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray
                    )
                )
            }
        }
    ) { paddingValues ->
        when (selectedTab) {
            0 -> HomeScreen(
                paddingValues = paddingValues,
                primaryColor = primaryColor,
                accentColor = accentColor,
                backgroundColor = backgroundColor,
                lightGreen = lightGreen,
                navigateToAddProduct = ::navigateToAddProduct,
                navigateToViewAddedProduct = ::navigateToViewAddedProduct,
                userName = userName
            )
            1 -> SearchScreen(
                paddingValues = paddingValues,
                primaryColor = primaryColor,
                accentColor = accentColor,
                lightGreen = lightGreen
            )
            2 -> ProfileScreen(
                paddingValues = paddingValues,
                primaryColor = primaryColor,
                accentColor = accentColor,
                lightGreen = lightGreen,
                userName = userName,
                userEmail = userEmail,
                userPhone = userPhone,
                userAddress = userAddress,
                isUserDataLoaded = isUserDataLoaded,
                userViewModel = userViewModel
            )
        }
    }
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    primaryColor: Color,
    accentColor: Color,
    backgroundColor: Color,
    lightGreen: Color,
    navigateToAddProduct: () -> Unit,
    navigateToViewAddedProduct: () -> Unit,
    userName: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(backgroundColor)
    ) {
        // Welcome Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = primaryColor
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(primaryColor, lightGreen)
                        )
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "🌱 Welcome Back, $userName! 🥕",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Fresh groceries delivered to your door",
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text(
                            "🥬 Farm fresh • 🍅 Organic • 🚚 Fast delivery",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "🛒",
                            fontSize = 32.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Fresh Produce Showcase Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    lightGreen.copy(alpha = 0.7f),
                                    primaryColor.copy(alpha = 0.9f)
                                )
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🥕", fontSize = 40.sp)
                        Text("🥬", fontSize = 40.sp)
                        Text("🍅", fontSize = 40.sp)
                        Text("🌽", fontSize = 40.sp)
                        Text("🥒", fontSize = 40.sp)
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(20.dp)
                ) {
                    Text(
                        "🌱 Fresh & Organic Produce",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "🥬 Farm to table in 24 hours",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // View Added Products Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { navigateToViewAddedProduct() },
            colors = CardDefaults.cardColors(
                containerColor = accentColor.copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(accentColor.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📋", fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            "🥕 View My Grocery List",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1B5E20)
                        )
                        Text(
                            "Check your added fresh items",
                            fontSize = 12.sp,
                            color = Color(0xFF1B5E20).copy(alpha = 0.7f)
                        )
                    }
                }
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = "Navigate",
                    tint = primaryColor,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add Product to Cart Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Button(
                onClick = { navigateToAddProduct() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("🛒", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Add Fresh Groceries",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            "🥬 Start shopping fresh produce!",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Categories Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    "🌟 Quick Categories",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    QuickCategoryItem("🥕", "Vegetables", lightGreen)
                    QuickCategoryItem("🍎", "Fruits", accentColor)
                    QuickCategoryItem("🥛", "Dairy", primaryColor)
                    QuickCategoryItem("🍞", "Bakery", Color(0xFF8D6E63))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun QuickCategoryItem(emoji: String, label: String, color: Color) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            Toast.makeText(context, "$emoji $label category coming soon!", Toast.LENGTH_SHORT).show()
        }
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1B5E20)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    paddingValues: PaddingValues,
    primaryColor: Color,
    accentColor: Color,
    lightGreen: Color
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .background(Color(0xFFF1F8E9))
    ) {
        Text(
            "🔍 Search Fresh Groceries",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            placeholder = { Text("Search for fresh vegetables, fruits...") },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = primaryColor
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.Gray
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = primaryColor,
                focusedLabelColor = primaryColor,
                cursorColor = primaryColor,
                unfocusedBorderColor = lightGreen
            ),
            shape = RoundedCornerShape(12.dp)
        )

        // Search placeholder content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("🔍", fontSize = 80.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Search Your Groceries",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
            Text(
                "🛒 Feature coming soon!",
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
            Text(
                "Enter keywords to find your fresh items",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    primaryColor: Color,
    accentColor: Color,
    lightGreen: Color,
    userName: String,
    userEmail: String,
    userPhone: String,
    userAddress: String,
    isUserDataLoaded: Boolean,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
            .background(Color(0xFFF1F8E9))
    ) {
        // Profile Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = primaryColor
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
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
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.White.copy(alpha = 0.2f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 60.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        userName,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "🌱 Fresh Grocery Member 🥕",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // User Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("📋", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Account Information",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1B5E20)
                    )
                }

                if (isUserDataLoaded) {
                    UserInfoRow(
                        label = "Email Address",
                        value = userEmail.ifEmpty { "Not provided" },
                        emoji = "📧"
                    )

                    UserInfoRow(
                        label = "Phone Number",
                        value = userPhone.ifEmpty { "Not added" },
                        emoji = "📱"
                    )

                    UserInfoRow(
                        label = "Delivery Address",
                        value = userAddress.ifEmpty { "Not added" },
                        emoji = "🏠"
                    )
                } else {
                    repeat(3) {
                        UserInfoRow(
                            label = "Loading...",
                            value = "🔄 Please wait...",
                            emoji = "⏳"
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Logout Option
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    userViewModel.logout { success, message ->
                        Toast.makeText(context, "🌱 $message", Toast.LENGTH_SHORT).show()
                        if (success) {
                            try {
                                val intent = Intent(context, LoginActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Logout successful", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        "🚪 Sign Out",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1B5E20)
                    )
                    Text(
                        "Leave FreshMart securely",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun UserInfoRow(
    label: String,
    value: String,
    emoji: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                label,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1B5E20)
            )
        }
    }
    if (label != "Delivery Address") {
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}
