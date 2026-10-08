package com.ergi.digimon.data.model

/** Dipakai di Home (list). */
data class DigimonSummary(
    val id: Int,
    val name: String,
    val imageUrl: String?
)

data class DigimonSkill(
    val name: String,
    val description: String
)

/** Dipakai di Detail. */
data class Digimon(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val level: String,
    val attribute: String,
    val type: String,
    val fields: List<String>,
    val releaseDate: String,
    val description: String,
    val skills: List<DigimonSkill>
)
