package com.example.freshmart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.freshmart.view.LoginActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreshMartTheme {
                SplashScreen(
                    onNavigateToLogin = {
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun FreshMartTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF4CAF50),
            secondary = Color(0xFF8BC34A),
            background = Color(0xFFF1F8E9)
        ),
        content = content
    )
}

@Composable
fun SplashScreen(onNavigateToLogin: () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    var logoVisible by remember { mutableStateOf(false) }
    var textVisible by remember { mutableStateOf(false) }

    // Rotation animation for decorative elements
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "rotation"
    )

    // Scale animation for logo
    val logoScale by animateFloatAsState(
        targetValue = if (logoVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "logoScale"
    )

    // Start animations with staggered delays
    LaunchedEffect(Unit) {
        visible = true
        delay(300)
        logoVisible = true
        delay(500)
        textVisible = true
        delay(1200) // Total 2 seconds
        onNavigateToLogin()
    }

    // Gradient colors for fresh grocery theme
    val gradientColors = listOf(
        Color(0xFF1B5E20), // Dark Green
        Color(0xFF2E7D32), // Green
        Color(0xFF388E3C), // Medium Green
        Color(0xFF4CAF50), // Light Green
        Color(0xFF81C784), // Very Light Green
        Color(0xFFA5D6A7)  // Pale Green
    )

    val freshGradient = listOf(
        Color(0xFF4CAF50), // Fresh Green
        Color(0xFF8BC34A), // Light Green
        Color(0xFFCDDC39)  // Lime Green
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = gradientColors
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Background decorative circles
        if (visible) {
            // Large rotating circle
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .rotate(rotationAngle)
                    .alpha(0.1f)
                    .background(
                        Color.White,
                        CircleShape
                    )
            )

            // Smaller rotating circle
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .rotate(-rotationAngle * 0.7f)
                    .alpha(0.15f)
                    .background(
                        brush = Brush.radialGradient(freshGradient),
                        CircleShape
                    )
            )
        }

        // Main content
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(800))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(32.dp)
            ) {
                // Logo with scale animation and gradient background
                Card(
                    modifier = Modifier
                        .scale(logoScale)
                        .size(140.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
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
                                    colors = freshGradient
                                ),
                                shape = CircleShape
                            )
                    ) {
                        // Using a fallback icon instead of potentially missing drawable
                        Text(
                            text = "🛒",
                            fontSize = 64.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                         Image(
                             painter = painterResource(id = R.drawable.img),
                             contentDescription = "FreshMart Logo",
                             modifier = Modifier.size(100.dp)
                         )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Animated title text
                AnimatedVisibility(
                    visible = textVisible,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(600)
                    ) + fadeIn(animationSpec = tween(600))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Main title with gradient effect
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.15f)
                            ),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "🛒 FreshMart 🍎",
                                fontSize = 36.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(
                                    horizontal = 24.dp,
                                    vertical = 12.dp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Subtitle with background
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = freshGradient[0].copy(alpha = 0.9f)
                            )
                        ) {
                            Text(
                                text = "🥬 Fresh Grocery Store 🥕",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(
                                    horizontal = 20.dp,
                                    vertical = 8.dp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Loading indicator
                        CircularProgressIndicator(
                            color = freshGradient[1],
                            strokeWidth = 3.dp,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}
