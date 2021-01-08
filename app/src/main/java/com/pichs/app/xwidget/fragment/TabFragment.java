package com.pichs.app.xwidget.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pichs.app.xwidget.R;

/**
 * @Description:
 * @Author: 吴波
 * @CreateDate: 2021/1/8 16:12
 * @UpdateUser: 吴波
 * @UpdateDate: 2021/1/8 16:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TabFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
