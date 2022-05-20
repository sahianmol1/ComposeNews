package com.example.composenews.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.composenews.data.remote.models.SourceItem

@Composable
fun SourceCard(source: SourceItem) {
    val context = LocalContext.current
    Card(
        elevation = 8.dp,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp).clickable {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(source.url))
            context.startActivity(intent)
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column() {
            Text(
                text = source.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                text = source.description
            )
        }
    }
}