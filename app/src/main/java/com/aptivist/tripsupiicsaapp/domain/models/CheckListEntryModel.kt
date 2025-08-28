package com.aptivist.tripsupiicsaapp.domain.models

data class CheckListEntryModel(
    val id: Long? = null,
    val name: String,
    val isChecked: Boolean,
)