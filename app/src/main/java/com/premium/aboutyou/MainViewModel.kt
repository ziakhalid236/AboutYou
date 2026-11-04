package com.premium.aboutyou

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.premium.aboutyou.data.*
import com.premium.aboutyou.net.Repo
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

    private val _nameState = MutableStateFlow<LoadState<NameReport>>(LoadState.Idle)
    val nameState: StateFlow<LoadState<NameReport>> = _nameState.asStateFlow()

    private val _dayBirths = MutableStateFlow<LoadState<List<BirthEntry>>>(LoadState.Idle)
    val dayBirths: StateFlow<LoadState<List<BirthEntry>>> = _dayBirths.asStateFlow()

    private val _exactBirths = MutableStateFlow<LoadState<List<BirthEntry>>>(LoadState.Idle)
    val exactBirths: StateFlow<LoadState<List<BirthEntry>>> = _exactBirths.asStateFlow()

    var lastBirthDate: LocalDate? = null
        private set

    private val _birthDateTime = MutableStateFlow<LocalDateTime?>(null)
    val birthDateTime: StateFlow<LocalDateTime?> = _birthDateTime.asStateFlow()

    fun setBirthDateTime(value: LocalDateTime) {
        _birthDateTime.value = value
        lastBirthDate = value.toLocalDate()
    }

    var lastSearchedName: String = ""
        private set

    fun searchName(raw: String) {
        val name = raw.trim()
        if (name.isBlank()) return
        lastSearchedName = name
        _nameState.value = LoadState.Loading
        viewModelScope.launch {
            _nameState.value = try {
                val report = withContext(Dispatchers.IO) { Repo.fetchNameReport(name) }
                LoadState.Ready(report)
            } catch (t: Throwable) {
                LoadState.Failed("Could not reach the internet. Check your connection and try again.")
            }
        }
    }

    fun loadBirthsOnDay(date: LocalDate) {
        lastBirthDate = date
        _dayBirths.value = LoadState.Loading
        viewModelScope.launch {
            _dayBirths.value = try {
                val list = withContext(Dispatchers.IO) {
                    val base = Repo.fetchBirthsOnDay(date.monthValue, date.dayOfMonth)
                    Repo.enrichWithExtracts(base, limit = 10)
                }
                LoadState.Ready(list)
            } catch (t: Throwable) {
                LoadState.Failed("Could not load birthdays. Check your connection and try again.")
            }
        }
    }

    fun loadExactBirths(date: LocalDate) {
        lastBirthDate = date
        _exactBirths.value = LoadState.Loading
        viewModelScope.launch {
            _exactBirths.value = try {
                val list = withContext(Dispatchers.IO) {
                    val base = Repo.fetchBirthsOnExactDate(date)
                    if (base.isEmpty()) emptyList() else Repo.enrichWithExtracts(base, limit = 10)
                }
                LoadState.Ready(list)
            } catch (t: Throwable) {
                LoadState.Failed("Could not load birthdays. Check your connection and try again.")
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
}
