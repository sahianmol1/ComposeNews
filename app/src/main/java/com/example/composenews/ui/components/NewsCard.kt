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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.composenews.data.local.TopNewsEntity
import com.example.composenews.ui.theme.ComposeNewsTheme

@Composable
fun NewsCard(item: TopNewsEntity) {
    val context = LocalContext.current
    Card(
        elevation = 8.dp,
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column() {
            Image(
                painter = rememberAsyncImagePainter(
                    item.urlToImage,
                    contentScale = ContentScale.FillWidth,
                ),
                contentDescription = "",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                text = item.description
            )
        }

    }
}

