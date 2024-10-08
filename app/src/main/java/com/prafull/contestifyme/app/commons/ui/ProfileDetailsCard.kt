package com.prafull.contestifyme.app.commons.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
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
import com.prafull.contestifyme.R
import com.prafull.contestifyme.app.commons.UserData
import com.prafull.contestifyme.network.model.userinfo.UserResult
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale

@Composable
fun ProfileCard(modifier: Modifier, user: UserData) {
    ElevatedCard {
        Column(
            modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                DetailsSection(modifier = Modifier.weight(.7f), user = user.usersInfo)
                ImageSection(
                    modifier = Modifier
                        .weight(.3f)
                        .fillMaxSize(),
                    user = user.usersInfo
                )
            }
        }
    }
}

@Composable
fun DetailsSection(modifier: Modifier, user: UserResult) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        val registeredDate = getTime(user.registrationTimeSeconds!!.toLong())
        val lastActiveTime = getTime(user.lastOnlineTimeSeconds!!.toLong())
        val now = LocalDateTime.now()
        val lastActive: String = when {
            now.toLocalDate() == lastActiveTime.toLocalDate() -> {
                val hoursAgo = now.hour - lastActiveTime.hour
                "Active $hoursAgo hours ago"
            }

            now.toLocalDate().minusDays(1) == lastActiveTime.toLocalDate() -> "Active yesterday"
            else -> "${lastActiveTime.dayOfMonth} ${
                lastActiveTime.month.name.lowercase().capitalize(Locale.ROOT)
            } ${lastActiveTime.year}"
        }
        DetailItem(text = "Contest Rating: ${user.rating}")
        DetailItem(text = "Max Rank: ${user.maxRank}")
        DetailItem(text = "Contribution: ${user.contribution}")
        DetailItem(text = "Friend of: ${user.friendOfCount}")
        DetailItem(text = "Last visit: $lastActive")
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
    ) {
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
fun ImageSection(modifier: Modifier, user: UserResult) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfilePicture(photoUrl = user.titlePhoto)
        if (user.firstName != null && user.lastName != null) {
            Text(
                text = "${user.firstName} ${user.lastName}".capitalize(Locale.ROOT),
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
            )
        } else if (user.firstName != null) {
            Text(
                text = user.firstName.capitalize(Locale.ROOT),
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
            )
        } else {
            Text(
                text = "User",
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
            )
        }
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