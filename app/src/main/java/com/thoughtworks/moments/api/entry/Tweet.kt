package com.thoughtworks.moments.api.entry

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tweet(
  val content: String?,
  val error: String? = null,
  val images: List<Image>?,
  val sender: User?,
  val comments: List<Comment>?,
)