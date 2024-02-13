package com.blez.anime_player_compose.feature_search.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.blez.anime_player_compose.R


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "Search Your Anime",
    onTextCompleted: (String) -> Unit = {},

    ) {
    var searchText by remember {
        mutableStateOf("")
    }

    val controller = LocalSoftwareKeyboardController.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 15.dp)


    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*  Icon(
                  imageVector = Icons.Default.Search,
                  contentDescription = "Search Button",
                  tint = Color.White,
                  modifier = Modifier
                      .size(32.dp)
                      .shadow(10.dp)
                      .clickable {

                      }
              )*/
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search,
                        contentDescription = "Search Button",
                        tint = Color.White,
                        modifier = Modifier
                            .size(32.dp)
                            .shadow(10.dp)
                            .clickable {

                            })
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        Icon(imageVector = Icons.Default.Close,
                            contentDescription = "Clear Text Button",
                            tint = Color.White,
                            modifier = Modifier
                                .size(32.dp)
                                .shadow(10.dp)
                                .clickable {
                                    searchText = ""
                                })
                    }
                },
                shape = RoundedCornerShape(30),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    if (searchText.isNotEmpty()) {
                        onTextCompleted(searchText)
                        controller?.hide()
                    }

                }),
                colors = TextFieldColors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    errorTextColor = Color.Red,
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    disabledContainerColor = Color.Black,
                    cursorColor = Color.Red,
                    errorCursorColor = Color.Red,
                    textSelectionColors = TextSelectionColors(
                        handleColor = Color.Red, backgroundColor = Color.Red
                    ),
                    focusedIndicatorColor = Color.Red,
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.Red,
                    errorIndicatorColor = Color.Red,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    disabledLeadingIconColor = Color.Black,
                    errorLeadingIconColor = Color.Black,
                    focusedTrailingIconColor = Color.White,
                    unfocusedTrailingIconColor = Color.Black,
                    disabledTrailingIconColor = Color.Unspecified,
                    errorTrailingIconColor = Color.Unspecified,
                    focusedLabelColor = Color.Unspecified,
                    unfocusedLabelColor = Color.Unspecified,
                    disabledLabelColor = Color.Unspecified,
                    errorLabelColor = Color.Unspecified,
                    focusedPlaceholderColor = Color.Unspecified,
                    unfocusedPlaceholderColor = Color.Unspecified,
                    disabledPlaceholderColor = Color.Unspecified,
                    errorPlaceholderColor = Color.Unspecified,
                    focusedSupportingTextColor = Color.Unspecified,
                    unfocusedSupportingTextColor = Color.Unspecified,
                    disabledSupportingTextColor = Color.Unspecified,
                    errorSupportingTextColor = Color.Unspecified,
                    focusedPrefixColor = Color.Unspecified,
                    unfocusedPrefixColor = Color.Unspecified,
                    disabledPrefixColor = Color.Unspecified,
                    errorPrefixColor = Color.Unspecified,
                    focusedSuffixColor = Color.Unspecified,
                    unfocusedSuffixColor = Color.Unspecified,
                    disabledSuffixColor = Color.Unspecified,
                    errorSuffixColor = Color.Unspecified,
                    errorContainerColor = Color.Red

                )
            )
        }
    }
}

@Composable
fun SearchCard(
    modifier: Modifier = Modifier,
    imageURL: String = "https://gogocdn.net/cover/tsuki-ga-michibiku-isekai-douchuu-2nd-season-1704251043.png",
    title: String = "Tsuki ga Michibiku Isekai Douchuu 2nd Season",
    onCardClicked : (String)->Unit = {}

) {
    Row(modifier = modifier.clickable {
        onCardClicked(title)
    }, verticalAlignment = Alignment.Top) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(10)),
                model = imageURL,
                contentDescription = "$title Image",
                loading = { CircularProgressIndicator() },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.error_png),
                        contentDescription = "Error in Image Loading"
                    )
                },
                contentScale = ContentScale.Crop
            )
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Start Button",
                modifier = Modifier
                    .size(45.dp)
                    .shadow(25.dp),
                tint = Color.LightGray
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = title,
            color = Color.White,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )
    }
}