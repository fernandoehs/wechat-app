package com.thoughtworks.moments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thoughtworks.moments.api.MomentRepository
import com.thoughtworks.moments.api.entry.Tweet
import com.thoughtworks.moments.api.entry.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
  private val repository: MomentRepository
) : ViewModel() {

  companion object {
    private const val PAGE_TWEET_COUNT = 5
  }

  init {
    loadUser()
    loadTweets()
  }

  val user: MutableStateFlow<User?> = MutableStateFlow(null)

  private fun loadUser() {
    viewModelScope.launch {
      try {
        val result = repository.fetchUser()
        user.emit(result)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  private val _tweets: MutableStateFlow<List<Tweet>> = MutableStateFlow<List<Tweet>>(emptyList())
  val tweets: StateFlow<List<Tweet>> = _tweets

  private val _tweetsList = mutableListOf<Tweet>()
  private var allTweets: List<Tweet> = emptyList()
  private var isLoading = false
  private var currentPage = 0

  private fun loadTweets() {
    viewModelScope.launch {
      allTweets = try {
        repository.fetchTweets()
      } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
      }
      _tweetsList.clear()
      currentPage = 0
      loadMoreTweets()
    }
  }

  fun loadMoreTweets() {
    if (isLoading) return
    isLoading = true

    viewModelScope.launch {
      // TODO: Implement Pagination
      val from = currentPage * PAGE_TWEET_COUNT
      val to = ((currentPage + 1) * PAGE_TWEET_COUNT).coerceAtMost(allTweets.size)

      if (from < to) {
        val next = allTweets.subList(from, to)

        val validated = next.filter {
          !it.content.isNullOrEmpty()
        }

        _tweetsList.addAll(validated)
        _tweets.emit(_tweetsList.toList())
        currentPage++
      }
      isLoading = false
    }
  }
}