package com.rgi.amhisevikari.presentation.screens.splash

import android.os.Build
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.Lyrics
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.rgi.amhisevikari.R
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rgi.amhisevikari.ui.theme.GoldAccent
import com.rgi.amhisevikari.ui.theme.Inter
import com.rgi.amhisevikari.ui.theme.NotoSansMarathi
import com.rgi.amhisevikari.ui.theme.PlusJakartaSans
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToMain: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2500L)
        onNavigateToMain()
    }

    // Infinite pulsing animation for loading bar
    val infiniteTransition = rememberInfiniteTransition(label = "splash_pulse")
    val loadingOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "loading_bar"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                    Modifier.statusBarsPadding().navigationBarsPadding()
                else Modifier
            )
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF9933), // Vivid saffron top
                        Color(0xFFFFB84D)  // Lighter golden bottom
                    )
                )
            )
    ) {
        // Background ambient gold glow — top left
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = (-60).dp, y = 40.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(GoldAccent.copy(alpha = 0.25f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
                .blur(80.dp)
        )

        // Background ambient white glow — bottom right
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = (-120).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.15f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
                .blur(60.dp)
        )

        // Watermark: large rotated music note in bottom right
        // Watermark: large subtle text instead of music note
        Text(
            text = "आम्ही सेवेकरी",
            fontSize = 80.sp,
            fontFamily = NotoSansMarathi,
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = 0.05f),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 40.dp, y = 40.dp)
                .rotate(-15f)
        )

        // Center brand cluster
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Glassmorphic logo circle
            Surface(
                modifier = Modifier.size(96.dp),
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.20f),
                tonalElevation = 0.dp,
                shadowElevation = 24.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo_themed),
                        contentDescription = "Logo",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(120.dp)
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            // App title — Marathi
            Text(
                text = "आम्ही सेवेकरी",
                fontFamily = NotoSansMarathi,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 40.sp,
                color = Color.White,
                letterSpacing = (-0.5).sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(10.dp))

            // Tagline
            Text(
                text = "भक्तांसाठी भजन सेवा",
                fontFamily = NotoSansMarathi,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.88f),
                letterSpacing = 0.8.sp,
                textAlign = TextAlign.Center
            )
        }

        // Bottom loading section
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Animated loading bar
            Box(
                modifier = Modifier
                    .width(56.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.25f))
            ) {
                Box(
                    modifier = Modifier
                        .width(18.dp)
                        .height(3.dp)
                        .offset(x = ((loadingOffset + 1f) / 2f * 38).dp)
                        .clip(RoundedCornerShape(50))
                        .background(GoldAccent)
                )
            }

            Text(
                text = "डिजिटल संकुल",
                fontFamily = NotoSansMarathi,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.60f),
                letterSpacing = 2.sp
            )
        }

        // Footer meta
        Text(
            text = "V1.0.0 • भजन सेवा",
            fontFamily = Inter,
            fontSize = 10.sp,
            color = Color.White.copy(alpha = 0.38f),
            letterSpacing = 1.5.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        )
    }
}
