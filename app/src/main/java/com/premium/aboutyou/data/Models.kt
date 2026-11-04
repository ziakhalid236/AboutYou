package com.premium.aboutyou.data

data class NameMeaning(
    val name: String,
    val meaning: String,
    val urduMeaning: String,
    val origin: String,
    val culture: String,
    val gender: String,
    val luckyNumber: String,
    val luckyColor: String,
    val traits: List<String>
)

data class WikiSummary(
    val title: String,
    val description: String,
    val extract: String,
    val imageUrl: String?
)

data class NotablePerson(
    val title: String,
    val description: String,
    val extract: String,
    val imageUrl: String?,
    val url: String?
)

data class BirthEntry(
    val year: Int,
    val text: String,
    val personName: String,
    val description: String,
    val extract: String,
    val imageUrl: String?,
    val pageUrl: String?
)

data class NameStats(
    val genderGuess: String?,
    val genderProbability: Double?,
    val avgAge: Int?,
    val countries: List<Pair<String, Double>>
)

data class NameReport(
    val query: String,
    val summary: WikiSummary?,
    val local: NameMeaning?,
    val notable: List<NotablePerson>,
    val stats: NameStats?
)
