package com.android.architecture.ui.widget.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.android.architecture.R
import com.android.architecture.databinding.ViewTitleBinding

class TitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding: ViewTitleBinding by lazy {
        ViewTitleBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.CustomItemView, defStyleAttr, 0)

    val backView: View
        get() = binding.backView
    val backText: TextView
        get() = binding.backText
    val backIcon: ImageView
        get() = binding.backIcon

    val titleText: TextView
        get() = binding.titleText

    val arrowView: View
        get() = binding.arrowView
    val arrowText: TextView
        get() = binding.arrowText
    val arrowIcon: ImageView
        get() = binding.arrowIcon

    val action1Icon: ImageView
        get() = binding.action1Icon
    val action2Icon: ImageView
        get() = binding.action2Icon

    init {
        initView()
    }

    private fun initView() {
        try {
            val titleString = typedArray.getString(R.styleable.CustomItemView_title_text) ?: ""
            setTitleText(titleString)
            val titleAllCaps =
                typedArray.getBoolean(R.styleable.CustomItemView_title_text_all_caps, false)
            isAllCaps(titleAllCaps)
            val backString = typedArray.getString(R.styleable.CustomItemView_back_text) ?: ""
            setBackText(backString)
            val backIconResId = typedArray.getResourceId(
                R.styleable.CustomItemView_back_icon,
                R.drawable.icon_back_white
            )
            setBackIcon(backIconResId)
            val arrowString = typedArray.getString(R.styleable.CustomItemView_arrow_text) ?: ""
            setArrowText(arrowString)
            val arrowIconResId = typedArray.getResourceId(R.styleable.CustomItemView_arrow_icon, 0)
            setArrowIcon(arrowIconResId)
            val action1IconResId =
                typedArray.getResourceId(R.styleable.CustomItemView_action1_icon, 0)
            setAction1Icon(action1IconResId)
            val action2IconResId =
                typedArray.getResourceId(R.styleable.CustomItemView_action2_icon, 0)
            setAction2Icon(action2IconResId)
        } finally {
            typedArray.recycle()
        }
    }

    fun setTitleText(@StringRes resId: Int) {
        titleText.setText(resId)
    }

    fun setTitleText(text: CharSequence) {
        titleText.text = text
    }

    fun isAllCaps(allCaps: Boolean) {
        titleText.isAllCaps = allCaps
    }

    fun setBackText(@StringRes resId: Int) {
        backText.setText(resId)
    }

    fun setBackText(text: CharSequence) {
        backText.text = text
    }

    fun setBackIcon(@DrawableRes resId: Int) {
        if (resId != 0) {
            backIcon.visibility = View.VISIBLE
            backIcon.setImageResource(resId)
        } else {
            backIcon.visibility = View.GONE
        }
    }

    fun setBackClickListener(listener: (View) -> Unit) {
        backView.setOnClickListener(listener)
    }

    fun setArrowText(@StringRes resId: Int) {
        arrowText.setText(resId)
    }

    fun setArrowText(text: CharSequence) {
        arrowText.text = text
    }

    fun setArrowIcon(@DrawableRes resId: Int) {
        if (resId != 0) {
            arrowIcon.visibility = View.VISIBLE
            arrowIcon.setImageResource(resId)
        } else {
            arrowIcon.visibility = View.GONE
        }
    }

    fun setAction1Icon(@DrawableRes resId: Int) {
        if (resId != 0) {
            action1Icon.visibility = View.VISIBLE
            action1Icon.setImageResource(resId)
        } else {
            action1Icon.visibility = View.GONE
        }
    }

    fun setAction2Icon(@DrawableRes resId: Int) {
        if (resId != 0) {
            action2Icon.visibility = View.VISIBLE
            action2Icon.setImageResource(resId)
        } else {
            action2Icon.visibility = View.GONE
        }
    }

    fun setArrowClickListener(listener: (View) -> Unit) {
        arrowView.setOnClickListener(listener)
    }

    fun setAction1ClickListener(listener: (View) -> Unit) {
        action1Icon.setOnClickListener(listener)
    }

    fun setAction2ClickListener(listener: (View) -> Unit) {
        action2Icon.setOnClickListener(listener)
    }

}