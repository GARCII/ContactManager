package com.lydia.contact.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DCodeIcon {
    data class ImageVectorIcon(val imageVector: ImageVector) : DCodeIcon()
}

object MyIcons {
    val AccountBox = Icons.Default.AccountBox
    val Calendar = Icons.Filled.DateRange
    val Phone = Icons.Filled.Phone
    val Location = Icons.Rounded.LocationOn
    val ArrowBack = Icons.AutoMirrored.Filled.ArrowBack
    val Email = Icons.Filled.Email
}