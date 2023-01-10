package com.example.newsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.newsapp.model.News
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListScreenViewModel = hiltViewModel(),
) {
    val newsList by viewModel.getNews().observeAsState(initial = emptyList())
    ListScreen(navController, newsList)
}

@Composable
fun ListScreen(
    navController: NavController,
    news: List<News>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Top News") }
            )
        }
    ) {
        LazyColumn{
            items(news){ new ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { }
                    ) {
                    Column() {
                        Image(
                            modifier = Modifier.fillMaxWidth().aspectRatio(16f/9f),
                            painter = rememberAsyncImagePainter(
                            model = new.urlToImage,
                            placeholder = painterResource(R.drawable.placeholder),
                            error = painterResource(R.drawable.placeholder)
                        ),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth
                        )

                        Column(Modifier.padding(8.dp)) {
                            Text(new.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(new.content ?: "", maxLines = 3)
                        }
                    }

                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    NewsAppTheme() {
        ListScreen(
            navController = rememberNavController(),
            news = arrayListOf(
                News("Titulo1", "Descripcion1", "andy", "", ""),
                News("Titulo2", "Descripcion2", "selma", "", ""),
                News("Titulo3", "Descripcion3", "teresa", "", "")
            ))
    }
}


