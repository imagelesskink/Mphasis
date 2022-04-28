package com.example.mphasis.networking

import com.example.mphasis.util.FailedResponseException
import com.example.mphasis.util.NullResponseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface SchoolRepository {
    fun getAll(): Flow<ResponseState>
    fun getSat(schoolDbn: String): Flow<ResponseState>
}

class SchoolRepositoryImpl @Inject  constructor(private val service: ApiService): SchoolRepository {
    override fun getAll(): Flow<ResponseState> =
        flow {
            emit(ResponseState.Loading)

            try {
                val response = service.getAllSchools()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResponseState.Success(it))
                    } ?: throw NullResponseException()
                }
                else throw FailedResponseException()
            } catch (e: Exception) {
                emit(ResponseState.Error(e))
            }
        }

    override fun getSat(schoolDbn: String): Flow<ResponseState> =
        flow {
            emit(ResponseState.Loading)

            try {
                val response = service.getSATScores(schoolDbn)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResponseState.Success(it))
                    } ?: throw NullResponseException()
                }
                else throw FailedResponseException()
            } catch (e: Exception) {
                emit(ResponseState.Error(e))
            }
        }
}