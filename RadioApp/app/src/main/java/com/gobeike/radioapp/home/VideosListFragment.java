package com.gobeike.radioapp.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.gobeike.radioapp.R;
import com.gobeike.radioapp.home.dummy.DummyContent;
import com.gobeike.radioapp.home.dummy.DummyContent.DummyItem;
import com.gobeike.radioapp.home.dummy.MyVideosListRecyclerViewAdapter;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 视频列表
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener} interface.
 */
public class VideosListFragment extends Fragment
{
    
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    
    // TODO: Customize parameters
    private int mColumnCount = 1;
    
    private OnListFragmentInteractionListener mListener = new OnListFragmentInteractionListener()
    {
        @Override
        public void onListFragmentInteraction(DummyItem item)
        {
            Intent intent = new Intent();
            intent.putExtra("item", item);
            intent.setClass(getActivity(), VideoDetailActivity.class);
            startActivity(intent);
        }
    };
    
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment (e.g. upon screen orientation
     * changes).
     */
    public VideosListFragment()
    {
    }
    
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static VideosListFragment newInstance(int columnCount)
    {
        VideosListFragment fragment = new VideosListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        if (getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_videoslist_list, container, false);
        RecyclerViewHeader header = (RecyclerViewHeader)view.findViewById(R.id.header);
        
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.list);
        // set LayoutManager for your RecyclerView
        header.attachTo(recyclerView, true);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        
        // if (mColumnCount <= 1)
        // {
        // recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // }
        // else
        // {
        // recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        // }
        recyclerView.setAdapter(new MyVideosListRecyclerViewAdapter(DummyContent.ITEMS, mListener));


        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard)view. findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
        jcVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640"));

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
    /**
     * This interface must be implemented by activities that contain this fragment to allow an interaction in this
     * fragment to be communicated to the activity and potentially other fragments contained in that activity.
     * <p/>
     * See the Android Training lesson
     * <a href= "http://developer.android.com/training/basics/fragments/communicating.html" >Communicating with Other
     * Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
