package com.premium.aboutyou

import com.premium.aboutyou.data.Zodiac
import com.premium.aboutyou.util.AgeCalc
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class AgeCalcTest {

    @Test
    fun `computes exact years months and days`() {
        val birth = LocalDateTime.of(2006, 9, 9, 12, 0, 0)
        val now = LocalDateTime.of(2026, 11, 4, 11, 0, 0)
        val parts = AgeCalc.compute(birth, now)
        assertEquals(20, parts.years)
        assertEquals(1, parts.months)
        assertEquals(26, parts.days)
    }

    @Test
    fun `borrowing days across month boundaries works`() {
        // 9 Sep 2006 → 4 Nov 2026 is 20 years, 1 month and 26 days.
        val parts = AgeCalc.compute(
            LocalDateTime.of(2006, 9, 9, 12, 0, 0),
            LocalDateTime.of(2026, 11, 4, 11, 0, 0)
        )
        assertEquals(26, parts.days)
    }

    @Test
    fun `totals are consistent`() {
        val birth = LocalDateTime.of(2000, 1, 1, 0, 0, 0)
        val now = LocalDateTime.of(2000, 1, 2, 1, 2, 3)
        val parts = AgeCalc.compute(birth, now)
        assertEquals(1, parts.totalDays)
        assertEquals(25, parts.totalHours)
        assertEquals(1502, parts.totalMinutes)
        assertEquals(90123, parts.totalSeconds)
        assertEquals(1, parts.hours)
        assertEquals(2, parts.minutes)
        assertEquals(3, parts.seconds)
    }

    @Test
    fun `counts days to next birthday`() {
        val birth = LocalDateTime.of(1990, 9, 9, 0, 0, 0)
        val now = LocalDateTime.of(2026, 9, 1, 0, 0, 0)
        val parts = AgeCalc.compute(birth, now)
        assertEquals(8, parts.daysToNextBirthday)
        assertEquals(36, parts.nextBirthdayAge)
    }

    @Test
    fun `weekday of birth is reported`() {
        val parts = AgeCalc.compute(
            LocalDateTime.of(2006, 9, 9, 10, 0),
            LocalDateTime.of(2026, 9, 9, 10, 0)
        )
        assertEquals("Saturday", parts.weekdayBorn)
        assertEquals(20, parts.years)
    }

    @Test
    fun `leap day birthdays do not crash`() {
        val parts = AgeCalc.compute(
            LocalDateTime.of(2000, 2, 29, 12, 0),
            LocalDateTime.of(2026, 3, 1, 12, 0)
        )
        assertEquals(26, parts.years)
        assertTrue(parts.daysToNextBirthday in 1..366)
    }

    @Test
    fun `zodiac boundaries are correct`() {
        assertEquals("Virgo", Zodiac.forDate(LocalDate.of(2006, 9, 9)).name)
        assertEquals("Capricorn", Zodiac.forDate(LocalDate.of(1990, 12, 25)).name)
        assertEquals("Capricorn", Zodiac.forDate(LocalDate.of(1990, 1, 10)).name)
        assertEquals("Aquarius", Zodiac.forDate(LocalDate.of(1990, 1, 25)).name)
        assertEquals("Aries", Zodiac.forDate(LocalDate.of(1990, 3, 21)).name)
        assertEquals("Pisces", Zodiac.forDate(LocalDate.of(1990, 3, 20)).name)
        assertEquals("Sagittarius", Zodiac.forDate(LocalDate.of(1990, 11, 22)).name)
    }

    @Test
    fun `every zodiac sign resolves to a usable image url`() {
        Zodiac.signs.forEach { sign ->
            assertTrue(sign.name, sign.imageUrl.startsWith("https://"))
            assertTrue(sign.name, sign.traits.isNotEmpty())
            assertTrue(sign.name, sign.urduDescription.isNotBlank())
        }
        assertEquals(12, Zodiac.signs.size)
    }
}
