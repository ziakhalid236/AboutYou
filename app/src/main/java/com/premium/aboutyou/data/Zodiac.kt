package com.premium.aboutyou.data

import java.time.LocalDate

data class ZodiacSign(
    val name: String,
    val symbol: String,
    val element: String,
    val quality: String,
    val rulingPlanet: String,
    val startMonth: Int,
    val startDay: Int,
    val endMonth: Int,
    val endDay: Int,
    val dateRange: String,
    val traits: List<String>,
    val strengths: String,
    val weaknesses: String,
    val luckyNumbers: String,
    val luckyColor: String,
    val compatible: String,
    val urduDescription: String,
    val imageUrl: String
)

object Zodiac {

    private const val BASE = "https://upload.wikimedia.org/wikipedia/commons/thumb/"

    val signs: List<ZodiacSign> = listOf(
        ZodiacSign("Aries", "♈", "Fire", "Cardinal", "Mars", 3, 21, 4, 19, "Mar 21 – Apr 19",
            listOf("Bold", "Ambitious", "Energetic", "Pioneering"),
            "Courageous, determined, confident, enthusiastic, optimistic, honest, passionate",
            "Impatient, moody, short-tempered, impulsive, aggressive",
            "1, 8, 17", "Red", "Leo, Sagittarius, Gemini, Aquarius",
            "حمل: آپ بہادر، پرجوش اور آگے بڑھنے والے ہیں۔ آپ کو نئی شروعات پسند ہے اور آپ مشکل حالات میں بھی ہمت نہیں ہارتے۔",
            BASE + "3/3d/Aries_symbol_%28bold%29.svg/500px-Aries_symbol_%28bold%29.svg.png"),

        ZodiacSign("Taurus", "♉", "Earth", "Fixed", "Venus", 4, 20, 5, 20, "Apr 20 – May 20",
            listOf("Reliable", "Patient", "Devoted", "Grounded"),
            "Reliable, patient, practical, devoted, responsible, stable, sensual",
            "Stubborn, possessive, uncompromising, lazy at times",
            "2, 6, 9, 12", "Green, Pink", "Virgo, Capricorn, Cancer, Pisces",
            "ثور: آپ قابلِ اعتماد، صابر اور عملی ہیں۔ آپ سکون اور استحکام کو اہمیت دیتے ہیں اور محنت سے کامیابی حاصل کرتے ہیں۔",
            BASE + "5/57/Taurus_symbol_%28bold%29.svg/500px-Taurus_symbol_%28bold%29.svg.png"),

        ZodiacSign("Gemini", "♊", "Air", "Mutable", "Mercury", 5, 21, 6, 20, "May 21 – Jun 20",
            listOf("Curious", "Adaptable", "Witty", "Communicative"),
            "Gentle, affectionate, curious, adaptable, quick learner, witty",
            "Nervous, inconsistent, indecisive, restless",
            "5, 7, 14, 23", "Yellow, Light Blue", "Aries, Leo, Libra, Aquarius",
            "جوزا: آپ ذہین، متجسس اور خوش گفتار ہیں۔ آپ کی سوچ تیز ہے اور آپ نئی معلومات حاصل کرنے کے شوقین ہیں۔",
            BASE + "4/43/Gemini_symbol_%28bold%29.svg/500px-Gemini_symbol_%28bold%29.svg.png"),

        ZodiacSign("Cancer", "♋", "Water", "Cardinal", "Moon", 6, 21, 7, 22, "Jun 21 – Jul 22",
            listOf("Nurturing", "Loyal", "Intuitive", "Protective"),
            "Tenacious, highly imaginative, loyal, emotional, sympathetic, persuasive",
            "Moody, pessimistic, suspicious, insecure",
            "2, 3, 15, 20", "Silver, White", "Taurus, Virgo, Scorpio, Pisces",
            "سرطان: آپ مہربان، وفادار اور جذباتی ہیں۔ آپ اپنے پیاروں کا خیال رکھتے ہیں اور خاندان آپ کے لیے سب سے اہم ہے۔",
            BASE + "3/38/Cancer_symbol_%28bold%29.svg/500px-Cancer_symbol_%28bold%29.svg.png"),

        ZodiacSign("Leo", "♌", "Fire", "Fixed", "Sun", 7, 23, 8, 22, "Jul 23 – Aug 22",
            listOf("Confident", "Generous", "Charismatic", "Loyal"),
            "Creative, passionate, generous, warm-hearted, cheerful, humorous",
            "Arrogant, stubborn, self-centered, lazy, inflexible",
            "1, 3, 10, 19", "Gold, Orange", "Aries, Sagittarius, Gemini, Libra",
            "اسد: آپ خود اعتماد، فیاض اور دلکش ہیں۔ آپ میں قائدانہ صلاحیت ہے اور لوگ آپ کی طرف کھنچے چلے آتے ہیں۔",
            BASE + "8/84/Leo_symbol_%28bold%29.svg/500px-Leo_symbol_%28bold%29.svg.png"),

        ZodiacSign("Virgo", "♍", "Earth", "Mutable", "Mercury", 8, 23, 9, 22, "Aug 23 – Sep 22",
            listOf("Analytical", "Kind", "Hardworking", "Practical"),
            "Loyal, analytical, kind, hardworking, practical, methodical",
            "Shyness, worry, overly critical, all work and no play",
            "5, 14, 15, 23", "Green, Brown", "Taurus, Cancer, Scorpio, Capricorn",
            "سنبلہ: آپ محنتی، منظم اور تجزیہ کرنے والے ہیں۔ آپ ہر کام مکمل مہارت اور باریکی سے کرنا پسند کرتے ہیں۔",
            BASE + "3/39/Virgo_symbol_%28bold%29.svg/500px-Virgo_symbol_%28bold%29.svg.png"),

        ZodiacSign("Libra", "♎", "Air", "Cardinal", "Venus", 9, 23, 10, 22, "Sep 23 – Oct 22",
            listOf("Diplomatic", "Fair", "Social", "Harmonious"),
            "Cooperative, diplomatic, gracious, fair-minded, social",
            "Indecisive, avoids confrontations, will carry a grudge",
            "4, 6, 13, 15", "Pink, Blue", "Gemini, Leo, Sagittarius, Aquarius",
            "میزان: آپ انصاف پسند، خوش اخلاق اور ملنسار ہیں۔ آپ توازن اور ہم آہنگی کو ترجیح دیتے ہیں۔",
            BASE + "9/96/Libra_symbol_%28bold%29.svg/500px-Libra_symbol_%28bold%29.svg.png"),

        ZodiacSign("Scorpio", "♏", "Water", "Fixed", "Mars / Pluto", 10, 23, 11, 21, "Oct 23 – Nov 21",
            listOf("Passionate", "Resourceful", "Brave", "Determined"),
            "Resourceful, powerful, brave, passionate, a true friend",
            "Distrusting, jealous, secretive, violent when provoked",
            "8, 11, 18, 22", "Maroon, Red", "Cancer, Virgo, Capricorn, Pisces",
            "عقرب: آپ پرجوش، بہادر اور پرعزم ہیں۔ آپ کی شخصیت میں گہرائی ہے اور آپ اپنے مقصد کے لیے ہر حد تک جاتے ہیں۔",
            BASE + "c/ca/Scorpius_symbol_%28bold%29.svg/500px-Scorpio_symbol_%28bold%29.svg.png"),

        ZodiacSign("Sagittarius", "♐", "Fire", "Mutable", "Jupiter", 11, 22, 12, 21, "Nov 22 – Dec 21",
            listOf("Adventurous", "Optimistic", "Honest", "Free-spirited"),
            "Generous, idealistic, great sense of humour, adventurous",
            "Promises more than can deliver, impatient, blunt",
            "3, 7, 9, 12", "Purple, Blue", "Aries, Leo, Libra, Aquarius",
            "قوس: آپ آزاد مزاج، پرامید اور مہم جو ہیں۔ آپ سچ بولنا پسند کرتے ہیں اور نئی جگہوں کی سیر کے شوقین ہیں۔",
            BASE + "f/f6/Sagittarius_symbol_%28bold%29.svg/500px-Sagittarius_symbol_%28bold%29.svg.png"),

        ZodiacSign("Capricorn", "♑", "Earth", "Cardinal", "Saturn", 12, 22, 1, 19, "Dec 22 – Jan 19",
            listOf("Disciplined", "Responsible", "Ambitious", "Patient"),
            "Responsible, disciplined, self-control, good managers",
            "Know-it-all, unforgiving, condescending, expecting the worst",
            "3, 7, 8, 15", "Brown, Black", "Taurus, Virgo, Scorpio, Pisces",
            "جدی: آپ نظم و ضبط والے، ذمہ دار اور بلند حوصلہ ہیں۔ آپ محنت اور صبر سے اپنے اہداف حاصل کرتے ہیں۔",
            BASE + "c/cc/Capricornus_symbol_%28bold%29.svg/500px-Capricorn_symbol_%28bold%29.svg.png"),

        ZodiacSign("Aquarius", "♒", "Air", "Fixed", "Saturn / Uranus", 1, 20, 2, 18, "Jan 20 – Feb 18",
            listOf("Original", "Independent", "Humanitarian", "Inventive"),
            "Progressive, original, independent, humanitarian, inventive",
            "Runs from emotional expression, temperamental, uncompromising",
            "4, 8, 13, 17", "Blue, Turquoise", "Aries, Gemini, Libra, Sagittarius",
            "دلو: آپ جدت پسند، آزاد اور انسان دوست ہیں۔ آپ نئے خیالات لاتے ہیں اور روایتی سوچ کو چیلنج کرتے ہیں۔",
            BASE + "d/d5/Aquarius_symbol_%28bold%29.svg/500px-Aquarius_symbol_%28bold%29.svg.png"),

        ZodiacSign("Pisces", "♓", "Water", "Mutable", "Jupiter / Neptune", 2, 19, 3, 20, "Feb 19 – Mar 20",
            listOf("Compassionate", "Artistic", "Intuitive", "Gentle"),
            "Compassionate, artistic, intuitive, gentle, wise, musical",
            "Fearful, overly trusting, sad, desire to escape reality",
            "3, 9, 12, 18", "Sea Green, Lilac", "Taurus, Cancer, Scorpio, Capricorn",
            "حوت: آپ ہمدرد، فنکارانہ اور حساس ہیں۔ آپ کی intuition مضبوط ہے اور آپ دوسروں کے دکھ کو سمجھتے ہیں۔",
            BASE + "d/d7/Pisces_symbol_%28bold%29.svg/500px-Pisces_symbol_%28bold%29.svg.png")
    )

    fun forDate(date: LocalDate): ZodiacSign {
        val m = date.monthValue
        val d = date.dayOfMonth
        return signs.firstOrNull { s ->
            if (s.startMonth <= s.endMonth) {
                (m == s.startMonth && d >= s.startDay) || (m == s.endMonth && d <= s.endDay) || (m > s.startMonth && m < s.endMonth)
            } else {
                (m == s.startMonth && d >= s.startDay) || (m == s.endMonth && d <= s.endDay)
            }
        } ?: signs[0]
    }

    fun elementSymbol(element: String): String = when (element) {
        "Fire" -> "🔥"; "Earth" -> "🌍"; "Air" -> "💨"; else -> "💧"
    }
}
