package com.example.superherocoroutines
import com.google.gson.annotations.SerializedName

data class JsData(
    val id: String,
    val name: String,
    val slug: String,
    val powerstats: PowerStats,
    val appearance: Appearance,
    val biography: Biography,
    val work: Work,
    val connections: Connections,
    val images: Images
)

data class PowerStats(
    val intelligence: Int,
    val strength: Int,
    val speed: Int,
    val durability: Int,
    val power: Int,
    val combat: Int
)
data class Appearance(
    val gender: String,
    val race: String,
    val height: List<String>,
    val weight: List<String>,
    val eyeColor: String,
    val hairColor: String
)
data class Height(
    @SerializedName("0")
    val height: String)
data class Biography(
    val fullName: String,
    val alterEgos: String,
    val aliases: List<String>,
    val placeOfBirth: String,
    val firstAppearance: String,
    val publisher: String,
    val alignment: String
)
data class Work(
    val occupation: String,
    val base: String
)
data class Connections(
    val groupAffiliation: String,
    val relatives: String
)
data class Images(
    val sm: String,
    val md: String
)