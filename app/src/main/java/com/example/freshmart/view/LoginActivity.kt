package com.example.freshmart.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.example.freshmart.R
import com.example.freshmart.repository.UserRepositoryImpl
import com.example.freshmart.viewmodel.UserViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                LoginBody(innerPadding)
            }
        }
    }
}

@Composable
fun LoginBody(innerPaddingValues: PaddingValues) {

    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    val context = LocalContext.current
    val activity = context as Activity

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // SharedPreferences for Remember Me functionality
    val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    // Load saved credentials if available
    LaunchedEffect(Unit) {
        val localEmail: String = sharedPreferences.getString("email", "") ?: ""
        val localPassword: String = sharedPreferences.getString("password", "") ?: ""

        if (localEmail.isNotEmpty()) {
            username = localEmail
            password = localPassword
            rememberMe = true
        }
    }

    // Fresh grocery theme color scheme
    val primaryColor = Color(0xFF2E7D32) // Fresh Green
    val accentColor = Color(0xFFFF6F00) // Fresh Orange
    val backgroundColor = Color(0xFFF1F8E9) // Very light green
    val cardColor = Color.White
    val textColor = Color(0xFF1B5E20) // Dark green
    val placeholderColor = Color(0xFF4CAF50) // Medium green
    val lightGreen = Color(0xFF81C784)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            backgroundColor,
                            Color(0xFFE8F5E8), // Light green gradient
                            Color(0xFFF1F8E9)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPaddingValues)
                    .padding(24.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // App Header
                Text(
                    text = "🌱 Welcome to FreshMart 🌱",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "🥬 Fresh Groceries at Your Doorstep 🥕",
                    fontSize = 16.sp,
                    color = placeholderColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Logo Section with Card
                Card(
                    modifier = Modifier
                        .size(180.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(90.dp),
                            ambientColor = primaryColor.copy(alpha = 0.3f)
                        ),
                    shape = RoundedCornerShape(90.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        lightGreen.copy(alpha = 0.3f),
                                        primaryColor.copy(alpha = 0.1f)
                                    )
                                )
                            )
                    ) {
                        // Using emoji as fallback - replace with your logo
                        Text(
                            text = "🛒",
                            fontSize = 64.sp
                        )
                        // Uncomment if you have logo drawable:
                        // Image(
                        //     painter = painterResource(R.drawable.freshmart_logo),
                        //     contentDescription = "FreshMart Logo",
                        //     modifier = Modifier.size(120.dp)
                        // )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Login Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(20.dp),
                            ambientColor = primaryColor.copy(alpha = 0.1f)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(28.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 24.dp)
                        ) {
                            Text(
                                text = "🍎",
                                fontSize = 24.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Sign In to FreshMart",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }

                        // Email Field
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            label = { Text("Email Address", color = primaryColor) },
                            placeholder = { Text("farmer@freshmart.com", color = placeholderColor.copy(alpha = 0.7f)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Email,
                                    contentDescription = null,
                                    tint = primaryColor
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                focusedLabelColor = primaryColor,
                                unfocusedLabelColor = placeholderColor
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Password Field
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password", color = primaryColor) },
                            placeholder = { Text("Enter your password", color = placeholderColor.copy(alpha = 0.7f)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = primaryColor
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { passwordVisibility = !passwordVisibility }
                                ) {
                                    Icon(
                                        imageVector = if (passwordVisibility)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,
                                        contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                                        tint = primaryColor
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisibility)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                focusedLabelColor = primaryColor,
                                unfocusedLabelColor = placeholderColor
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Remember Me and Forgot Password Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = rememberMe,
                                    onCheckedChange = { rememberMe = it },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = primaryColor,
                                        checkmarkColor = Color.White,
                                        uncheckedColor = lightGreen
                                    )
                                )
                                Text(
                                    "Keep me signed in",
                                    color = textColor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Text(
                                "Forgot Password?",
                                color = accentColor,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    val intent = Intent(context, ResetPasswordActivity::class.java)
                                    context.startActivity(intent)
                                    activity.finish()
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // Login Button
                        Button(
                            onClick = {
                                if (rememberMe) {
                                    editor.putString("email", username)
                                    editor.putString("password", password)
                                    editor.apply()
                                }

                                userViewModel.login(username, password) { success, message ->
                                    if (success) {
                                        Toast.makeText(context, "🎉 $message", Toast.LENGTH_LONG).show()
                                        val intent = Intent(context, DashboardActivity::class.java)
                                        context.startActivity(intent)
                                        activity.finish()
                                    } else {
                                        Toast.makeText(context, "❌ $message", Toast.LENGTH_LONG).show()
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 6.dp,
                                pressedElevation = 12.dp
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "🚀",
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Sign In to FreshMart",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Sign Up Link
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "New to FreshMart? ",
                                color = placeholderColor,
                                fontSize = 15.sp
                            )
                            Text(
                                "Create Account 🌟",
                                color = accentColor,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    val intent = Intent(context, RegistrationActivity::class.java)
                                    context.startActivity(intent)
                                    activity.finish()
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Divider with text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(lightGreen.copy(alpha = 0.5f))
                    )
                    Text(
                        "  or continue with  ",
                        color = placeholderColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(lightGreen.copy(alpha = 0.5f))
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Social Login Options
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Google Login Card
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                Toast.makeText(context, "🔍 Google login coming soon!", Toast.LENGTH_SHORT).show()
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Replace with your Google icon
                            Text("G", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4285F4))
                            // Image(
                            //     painter = painterResource(R.drawable.google_icon),
                            //     contentDescription = "Google Login",
                            //     modifier = Modifier.size(32.dp)
                            // )
                        }
                    }

                    // Facebook Login Card
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                Toast.makeText(context, "📘 Facebook login coming soon!", Toast.LENGTH_SHORT).show()
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Replace with your Facebook icon
                            Text("f", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1877F2))
                            // Image(
                            //     painter = painterResource(R.drawable.facebook_icon),
                            //     contentDescription = "Facebook Login",
                            //     modifier = Modifier.size(32.dp)
                            // )
                        }
                    }

                    // Apple Login Card (Optional)
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                Toast.makeText(context, "🍎 Apple login coming soon!", Toast.LENGTH_SHORT).show()
                            },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Replace with your Apple icon
                            Text("🍎", fontSize = 24.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Footer text
                Text(
                    "🌿 Fresh • Organic • Delivered 🌿",
                    color = placeholderColor.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
fun LoginPreviewBody() {
    LoginBody(innerPaddingValues = PaddingValues(0.dp))
}
