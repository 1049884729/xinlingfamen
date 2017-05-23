package com.gobeike.radioapp.home;

import java.util.ArrayList;
import java.util.Arrays;

import com.gobeike.radioapp.R;
import com.gobeike.radioapp.config.Constants;
import com.gobeike.radioapp.view.MusicPlayView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xuff on 17-3-17. 主要功能模块的Fragment
 */

public class MusicFragment extends Fragment
{
    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    
    private static final String ARG_MUSIC_PATH = "music_path";
    
    public MusicFragment()
    {
    }
    
    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static MusicFragment newInstance(int sectionNumber)
    {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    private MusicPlayView musicPlayView;
    
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        musicPlayView = (MusicPlayView)rootView.findViewById(R.id.musicPlayView);
        // String[] arraylist=Constants.music_List_local;
        String[] arraylist = Constants.music_List;
        
        musicPlayView.setPlayList(new ArrayList<String>(Arrays.asList(arraylist)));
        musicPlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MusicDetailActivity.class));
            }
        });
        return rootView;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        musicPlayView.onResume();
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        musicPlayView.onPause();
        
    }
    
    // @Override
    // public void onStart() {
    // super.onStart();
    // EventBus.getDefault().register(this);
    // }
    //
    // @Override
    // public void onStop() {
    // super.onStop();
    // EventBus.getDefault().unregister(this);
    // }
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        ((MainActivity)activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
