package com.premium.aboutyou.net

import com.premium.aboutyou.data.*
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.util.Locale

/**
 * All live data comes from public, key-less, worldwide sources:
 *  - en.wikipedia.org  (search, summaries, article extracts)
 *  - api.wikimedia.org (Wikipedia "on this day" births, has an explicit year)
 *  - query.wikidata.org (exact date-of-birth search)
 *  - api.genderize.io / api.nationalize.io (name statistics)
 */
object Repo {

    private val DAY_MONTHS = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    private val COUNTRY_NAMES = mapOf(
        "TR" to "Turkey", "IR" to "Iran", "SA" to "Saudi Arabia", "EG" to "Egypt",
        "LB" to "Lebanon", "PK" to "Pakistan", "IN" to "India", "BD" to "Bangladesh",
        "US" to "United States", "GB" to "United Kingdom", "DE" to "Germany",
        "FR" to "France", "IT" to "Italy", "ES" to "Spain", "CN" to "China",
        "JP" to "Japan", "ID" to "Indonesia", "MY" to "Malaysia", "NG" to "Nigeria",
        "ZA" to "South Africa", "BR" to "Brazil", "MX" to "Mexico", "RU" to "Russia",
        "CA" to "Canada", "AU" to "Australia", "NL" to "Netherlands", "SE" to "Sweden",
        "PL" to "Poland", "IQ" to "Iraq", "SY" to "Syria", "JO" to "Jordan",
        "AE" to "United Arab Emirates", "AF" to "Afghanistan", "KR" to "South Korea",
        "VN" to "Vietnam", "TH" to "Thailand", "PH" to "Philippines", "AR" to "Argentina",
        "CO" to "Colombia", "KE" to "Kenya", "ET" to "Ethiopia", "MA" to "Morocco",
        "DZ" to "Algeria", "TN" to "Tunisia", "SD" to "Sudan", "YE" to "Yemen",
        "UA" to "Ukraine", "PT" to "Portugal", "GR" to "Greece", "CH" to "Switzerland"
    )

    // ---------------------------------------------------------------- name info

    fun fetchNameReport(rawName: String): NameReport {
        val query = rawName.trim()
        val stats = fetchNameStats(query)
        val summary = fetchNameSummary(query)
        val notable = fetchNotablePeople(query)
        val local = LocalNameData.lookup(query)
        return NameReport(
            query = query,
            summary = summary,
            local = local,
            notable = notable,
            stats = stats
        )
    }

    /** Wikipedia page summary for the name itself (its history, origin and usage). */
    private fun fetchNameSummary(name: String): WikiSummary? {
        // Try "<Name> (given name)" first, then the plain title.
        val candidates = listOf("$name (given name)", name)
        for (title in candidates) {
            val json = Http.get(
                "https://en.wikipedia.org/api/rest_v1/page/summary/" + Http.enc(title.replace(' ', '_'))
            ) ?: continue
            val obj = runCatching { JSONObject(json) }.getOrNull() ?: continue
            if (obj.optString("type") == "disambiguation") continue
            val extract = obj.optString("extract")
            if (extract.isBlank()) continue
            val thumb = obj.optJSONObject("thumbnail")?.optString("source")
            return WikiSummary(
                title = obj.optString("title", title),
                description = obj.optString("description"),
                extract = extract,
                imageUrl = thumb
            )
        }
        return null
    }

    /** Famous people who carry this given name. */
    private fun fetchNotablePeople(name: String): List<NotablePerson> {
        val safe = name.replace("\"", "")
        val url = "https://en.wikipedia.org/w/api.php?action=query&list=search" +
            "&srsearch=" + Http.enc("intitle:\"$safe\"") +
            "&srlimit=8&srnamespace=0&format=json&srprop=snippet"
        val json = Http.get(url) ?: return emptyList()
        val titles = runCatching {
            JSONObject(json).getJSONObject("query").getJSONArray("search")
                .let { arr -> (0 until arr.length()).map { arr.getJSONObject(it).optString("title") } }
        }.getOrDefault(emptyList())
        if (titles.isEmpty()) return emptyList()
        return fetchPages(titles.take(6))
    }

    /** Batch fetch of intro extracts + thumbnails for a list of article titles. */
    private fun fetchPages(titles: List<String>): List<NotablePerson> {
        if (titles.isEmpty()) return emptyList()
        val url = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts|pageimages|info" +
            "&inprop=url&exintro=1&explaintext=1&exsentences=2&exlimit=max" +
            "&pithumbsize=320&redirects=1&format=json&titles=" + Http.enc(titles.joinToString("|"))
        val json = Http.get(url) ?: return emptyList()
        return runCatching {
            val pages = JSONObject(json).getJSONObject("query").getJSONObject("pages")
            val out = ArrayList<NotablePerson>()
            val keys = pages.keys()
            while (keys.hasNext()) {
                val p = pages.getJSONObject(keys.next())
                val title = p.optString("title")
                if (title.isBlank()) continue
                val extract = p.optString("extract").trim()
                val desc = extract.substringBefore(". ").take(160)
                out.add(
                    NotablePerson(
                        title = title,
                        description = desc,
                        extract = extract,
                        imageUrl = p.optJSONObject("thumbnail")?.optString("source"),
                        url = p.optString("fullurl").ifBlank { null }
                    )
                )
            }
            out
        }.getOrDefault(emptyList())
    }

    private fun fetchNameStats(name: String): NameStats? {
        var result: NameStats? = null
        runCatching {
            val g = Http.get("https://api.genderize.io?name=" + Http.enc(name))
            val go = g?.let { JSONObject(it) }
            val gj = go?.optString("gender")?.ifBlank { null }
            val gp = go?.let { if (it.has("probability")) it.optDouble("probability") else null }
            val n = Http.get("https://api.nationalize.io?name=" + Http.enc(name))
            val no = n?.let { JSONObject(it) }
            val countries = ArrayList<Pair<String, Double>>()
            no?.optJSONArray("country")?.let { arr ->
                for (i in 0 until minOf(arr.length(), 6)) {
                    val c = arr.getJSONObject(i)
                    val code = c.optString("country_id")
                    val label = COUNTRY_NAMES[code] ?: code
                    countries.add(label to c.optDouble("probability", 0.0))
                }
            }
            if (gj != null || countries.isNotEmpty()) {
                result = NameStats(genderGuess = gj?.replaceFirstChar { it.uppercase() }, genderProbability = gp, avgAge = null, countries = countries)
            }
        }
        return result
    }

    // ------------------------------------------------------- births on a date

    /** All people Wikipedia knows born on this month/day (any year). */
    fun fetchBirthsOnDay(month: Int, day: Int): List<BirthEntry> {
        val mm = "%02d".format(month)
        val dd = "%02d".format(day)
        val json = Http.get("https://api.wikimedia.org/feed/v1/wikipedia/en/onthisday/births/$mm/$dd")
            ?: return emptyList()
        return runCatching {
            val arr = JSONObject(json).optJSONArray("births") ?: JSONArray()
            val out = ArrayList<BirthEntry>()
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                val year = o.optInt("year", 0)
                val text = o.optString("text")
                val pages = o.optJSONArray("pages")
                val page = if (pages != null && pages.length() > 0) pages.getJSONObject(0) else null
                val title = page?.optString("normalizedtitle")?.ifBlank { page.optString("title") } ?: ""
                val thumb = page?.optJSONObject("thumbnail")?.optString("source")
                val person = if (title.isNotBlank()) title.replace('_', ' ')
                else text.substringBefore(",").trim()
                val desc = text.substringAfter(",", "").trim().ifBlank { "Notable figure" }
                val url = page?.optJSONObject("content_urls")?.optJSONObject("desktop")?.optString("page")
                out.add(
                    BirthEntry(
                        year = year,
                        text = text,
                        personName = person,
                        description = desc.replaceFirstChar { it.uppercase() },
                        extract = text,
                        imageUrl = thumb,
                        pageUrl = url
                    )
                )
            }
            out
        }.getOrDefault(emptyList())
    }

    /** People born on this exact calendar date (day + month + year) via Wikidata. */
    fun fetchBirthsOnExactDate(date: LocalDate): List<BirthEntry> {
        val next = date.plusDays(1)
        val from = "%04d-%02d-%02dT00:00:00Z".format(date.year, date.monthValue, date.dayOfMonth)
        val to = "%04d-%02d-%02dT00:00:00Z".format(next.year, next.monthValue, next.dayOfMonth)
        val query = """
            SELECT ?p ?dob ?article ?name ?image WHERE {
              ?p wdt:P569 ?dob .
              FILTER( ?dob >= "$from"^^xsd:dateTime && ?dob < "$to"^^xsd:dateTime )
              ?article schema:about ?p ; schema:isPartOf <https://en.wikipedia.org/> ; schema:name ?name .
              OPTIONAL { ?p wdt:P18 ?image }
            } LIMIT 60
        """.trimIndent()
        val json = Http.get("https://query.wikidata.org/sparql?format=json&query=" + Http.enc(query))
            ?: return emptyList()
        val rows = runCatching {
            JSONObject(json).getJSONObject("results").getJSONArray("bindings")
        }.getOrNull() ?: return emptyList()
        val out = ArrayList<BirthEntry>()
        for (i in 0 until rows.length()) {
            val b = rows.getJSONObject(i)
            val name = b.optJSONObject("name")?.optString("value") ?: continue
            val article = b.optJSONObject("article")?.optString("value") ?: continue
            val image = b.optJSONObject("image")?.optString("value")?.let { fileToUrl(it) }
            out.add(
                BirthEntry(
                    year = date.year,
                    text = "$name (born ${date.year})",
                    personName = name,
                    description = "Born on ${DAY_MONTHS[date.monthValue - 1]} ${date.dayOfMonth}, ${date.year}",
                    extract = "",
                    imageUrl = image,
                    pageUrl = article
                )
            )
        }
        return out
    }

    private fun fileToUrl(entityUrl: String): String? {
        if (entityUrl.isBlank()) return null
        // Wikidata returns "http://commons.wikimedia.org/wiki/Special:FilePath/Name.jpg".
        // The app blocks cleartext traffic, so upgrade to https and ask for a small width.
        val https = entityUrl.replaceFirst("http://", "https://")
        return if (https.contains("Special:FilePath")) {
            https + (if (https.contains("?")) "&" else "?") + "width=320"
        } else {
            https
        }
    }

    /** Enriches a small list of entries with intro extracts (batched). */
    fun enrichWithExtracts(entries: List<BirthEntry>, limit: Int = 8): List<BirthEntry> {
        val target = entries.take(limit)
        if (target.isEmpty()) return entries
        val titles = target.mapNotNull { it.pageUrl?.substringAfterLast('/')?.replace('_', ' ') }
        if (titles.isEmpty()) return entries
        val url = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts&exintro=1&explaintext=1" +
            "&exsentences=2&exlimit=max&redirects=1&format=json&titles=" + Http.enc(titles.joinToString("|"))
        val json = Http.get(url) ?: return entries
        val map = runCatching {
            val pages = JSONObject(json).getJSONObject("query").getJSONObject("pages")
            val m = HashMap<String, String>()
            val keys = pages.keys()
            while (keys.hasNext()) {
                val p = pages.getJSONObject(keys.next())
                m[p.optString("title").lowercase(Locale.ROOT)] = p.optString("extract").trim()
            }
            m
        }.getOrDefault(emptyMap())
        return entries.map { e ->
            val ex = map[e.personName.lowercase(Locale.ROOT)]
            if (ex.isNullOrBlank()) e else e.copy(extract = ex)
        }
    }

    fun monthName(m: Int): String = DAY_MONTHS.getOrElse(m - 1) { "" }
}
