package com.aptivist.tripsupiicsaapp.ui.state

import androidx.annotation.StringRes

data class DialogState(
    @param:StringRes val titleResId: Int? = null,
    @param:StringRes val messageResId: Int? = null,
    @param:StringRes val confirmText: Int,
    @param:StringRes val dismissText: Int? = null,
    val onConfirm: (() -> Unit),
    val onDismiss: (() -> Unit),
)