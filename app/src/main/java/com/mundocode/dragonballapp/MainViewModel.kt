package com.mundocode.dragonballapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mundocode.dragonballapp.repositories.LocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    localRepository: LocalRepository,
) : ViewModel() {

    val isDarkMode = localRepository.darkModeFlow.stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(2000),
        initialValue = false,
    )
}
