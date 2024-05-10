package org.d3if3060.assessment1.navigation

import org.d3if3060.assessment1.ui.screen.KEY_ID_LAPORAN

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_LAPORAN}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}