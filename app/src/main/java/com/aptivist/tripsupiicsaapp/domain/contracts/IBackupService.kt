package com.aptivist.tripsupiicsaapp.domain.contracts

import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import java.io.File

interface IBackupService {

    suspend fun export() : DomainResponse<File>

    suspend fun import(uri: String) : DomainResponse<Unit>

}