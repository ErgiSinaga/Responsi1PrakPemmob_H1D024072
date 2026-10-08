package com.ergi.digimon.data.remote

data class DigimonListResponse(
    val content: List<DigimonListItemDto>?
)

data class DigimonListItemDto(
    val id: Int?,
    val name: String?,
    val href: String?,
    val image: String?
)

data class DigimonDetailDto(
    val id: Int?,
    val name: String?,
    val images: List<ImageDto>?,
    val levels: List<LevelDto>?,
    val types: List<TypeDto>?,
    val attributes: List<AttributeDto>?,
    val fields: List<FieldDto>?,
    val releaseDate: String?,
    val descriptions: List<DescriptionDto>?,
    val skills: List<SkillDto>?
)

data class ImageDto(val href: String?)
data class LevelDto(val level: String?)
data class TypeDto(val type: String?)
data class AttributeDto(val attribute: String?)
data class FieldDto(val field: String?)
data class DescriptionDto(val language: String?, val description: String?)
data class SkillDto(val skill: String?, val description: String?)
