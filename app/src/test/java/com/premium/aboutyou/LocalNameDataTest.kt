package com.premium.aboutyou

import com.premium.aboutyou.data.LocalNameData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LocalNameDataTest {

    @Test
    fun `known names resolve with full detail`() {
        val ali = LocalNameData.lookup("Ali")
        assertNotNull(ali)
        assertTrue(ali!!.name.equals("Ali", ignoreCase = true))
        assertTrue(ali.meaning.isNotBlank())
        assertTrue(ali.urduMeaning.isNotBlank())
        assertTrue(ali.traits.isNotEmpty())
        assertTrue(ali.gender.isNotBlank())
    }

    @Test
    fun `lookup ignores surrounding space and case`() {
        assertNotNull(LocalNameData.lookup("  muhammad  "))
        assertNotNull(LocalNameData.lookup("FATIMA"))
    }

    @Test
    fun `unknown names return null`() {
        assertNull(LocalNameData.lookup("Zzzqxwv"))
    }

    @Test
    fun `generated fallback is stable and complete`() {
        val a = LocalNameData.generated("Zzzqxwv")
        val b = LocalNameData.generated("Zzzqxwv")
        assertEquals(a.luckyNumber, b.luckyNumber)
        assertEquals(a.luckyColor, b.luckyColor)
        assertTrue(a.meaning.contains("Z"))
        assertTrue(a.urduMeaning.isNotBlank())
    }

    @Test
    fun `generated fallback respects live hints`() {
        val hint = LocalNameData.generated("Zzzqxwv", genderHint = "Female", originHint = "Pakistan")
        assertEquals("Female", hint.gender)
        assertEquals("Pakistan", hint.origin)
    }
}
