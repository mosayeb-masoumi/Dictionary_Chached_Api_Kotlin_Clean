package com.example.dictionaryapplication.feature_dictionary.presentation

sealed class UIEvent{
    data class showSnackbar(val message: String): UIEvent()
}
