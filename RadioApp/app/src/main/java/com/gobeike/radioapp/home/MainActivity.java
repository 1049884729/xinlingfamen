package com.gobeike.radioapp.home;

import com.gobeike.radioapp.R;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks
{
    
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    
    private String[] titleArray = null;
    
    private FragmentManager fragmentManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        titleArray = getResources().getStringArray(R.array.title_menus);
        setContentView(R.layout.activity_main);
        initPermission();

        mNavigationDrawerFragment =
            (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout)findViewById(R.id.drawer_layout));
    }
    
    private MusicFragment musicFragment;
    
    private VideosListFragment videosListFragment;
    
    @Override
    public void onNavigationDrawerItemSelected(int position)
    {
        // update the main content by replacing fragments
        if (fragmentManager == null)
            fragmentManager = getSupportFragmentManager();
        
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (position)
        {
            case 0:
                musicFragment = (MusicFragment)fragmentManager.findFragmentByTag("MusicFragment");
                if (musicFragment == null)
                {
                    transaction.replace(R.id.container, MusicFragment.newInstance(position + 1), "MusicFragment");
                }
                else
                {
                    transaction.replace(R.id.container, musicFragment, "MusicFragment");
                }
                break;
            case 1:
                videosListFragment = (VideosListFragment)fragmentManager.findFragmentByTag("videosListFragment");
                if (videosListFragment == null)
                {
                    transaction.replace(R.id.container, VideosListFragment.newInstance(8), "videosListFragment");
                }
                else
                {
                    transaction.replace(R.id.container, videosListFragment, "videosListFragment");
                }
                break;
            case 2:
                break;
        }
        transaction.commitNowAllowingStateLoss();
    }
    
    public void onSectionAttached(int number)
    {
        mTitle = titleArray[number - 1];
    }
    
    private int REQUEST_PERMISSION = 0x11;
    
    private void initPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int grant = checkCallingPermission(Manifest.permission_group.STORAGE);
            if (grant != PermissionChecker.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission_group.STORAGE, Manifest.permission.INTERNET},
                    REQUEST_PERMISSION);
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION)
        {
            boolean resultfinal = false;
            for (int i = 0; i < grantResults.length; i++)
            {
                int result = grantResults[i];
                if (result != PermissionChecker.PERMISSION_GRANTED)
                {
                    String tip = null;
                    if (permissions[i] == Manifest.permission_group.STORAGE)
                    {
                        tip = "读取存储卡权限";
                    }
                    if (permissions[i] == Manifest.permission.INTERNET)
                    {
                        tip = "访问网络的权限";
                    }
                    Toast.makeText(MainActivity.this, "没有" + tip + "\n 请授权后在打开应用", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
        
    }
    
}
