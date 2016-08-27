package com.xinlingfamen.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by xuff on 16-8-27.
 */

public class BaseFragment extends Fragment {
    protected Context mContext;
    protected View parentView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }
}
