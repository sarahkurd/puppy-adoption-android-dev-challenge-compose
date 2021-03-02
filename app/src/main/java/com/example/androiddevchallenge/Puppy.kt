package com.example.androiddevchallenge

import android.media.Image

data class Puppy(val name: String,
                 val age: Int,
                 val breed: String,
                 val mix: String?,
                 val gender: String,
                 val size: String,
                 val bio: String,
                 val images: List<Int>)
