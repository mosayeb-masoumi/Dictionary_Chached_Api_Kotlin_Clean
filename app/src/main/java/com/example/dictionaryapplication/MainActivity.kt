package com.example.dictionaryapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import  androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dictionaryapplication.feature_dictionary.presentation.UIEvent
import com.example.dictionaryapplication.feature_dictionary.presentation.WordInfoItem
import com.example.dictionaryapplication.feature_dictionary.presentation.WordInfoViewModel
import com.example.dictionaryapplication.ui.theme.DictionaryApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryApplicationTheme {

                val viewModel: WordInfoViewModel = hiltViewModel()
                val state = viewModel.state.value


                val snackState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->

                        when (event) {
                            is UIEvent.showSnackbar -> {
                                coroutineScope.launch {
                                  snackState.showSnackbar(message = event.message)
                                }
                            }
                        }

                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Green.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 16.dp),
                    ) {
                        TextField(
                            value = viewModel.searchQuery.value,
                            onValueChange = {
                                viewModel.onSearch(it)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Search ...")
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LazyColumn(modifier = Modifier.fillMaxSize()) {

                            items(state.wordInfoItems.size) { i ->
                                val wordInfo = state.wordInfoItems[i]
                                if (i > 0) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                WordInfoItem(wordInfo = wordInfo)

                                if (i < state.wordInfoItems.size - 1) {
                                    Divider()
                                }
                            }
                        }

                    }

                }


                if (state.isLoading) {
                    CircularProgressIndicator()
                }

            }
        }
    }
}
