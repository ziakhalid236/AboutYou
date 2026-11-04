package com.premium.aboutyou.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Small settings store. Everything the user chooses in Settings, plus the
 * last name and date of birth they typed, so the Home form is filled in when
 * they come back to the app.
 */
object Prefs {

    private const val FILE = "about_you_settings"
    private const val KEY_LANGUAGE = "language"
    private const val KEY_ADS = "ads_enabled"
    private const val KEY_REMINDER = "birthday_reminder"
    private const val KEY_DAILY = "daily_insight"
    private const val KEY_NAME = "last_name"
    private const val KEY_DAY = "dob_day"
    private const val KEY_MONTH = "dob_month"
    private const val KEY_YEAR = "dob_year"
    private const val KEY_HOUR = "dob_hour"
    private const val KEY_MINUTE = "dob_minute"

    private lateinit var sp: SharedPreferences

    fun init(context: Context) {
        if (!::sp.isInitialized) {
            sp = context.applicationContext.getSharedPreferences(FILE, Context.MODE_PRIVATE)
        }
    }

    var language: Language
        get() = Language.from(sp.getString(KEY_LANGUAGE, Language.ENGLISH.code))
        set(value) = sp.edit().putString(KEY_LANGUAGE, value.code).apply()

    var adsEnabled: Boolean
        get() = sp.getBoolean(KEY_ADS, false)
        set(value) = sp.edit().putBoolean(KEY_ADS, value).apply()

    var birthdayReminder: Boolean
        get() = sp.getBoolean(KEY_REMINDER, true)
        set(value) = sp.edit().putBoolean(KEY_REMINDER, value).apply()

    var dailyInsight: Boolean
        get() = sp.getBoolean(KEY_DAILY, false)
        set(value) = sp.edit().putBoolean(KEY_DAILY, value).apply()

    var lastName: String
        get() = sp.getString(KEY_NAME, "").orEmpty()
        set(value) = sp.edit().putString(KEY_NAME, value).apply()

    /** Typed date of birth, kept as text so a half-typed form is restored too. */
    var dobDay: String
        get() = sp.getString(KEY_DAY, "").orEmpty()
        set(value) = sp.edit().putString(KEY_DAY, value).apply()

    var dobMonth: String
        get() = sp.getString(KEY_MONTH, "").orEmpty()
        set(value) = sp.edit().putString(KEY_MONTH, value).apply()

    var dobYear: String
        get() = sp.getString(KEY_YEAR, "").orEmpty()
        set(value) = sp.edit().putString(KEY_YEAR, value).apply()

    var dobHour: String
        get() = sp.getString(KEY_HOUR, "").orEmpty()
        set(value) = sp.edit().putString(KEY_HOUR, value).apply()

    var dobMinute: String
        get() = sp.getString(KEY_MINUTE, "").orEmpty()
        set(value) = sp.edit().putString(KEY_MINUTE, value).apply()

    fun clear() {
        val language = language
        sp.edit().clear().apply()
        this.language = language
    }
}
