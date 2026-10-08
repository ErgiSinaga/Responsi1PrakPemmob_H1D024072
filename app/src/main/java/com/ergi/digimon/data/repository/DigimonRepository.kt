package com.ergi.digimon.data.repository

import com.ergi.digimon.data.model.Digimon
import com.ergi.digimon.data.model.DigimonSkill
import com.ergi.digimon.data.model.DigimonSummary
import com.ergi.digimon.data.remote.DigimonApiService
import com.ergi.digimon.data.remote.DigimonDetailDto
import com.ergi.digimon.data.remote.RetrofitClient
import retrofit2.HttpException
import java.io.IOException

class DigimonRepository(
    private val api: DigimonApiService = RetrofitClient.api
) {

    suspend fun getDigimonList(): Result<List<DigimonSummary>> = safeCall {
        api.getDigimonList().content.orEmpty().mapNotNull { dto ->
            val id = dto.id ?: return@mapNotNull null
            val name = dto.name ?: return@mapNotNull null
            DigimonSummary(id = id, name = name, imageUrl = dto.image)
        }
    }

    suspend fun getDigimonDetail(id: Int): Result<Digimon> = safeCall {
        api.getDigimonDetail(id).toDomain()
    }

    private fun DigimonDetailDto.toDomain(): Digimon {
        val englishDesc = descriptions.orEmpty()
            .firstOrNull { it.language?.startsWith("en", ignoreCase = true) == true }
            ?: descriptions.orEmpty().firstOrNull()

        return Digimon(
            id = id ?: 0,
            name = name ?: "Unknown",
            imageUrl = images?.firstOrNull()?.href,
            level = levels.orEmpty().mapNotNull { it.level }.joinToString().ifBlank { "-" },
            attribute = attributes.orEmpty().mapNotNull { it.attribute }.joinToString().ifBlank { "-" },
            type = types.orEmpty().mapNotNull { it.type }.joinToString().ifBlank { "-" },
            fields = fields.orEmpty().mapNotNull { it.field },
            releaseDate = releaseDate?.ifBlank { null } ?: "-",
            description = englishDesc?.description?.ifBlank { null } ?: "Tidak ada deskripsi.",
            skills = skills.orEmpty().mapNotNull { s ->
                s.skill?.let { DigimonSkill(it, s.description.orEmpty()) }
            }
        )
    }

    private suspend fun <T> safeCall(block: suspend () -> T): Result<T> =
        try {
            Result.success(block())
        } catch (e: IOException) {
            Result.failure(Exception("Tidak dapat terhubung ke server. Periksa koneksi internet.", e))
        } catch (e: HttpException) {
            Result.failure(Exception("Server error (kode ${e.code()})."))
        } catch (e: Exception) {
            Result.failure(Exception("Terjadi kesalahan: ${e.localizedMessage ?: "tidak diketahui"}"))
        }
}
