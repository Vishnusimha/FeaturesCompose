package com.vishnu.featurescompose.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vishnu.featurescompose.data.remote.Author
import com.vishnu.featurescompose.viewmodel.AuthorViewModel

@Composable
fun FireBaseScreen(authorViewModel: AuthorViewModel) {
    val authors by authorViewModel.authors.collectAsState()
    val addAuthorResponse by authorViewModel.addAuthorResponse.collectAsState()

//Like init for composable
    LaunchedEffect(key1 = 1) {
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            var isDialogVisible by remember { mutableStateOf(false) }
            var textValue by remember { mutableStateOf("") }

            // Button to open the dialog
            Button(
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                onClick = { isDialogVisible = true }) {
                Text("Open Dialog")
            }

            if (authors.isNotEmpty() && authors.size > 2) {
                Box(modifier = Modifier.fillMaxSize()) {
                    //sending data to a separate composable with recycler view
                    DisplayAuthors(authors, authorViewModel)
                }
            } else {
                Text("We have only ${authors.size} authors")
            }


            // Dialog
            if (isDialogVisible) {
                Dialog(
                    onDismissRequest = {
                        // Dismiss the dialog on back press or outside click
                        isDialogVisible = false
                    },
                    content = {
                        DialogContent(
                            textValue = textValue,
                            onTextValueChange = { newTextValue -> textValue = newTextValue },
                            onConfirm = { confirmedTextValue ->
                                // Handle the text value from the dialog
                                if (confirmedTextValue.isNotBlank()) {
                                    authorViewModel.addAuthor(Author("", confirmedTextValue))
                                }
                                println("Text value from dialog: $confirmedTextValue")
                                println("addAuthorResponse Error from firebase for adding author: $addAuthorResponse")
                                // Dismiss the dialog
                                isDialogVisible = false
                                authorViewModel.updateDataValueFromFirebase()
                            },
                            onDismiss = {
                                // Dismiss the dialog
                                isDialogVisible = false
                            }
                        )
                    }
                )
            }
// We need to clear the text value otherwise var remembers it so that cna be done in DisposableEffect scope.
            DisposableEffect(isDialogVisible) {
                onDispose {
                    textValue = ""
                }
            }
        }
    }


}

@Composable
private fun DisplayAuthors(
    authors: List<Author>,
    authorViewModel: AuthorViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(authors.size) { index ->
            val author = authors[index]
            AuthorItem(
                author = author,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { authorViewModel.deleteAuthor(author) }
            )
        }
    }
}

@Composable
private fun DisplayAuthors(authors: List<Author>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(authors.size) { index ->
            val author = authors[index]
            AuthorItem(
                author = author,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun DialogContent(
    textValue: String,
    onTextValueChange: (String) -> Unit,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        // Text input in the dialog
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = textValue,
            onValueChange = {
                onTextValueChange(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            label = {
                Text("Enter text")
            }
        )
        // Confirm button in the dialog
        Button(
            onClick = {
                // Handle the text value from the dialog
                onConfirm(textValue)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("OK")
        }
    }
}
