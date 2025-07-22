package com.thoughtworks.moments.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.thoughtworks.moments.R
import com.thoughtworks.moments.api.entry.User

@Composable
fun UserHeader(user: User) {
  Box(
    modifier = Modifier
      .width(450.dp)
      .height(300.dp)
  ) {

    // TODO: Load user image here
    if (LocalInspectionMode.current) {
      Image(
        painter = painterResource(id = R.drawable.ic_profile),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    } else {
      AsyncImage(
        model = user.profileImage,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
      )
    }

    user.nick?.let {
      Text(
        text = user.nick,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(end = 112.dp, bottom = 14.dp)
      )
    }
    if (LocalInspectionMode.current) {
      Image(
        painter = painterResource(id = R.drawable.ic_avatar),
        contentDescription = "Avatar",
        modifier = Modifier
          .size(96.dp)
          .align(Alignment.BottomEnd)
          .offset(y = 88.dp / 2)
          .border(2.dp, Color.White),
        contentScale = ContentScale.Crop
      )
    } else {
      AsyncImage(
        model = user.avatar,
        contentDescription = null,
        modifier = Modifier
          .size(96.dp)
          .align(Alignment.BottomEnd)
          .offset(y = 88.dp / 2)
          .border(2.dp, Color.White),
        contentScale = ContentScale.Crop,
      )
    }
  }
  Spacer(modifier = Modifier.height(40.dp))
}

@Preview(widthDp = 450, heightDp = 300)
@Composable
fun UserHeaderPreview() {
  val mockUser = User(
    username = "pedrope",
    nick = "Pedro Perez",
    avatar = "",
    profileImage = ""
  )
  UserHeader(mockUser)
}