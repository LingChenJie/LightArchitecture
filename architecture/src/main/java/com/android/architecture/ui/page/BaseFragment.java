package com.android.architecture.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
    private final ViewModelScope mViewModelScope = new ViewModelScope();

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i(TAG, "----onViewCreated");
        initView();
        output();
        input();
    }

    protected void initView() {
    }

    protected void output() {
    }

    protected void input() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }

    private void addOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (!onBackPressed()) {
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    protected boolean onBackPressed() {
        return true;
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
