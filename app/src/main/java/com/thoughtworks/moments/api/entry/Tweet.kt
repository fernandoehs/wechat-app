package com.thoughtworks.moments.api.entry

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tweet(
  val content: String?,
  val error: String? = null,
  val images: List<Image>? = null,
  val sender: User? = null,
  val comments: List<Comment>? = null,
)