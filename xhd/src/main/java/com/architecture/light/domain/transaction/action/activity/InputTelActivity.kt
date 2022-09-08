package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.databinding.ActivityInputTelBinding
import com.architecture.light.domain.transaction.action.ActionInputTel

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class InputTelActivity : AppActivityForAction() {

    private val binding: ActivityInputTelBinding by lazy {
        ActivityInputTelBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.keyboard.n0.click { inputText("0") }
        binding.keyboard.n1.click { inputText("1") }
        binding.keyboard.n2.click { inputText("2") }
        binding.keyboard.n3.click { inputText("3") }
        binding.keyboard.n4.click { inputText("4") }
        binding.keyboard.n5.click { inputText("5") }
        binding.keyboard.n6.click { inputText("6") }
        binding.keyboard.n7.click { inputText("7") }
        binding.keyboard.n8.click { inputText("8") }
        binding.keyboard.n9.click { inputText("9") }
        binding.keyboard.back.click { back() }
        binding.keyboard.clear.click { clear() }
        binding.keyboard.confirm.click { confirm() }
    }

    private fun inputText(text: String) {
        binding.tvTel.text = binding.tvTel.text.toString() + text
    }

    private fun back() {
        val text = binding.tvTel.text.toString()
        if (text.valid) {
            binding.tvTel.text = text.substring(0, text.length - 1)
        }
    }

    private fun clear() {
        binding.tvTel.text = ""
    }

    private fun confirm() {
        val tel = binding.tvTel.text.toString()
        if (tel.valid) {
            val telInfo = ActionInputTel.TelInfo(tel)
            finish(ActionResult(ErrorCode.SUCCESS, telInfo))
        }
    }

}