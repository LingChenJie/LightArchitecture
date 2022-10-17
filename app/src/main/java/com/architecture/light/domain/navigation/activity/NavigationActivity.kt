package com.architecture.light.domain.navigation.activity

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.android.architecture.helper.Logger
import com.android.architecture.ui.page.action.FragmentAction
import com.architecture.light.app.AppActivityForNavigationAction
import com.architecture.light.databinding.ActivityNavigationBinding
import java.util.*

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class NavigationActivity : AppActivityForNavigationAction(), FragmentAction {

    private val binding: ActivityNavigationBinding by lazy {
        ActivityNavigationBinding.inflate(layoutInflater)
    }
    private val fragmentBackStack = ArrayDeque<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            val stack = savedInstanceState.getStringArray("FRAGMENT_BACK_STACK")
            Logger.e(TAG, "fragmentBackStack:${stack.contentToString()}")
            stack?.forEach { item ->
                fragmentBackStack.add(item)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val stack = arrayOfNulls<String>(fragmentBackStack.size)
        for ((index, item) in fragmentBackStack.withIndex()) {
            stack[index] = item
        }
        Logger.e(TAG, "fragmentBackStack:${stack.contentToString()}")
        outState.putStringArray("FRAGMENT_BACK_STACK", stack)
    }


    override fun initView() {
        setContentView(binding.root)
    }

    override fun getBackStack(): ArrayDeque<String> {
        return fragmentBackStack
    }

    override fun getStackFragmentId(): Int {
        return binding.container.id
    }

    override fun getStackFragmentManager(): FragmentManager {
        return supportFragmentManager
    }

}