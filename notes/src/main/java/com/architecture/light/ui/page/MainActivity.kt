package com.architecture.light.ui.page

import android.graphics.Color
import android.view.View
import com.android.architecture.extension.*
import com.android.architecture.helper.CacheHelper
import com.android.architecture.helper.Logger
import com.architecture.light.app.AppActivity
import com.architecture.light.constant.Constant
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.ui.page.mvi.NotesActivity
import com.github.ihsg.patternlocker.OnPatternChangeListener
import com.github.ihsg.patternlocker.PatternLockerView

class MainActivity : AppActivity() {

    companion object{
        const val KEY_PWD = "KEY_PWD"
    }

    private val binding: ActivityMainBinding by binding()
    private var isLogon = false
    private var drawNum = 0
    private var firstDrawContent = ""


    private val onPatternChangeListener = object : OnPatternChangeListener {
        override fun onStart(view: PatternLockerView) {
        }

        override fun onChange(view: PatternLockerView, hitIndexList: List<Int>) {
        }

        override fun onClear(view: PatternLockerView) {
        }

        override fun onComplete(view: PatternLockerView, hitIndexList: List<Int>) {
            Logger.d(Constant.TAG, "onComplete:$hitIndexList")
            drawNum++
            if (isLogon) {
                val drawContent = hitIndexList.toString()
                val pwd = CacheHelper.getString(KEY_PWD)
                if (drawContent == pwd) {
                    openActivity<NotesActivity>()
                    finish()
                } else {
                    toast("解锁失败，请重试")
                }
            } else {
                if (drawNum == 1) {
                    firstDrawContent = hitIndexList.toString()
                } else if (drawNum == 2) {
                    drawNum = 0
                    val secondDrawContent = hitIndexList.toString()
                    if (firstDrawContent == secondDrawContent) {
                        CacheHelper.saveString(KEY_PWD, firstDrawContent)
                        toast("设置解锁图案成功")
                    } else {
                        toast("与上次绘制不一致，请重新绘制")
                    }
                }
                updateUI()
            }
        }

    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.apply {
            backIcon.visibility = View.GONE
        }
        binding.patternLockView.setOnPatternChangedListener(onPatternChangeListener)
        binding.tvResetPwd.click {
            drawNum = 0
            CacheHelper.saveString(KEY_PWD, "")
            updateUI()
        }
    }

    override fun initData() {
        updateUI()
    }

    private fun updateUI() {
        val pwd = CacheHelper.getString(KEY_PWD)
        if (pwd.empty) {
            isLogon = false
            binding.tvResetPwd.visibility = View.INVISIBLE
            if (drawNum == 0) {
                binding.tvPromptInfo.setTextColor(Color.parseColor("#B3000000"))
                binding.tvPromptInfo.text = "设置解锁图案"
            } else {
                binding.tvPromptInfo.setTextColor(Color.parseColor("#FA8072"))
                binding.tvPromptInfo.text = "请再次绘制解锁图案"
            }
        } else {
            isLogon = true
            binding.tvResetPwd.visibility = View.VISIBLE
            binding.tvPromptInfo.setTextColor(Color.parseColor("#2196F3"))
            binding.tvPromptInfo.text = "请绘制解锁图案进入"
        }
    }

}