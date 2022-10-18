package com.android.architecture.ui.widget.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.architecture.R
import com.android.architecture.databinding.ViewItemBinding

class ItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : SimpleLayout(context, attrs, defStyleAttr) {

    private val binding: ViewItemBinding by lazy {
        ViewItemBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.CustomItemView, defStyleAttr, 0)

    init {
        initView()
    }

    private fun initView() {
        try {
            val backString = typedArray.getString(R.styleable.CustomItemView_back_text) ?: ""
            setBackText(backString)
            val backIconResId = typedArray.getResourceId(R.styleable.CustomItemView_back_icon, 0)
            setBackIcon(backIconResId)
            val arrowString = typedArray.getString(R.styleable.CustomItemView_arrow_text) ?: ""
            setArrowText(arrowString)
            val arrowIconResId = typedArray.getResourceId(
                R.styleable.CustomItemView_arrow_icon,
                R.drawable.icon_arrow_gray
            )
            setArrowIcon(arrowIconResId)
        } finally {
            typedArray.recycle()
        }
    }

    fun setBackText(@StringRes resId: Int) {
        binding.backText.setText(resId)
    }

    fun setBackText(text: CharSequence) {
        binding.backText.text = text
    }

    fun setBackIcon(@DrawableRes resId: Int) {
        if (resId != 0) {
            binding.backIcon.visibility = View.VISIBLE
            binding.backIcon.setImageResource(resId)
        } else {
            binding.backIcon.visibility = View.GONE
        }
    }

    fun setArrowText(@StringRes resId: Int) {
        binding.arrowText.setText(resId)
    }

    fun setArrowText(text: CharSequence) {
        binding.arrowText.text = text
    }

    fun getArrowText(): String {
        return binding.arrowText.text.toString()
    }

    fun setArrowIcon(@DrawableRes resId: Int) {
        if (resId != 0) {
            binding.arrowIcon.visibility = View.VISIBLE
            binding.arrowIcon.setImageResource(resId)
        } else {
            binding.arrowIcon.visibility = View.GONE
        }
    }

    fun setArrowClickListener(listener: (View) -> Unit) {
        binding.arrowView.setOnClickListener(listener)
    }

    fun setClickListener(listener: (View) -> Unit) {
        binding.rootView.setOnClickListener(listener)
        binding.rootView.setBackgroundResource(R.drawable.selector_itemview_click)
    }

}