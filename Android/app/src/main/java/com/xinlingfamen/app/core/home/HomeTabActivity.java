package com.xinlingfamen.app.core.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;

import com.xinlingfamen.app.BaseActivity;
import com.xinlingfamen.app.R;

/**
 * Tab
 */
public class HomeTabActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);
        showFragment();

    }
    //        FragmentTabHost

    private void showFragment(){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment,new WeiOrgNetFragment()).commitAllowingStateLoss();
    }
}
