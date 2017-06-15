package com.gobeike.radioapp.home;

import com.gobeike.radioapp.R;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    
    private FragmentManager fragmentManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        /**
         * 选中第一个默认
         */
        
        navigationView.getMenu().getItem(0).setChecked(true);
        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
    }
    
    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }
    
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (fragmentManager == null)
            fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        
        if (id == R.id.nav_camera)
        {
            // Handle the camera action
            
            musicFragment = (MusicFragment)fragmentManager.findFragmentByTag("MusicFragment");
            if (musicFragment == null)
            {
                transaction.replace(R.id.container, MusicFragment.newInstance(1), "MusicFragment");
            }
            else
            {
                transaction.replace(R.id.container, musicFragment, "MusicFragment");
            }
            
            transaction.commitNowAllowingStateLoss();
        }
        else if (id == R.id.nav_gallery)
        {
            videosListFragment = (VideosListFragment)fragmentManager.findFragmentByTag("videosListFragment");
            if (videosListFragment == null)
            {
                transaction.replace(R.id.container, VideosListFragment.newInstance(8), "videosListFragment");
            }
            else
            {
                transaction.replace(R.id.container, videosListFragment, "videosListFragment");
            }
            transaction.commitNowAllowingStateLoss();
            
        }
        else if (id == R.id.nav_slideshow)
        {
            
        }
        else if (id == R.id.nav_share)
        {
            
        }
        else if (id == R.id.nav_exit)
        {
            return true;
        }
        
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        getSupportActionBar().setTitle(item.getTitle());
        
        return true;
    }
    
    private MusicFragment musicFragment;
    
    private VideosListFragment videosListFragment;
    
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
