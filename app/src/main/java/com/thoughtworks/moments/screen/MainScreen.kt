package com.thoughtworks.moments.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thoughtworks.moments.api.entry.Comment
import com.thoughtworks.moments.api.entry.Tweet
import com.thoughtworks.moments.api.entry.User
import com.thoughtworks.moments.screen.components.TweetItem
import com.thoughtworks.moments.screen.components.UserHeader
import com.thoughtworks.moments.viewmodels.MainViewModel

@Composable
fun MainScreen(
  mainViewModel: MainViewModel
) {
  val user by mainViewModel.user.collectAsStateWithLifecycle()
  val tweets by mainViewModel.tweets.collectAsStateWithLifecycle()

  MainScreenContent(
    user = user,
    tweets = tweets,
    onLoadMore = { mainViewModel.loadMoreTweets() }
  )

}

@Composable
fun MainScreenContent(
  user: User?,
  tweets: List<Tweet>,
  onLoadMore: () -> Unit
) {
  val listState = rememberLazyListState()
  val endOfListReached by remember {
    derivedStateOf {
      listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == listState.layoutInfo.totalItemsCount - 1
    }
  }

  LaunchedEffect(endOfListReached) {
    if (endOfListReached) {
      onLoadMore()
    }
  }

  Scaffold { paddingValues ->
    LazyColumn(
      state = listState,
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      items(tweets.size + 1) { index ->
        if (index == 0) {
          user?.let {
            UserHeader(user = it)
          }
        } else {
          TweetItem(tweets[index - 1])
        }
      }
    }
  }
}


@Preview
@Composable
fun MainScreenPreview() {
  // TODO: Write a preview for MainScreen with two sample tweets
  val mockUser = User(username = "johndoe", nick = "John Doe", avatar = "", profileImage = "")
  val mockTweets = listOf(
    Tweet(
      comments = listOf(Comment(content = "Nice!", sender = mockUser)),
      sender = mockUser,
      content = "Tweet 1",
      images = emptyList()
    ),
    Tweet(
      comments = listOf(Comment(content = "Good job", sender = mockUser)),
      sender = mockUser,
      content = "Tweet 2",
      images = emptyList()
    ),
  )

  MainScreenContent(
    user = mockUser,
    tweets = mockTweets,
    onLoadMore = {}
  )
}