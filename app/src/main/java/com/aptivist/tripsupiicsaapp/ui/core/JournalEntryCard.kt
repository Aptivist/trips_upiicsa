package com.aptivist.tripsupiicsaapp.ui.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aptivist.tripsupiicsaapp.R
import com.aptivist.tripsupiicsaapp.domain.models.JournalEntryModel

@Composable
fun JournalEntryCard(
    modifier: Modifier = Modifier,
    journalEntryItem: JournalEntryModel,
    onDeleteJournalItem: (JournalEntryModel) -> Unit,
    onUpdateJournalItem: () -> Unit,
) {
    var showDropdownMenu by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    journalEntryItem.title,
                    modifier = Modifier.weight(1f),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                )
                Box {
                    IconButton(
                        onClick = { showDropdownMenu = !showDropdownMenu }
                    ) {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = showDropdownMenu,
                        onDismissRequest = { showDropdownMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.update)) },
                            leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = null) },
                            onClick = {
                                onUpdateJournalItem()
                                showDropdownMenu = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.delete)) },
                            leadingIcon = { Icon(Icons.Filled.Delete, contentDescription = null) },
                            onClick = {
                                onDeleteJournalItem(journalEntryItem)
                                showDropdownMenu = false
                            }
                        )
                    }
                }
            }
            if (journalEntryItem.content.isNotEmpty()) {
                Text(journalEntryItem.content)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JournalEntryCardPreview() {
    JournalEntryCard(
        journalEntryItem = JournalEntryModel(
            id = 0,
            title = "Title",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ex augue, porta quis sapien non, cursus tempus nisi. Maecenas fringilla blandit blandit. Cras quis ligula non sem vehicula malesuada quis ut orci. Nullam lobortis, nunc vel mattis molestie, ante velit volutpat felis, in sollicitudin turpis erat non lectus. Curabitur rutrum, libero nec interdum venenatis, ipsum diam commodo nulla, at feugiat ante justo sed magna. Vivamus rhoncus gravida dui a tristique. Maecenas quam enim, posuere ac malesuada sit amet, euismod vitae mi. Morbi interdum risus et nulla rhoncus interdum. Praesent vehicula, ligula vel convallis lacinia, justo risus sollicitudin nisi, vel fermentum purus elit ac libero. In a tortor ac nulla pretium convallis. Nullam blandit, tortor sed tempus suscipit, ante massa viverra lectus, quis semper sapien velit eget risus."
        ),
        onDeleteJournalItem = {},
        onUpdateJournalItem = {}
    )
}