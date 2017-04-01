package com.gobeike.radioapp.home;

import com.gobeike.radioapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass. Use the {@link VideoDetailFragment#newInstance} factory method to create an
 * instance of this fragment.
 */
public class VideoDetailFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    
    private static final String ARG_PARAM2 = "param2";
    
    // TODO: Rename and change types of parameters
    private String mParam1;
    
    private String mParam2;
    
    public VideoDetailFragment()
    {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoPlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoDetailFragment newInstance(String param1, String param2)
    {
        VideoDetailFragment fragment = new VideoDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    
    /**
     * 考虑视频全屏怎么处理
     * 
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_play, container, false);
    }
    
}