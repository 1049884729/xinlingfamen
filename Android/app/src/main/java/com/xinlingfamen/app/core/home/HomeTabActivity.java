package com.xinlingfamen.app.core.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xinlingfamen.app.BaseActivity;
import com.xinlingfamen.app.R;

import java.util.List;

/**
 * Tab
 */
public class HomeTabActivity extends BaseActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);
        initView();
    }
    // FragmentTabHost
    
    private void hideFragments()
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        
        if (weiOrgNetFragment != null)
            fragmentTransaction.hide(weiOrgNetFragment);
        if (resourceFragment != null)
            fragmentTransaction.hide(resourceFragment);
        if (communityFragment != null)
            fragmentTransaction.hide(communityFragment);
        if (mineFragment != null)
            fragmentTransaction.hide(mineFragment);
        fragmentTransaction.commit();
    }
    
    private WeiOrgNetFragment weiOrgNetFragment;
    
    private ResourceFragment resourceFragment;
    
    private CommunityFragment communityFragment;
    
    private MineFragment mineFragment;
    
    private void showFragment(int value)
    {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments();
        
        switch (value)
        {
            case 0:
                weiOrgNetFragment = (WeiOrgNetFragment)fragmentManager.findFragmentByTag("weiOrgNetFragment");
                if (weiOrgNetFragment == null)
                {
                    weiOrgNetFragment = new WeiOrgNetFragment();
                    fragmentTransaction.add(R.id.main_fragment, weiOrgNetFragment, "weiOrgNetFragment");
                    
                }
                else
                {
                    fragmentTransaction.show(weiOrgNetFragment);
                }
                break;
            case 1:
                resourceFragment = (ResourceFragment)fragmentManager.findFragmentByTag("resourceFragment");
                if (resourceFragment == null)
                {
                    resourceFragment = new ResourceFragment();
                    fragmentTransaction.add(R.id.main_fragment, resourceFragment, "resourceFragment");
                    
                }
                else
                {
                    fragmentTransaction.show(resourceFragment);
                }
                
                break;
            case 2:
//                communityFragment = (CommunityFragment)fragmentManager.findFragmentByTag("communityFragment");
//                if (communityFragment == null)
//                {
//                    communityFragment = new CommunityFragment();
//                    fragmentTransaction.add(R.id.main_fragment, communityFragment, "communityFragment");
//
//                }
//                else
//                {
//                    fragmentTransaction.show(communityFragment);
//                }
//                break;
            case 3:
                mineFragment = (MineFragment)fragmentManager.findFragmentByTag("mineFragment");
                if (mineFragment == null)
                {
                    mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.main_fragment, mineFragment, "mineFragment");
                    
                }
                else
                {
                    fragmentTransaction.show(mineFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
        
    }
    
    private LinearLayout tablinearLayout;
    
    private int currentPosition = 0;
    
    @Override
    public void onAttachFragment(Fragment fragment)
    {
        super.onAttachFragment(fragment);
        try
        {
            weiOrgNetFragmentBack = (WeiOrgNetFragmentBack)fragment;
        }
        catch (Exception e)
        {
        }
    }
    
    private WeiOrgNetFragmentBack weiOrgNetFragmentBack = null;
    
    @Override
    public void onBackPressed()
    {
        /**
         * 如果当前Fragment为微官网的，且WebView没法返回时，才退出程序
         */
        if (currentPosition == 0 && weiOrgNetFragmentBack != null && !weiOrgNetFragmentBack.onBackPressed())
            return;
        super.onBackPressed();
    }
    
    private void initView()
    {
        tablinearLayout = (LinearLayout)findViewById(R.id.lin_bottom);
        int size = tablinearLayout.getChildCount();
        if (size > 0)
        {
            TextView childView;
            for (int i = 0; i < size; i++)
            {
                childView = (TextView)tablinearLayout.getChildAt(i);
                childView.setTag(i);
                childView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
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
    private void settabLinearViewsSelected()
    {
        int size = tablinearLayout.getChildCount();
        if (size > 0)
        {
            TextView childView;
            for (int i = 0; i < size; i++)
            {
                childView = (TextView)tablinearLayout.getChildAt(i);
                childView.setTag(i);
                if (currentPosition == i)
                {
                    showFragment(currentPosition);
                    childView.setTextColor(mContext.getResources().getColor(R.color.accent_1));
                }
                else
                {
                    childView.setTextColor(mContext.getResources().getColor(R.color.textUnselected));
                    
                }
            }
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0)
        {
            for (Fragment fragment : fragments)
            {
                try
                {
                    fragment.onActivityResult(requestCode, resultCode, data);
                }
                catch (Exception e)
                {
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
