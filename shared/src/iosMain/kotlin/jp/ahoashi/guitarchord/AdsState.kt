package jp.ahoashi.guitarchord

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object AdsState {
    private val _canShowAds = MutableStateFlow(false)
    val canShowAds: StateFlow<Boolean> = _canShowAds

    fun setCanShowAds(value: Boolean) {
        _canShowAds.value = value
    }
}