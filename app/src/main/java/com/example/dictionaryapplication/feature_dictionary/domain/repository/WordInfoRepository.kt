package com.example.dictionaryapplication.feature_dictionary.domain.repository

import com.example.dictionaryapplication.core.util.Resource
import com.example.dictionaryapplication.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String) : Flow<Resource<List<WordInfo>>>
}