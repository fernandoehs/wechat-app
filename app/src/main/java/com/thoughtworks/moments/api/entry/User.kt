package com.thoughtworks.moments.api.entry

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
  val username: String?,
  @Json(name = "profile-image")
  val profileImage: String?,
  val avatar: String?,
  val nick: String?,
)