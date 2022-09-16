package com.architecture.light.ui.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RelativeLayout
import com.android.architecture.extension.touch
import com.android.architecture.extension.valid
import com.architecture.light.R
import com.architecture.light.databinding.ViewNumberKeyboardBinding
import java.lang.reflect.Method

class NumberKeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding: ViewNumberKeyboardBinding by lazy {
        ViewNumberKeyboardBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val typedArray =
        context.obtainStyledAttributes(attrs, R.styleable.CustomKeyboardView, defStyleAttr, 0)


    init {
        initView()
    }

    private fun initView() {
        try {
            val showPoint =
                typedArray.getBoolean(R.styleable.CustomKeyboardView_show_point, true)
            if (!showPoint) {
                binding.point.text = ""
                binding.point.isEnabled = false
            } else {
                binding.point.text = "."
                binding.point.isEnabled = true
            }
            binding.n0.touch { inputText("0") }
            binding.n1.touch { inputText("1") }
            binding.n2.touch { inputText("2") }
            binding.n3.touch { inputText("3") }
            binding.n4.touch { inputText("4") }
            binding.n5.touch { inputText("5") }
            binding.n6.touch { inputText("6") }
            binding.n7.touch { inputText("7") }
            binding.n8.touch { inputText("8") }
            binding.n9.touch { inputText("9") }
            binding.point.touch { inputText(".") }
            binding.back.touch(true) { back() }
            binding.confirm.touch { confirm() }
        } finally {
            typedArray.recycle()
        }
    }

    private fun inputText(s: String) {
        editText?.append(s)
    }

    private fun back() {
        editText?.let {
            val text = it.text.toString()
            if (text.valid) {
                it.setText(text.substring(0, text.length - 1))
            }
        }
    }

    private fun confirm() {
        listener?.confirm()
    }

    private var listener: OnInputListener? = null
    private var editText: EditText? = null

    fun bindEditText(editText: EditText) {
        this.editText = editText
        disableShowInput(editText)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                editText.setSelection(editText.length())
                listener?.textChanged()
            }
        })
    }

    fun setOnInputListener(listener: OnInputListener) {
        this.listener = listener
    }

    fun setConfirmEnabled(enabled: Boolean) {
        if (enabled == binding.confirm.isEnabled) {
            return
        }
        if (enabled) {
            binding.confirm.isEnabled = true
            binding.confirm.alpha = 1f
        } else {
            binding.confirm.isEnabled = false
            binding.confirm.alpha = 0.5f
        }
    }

    interface OnInputListener {
        fun textChanged()
        fun confirm()
    }

    private fun disableShowInput(editText: EditText) {
        val cls = EditText::class.java
        val method: Method
        try {
            method = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
            method.isAccessible = true
            method.invoke(editText, false)
        } catch (e: Exception) {
        }
    }


}