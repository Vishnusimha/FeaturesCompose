package com.vishnu.featurescompose.data.remote

import com.google.firebase.database.Exclude

data class Author(@get:Exclude var id: String? = "", var name: String? = "")
