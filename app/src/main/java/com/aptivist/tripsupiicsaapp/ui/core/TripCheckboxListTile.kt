package com.aptivist.tripsupiicsaapp.ui.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aptivist.tripsupiicsaapp.domain.models.CheckListEntryModel

@Composable
fun TripCheckboxListTile(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    item: CheckListEntryModel,
    onCheckedChange: (Boolean) -> Unit,
    onRemove: (CheckListEntryModel) -> Unit
) {
    val swipeToDismissBoxValue = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) onRemove(item)
            it != SwipeToDismissBoxValue.StartToEnd
        }
    )

    SwipeToDismissBox(
        modifier = modifier,
        state = swipeToDismissBoxValue,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            when (swipeToDismissBoxValue.dismissDirection) {
                SwipeToDismissBoxValue.StartToEnd -> {}
                SwipeToDismissBoxValue.EndToStart -> {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )
                }

                SwipeToDismissBoxValue.Settled -> {}
            }
        }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.background)
                    .clickable { onCheckedChange(!isChecked) },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { onCheckedChange(it) }
                )
                Text(item.name)
            }
            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TripCheckboxListTilePreview() {
    TripCheckboxListTile(
        isChecked = false,
        item = CheckListEntryModel(
            id = 0,
            name = "Title",
            isChecked = false,
        ),
        onCheckedChange = {},
        onRemove = {}
    )
}