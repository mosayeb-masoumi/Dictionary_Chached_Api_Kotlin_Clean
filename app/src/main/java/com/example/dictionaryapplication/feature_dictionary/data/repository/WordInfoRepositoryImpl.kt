package com.example.dictionaryapplication.feature_dictionary.data.repository

import com.example.dictionaryapplication.core.util.Resource
import com.example.dictionaryapplication.feature_dictionary.data.local.WordInfoDao
import com.example.dictionaryapplication.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionaryapplication.feature_dictionary.domain.model.WordInfo
import com.example.dictionaryapplication.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {

        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfos))  // loading to wait call api

// call api
        try {
            //call api
            val remoteWordInfos = api.getWordInfo(word)

            // delete data in database
            dao.deleteWordInfos(remoteWordInfos.map { it.word })

            //insert new data in database
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

        }catch (e: HttpException){

            emit(Resource.Error(
                message = "Oops, something went wrong!",
                data = wordInfos    // emit data from database
            ))
        }catch (e: IOException){

            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection",
                data = wordInfos    // emit data from database
            ))
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}