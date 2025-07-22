package com.thoughtworks.moments.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.thoughtworks.moments.R
import com.thoughtworks.moments.api.entry.Comment
import com.thoughtworks.moments.api.entry.Image
import com.thoughtworks.moments.api.entry.Tweet
import com.thoughtworks.moments.api.entry.User

@Composable
fun TweetItem(
  tweet: Tweet
) {
  Row(modifier = Modifier.padding(10.dp)) {
    Column {
      if (LocalInspectionMode.current) {
        Image(
          painter = painterResource(id = R.drawable.ic_avatar),
          contentDescription = null,
        )
      } else {
        AsyncImage(
          model = tweet.sender?.avatar,
          contentDescription = null,
          modifier = Modifier
            .size(55.dp)
        )
      }
    }
    Spacer(modifier = Modifier.width(10.dp))

    Column {
      Text(tweet.sender?.username.orEmpty())
      Text(tweet.content.orEmpty())
      tweet.images?.let { ImageGrid(it) }
      CommentTweet(tweet)
    }
  }
  Text(tweet.error.orEmpty())
}

@Composable
private fun CommentTweet(tweet: Tweet) {
  Column {
    tweet.comments?.forEach { comment ->
      Spacer(modifier = Modifier.height(10.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(color = Color(0xFFEEEEEE))
      ) {
        comment.sender?.avatar?.let { avatarUrl ->
          if (LocalInspectionMode.current) {
            Image(
              painter = painterResource(id = R.drawable.ic_avatar),
              contentDescription = null,
            )
          } else {
            AsyncImage(
              model = avatarUrl,
              contentDescription = null,
              modifier = Modifier.size(55.dp)
            )
          }
        }
        Column {
          comment.sender?.nick?.let { nick ->
            Text(
              text = nick,
              modifier = Modifier.padding(start = 8.dp)
            )
          }
          comment.content?.let { content ->
            Text(
              text = content,
              modifier = Modifier.padding(start = 8.dp)
            )
          }
        }
      }
    }
  }
}

// TODO: Rework this to be more user friendly for different form factors
@Composable
private fun ImageGrid(imageUrl: List<Image>) {
  if (imageUrl.isEmpty()) {
    return
  } else if (imageUrl.size == 1) {
    AsyncImage(
      model = imageUrl[0].url,
      contentDescription = null,
      modifier = Modifier
        .width(100.dp)
        .height(150.dp),
      contentScale = ContentScale.Crop
    )
  } else {
    val columns = if (imageUrl.size == 4) {
      2
    } else {
      3
    }
    Grid(
      columns = columns,
      itemCount = imageUrl.size,
      verticalPadding = 8.dp,
      horizontalPadding = 16.dp,
      content = { index ->
        AsyncImage(
          model = imageUrl[index].url,
          contentDescription = null,
          modifier = Modifier
            .size(94.dp),
          contentScale = ContentScale.Crop
        )
      }
    )
  }
}


@Preview(showBackground = true)
@Composable
fun TweetItemPreview() {
  val mockUser = User(username = "Juan Perez", nick = "juanpe", avatar = "", profileImage = "")
  val mockTweet = Tweet(
    comments = listOf(Comment(content = "Nice!", sender = mockUser)),
    sender = mockUser,
    content = "Tweet 1",
    images = emptyList()
  )

  TweetItem( tweet = mockTweet )
}