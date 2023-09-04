package com.example.dictionaryapplication.feature_dictionary.data.remote.dto
import com.example.dictionaryapplication.feature_dictionary.data.local.entity.WordInfoEntity


data class WordInfoDto(
    val license: LicenseDto,
    val meanings: List<MeaningDto>, //
    val phonetic: String, //
    val phonetics: List<PhoneticDto>, //
    val sourceUrls: List<String>,
    val word: String //
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            phonetic = phonetic,
            word = word
        )
    }
}