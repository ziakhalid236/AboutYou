package com.premium.aboutyou

import com.premium.aboutyou.data.Dict
import com.premium.aboutyou.data.Language
import com.premium.aboutyou.data.Strings
import com.premium.aboutyou.data.Zodiac
import com.premium.aboutyou.data.localized
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate

class LocalisationTest {

    @Test
    fun `every language is reachable from its code`() {
        Language.entries.forEach { lang ->
            assertEquals(lang, Language.from(lang.code))
        }
        assertEquals(Language.ENGLISH, Language.from("nope"))
        assertEquals(Language.ENGLISH, Language.from(null))
    }

    @Test
    fun `rtl languages are flagged`() {
        assertTrue(Language.URDU.rtl)
        assertTrue(Language.ARABIC.rtl)
        assertTrue(Language.PERSIAN.rtl)
        assertTrue(!Language.ENGLISH.rtl)
        assertTrue(!Language.SPANISH.rtl)
    }

    @Test
    fun `interface text is filled in for every language`() {
        Language.entries.forEach { lang ->
            val s = Strings(lang)
            assertTrue("hero title missing for ${lang.code}", s.heroTitle.isNotBlank())
            assertTrue("analyze missing for ${lang.code}", s.analyze.isNotBlank())
            assertTrue("tab home missing for ${lang.code}", s.tabHome.isNotBlank())
            assertTrue("star sign missing for ${lang.code}", s.starSign.isNotBlank())
        }
    }

    @Test
    fun `urdu strings really are urdu`() {
        val urdu = Strings(Language.URDU)
        assertNotEquals(Strings(Language.ENGLISH).analyze, urdu.analyze)
        assertEquals("تجزیہ کریں", urdu.analyze)
    }

    @Test
    fun `dictionary translates known words and keeps unknown ones`() {
        assertEquals("آگ", Dict.word("Fire", Language.URDU))
        assertEquals("آتش", Dict.word("Fire", Language.PERSIAN))
        assertEquals("Agua", Dict.word("Water", Language.SPANISH))
        assertEquals("Fire", Dict.word("Fire", Language.ENGLISH))
        // Unknown words are returned unchanged rather than blanked out.
        assertEquals("Unmapped", Dict.word("Unmapped", Language.URDU))
    }

    @Test
    fun `dictionary translates a comma separated list in place`() {
        val out = Dict.list("Courageous, determined, honest", Language.URDU)
        assertTrue(out.contains("بہادر"))
        assertTrue(out.contains("پرعزم"))
        assertTrue(out.contains(", "))
    }

    @Test
    fun `zodiac sign is localised`() {
        val leo = Zodiac.forDate(LocalDate.of(2000, 8, 1))
        assertEquals("Leo", leo.name)

        val english = leo.localized(Language.ENGLISH)
        assertEquals("Fire", english.element)
        assertEquals("Fixed", english.quality)

        val urdu = leo.localized(Language.URDU)
        assertEquals("آگ", urdu.element)
        assertEquals("مستحکم", urdu.quality)
        assertTrue(urdu.traits.contains("دلکش"))
    }

    @Test
    fun `ruling planet with two options is fully localised`() {
        val scorpio = Zodiac.forDate(LocalDate.of(2000, 11, 1))
        assertEquals("Mars / Pluto", scorpio.rulingPlanet)
        assertEquals("مریخ / پلوٹو", scorpio.localized(Language.URDU).rulingPlanet)
    }
}
