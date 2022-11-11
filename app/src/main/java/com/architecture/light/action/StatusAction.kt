package com.architecture.light.action

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.android.architecture.extension.getDrawable
import com.android.architecture.extension.getString
import com.android.architecture.utils.NetworkUtils
import com.architecture.light.R
import com.architecture.light.ui.view.StatusLayout
import com.architecture.light.ui.view.StatusLayout.OnRetryListener

/**
 * File describe:状态布局意图
 * Author: SuQi
 * Create date: 2022/11/11
 * Modify date: 2022/11/11
 * Version: 1
 */
interface StatusAction {

    fun getStatusLayout(): StatusLayout

    fun showLoadingStatus() {
        showLoadingStatus(R.raw.loading)
    }

    fun showLoadingStatus(@RawRes id: Int) {
        val layout = getStatusLayout()
        layout.show()
        layout.setAnimResource(id)
        layout.setHint("")
        layout.setOnRetryListener(null)
    }

    fun showCompleteStatus() {
        val layout = getStatusLayout()
        if (layout.isShow) {
            layout.hide()
        }
    }

    fun showEmptyStatus(listener: OnRetryListener) {
        showLayoutStatus(R.drawable.ic_status_empty, R.string.status_layout_no_data)
    }

    fun showErrorStatus(listener: OnRetryListener) {
        val connected = NetworkUtils.isConnected()
        if (!connected) {
            showLayoutStatus(
                R.drawable.ic_status_network,
                R.string.status_layout_error_network,
                listener
            )
            return
        }
        showLayoutStatus(R.drawable.ic_status_error, R.string.status_layout_error_request, listener)
    }

    fun showLayoutStatus(
        @DrawableRes iconDrawableId: Int,
        @StringRes hintStringId: Int,
        listener: OnRetryListener? = null
    ) {
        showLayoutStatus(getDrawable(iconDrawableId)!!, getString(hintStringId), listener)
    }

    fun showLayoutStatus(iconDrawable: Drawable, hint: String, listener: OnRetryListener? = null) {
        val layout = getStatusLayout()
        layout.show()
        layout.setHint(hint)
        layout.setIcon(iconDrawable)
        layout.setOnRetryListener(listener)
    }

}