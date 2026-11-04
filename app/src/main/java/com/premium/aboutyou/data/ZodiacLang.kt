package com.premium.aboutyou.data

/**
 * Localised views over a [ZodiacSign]. The stored data is English, so the
 * element, quality, ruling planet and trait words are translated on demand.
 * The longer prose (strengths, weaknesses, description) stays English, while
 * the Urdu description is kept from the curated data.
 */
data class LocalZodiac(
    val sign: ZodiacSign,
    val element: String,
    val quality: String,
    val rulingPlanet: String,
    val traits: List<String>,
    val strengths: String,
    val weaknesses: String,
    val description: String
)

fun ZodiacSign.localized(lang: Language): LocalZodiac = LocalZodiac(
    sign = this,
    element = Dict.word(element, lang),
    quality = Dict.word(quality, lang),
    rulingPlanet = rulingPlanet
        .split("/")
        .joinToString(" / ") { Dict.word(it.trim(), lang) },
    traits = traits.map { Dict.word(it, lang) },
    strengths = Dict.list(strengths, lang),
    weaknesses = Dict.list(weaknesses, lang),
    description = if (lang == Language.URDU) urduDescription else urduDescription
)
