package com.example.freshmart.view

import android.app.Activity
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import com.example.freshmart.model.UserModel
import com.example.freshmart.repository.UserRepositoryImpl
import com.example.freshmart.viewmodel.UserViewModel

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationBody()
        }
    }
}

@Composable
fun RegistrationBody() {
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var phoneNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var agreeToTerms by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val activity = context as? Activity
    val scrollState = rememberScrollState()

    // Fresh grocery theme colors
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
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                // Header
                Text(
                    text = "🌱 Join FreshMart Family 🌱",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = textColor
                )
                Text(
                    text = "🥬 Create account for fresh groceries 🥕",
                    fontSize = 16.sp,
                    color = placeholderColor,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Logo Section with Card
                Card(
                    modifier = Modifier
                        .size(160.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(80.dp),
                            ambientColor = primaryColor.copy(alpha = 0.3f)
                        ),
                    shape = RoundedCornerShape(80.dp),
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
                            fontSize = 56.sp
                        )
                        // Uncomment if you have logo drawable:
                        // Image(
                        //     painter = painterResource(R.drawable.freshmart_logo),
                        //     contentDescription = "FreshMart Logo",
                        //     modifier = Modifier.size(100.dp)
                        // )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                // Registration Form Card
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
                                text = "Create Your Account",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }

                        // Full Name Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = primaryColor
                                )
                            },
                            label = { Text("Full Name", color = primaryColor) },
                            placeholder = {
                                Text("Enter your full name", color = placeholderColor.copy(alpha = 0.7f))
                            },
                            value = fullName,
                            onValueChange = { input ->
                                fullName = input
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                focusedLabelColor = primaryColor,
                                unfocusedLabelColor = placeholderColor
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Email Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = null,
                                    tint = primaryColor
                                )
                            },
                            label = { Text("Email Address", color = primaryColor) },
                            placeholder = {
                                Text("farmer@freshmart.com", color = placeholderColor.copy(alpha = 0.7f))
                            },
                            value = email,
                            onValueChange = { input ->
                                email = input
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                focusedLabelColor = primaryColor,
                                unfocusedLabelColor = placeholderColor
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Password Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            visualTransformation = if (passwordVisibility) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
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
                            label = { Text("Password", color = primaryColor) },
                            placeholder = {
                                Text("Create strong password", color = placeholderColor.copy(alpha = 0.7f))
                            },
                            value = password,
                            onValueChange = { input ->
                                password = input
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                focusedLabelColor = primaryColor,
                                unfocusedLabelColor = placeholderColor
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Confirm Password Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = primaryColor
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }
                                ) {
                                    Icon(
                                        imageVector = if (confirmPasswordVisibility)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,
                                        contentDescription = if (confirmPasswordVisibility) "Hide password" else "Show password",
                                        tint = primaryColor
                                    )
                                }
                            },
                            label = { Text("Confirm Password", color = primaryColor) },
                            placeholder = {
                                Text("Re-enter your password", color = placeholderColor.copy(alpha = 0.7f))
                            },
                            value = confirmPassword,
                            onValueChange = { input ->
                                confirmPassword = input
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                focusedLabelColor = primaryColor,
                                unfocusedLabelColor = placeholderColor
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Phone Number Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = primaryColor
                                )
                            },
                            label = { Text("Phone Number", color = primaryColor) },
                            placeholder = {
                                Text("+1 (555) 123-4567", color = placeholderColor.copy(alpha = 0.7f))
                            },
                            value = phoneNumber,
                            onValueChange = { input ->
                                phoneNumber = input
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                focusedLabelColor = primaryColor,
                                unfocusedLabelColor = placeholderColor
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // Address Field
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            keyboardOptions = KeyboardOptions(),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = primaryColor
                                )
                            },
                            label = { Text("Delivery Address", color = primaryColor) },
                            placeholder = {
                                Text("123 Fresh Street, Green City", color = placeholderColor.copy(alpha = 0.7f))
                            },
                            value = address,
                            onValueChange = { input ->
                                address = input
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = lightGreen.copy(alpha = 0.5f),
                                focusedLabelColor = primaryColor,
                                unfocusedLabelColor = placeholderColor
                            )
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Terms and Conditions Checkbox
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = backgroundColor.copy(alpha = 0.3f)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = agreeToTerms,
                                    onCheckedChange = { checked ->
                                        agreeToTerms = checked
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = primaryColor,
                                        checkmarkColor = Color.White,
                                        uncheckedColor = lightGreen
                                    )
                                )
                                Column {
                                    Text(
                                        text = "I agree to FreshMart's Terms & Conditions",
                                        color = textColor,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Text(
                                        text = "🌿 Fresh produce guarantee & privacy policy",
                                        color = placeholderColor,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(28.dp))

                        // Register Button
                        Button(
                            onClick = {
                                // Validation
                                if (fullName.isBlank() || email.isBlank() || phoneNumber.isBlank() ||
                                    password.isBlank() || confirmPassword.isBlank() || address.isBlank()) {
                                    Toast.makeText(context, "🚫 Please fill all fields", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                if (password != confirmPassword) {
                                    Toast.makeText(context, "🔒 Passwords do not match", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                if (!agreeToTerms) {
                                    Toast.makeText(context, "📋 Please agree to terms and conditions", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                userViewModel.register(email, password) { success, message, userId ->
                                    if (success) {
                                        val userModel = UserModel(
                                            userId, fullName, email, "", phoneNumber, address
                                        )
                                        userViewModel.addUserToDatabase(userId, userModel) { success, message ->
                                            if (success) {
                                                Toast.makeText(context, "🎉 $message", Toast.LENGTH_LONG).show()
                                                val intent = Intent(context, LoginActivity::class.java)
                                                context.startActivity(intent)
                                                activity?.finish()
                                            } else {
                                                Toast.makeText(context, "❌ $message", Toast.LENGTH_LONG).show()
                                            }
                                        }
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
                                    text = "🌱",
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "Join FreshMart Community",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Login Link
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Already shopping with us? ",
                                color = placeholderColor,
                                fontSize = 15.sp
                            )
                            Text(
                                "Sign In 🍎",
                                color = accentColor,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline,
                                modifier = Modifier.clickable {
                                    val intent = Intent(context, LoginActivity::class.java)
                                    context.startActivity(intent)
                                    activity?.finish()
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
                        "  or register with  ",
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

                // Social Registration Options
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Google Registration Card
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                Toast.makeText(context, "🔍 Google registration coming soon!", Toast.LENGTH_SHORT).show()
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
                            //     contentDescription = "Google Registration",
                            //     modifier = Modifier.size(32.dp)
                            // )
                        }
                    }

                    // Facebook Registration Card
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                Toast.makeText(context, "📘 Facebook registration coming soon!", Toast.LENGTH_SHORT).show()
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
                            //     contentDescription = "Facebook Registration",
                            //     modifier = Modifier.size(32.dp)
                            // )
                        }
                    }

                    // Apple Registration Card (Optional)
                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                Toast.makeText(context, "🍎 Apple registration coming soon!", Toast.LENGTH_SHORT).show()
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
                    "🌿 Fresh • Organic • Healthy • Delivered 🌿",
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
fun RegistrationPreviewBody() {
    RegistrationBody()
}
