package com.aptivist.tripsupiicsaapp.domain.models

sealed class DomainResponse<out T> {
    data class Success<T>(val data: T) : DomainResponse<T>()
    data class Error(val message: String, val code: Int) : DomainResponse<Nothing>()
}