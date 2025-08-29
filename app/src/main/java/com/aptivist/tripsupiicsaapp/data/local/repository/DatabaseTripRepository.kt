package com.aptivist.tripsupiicsaapp.data.local.repository

import com.aptivist.tripsupiicsaapp.data.local.database.TripsDatabase
import com.aptivist.tripsupiicsaapp.data.local.database.daos.PhotoDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripDao
import com.aptivist.tripsupiicsaapp.data.local.database.daos.TripPhotoDao
import com.aptivist.tripsupiicsaapp.data.local.database.entities.PhotoEntity
import com.aptivist.tripsupiicsaapp.data.local.database.entities.TripPhotoEntity
import com.aptivist.tripsupiicsaapp.data.local.database.toDomainModel
import com.aptivist.tripsupiicsaapp.data.local.database.toEntity
import com.aptivist.tripsupiicsaapp.domain.contracts.ITripRepository
import com.aptivist.tripsupiicsaapp.domain.models.DomainResponse
import com.aptivist.tripsupiicsaapp.domain.models.TripModel
import com.aptivist.tripsupiicsaapp.domain.models.TripPhotoModel
import javax.inject.Inject
import kotlin.sequences.ifEmpty
import kotlin.text.insert

class DatabaseTripRepository @Inject constructor(
    private val tripDao: TripDao,
    private val photoDao: PhotoDao,
    private val tripPhotoDao: TripPhotoDao
) : ITripRepository {


    override suspend fun getAll(): DomainResponse<List<TripModel>> {
        return try {
            val trips = tripDao.getAllTripsWithPhotos().map { it.toDomainModel() }
            DomainResponse.Success(trips)
        } catch (e: Exception) {
            DomainResponse.Error("Error fetching trips: ${e.message}", code = 500)
        }
    }

    override suspend fun getTripPhotos(tripId: Long): DomainResponse<List<TripPhotoModel>> {
        return try {
            val photos = tripPhotoDao.getPhotosForTrip(tripId).map { it.toDomainModel() }
            DomainResponse.Success(photos)
        } catch (e: Exception) {
            DomainResponse.Error("Error fetching trip photos: ${e.message}", code = 500)
        }
    }

    override suspend fun delete(tripId: Long): DomainResponse<Unit> {
        return try {
            val result = tripDao.deleteTripById(tripId)
            if (result == 0) {
                return DomainResponse.Error("Trip not found", code = 404)
            }
            DomainResponse.Success(Unit)
        } catch (e: Exception) {
            DomainResponse.Error("Error deleting trip: ${e.message}", code = 500)
        }
    }

    override suspend fun getById(tripId: Long): DomainResponse<TripModel> {
        return try {
            val tripWithCoverPhoto = tripDao.getAllTripsWithPhotos().find { it.trip.id == tripId }
            if (tripWithCoverPhoto != null) {
                DomainResponse.Success(tripWithCoverPhoto.toDomainModel())
            } else {
                DomainResponse.Error("Trip not found", code = 404)
            }
        } catch (e: Exception) {
            DomainResponse.Error("Error fetching trip: ${e.message}", code = 500)
        }
    }

    override suspend fun upsert(trip: TripModel): DomainResponse<TripModel> {
        return try {
            val tripEntity = trip.toEntity()
            val tripUpsertStatus = tripDao.upsert(tripEntity)

            var updatedTrip = if (tripUpsertStatus == -1L) {
                trip.copy()
            } else {
                trip.copy(id = tripUpsertStatus)
            }

            updatedTrip.id?.let { tripId ->
                // Clear existing photos for the trip
                tripPhotoDao.getPhotosForTrip(tripId).forEach { photo ->
                    photo.id?.let { photoId ->
                        tripPhotoDao.unlink(tripId, photoId)
                    }
                }

                //Insert all photos
                trip.photosUris.forEach { photoUri ->
                    val photoInDb = tripPhotoDao.photoExists(photoUri)
                    photoInDb?.id?.let { photoId ->
                        tripPhotoDao.link(TripPhotoEntity(tripId = tripId, photoId = photoId))
                        return@forEach
                    }
                    val photoEntity = PhotoEntity(name = photoUri)
                    val photoId = photoDao.insert(photoEntity)
                    tripPhotoDao.link(TripPhotoEntity(tripId = tripId, photoId = photoId))
                }

                // Set first photo as cover photo if no cover photo is set
                if (trip.coverImageUrl.isNotEmpty()) {
                    val coverPhoto = tripPhotoDao.photoExistsInTrip(tripId, trip.coverImageUrl)
                    if (coverPhoto != null) {
                        tripDao.setCoverPhotoId(tripId, coverPhoto.id)
                    } else {
                        val photoEntity = PhotoEntity(name = trip.coverImageUrl)
                        val photoId = photoDao.insert(photoEntity)
                        tripPhotoDao.link(TripPhotoEntity(tripId = tripId, photoId = photoId))
                        tripDao.setCoverPhotoId(tripId, photoId)
                    }
                } else {
                    val photosForTrip = tripPhotoDao.getPhotosForTrip(tripId)
                    if (photosForTrip.isNotEmpty()) {
                        tripDao.setCoverPhotoId(tripId, photosForTrip.first().id)
                    } else {
                        tripDao.setCoverPhotoId(tripId, null)
                    }
                }
            }

            // Return the updated trip with the new ID
            updatedTrip = updatedTrip.copy(
                coverImageUrl = trip.coverImageUrl.ifEmpty {
                    trip.photosUris.firstOrNull().orEmpty()
                }
            )

            DomainResponse.Success(updatedTrip)
        } catch (e: Exception) {
            DomainResponse.Error("Error upserting trip: ${e.message}", code = 500)
        }
    }

}