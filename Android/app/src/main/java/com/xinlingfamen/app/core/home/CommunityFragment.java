package com.xinlingfamen.app.core.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinlingfamen.app.BaseFragment;
import com.xinlingfamen.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends BaseFragment {


    public CommunityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

}