package com.rgi.amhisevikari.presentation.screens.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Lyrics
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.rgi.amhisevikari.R
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rgi.amhisevikari.domain.model.Bhajan
import com.rgi.amhisevikari.domain.usecase.CategoryWithBhajans
import com.rgi.amhisevikari.ui.theme.GoldAccent
import com.rgi.amhisevikari.ui.theme.Inter
import com.rgi.amhisevikari.ui.theme.NotoSansMarathi
import com.rgi.amhisevikari.ui.theme.PlusJakartaSans
import com.rgi.amhisevikari.ui.theme.SurfaceContainerLowest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateToSubList: (String) -> Unit,
    onNavigateToLyrics: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var showSocialSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {

        // Subtle ambient saffron radial glow — top right corner
        Box(
            modifier = Modifier
                .size(360.dp)
                .align(Alignment.TopEnd)
                .blur(100.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Color(0x14FF9933), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                // Glassmorphic top bar — respects status bar on API 29+ edge-to-edge devices
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White.copy(alpha = 0.92f),
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                                    Modifier.statusBarsPadding() else Modifier
                            )
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "आम्ही सेवेकरी",
                            fontFamily = NotoSansMarathi,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        // Follow Us pill button — replaces plain search icon
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.18f),
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { showSocialSheet = true }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    Icons.Default.Share,
                                    contentDescription = "Follow Us",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "फॉलो करा",
                                    fontFamily = PlusJakartaSans,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                // Peace Header
                item {
                    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
                    val (greeting, subtitle) = when (hour) {
                        in 5..11 -> "शुभ प्रभात" to "तुमचा दिवस आध्यात्मिक ऊर्जेने भरलेला जावो."
                        in 12..16 -> "शुभ दुपार" to "तुमचा आजचा दिवस आनंददायी आणि भक्तीमय जावो."
                        else -> "शुभ संध्याकाळ" to "तुमची संध्याकाळ शांतता आणि समाधानाने भरलेली जावो."
                    }

                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 24.dp)) {
                        Text(
                            text = "नमस्कार",
                            fontFamily = NotoSansMarathi,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = greeting,
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 36.sp,
                            letterSpacing = (-0.5).sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = subtitle,
                            fontFamily = NotoSansMarathi,
                            fontSize = 14.sp,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Pill Search Bar
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(50),
                        color = SurfaceContainerLowest,
                        shadowElevation = 3.dp,
                        tonalElevation = 0.dp
                    ) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { viewModel.onSearchQueryChange(it) },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    "भजन किंवा देवता शोधा...",
                                    fontFamily = NotoSansMarathi,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }

                // Section header
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "देवता आणि सेवा",
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                // Content states
                when (val state = uiState) {
                    is MainUiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                    is MainUiState.Empty -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "माहिती समक्रमित होत आहे...",
                                    fontFamily = NotoSansMarathi,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    is MainUiState.NoResults -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "\"$searchQuery\" साठी काहीही सापडले नाही",
                                    fontFamily = NotoSansMarathi,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    is MainUiState.Error -> {
                        item {
                            Text(
                                "Error: ${state.message}",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(24.dp)
                            )
                        }
                    }
                    is MainUiState.Success -> {
                        items(state.categories, key = { it.category.id }) { item ->
                            ExpandableCategoryCard(
                                item = item,
                                isSearchActive = searchQuery.isNotBlank(),
                                onNavigateToSubList = { onNavigateToSubList(item.category.id) },
                                onNavigateToLyrics = { bhajan -> onNavigateToLyrics(bhajan.id) }
                            )
                        }
                    }
                }

                // Quote Bento
                item {
                    Spacer(Modifier.height(8.dp))
                    QuoteBentoSection()
                }
            }
        }

    }

    // Follow Us Bottom Sheet
    if (showSocialSheet) {
        SocialFollowSheet(onDismiss = { showSocialSheet = false })
    }
}

// ──────────────────────────────────────────────
// Expandable Category Card — matches screenshot
// ──────────────────────────────────────────────
@Composable
fun ExpandableCategoryCard(
    item: CategoryWithBhajans,
    isSearchActive: Boolean = false,
    onNavigateToSubList: () -> Unit,
    onNavigateToLyrics: (Bhajan) -> Unit
) {
    var expanded by remember(isSearchActive) { mutableStateOf(isSearchActive) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        shape = RoundedCornerShape(24.dp),
        color = if (expanded) Color(0xFFF0EDE8) else SurfaceContainerLowest,
        shadowElevation = if (expanded) 3.dp else 1.dp,
        tonalElevation = 0.dp
    ) {
        Column {
            // Header row — tap to expand/collapse
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { expanded = !expanded }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circle icon — matches screenshot exactly
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(
                            if (expanded)
                                MaterialTheme.colorScheme.primary       // filled saffron when open
                            else
                                Color(0xFFFFF0E0)                       // warm peach when closed
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Lyrics,
                        contentDescription = null,
                        tint = if (expanded) Color.White else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.category.name,
                        fontFamily = NotoSansMarathi,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (expanded)
                            item.category.enName.ifEmpty { "Bhajan Collection" }
                        else
                            "${item.bhajans.size} भजन",
                        fontFamily = Inter,
                        fontSize = 12.sp,
                        color = if (expanded)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.outline
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                                  else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Expanded bhajan list
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item.bhajans.take(5).forEach { bhajan ->
                        BhajanRowItem(
                            title = bhajan.title,
                            onClick = { onNavigateToLyrics(bhajan) }
                        )
                    }

                    // "View all" link
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = if (item.bhajans.size > 5)
                            "सर्व ${item.bhajans.size} भजन पहा →"
                        else
                            "संपूर्ण श्रेणी पहा →",
                        fontFamily = NotoSansMarathi,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onNavigateToSubList() }
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BhajanRowItem(title: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        color = Color.White,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Lyrics,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.60f),
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = title,
                fontFamily = NotoSansMarathi,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.50f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// ──────────────────────────────────────────────
// Quote Bento Section
// ──────────────────────────────────────────────
@Composable
fun QuoteBentoSection() {
    val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
    val quote = when (hour) {
        in 5..11 -> "सूर्योदयाची पहिली किरणे जशी अंधार नष्ट करतात, तशी परमेश्वराची भक्ती मनातील भीती नष्ट करते."
        in 12..16 -> "कर्तव्य पालनातच खऱ्या अर्थाने देवाची सेवा सामावलेली असते."
        else -> "दिवसाच्या थकव्यानंतर देवाचे नाव घेतल्याने मनाला असीम शांती मिळते."
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF7A4000), Color(0xFFAF5C00))
                    ),
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(22.dp)
        ) {
            Column {
                Text(
                    text = "❝",
                    fontSize = 28.sp,
                    color = Color.White.copy(alpha = 0.55f)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = quote,
                    fontFamily = NotoSansMarathi,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    color = Color.White
                )
                Spacer(Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "- संत विचार",
                        fontFamily = Inter,
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.70f)
                    )
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.White.copy(alpha = 0.18f)
                    ) {
                        Text(
                            text = "शेअर करा",
                            fontFamily = PlusJakartaSans,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = Color.White,
                            letterSpacing = 0.8.sp,
                            modifier = Modifier.padding(horizontal = 13.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                color = GoldAccent
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text("📅", fontSize = 20.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "आजचा पंचांग",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFF6E5C00)
                    )
                    Text(
                        "श्रावण शुद्ध दशमी",
                        fontFamily = NotoSansMarathi,
                        fontSize = 10.sp,
                        color = Color(0xFF6E5C00).copy(alpha = 0.78f)
                    )
                }
            }
            Surface(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
                color = Color(0xFFE8E8E8)
            ) {
                Column(Modifier.padding(14.dp)) {
                    Text("🔔", fontSize = 20.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "पुढील उत्सव",
                        fontFamily = PlusJakartaSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "महाशिवरात्री",
                        fontFamily = NotoSansMarathi,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
    }
}

// ──────────────────────────────────────────────
// Follow Us Bottom Sheet
// ──────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialFollowSheet(onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val context = LocalContext.current

    data class SocialLink(
        val icon: androidx.compose.ui.graphics.vector.ImageVector,
        val name: String,
        val handle: String,
        val bgColor: Color,
        val url: String
    )

    val links = listOf(
        SocialLink(Icons.Default.ChatBubble, "व्हॉट्सॲप", "WhatsApp Channel", Color(0xFF25D366), "https://whatsapp.com/channel/"),
        SocialLink(Icons.Default.CameraAlt, "इन्स्टाग्राम", "@amhisevekari", Color(0xFFE1306C), "https://instagram.com/"),
        SocialLink(Icons.Default.Facebook, "फेसबुक", "Amhi Sevekari", Color(0xFF1877F2), "https://facebook.com/"),
        SocialLink(Icons.Default.PlayArrow, "यूट्यूब", "Amhi Sevekari", Color(0xFFFF0000), "https://youtube.com/")
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 36.dp)
        ) {
            // Sheet handle + title
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFE0E0E0))
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "आम्हाला फॉलो करा",
                    fontFamily = PlusJakartaSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "सोशल मीडियावर जुडा आणि भक्तीसाठी अपडेट मिळवा",
                    fontFamily = NotoSansMarathi,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }

            // Social link rows
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                links.forEach { link ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                runCatching {
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
                                    )
                                }
                                onDismiss()
                            },
                        shape = RoundedCornerShape(18.dp),
                        color = link.bgColor.copy(alpha = 0.08f),
                        tonalElevation = 0.dp,
                        shadowElevation = 0.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            // Platform icon circle
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(link.bgColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = link.icon,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = link.name,
                                    fontFamily = NotoSansMarathi,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = link.handle,
                                    fontFamily = Inter,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            Icon(
                                Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = link.bgColor,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
