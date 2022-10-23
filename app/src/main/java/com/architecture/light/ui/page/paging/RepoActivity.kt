package com.architecture.light.ui.page.paging

import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.android.architecture.extension.toast
import com.android.architecture.ui.page.BaseState
import com.architecture.light.app.AppActivity
import com.architecture.light.data.remote.bean.Repo
import com.architecture.light.data.repository.RepoRepository
import com.architecture.light.databinding.ActivityRepoBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Created by SuQi on 2022/10/23.
 * Describe:
 */
class RepoActivity : AppActivity() {

    private val binding: ActivityRepoBinding by binding()
    private val state by viewModels<State>()
    private val repoAdapter = RepoAdapter()

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.backView.click { finish() }
        lifecycleScope.launch {
            state.getPagingData().collect {
                repoAdapter.submitData(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = repoAdapter
        repoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.recyclerView.visibility = View.GONE
                    toast("Load Error: ${state.error.message}")
                }
            }
        }
    }

    class State : BaseState() {
        fun getPagingData(): Flow<PagingData<Repo>> {
            return RepoRepository.getPagingData().cachedIn(viewModelScope)
        }
    }

}