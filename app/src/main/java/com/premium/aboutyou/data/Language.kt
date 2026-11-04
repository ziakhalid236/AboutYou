package com.premium.aboutyou.data

/**
 * Languages the app can present its results in. The Wikipedia sub-domain is
 * used for live content (encyclopaedia text, famous people, birthdays), so the
 * results are genuinely written in the chosen language rather than translated
 * word by word.
 */
enum class Language(
    val code: String,
    val wiki: String,
    val englishName: String,
    val nativeName: String,
    val rtl: Boolean
) {
    ENGLISH("en", "en", "English", "English", false),
    URDU("ur", "ur", "Urdu", "اردو", true),
    ARABIC("ar", "ar", "Arabic", "العربية", true),
    HINDI("hi", "hi", "Hindi", "हिन्दी", false),
    PERSIAN("fa", "fa", "Persian", "فارسی", true),
    SPANISH("es", "es", "Spanish", "Español", false);

    /** "اردو (Urdu)" — for the settings list. */
    val displayName: String get() = if (nativeName == englishName) englishName else "$nativeName ($englishName)"

    companion object {
        fun from(code: String?): Language = entries.firstOrNull { it.code == code } ?: ENGLISH
    }
}
