package com.dedany.cinenear.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dedany.cinenear.ui.theme.CinenearTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    CinenearTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            content = content
        )
    }
}
