package com.xinlingfamen.app.core.home;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        initView();
    }
    //        FragmentTabHost

    private void hideFragments() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (weiOrgNetFragment != null) fragmentTransaction.hide(weiOrgNetFragment);
        if (resourceFragment != null) fragmentTransaction.hide(resourceFragment);
        if (communityFragment != null) fragmentTransaction.hide(communityFragment);
        if (mineFragment != null) fragmentTransaction.hide(mineFragment);
        fragmentTransaction.commit();
    }

    private WeiOrgNetFragment weiOrgNetFragment;
    private ResourceFragment resourceFragment;
    private CommunityFragment communityFragment;
    private MineFragment mineFragment;

    private void showFragment(int value) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments();

        switch (value) {
            case 0:
                weiOrgNetFragment = (WeiOrgNetFragment) fragmentManager.findFragmentByTag("weiOrgNetFragment");
                if (weiOrgNetFragment == null) {
                    weiOrgNetFragment = new WeiOrgNetFragment();
                    fragmentTransaction.add(R.id.main_fragment, weiOrgNetFragment, "weiOrgNetFragment");

                } else {
                    fragmentTransaction.show(weiOrgNetFragment);
                }
                break;
            case 1:
                resourceFragment = (ResourceFragment) fragmentManager.findFragmentByTag("resourceFragment");
                if (resourceFragment == null) {
                    resourceFragment = new ResourceFragment();
                    fragmentTransaction.add(R.id.main_fragment, resourceFragment, "resourceFragment");

                } else {
                    fragmentTransaction.show(resourceFragment);
                }

                break;
            case 2:
                communityFragment = (CommunityFragment) fragmentManager.findFragmentByTag("communityFragment");
                if (communityFragment == null) {
                    communityFragment = new CommunityFragment();
                    fragmentTransaction.add(R.id.main_fragment, communityFragment, "communityFragment");

                } else {
                    fragmentTransaction.show(communityFragment);
                }
                break;
            case 3:
                mineFragment = (MineFragment) fragmentManager.findFragmentByTag("mineFragment");
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.main_fragment, mineFragment, "mineFragment");

                } else {
                    fragmentTransaction.show(mineFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();

    }

    private LinearLayout tablinearLayout;
    private int currentPosition = 0;

    private void initView() {
        tablinearLayout = (LinearLayout) findViewById(R.id.lin_bottom);
        int size = tablinearLayout.getChildCount();
        if (size > 0) {
            TextView childView;
            for (int i = 0; i < size; i++) {
                childView = (TextView) tablinearLayout.getChildAt(i);
                childView.setTag(i);
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentPosition = Integer.valueOf(view.getTag().toString());
                        settabLinearViewsSelected();
                    }
                });
            }
        }
        settabLinearViewsSelected();
    }

    /**
     *
     */
    private void settabLinearViewsSelected() {
        int size = tablinearLayout.getChildCount();
        if (size > 0) {
            TextView childView;
            for (int i = 0; i < size; i++) {
                childView = (TextView) tablinearLayout.getChildAt(i);
                childView.setTag(i);
                if (currentPosition == i) {
                    showFragment(currentPosition);
                    childView.setTextColor(mContext.getResources().getColor(R.color.accent_1));
                } else {
                    childView.setTextColor(mContext.getResources().getColor(R.color.textUnselected));

                }
            }
        }
    }


}
