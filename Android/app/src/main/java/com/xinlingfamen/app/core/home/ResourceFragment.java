package com.xinlingfamen.app.core.home;

import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.xinlingfamen.app.BaseFragment;
import com.xinlingfamen.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * 添加资源ExpandListView
 * 每次进入，扫描固定文件夹的目录的文件数，并存入数据库，一旦文件夹内容有更新，更新数据库；
 * 这里需要注册个系统广播进行监听文件的变化
 */
public class ResourceFragment extends BaseFragment
{
    
    public ResourceFragment()
    {
        // Required empty public constructor
    }
    
    private static final String KEY = "key";
    
    private List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    
    private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
    
    private PullToRefreshExpandableListView mPullRefreshListView;
    
    private SimpleExpandableListAdapter mAdapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_resource, container, false);
        mPullRefreshListView =
            (PullToRefreshExpandableListView)parentView.findViewById(R.id.pull_refresh_expandable_list);
        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ExpandableListView>()
        {
            @Override
            public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView)
            {
                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
        
        for (String group : mGroupStrings)
        {
            Map<String, String> groupMap1 = new HashMap<String, String>();
            groupData.add(groupMap1);
            groupMap1.put(KEY, group);
            
            List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
            for (String string : mChildStrings)
            {
                Map<String, String> childMap = new HashMap<String, String>();
                childList.add(childMap);
                childMap.put(KEY, string);
            }
            childData.add(childList);
        }
        
        mAdapter = new SimpleExpandableListAdapter(mContext, groupData, android.R.layout.simple_expandable_list_item_1,
            new String[] {KEY}, new int[] {android.R.id.text1}, childData,
            android.R.layout.simple_expandable_list_item_2, new String[] {KEY}, new int[] {android.R.id.text1});
        mPullRefreshListView.getRefreshableView().setAdapter(mAdapter);
        return parentView;
    }
    
    private class GetDataTask extends AsyncTask<Void, Void, String[]>
    {
        
        @Override
        protected String[] doInBackground(Void... params)
        {
            // Simulates a background job.
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
            }
            return mChildStrings;
        }
        
        @Override
        protected void onPostExecute(String[] result)
        {
            Map<String, String> newMap = new HashMap<String, String>();
            newMap.put(KEY, "Added after refresh...");
            groupData.add(newMap);
            
            List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
            for (String string : mChildStrings)
            {
                Map<String, String> childMap = new HashMap<String, String>();
                childMap.put(KEY, string);
                childList.add(childMap);
            }
            childData.add(childList);
            
            mAdapter.notifyDataSetChanged();
            
            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();
            
            super.onPostExecute(result);
        }
    }
    
    private String[] mChildStrings = {"Child One", "Child Two", "Child Three", "Child Four", "Child Five", "Child Six"};
    
    private String[] mGroupStrings = {"Group One", "Group Two", "Group Three"};
    
    private class ExpandableListViewAdapter implements ExpandableListAdapter
    {
        
        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver)
        {
            
        }
        
        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver)
        {
            
        }
        
        @Override
        public int getGroupCount()
        {
            return 0;
        }
        
        @Override
        public int getChildrenCount(int i)
        {
            return 0;
        }
        
        @Override
        public Object getGroup(int i)
        {
            return null;
        }
        
        @Override
        public Object getChild(int i, int i1)
        {
            return null;
        }
        
        @Override
        public long getGroupId(int i)
        {
            return 0;
        }
        
        @Override
        public long getChildId(int i, int i1)
        {
            return 0;
        }
        
        @Override
        public boolean hasStableIds()
        {
            return false;
        }
        
        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup)
        {
            return null;
        }
        
        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup)
        {
            return null;
        }
        
        @Override
        public boolean isChildSelectable(int i, int i1)
        {
            return false;
        }
        
        @Override
        public boolean areAllItemsEnabled()
        {
            return false;
        }
        
        @Override
        public boolean isEmpty()
        {
            return false;
        }
        
        @Override
        public void onGroupExpanded(int i)
        {
            
        }
        
        @Override
        public void onGroupCollapsed(int i)
        {
            
        }
        
        @Override
        public long getCombinedChildId(long l, long l1)
        {
            return 0;
        }
        
        @Override
        public long getCombinedGroupId(long l)
        {
            return 0;
        }
    }
    
    private class GroupBean
    {
        public String groupName;
        
        public int groupChildSize;
    }
    
    private class ChildBean
    {
        public String fileName, filePath;
        
        public String fileSize, downTime;
    }
}
