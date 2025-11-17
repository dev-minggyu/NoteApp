package com.note.feature.main.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.note.feature.common.ui.component.SimpleTextField
import com.note.feature.common.ui.theme.AppTheme
import com.note.feature.main.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    isGrid: Boolean,
    onSearchQueryChange: (String) -> Unit,
    onToggleListMode: () -> Unit
) {
    val toggleButtonDegree by animateFloatAsState(
        targetValue = if (isGrid) 90f else 0f,
        label = "toggle_animation"
    )

    TopAppBar(
        modifier = modifier,
        title = {
            MainSearchBar(
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange
            )
        },
        actions = {
            IconButton(onClick = onToggleListMode) {
                Icon(
                    modifier = Modifier.rotate(toggleButtonDegree),
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.main_change_note_list_layout),
                    tint = AppTheme.colors.toggleTint,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = AppTheme.colors.contentBackground
        )
    )
}

@Composable
private fun MainSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    SimpleTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = AppTheme.colors.primary,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                SearchClearButton(onClick = { onSearchQueryChange("") })
            }
        }
    )
}

@Composable
private fun SearchClearButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(24.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = AppTheme.colors.primary,
            modifier = Modifier.size(20.dp)
        )
    }
}