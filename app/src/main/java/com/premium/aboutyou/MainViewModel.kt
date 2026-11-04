package com.premium.aboutyou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.premium.aboutyou.data.*
import com.premium.aboutyou.net.Repo
import com.premium.aboutyou.util.AgeCalc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime

sealed interface LoadState<out T> {
    data object Idle : LoadState<Nothing>
    data object Loading : LoadState<Nothing>
    data class Ready<T>(val data: T) : LoadState<T>
    data class Failed(val message: String) : LoadState<Nothing>
}

class MainViewModel : ViewModel() {

    private val _language = MutableStateFlow(Prefs.language)
    val language: StateFlow<Language> = _language.asStateFlow()

    private val _nameState = MutableStateFlow<LoadState<NameReport>>(LoadState.Idle)
    val nameState: StateFlow<LoadState<NameReport>> = _nameState.asStateFlow()

    private val _dayBirths = MutableStateFlow<LoadState<List<BirthEntry>>>(LoadState.Idle)
    val dayBirths: StateFlow<LoadState<List<BirthEntry>>> = _dayBirths.asStateFlow()

    private val _exactBirths = MutableStateFlow<LoadState<List<BirthEntry>>>(LoadState.Idle)
    val exactBirths: StateFlow<LoadState<List<BirthEntry>>> = _exactBirths.asStateFlow()

    private val _birthDateTime = MutableStateFlow<LocalDateTime?>(null)
    val birthDateTime: StateFlow<LocalDateTime?> = _birthDateTime.asStateFlow()

    var lastBirthDate: LocalDate? = null
        private set

    var lastSearchedName: String = ""
        private set

    val strings: Strings get() = Strings(_language.value)

    fun setLanguage(language: Language) {
        if (_language.value == language) return
        _language.value = language
        Prefs.language = language
        // Refresh everything that was already on screen so the new language
        // applies immediately instead of on the next search.
        if (lastSearchedName.isNotBlank()) searchName(lastSearchedName)
        lastBirthDate?.let { loadAllBirths(it) }
    }

    fun setBirthDateTime(value: LocalDateTime) {
        _birthDateTime.value = value
        lastBirthDate = value.toLocalDate()
    }

    fun searchName(raw: String) {
        val name = raw.trim()
        if (name.isBlank()) return
        lastSearchedName = name
        Prefs.lastName = name
        _nameState.value = LoadState.Loading
        viewModelScope.launch {
            _nameState.value = try {
                val report = withContext(Dispatchers.IO) {
                    Repo.fetchNameReport(name, _language.value)
                }
                LoadState.Ready(report)
            } catch (t: Throwable) {
                LoadState.Failed(strings.errorNet)
            }
        }
    }

    fun loadBirthsOnDay(date: LocalDate) {
        lastBirthDate = date
        _dayBirths.value = LoadState.Loading
        viewModelScope.launch {
            _dayBirths.value = try {
                val lang = _language.value
                val list = withContext(Dispatchers.IO) {
                    val base = Repo.fetchBirthsOnDay(date.monthValue, date.dayOfMonth, lang)
                    Repo.enrichWithExtracts(base, lang, limit = 10)
                }
                LoadState.Ready(list)
            } catch (t: Throwable) {
                LoadState.Failed(strings.errorNet)
            }
        }
    }

    fun loadExactBirths(date: LocalDate) {
        lastBirthDate = date
        _exactBirths.value = LoadState.Loading
        viewModelScope.launch {
            _exactBirths.value = try {
                val lang = _language.value
                val list = withContext(Dispatchers.IO) {
                    val base = Repo.fetchBirthsOnExactDate(date, lang)
                    if (base.isEmpty()) emptyList() else Repo.enrichWithExtracts(base, lang, limit = 10)
                }
                LoadState.Ready(list)
            } catch (t: Throwable) {
                LoadState.Failed(strings.errorNet)
            }
        }
    }

    fun loadAllBirths(date: LocalDate) {
        loadBirthsOnDay(date)
        loadExactBirths(date)
    }

    fun refresh() {
        val d = lastBirthDate ?: LocalDate.now()
        loadAllBirths(d)
    }

    /** Plain-text version of everything currently known, used by Print / Share. */
    fun buildReportText(): String {
        val s = strings
        val out = StringBuilder()
        out.appendLine("${s.heroTitle} — ${s.poweredBy}")
        out.appendLine()

        lastSearchedName.takeIf { it.isNotBlank() }?.let { name ->
            out.appendLine("== ${s.nameTitle}: $name ==")
            val local = (nameState.value as? LoadState.Ready)?.data?.local
                ?: LocalNameData.generated(name)
            out.appendLine("${s.meaning}: ${local.meaning}")
            out.appendLine("${s.origin}: ${local.origin}")
            out.appendLine("${s.culture}: ${Dict.word(local.culture, _language.value)}")
            out.appendLine("${s.gender}: ${Dict.word(local.gender, _language.value)}")
            out.appendLine("${s.luckyNumber}: ${local.luckyNumber}")
            out.appendLine("${s.luckyColour}: ${local.luckyColor}")
            out.appendLine("${s.traits}: ${local.traits.joinToString(", ") { Dict.word(it, _language.value) }}")
            out.appendLine()
        }

        lastBirthDate?.let { date ->
            val lang = _language.value
            out.appendLine("== ${s.birthTitle} ==")
            out.appendLine("${s.bornOn}: ${Repo.fullDate(date, lang)} (${Repo.weekday(date, lang)})")
            val parts = AgeCalc.compute(
                _birthDateTime.value ?: date.atStartOfDay(),
                LocalDateTime.now()
            )
            out.appendLine(
                "${parts.years} ${s.years}, ${parts.months} ${s.months}, ${parts.days} ${s.days}, " +
                    "${parts.hours} ${s.hours}, ${parts.minutes} ${s.minutes}, ${parts.seconds} ${s.seconds}"
            )
            out.appendLine("${s.totalSeconds}: ${parts.totalSeconds}")
            val z = Zodiac.forDate(date).localized(lang)
            out.appendLine("${s.starSign}: ${z.sign.symbol} ${z.sign.name} (${z.element}, ${z.quality})")
            out.appendLine()
        }
        return out.toString()
    }
}
