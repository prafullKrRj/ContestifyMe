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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.contestifyme.R
import com.example.contestifyme.features.profileFeature.data.local.entities.UserInfoEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun FrontScreen(user: UserInfoEntity, swipeToSubmission: () -> Unit = {}) {
    LazyColumn(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                FilledIconButton(onClick = { swipeToSubmission() }) {
                    Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
                }
            }
        }
        item {
            RankCard(
                modifier = Modifier.fillMaxWidth(),
                rank = "${user.rank}",
                handle = user.handle,
                country = "India"
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            ProfileCard(modifier = Modifier.fillMaxWidth(), user = user)
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
fun ProfileCard (modifier: Modifier, user: UserInfoEntity) {
    ElevatedCard {
        Column(
            modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row (Modifier.fillMaxWidth()) {
                DetailsSection(modifier = Modifier.weight(.7f), user = user)
                ImageSection(modifier = Modifier
                    .weight(.3f)
                    .fillMaxSize(),
                    user = user)
            }
        }
    }
}

@Composable
fun DetailsSection(modifier: Modifier, user: UserInfoEntity) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val registeredDate = getTime(user.registrationTimeSeconds!!.toLong())
        val lastActive = getTime(user.lastOnlineTimeSeconds!!.toLong())
        DetailItem(text = "Contest Rating: ${user.rating}")
        DetailItem(text = "Max Rank: ${user.maxRank}")
        DetailItem(text = "Contribution: ${user.contribution}")
        DetailItem(text = "Friend of: ${user.friendOfCount}")
        DetailItem(text = "Last visit: ${lastActive.hour}")
        DetailItem(text = "Registered On:" + " ${registeredDate.dayOfMonth} ${registeredDate.month} ${registeredDate.year}".lowercase())
    }
}
fun getTime(time: Long): LocalDateTime {
    return LocalDateTime.ofInstant(
        Instant.ofEpochSecond(time.toLong()),
        ZoneId.systemDefault()
    )
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
fun ImageSection(modifier: Modifier, user: UserInfoEntity) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfilePicture(photoUrl = user.titlePhoto)
        Text(
            text = "${user.name}",
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
fun ProfilePicture(photoUrl: String?) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(
            photoUrl,
        ).crossfade(true).placeholder(R.drawable.ic_broken_image).build(),
        contentDescription = null,
        modifier = Modifier
            .size(100.dp)
            .padding(6.dp)
            .clip(CircleShape)
    )
}