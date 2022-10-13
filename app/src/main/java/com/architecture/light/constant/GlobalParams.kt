package com.architecture.light.constant

import com.architecture.light.data.bean.NavigationBean

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
object GlobalParams {

    private var navigationBean: NavigationBean? = null

    fun initNavigationBean(): NavigationBean {
        navigationBean = newNavigationBean()
        return navigationBean!!
    }

    fun getNavigationBean(): NavigationBean {
        return navigationBean!!
    }

    fun resetNavigationBean() {
        navigationBean = null
    }

    private fun newNavigationBean(): NavigationBean {
        val navigationBean = NavigationBean()
        return navigationBean
    }

}