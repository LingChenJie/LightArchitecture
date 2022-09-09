package com.architecture.light.print.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.architecture.light.databinding.ViewPreviewBillBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 9/9/22
 * Modify date: 9/9/22
 * Version: 1
 */
class PreviewBillView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding: ViewPreviewBillBinding by lazy {
        val layoutInflater = LayoutInflater.from(context)
        ViewPreviewBillBinding.inflate(layoutInflater, this, true)
    }

    fun fullData() {
        binding.root
    }

}