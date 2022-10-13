package com.android.architecture.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.android.architecture.helper.Logger;
import com.android.architecture.ui.scope.ViewModelScope;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/28
 * Modify date: 2022/7/28
 * Version: 1
 */
public abstract class BaseFragment<A extends BaseActivity> extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();
    protected A mActivity;
    private View mRootView;
    private final ViewModelScope mViewModelScope = new ViewModelScope();

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Logger.i(TAG, "----onAttach");
        mActivity = (A) requireActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(TAG, "----onCreate");
        addOnBackPressed();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Logger.i(TAG, "----onCreateView");
        mRootView = getRootView(inflater, container);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i(TAG, "----onViewCreated");
        initView();
        output();
        input();
    }

    protected abstract View getRootView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView();

    protected void input() {
    }

    protected void output() {
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.i(TAG, "----onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.i(TAG, "----onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.i(TAG, "----onHiddenChanged: hidden:" + hidden);
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.i(TAG, "----onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.i(TAG, "----onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
        Logger.i(TAG, "----onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i(TAG, "----onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.i(TAG, "----onDetach");
        mActivity = null;
    }

    public View getRootView() {
        return mRootView;
    }

    protected NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if (!onBackPressed()) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        }
    };

    private void addOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    protected void setBackPressed(boolean enabled) {
        if (onBackPressed()) {
            onBackPressedCallback.setEnabled(enabled);
        }
    }

    protected boolean onBackPressed() {
        return false;
    }


    protected <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getFragmentScopeViewModel(this, modelClass);
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getActivityScopeViewModel(mActivity, modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getApplicationScopeViewModel(modelClass);
    }


}
