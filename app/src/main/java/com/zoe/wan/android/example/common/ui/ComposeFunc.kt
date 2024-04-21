package com.zoe.wan.android.example.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.zoe.wan.android.example.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(onBackClick: () -> Unit, title: String) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(id = R.color.teal_700)),
        modifier = Modifier.background(color = colorResource(id = R.color.teal_700)),
        title = { Text(text = title, color = Color.White) }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.dp_30))
                        .height(dimensionResource(id = R.dimen.dp_30))
                )
            }
        })
}
