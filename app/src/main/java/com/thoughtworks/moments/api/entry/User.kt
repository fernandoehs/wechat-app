package com.thoughtworks.moments.api.entry

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
  val username: String?,
  @Json(name = "profile-image")
  val profileImage: String? = null,
  val avatar: String? = null,
  val nick: String? = null,
)