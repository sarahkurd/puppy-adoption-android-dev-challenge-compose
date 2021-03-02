/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import com.example.androiddevchallenge.ui.theme.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    ) {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                PuppyList(puppies = puppyList)
            }
        }
    }
}

@Composable
fun PuppyList(puppies: List<Puppy>) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        puppies.forEach { puppy ->
            Spacer(modifier = Modifier.absolutePadding(top = 16.dp))
            PuppyItem(puppy)
        }
    }
}

@Composable
fun PuppyItem(puppy: Puppy) {
    // expanded is "internal state" for PuppyItem
    val expanded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = if (puppy.gender == "Girl") purple200 else teal200)
            .clickable { expanded.value = !expanded.value }
    ) {
        // content of the card depends on the current value of expanded
        PuppyPicture(puppy.images)
        if (expanded.value) PuppyContent(puppy)
    }
}

@Composable
fun PuppyPicture(images: List<Int>) {
    Card(
        elevation = 4.dp
    ) {
        Image(
            painter = painterResource(id = images[0]),
            contentDescription = null,
            modifier = Modifier
                .height(270.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun PuppyContent(puppy: Puppy) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize()
    ) {
        Text("Hello, my name is ${puppy.name}", fontWeight = FontWeight.Bold)
        Text("I am ${puppy.age} years old",
            style = typography.caption)
        Text("I am a ${puppy.breed}, and I might have some ${puppy.mix}!",
            style = typography.caption)
    }
}

// ------- Previews --------
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}

@Preview("Puppy Item")
@Composable
fun PuppyItemPreview() {
    PuppyItem(puppyList[0])
}

private enum class PuppyItemState {
    Collapsed,
    Expanded
}


