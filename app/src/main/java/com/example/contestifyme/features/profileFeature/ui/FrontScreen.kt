package com.example.contestifyme.features.profileFeature.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contestifyme.R

@Composable
fun FrontScreen(handle: String, swipeToSubmission: () -> Unit = {}) {
    LazyColumn(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                FilledIconButton(onClick = { swipeToSubmission() }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
                }
            }
        }
        item {
            RankCard(modifier = Modifier.fillMaxWidth(), rank = "newbie", handle = handle, country = "India")
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            ProfileCard(modifier = Modifier.fillMaxWidth())
        }
    }
}
@Composable
fun RankCard(
    modifier: Modifier,
    rank: String,
    handle: String,
    country: String
) {
    ElevatedCard {
        Column(
            modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(text = rank)
            Text(text = handle)
            Text(text = country)
        }
    }
}
@Composable
fun ProfileCard (modifier: Modifier) {
    ElevatedCard {
        Column(
            modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row (Modifier.fillMaxWidth()) {
                DetailsSection(modifier = Modifier.weight(.7f))
                ImageSection(modifier = Modifier
                    .weight(.3f)
                    .fillMaxSize())
            }
        }
    }
}

@Composable
fun DetailsSection(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        DetailItem(text = "Contest Rating")
        DetailItem(text = "Max Rank")
        DetailItem(text = "Contribution")
        DetailItem(text = "Friend of ")
        DetailItem(text = "Last visit")
        DetailItem(text = "Registered On")
    }
}

@Composable
fun DetailItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.logo2),
            contentDescription = null,
            Modifier.size(16.dp)
        )
        Text(text = text, fontSize = 16.sp)
    }
    Spacer(modifier = Modifier.padding(6.dp))
}
@Composable
fun ImageSection(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            Modifier
                .size(100.dp)
                .padding(6.dp)
                .clip(CircleShape),
        )
        Text(
            text = "Prafull Kumar Rajput ",
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            overflow = TextOverflow.Ellipsis,
        )
    }

}
