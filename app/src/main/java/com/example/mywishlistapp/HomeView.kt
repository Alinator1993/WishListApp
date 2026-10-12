package com.example.mywishlistapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.DummyWish
import com.example.mywishlistapp.data.Wish
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.ui.Alignment
import androidx.compose.animation.animateColorAsState
import androidx.compose.runtime.getValue

@Composable
fun HomeView(modifier: Modifier,
             navController: NavController,
             viewModel: WishViewModel
             ) {
    Scaffold(
        topBar = { AppBarView(title = "WishList") },

        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                containerColor = colorResource(id = R.color.app_bar_color), // same as AppBarView
                contentColor = Color.White,
                onClick = {
                    navController.navigate(Screen.AddScreen.route + "/0L")
                }) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "add"
                )
            }
        }

    ) {
        val wishList = viewModel.getAllWishes.collectAsState(listOf())
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            items(wishList.value, key = { wish -> wish.id }) { wish ->
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { value ->
                        if (value == SwipeToDismissBoxValue.StartToEnd ||
                            value == SwipeToDismissBoxValue.EndToStart
                        ) {
                            viewModel.deleteWish(wish)
                            true
                        } else {
                            false
                        }
                    },
                    positionalThreshold = { totalDistance -> totalDistance * 0.25f }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = true,
                    enableDismissFromEndToStart = true,
                    backgroundContent = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection == SwipeToDismissBoxValue.Settled)
                                Color.Transparent else Color.Red,
                            label = "dismissBg"
                        )
                        val alignment =
                            if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd)
                                Alignment.CenterStart else Alignment.CenterEnd
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.delete),
                                contentDescription = "delete",
                                tint = Color.White
                            )
                        }
                    }
                ) {
                    WishItem(wish = wish) {
                        navController.navigate(Screen.AddScreen.route + "/${wish.id}")
                    }
                }
            }
        }

    }
}

@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Blue
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
            Text(text = wish.description)
        }

    }

}