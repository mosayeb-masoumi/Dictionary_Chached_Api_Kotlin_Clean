package com.example.dictionaryapplication.feature_dictionary.domain.use_case

import com.example.dictionaryapplication.core.util.Resource
import com.example.dictionaryapplication.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapplication.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWordInfo @Inject constructor(private val repository: WordInfoRepository) {

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if (word.isBlank()) {
            return flow { }
        }
        return repository.getWordInfo(word)
    }
}