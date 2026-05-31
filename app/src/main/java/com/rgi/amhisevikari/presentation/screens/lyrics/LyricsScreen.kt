package com.rgi.amhisevikari.presentation.screens.lyrics

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import android.os.Build
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rgi.amhisevikari.ui.theme.Inter
import com.rgi.amhisevikari.ui.theme.NotoSansMarathi
import com.rgi.amhisevikari.ui.theme.PlusJakartaSans
import com.rgi.amhisevikari.ui.theme.SurfaceContainerLowest

@Composable
fun LyricsScreen(
    bhajanId: String,
    viewModel: LyricsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var fontSize by remember { mutableFloatStateOf(18f) }

    LaunchedEffect(bhajanId) {
        // ViewModel auto-loads bhajan by saved state handle
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Subtle ambient glow watermark background
        Box(
            modifier = Modifier
                .size(360.dp)
                .align(Alignment.Center)
                .blur(100.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0x0AFF9933),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        when (val state = uiState) {
            is LyricsUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            is LyricsUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "त्रुटी: ${state.message}",
                        fontFamily = NotoSansMarathi,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(24.dp)
                    )
                }
            }

            is LyricsUiState.Success -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Glassmorphic TopAppBar
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.White.copy(alpha = 0.88f),
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                                        Modifier.statusBarsPadding()
                                    else Modifier
                                )
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = onNavigateBack) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = state.bhajan.title,
                                    fontFamily = PlusJakartaSans,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 1
                                )
                                Text(
                                    text = "नित्य सेवा",
                                    fontFamily = Inter,
                                    fontSize = 10.sp,
                                    letterSpacing = 1.5.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            IconButton(onClick = {}) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = "More",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    // Main scrollable content
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 24.dp, vertical = 24.dp)
                    ) {
                        // Category badge
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.30f)
                        ) {
                            Text(
                                text = "भक्ती संगीत",
                                fontFamily = Inter,
                                fontWeight = FontWeight.Medium,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }

                        Spacer(Modifier.height(20.dp))

                        // Bhajan title — Peace Header style
                        Text(
                            text = state.bhajan.title,
                            fontFamily = NotoSansMarathi,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp,
                            lineHeight = 38.sp,
                            letterSpacing = (-0.3).sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(12.dp))

                        // Orange accent divider
                        Box(
                            modifier = Modifier
                                .width(48.dp)
                                .height(3.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .align(Alignment.CenterHorizontally)
                        )

                        Spacer(Modifier.height(28.dp))

                        // Lyrics card — frosted white container
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(28.dp),
                            color = SurfaceContainerLowest,
                            shadowElevation = 1.dp,
                            tonalElevation = 0.dp
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                // Parse lyrics and render each line-group
                                val stanzas = state.bhajan.lyrics.split("\n\n")
                                stanzas.forEachIndexed { idx, stanza ->
                                    if (stanza.isNotBlank()) {
                                        Text(
                                            text = stanza.trim(),
                                            fontFamily = NotoSansMarathi,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = fontSize.sp,
                                            lineHeight = (fontSize * 1.65f).sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        if (idx < stanzas.size - 1) {
                                            Spacer(Modifier.height(20.dp))
                                        }
                                    }
                                }

                                Spacer(Modifier.height(24.dp))

                                // Decorative separator
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "✦",
                                        fontSize = 18.sp,
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    )
                                }

                                Spacer(Modifier.height(16.dp))

                                // God name sign-off
                                Text(
                                    text = "|| श्री स्वामी समर्थ ||",
                                    fontFamily = NotoSansMarathi,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        Spacer(Modifier.height(100.dp))
                    }
                }
            }
        }

        // Font size FABs — floating on right side
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Increase font
            Surface(
                modifier = Modifier
                    .size(52.dp)
                    .clickable { if (fontSize < 32f) fontSize += 2f },
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                shadowElevation = 12.dp,
                tonalElevation = 0.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Increase Font",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Decrease font
            Surface(
                modifier = Modifier
                    .size(52.dp)
                    .clickable { if (fontSize > 12f) fontSize -= 2f },
                shape = CircleShape,
                color = SurfaceContainerLowest,
                shadowElevation = 6.dp,
                tonalElevation = 0.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Decrease Font",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }

        // Bottom save bar — fading gradient
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background)
                        )
                    )
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .then(
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                            Modifier.navigationBarsPadding()
                        else Modifier
                    )
                    .padding(bottom = 24.dp),
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                tonalElevation = 0.dp,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .clickable { }
                        .padding(horizontal = 24.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "आवडते म्हणून जतन करा",
                        fontFamily = NotoSansMarathi,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}
