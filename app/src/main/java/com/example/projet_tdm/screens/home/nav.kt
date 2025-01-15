package com.example.projet_tdm.screens.home

object TabNavigationState {
    private var _onTabSelected: ((Int) -> Unit)? = null

    fun setTabSelector(onTabSelected: (Int) -> Unit) {
        _onTabSelected = onTabSelected
       println("TabNavigationStat Tab selector set")
    }

    fun navigateToTab(tabIndex: Int) {
        println("TabNavigationState b: $tabIndex")
        if (_onTabSelected == null) {
            println("TabNavigationSta  selector is null!")
        }
        _onTabSelected?.invoke(tabIndex)
    }
}