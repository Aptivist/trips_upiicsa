package com.aptivist.tripsupiicsaapp.data.local.backup

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.aptivist.tripsupiicsaapp.data.local.database.daos.CheckListEntryDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.JournalEntryDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.PhotoDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripPhotoDao
import com.aptivist.tripsupiicsaapp.domain.contracts.IBackupService
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import javax.inject.Inject

class DatabaseBackupServiceImplementation @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val tripDao: TripDao,
    private val journalDao: JournalEntryDao,
    private val checklistDao: CheckListEntryDao,
    private val photoDao: PhotoDao,
    private val tripPhotoDao: TripPhotoDao,
) : IBackupService {

    override suspend fun export(): DomainResponse<File> {
        return try {
            val payload = getExportPayload()

            //val jsonString = Json.encodeToString(BackupPayload.serializer(), payload)
            val jsonString = payload.toJson()

            val backupFile = File(context.cacheDir, "trips_backup_${System.currentTimeMillis()}.trip")
            backupFile.writeText(jsonString)

            DomainResponse.Success(backupFile)
        } catch (e: Exception) {
            DomainResponse.Error("Failed to export backup: ${e.message}", code = 500)
        }
    }

    private suspend fun getExportPayload(): BackupPayload = BackupPayload(
            exportedAtEpochMs = System.currentTimeMillis(),
            trips = tripDao.getAll().map { it.toDump() },
            journalEntries = journalDao.getAll().map { it.toDump() },
            checklistEntries = checklistDao.getAll().map { it.toDump() },
            photos = photoDao.getAll().map { it.toDump() },
            tripPhotos = tripPhotoDao.getAll().map { it.toDump() },
        )

    override suspend fun import(uri: String): DomainResponse<Unit> {
        return try {

            val file = context.copyUriToTempFile(uri.toUri(), ".trip")

            if (file == null || !file.exists() || !file.canRead()) {
                return DomainResponse.Error("Backup file not found or not readable", code = 404)
            }

            val content = file.readText()

            val payload = Json.decodeFromString(BackupPayload.serializer(), content)

            if (importAll(payload)) {
                DomainResponse.Success(Unit)
            } else {
                DomainResponse.Error("Import failed", code = 500)
            }

        } catch (e: Exception) {
            DomainResponse.Error("Failed to import backup: ${e.message}", code = 500)
        }
    }

    private suspend fun importAll(
        payload: BackupPayload,
    ): Boolean {
        tripPhotoDao.deleteAll()
        photoDao.deleteAll()
        journalDao.deleteAll()
        checklistDao.deleteAll()
        tripDao.deleteAll()

        photoDao.insertAll(payload.photos.map { it.toEntity() })
        tripDao.insertAll(payload.trips.map { it.toEntity() })
        tripPhotoDao.insertAll(payload.tripPhotos.map { it.toEntity() })
        journalDao.insertAll(payload.journalEntries.map { it.toEntity() })
        checklistDao.insertAll(payload.checklistEntries.map { it.toEntity() })
        return true
    }


    private fun Context.copyUriToTempFile(uri: Uri, extension: String): File? {
        try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile("import_", extension, cacheDir)
            inputStream.use { input ->
                tempFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            return tempFile
        }
        catch (io: IOException){
            // Handle IO exceptions if needed
            return null
        }
        catch (fnf: FileNotFoundException){
            // Handle file not found specifically if needed
            return null
        }
        catch (e: Exception) {
            return null
        }
    }

}