package com.premium.aboutyou.util

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period

data class AgeParts(
    val years: Long,
    val months: Long,
    val days: Long,
    val hours: Long,
    val minutes: Long,
    val seconds: Long,
    val totalMonths: Long,
    val totalWeeks: Long,
    val totalDays: Long,
    val totalHours: Long,
    val totalMinutes: Long,
    val totalSeconds: Long,
    val daysToNextBirthday: Long,
    val nextBirthdayAge: Long,
    val weekdayBorn: String
)

object AgeCalc {

    fun compute(birth: LocalDateTime, now: LocalDateTime): AgeParts {
        val birthDate: LocalDate = birth.toLocalDate()
        val nowDate: LocalDate = now.toLocalDate()

        val period = Period.between(birthDate, nowDate)
        val duration = Duration.between(birth, now)

        val totalSeconds = duration.seconds.coerceAtLeast(0)
        val totalMinutes = totalSeconds / 60
        val totalHours = totalSeconds / 3600
        val totalDays = duration.toDays().coerceAtLeast(0)
        val totalWeeks = totalDays / 7
        val totalMonths = period.toTotalMonths()

        // Time-of-day component after subtracting whole days.
        val secOfDay = totalSeconds - (totalDays * 86400)
        val hours = secOfDay / 3600
        val minutes = (secOfDay % 3600) / 60
        val seconds = secOfDay % 60

        var next = safeWithYear(birthDate, nowDate.year)
        if (!next.isAfter(nowDate)) next = safeWithYear(birthDate, nowDate.year + 1)
        val daysToNext = java.time.temporal.ChronoUnit.DAYS.between(nowDate, next)
        val nextAge = Period.between(birthDate, next).years.toLong()

        return AgeParts(
            years = period.years.toLong(),
            months = period.months.toLong(),
            days = period.days.toLong(),
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            totalMonths = totalMonths,
            totalWeeks = totalWeeks,
            totalDays = totalDays,
            totalHours = totalHours,
            totalMinutes = totalMinutes,
            totalSeconds = totalSeconds,
            daysToNextBirthday = daysToNext,
            nextBirthdayAge = nextAge,
            weekdayBorn = birthDate.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
        )
    }

    /** Moves the birthday to [year], falling back to 28 Feb for 29 Feb births. */
    private fun safeWithYear(date: LocalDate, year: Int): LocalDate =
        try {
            date.withYear(year)
        } catch (e: java.time.DateTimeException) {
            LocalDate.of(year, 2, 28)
        }
}
