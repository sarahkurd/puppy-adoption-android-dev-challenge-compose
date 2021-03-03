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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.lighterPurple
import com.example.androiddevchallenge.ui.theme.lighterTeal
import com.example.androiddevchallenge.ui.theme.purple200
import com.example.androiddevchallenge.ui.theme.purple700
import com.example.androiddevchallenge.ui.theme.teal200
import com.example.androiddevchallenge.ui.theme.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme(darkTheme = isSystemInDarkTheme()) {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = purple700,
                title = { Text("AdoptMe") },
                contentColor = Color.White,
                elevation = 12.dp,
            )
        },
        content = {
            Surface(color = MaterialTheme.colors.background) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    PuppyList(puppies = puppyList)
                }
            }
        }
    )
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
    Column(
        modifier = Modifier
            .wrapContentSize()
            .clip(shape = RoundedCornerShape(8.dp))
    ) {
        PuppyPicture(puppy)
    }
}

@Composable
fun PuppyPicture(puppy: Puppy) {
    // expanded is "internal state" for PuppyItem
    val expanded = remember { mutableStateOf(false) }
    Column {
        Card(
            elevation = if (expanded.value) 16.dp else 8.dp,
            modifier = Modifier
                .clickable { expanded.value = !expanded.value }
        ) {
            Image(
                painter = painterResource(id = puppy.images[0]),
                contentDescription = null,
                modifier = Modifier
                    .height(270.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        }
        // content of the card depends on the current value of expanded
        if (expanded.value) PuppyContent(puppy)
    }
}

@Composable
fun PuppyContent(puppy: Puppy) {
    val expanded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(
                color =
                if (puppy.gender == "Girl") {
                    if (expanded.value) lighterPurple else purple200
                } else {
                    if (expanded.value) lighterTeal else teal200
                }
            )
            .padding(16.dp)
            .animateContentSize()
    ) {
        Text(
            "Hello, my name is ${puppy.name}",
            fontWeight = FontWeight.Bold
        )
        Text(
            "I am ${puppy.age} years old",
            style = typography.caption
        )
        Text(
            "I am a ${puppy.breed}, and I might have some ${puppy.mix}!",
            style = typography.caption
        )
        if (!expanded.value) {
            IconButton(onClick = { expanded.value = true }, modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null)
            }
        }
        if (expanded.value) {
            PuppyDetails(puppy = puppy)
            IconButton(onClick = { expanded.value = false }, modifier = Modifier.fillMaxWidth()) {
                Icon(imageVector = Icons.Default.ExpandLess, contentDescription = null)
            }
        }
    }
}

@Composable
fun PuppyDetails(puppy: Puppy) {
    Column(
        modifier = Modifier
            .animateContentSize()
    ) {
        Spacer(modifier = Modifier.absolutePadding(top = 16.dp))
        Text(
            "Bio",
            fontWeight = FontWeight.Bold,
            style = typography.h6
        )
        Text(
            puppy.bio,
            style = typography.body2
        )
        Spacer(modifier = Modifier.absolutePadding(top = 8.dp))
        Text(
            "Sex",
            fontWeight = FontWeight.Bold,
            style = typography.h6
        )
        Text(
            puppy.gender,
            style = typography.body2
        )
        Spacer(modifier = Modifier.absolutePadding(top = 8.dp))
        Text(
            "Size",
            fontWeight = FontWeight.Bold,
            style = typography.h6
        )
        Text(
            puppy.size,
            style = typography.body2
        )
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
