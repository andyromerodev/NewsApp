package com.example.newsapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.model.News
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun DetailsScreen(
    newTitle: String,
    navController: NavController,
    viewModel: DetailsScreenViewModel = hiltViewModel(),
) {
    val new by viewModel.getNewsByTitle(newTitle).observeAsState(initial = null)
    DetailsScreen(newTitle, navController, new)
}

@Composable
fun DetailsScreen(
    newTitle: String,
    navController: NavController,
    new: News?,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(newTitle, maxLines = 1)},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        new?.let {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ){
                Column() {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        painter = rememberAsyncImagePainter(
                            model = new.urlToImage,
                            placeholder = painterResource(R.drawable.placeholder),
                            error = painterResource(R.drawable.placeholder)
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )

                    Column(Modifier.padding(8.dp)) {

                        val context = LocalContext.current
                        Text(new.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(new.content ?: "", Modifier.fillMaxSize())
                        Box(Modifier.size(8.dp))
                        Button(
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(new.url))
                                context.startActivity(intent)
                            }) {
                            Text("Ver mas")
                        }
                    }
                }
            }
        }?: run { 
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    NewsAppTheme {
        DetailsScreen(
            newTitle = "Hello",
            navController = rememberNavController(),
            new = News("Titulo1", "Descripcion1", "andy", "", "")
        )

    }
}