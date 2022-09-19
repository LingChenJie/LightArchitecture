package com.architecture.light.ui.page.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.architecture.light.app.AppActivity
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.databinding.ActivityProjectChooseBinding
import com.architecture.light.settings.ProjectCache
import com.architecture.light.ui.adapter.ProjectChooseAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ChooseProjectActivity : AppActivity() {

    private val binding: ActivityProjectChooseBinding by lazy {
        ActivityProjectChooseBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { ProjectChooseAdapter() }
    private var project: LoginResponse.Data.Project? = null

    override fun initView() {
        setContentView(binding.root)
        adapter.setData(ProjectCache.getProjectList())
        binding.recyclerView.adapter = adapter
        adapter.setItemClickListener { _, _, item ->
            project = item
            binding.btConfirm.isEnabled = true
        }
        binding.titleView.backView.click { finish() }
        binding.btCancel.click { finish() }
        binding.btConfirm.click {
            if (project != null) {
                ProjectCache.saveProject(project!!)
                finish()
            }
        }
        binding.btConfirm.isEnabled = false
    }

}