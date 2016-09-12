package com.xinlingfamen.app.core.home;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.xinlingfamen.app.BaseFragment;
import com.xinlingfamen.app.R;
import com.xinlingfamen.app.core.modules.ChildBean;
import com.xinlingfamen.app.core.modules.GroupBean;
import com.xinlingfamen.app.utils.FilesUtils;
import com.xinlingfamen.app.utils.StringUtils;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. 添加资源ExpandListView 每次进入，扫描固定文件夹的目录的文件数，并存入数据库，一旦文件夹内容有更新，更新数据库；
 * 这里需要注册个系统广播进行监听文件的变化
 */
public class ResourceFragment extends BaseFragment
{
    
    public ResourceFragment()
    {
        // Required empty public constructor
    }
    
    private List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    
    private List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
    
    private PullToRefreshExpandableListView mPullRefreshListView;
    
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
                initDataFromLocalDirs();
            }
        });
        
//        mPullRefreshListView.getRefreshableView().setOnChildClickListener(new ExpandableListView.OnChildClickListener()
//        {
//            @Override
//            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l)
//            {
//              ExpandableListViewAdapter expandableListViewAdapter= (ExpandableListViewAdapter) expandableListView.getExpandableListAdapter();
//                ChildBean childBean = (ChildBean)expandableListViewAdapter.getChild(i, i1);
//                Toast.makeText(mContext,childBean.fileName,Toast.LENGTH_LONG).show();
//                /**
//                 * 0 :file 1:mp3 2:video
//                 */
//
//                return true;
//
//            }
//
//        });
        initDataFromLocalDirs();
        return parentView;
        
    }
    
    private void initDataFromLocalDirs()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (getActivity().checkSelfPermission(WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
            }
            else
            {
                new GetDataTask().execute();
            }
        }
        else
        {
            new GetDataTask().execute();
        }
        
    }
    
    private static final int REQUEST_WRITE = 0;
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE)
        {
            int size = permissions.length;
            for (int grant = 0; grant < size; grant++)
            {
                if (grantResults[grant] == PackageManager.PERMISSION_GRANTED)
                {
                    new GetDataTask().execute();
                }
                else
                {
                    Toast.makeText(mContext, "没有权限使用存储卡", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    
    private class GetDataTask extends AsyncTask<Void, Void, Void>
    {
        
        @Override
        protected Void doInBackground(Void... params)
        {
            // Simulates a background job.
            File destDir =
                new File(Environment.getExternalStorageDirectory() + File.separator + FilesUtils.FOLDER_NAME);
            if (FilesUtils.isDirs(destDir))
            {
                File[] files = destDir.listFiles();
                if (mGroupBeans != null)
                    mGroupBeans.clear();
                mGroupBeans = new ArrayList<>();
                if (mChildBeans != null)
                    mChildBeans.clear();
                mChildBeans = new ArrayList<>();
                GroupBean groupBean = null;
                for (File file : files)
                {
                    ArrayList<ChildBean> childBeanArrayList = null;
                    int fileType = 0;// 文件类型，用于ChildBean
                    if (file.getPath().endsWith(FilesUtils.DOWNLOAD_FILE))
                    {
                        groupBean = new GroupBean();
                        fileType = 0;
                        groupBean.groupName = "文本文件";
                        groupBean.localPath = "(路径：" + FilesUtils.DOWNLOAD_FILE + ")";
                        childBeanArrayList = addChild(file, fileType);
                        groupBean.groupChildSize = childBeanArrayList == null ? 0 : childBeanArrayList.size();
                        mGroupBeans.add(groupBean);
                        mChildBeans.add(childBeanArrayList);
                        
                    }
                    if (file.getPath().endsWith(FilesUtils.DOWNLOAD_MP3))
                    {
                        groupBean = new GroupBean();
                        groupBean.groupName = "音频文件";
                        groupBean.localPath = "(路径：" + FilesUtils.DOWNLOAD_MP3 + ")";
                        fileType = 1;
                        childBeanArrayList = addChild(file, fileType);
                        groupBean.groupChildSize = childBeanArrayList == null ? 0 : childBeanArrayList.size();
                        mGroupBeans.add(groupBean);
                        mChildBeans.add(childBeanArrayList);
                        
                    }
                    if (file.getPath().endsWith(FilesUtils.DOWNLOAD_VIDEO))
                    {
                        groupBean = new GroupBean();
                        groupBean.groupName = "视频文件";
                        groupBean.localPath = "(路径：" + FilesUtils.DOWNLOAD_VIDEO + ")";
                        
                        fileType = 2;
                        childBeanArrayList = addChild(file, fileType);
                        groupBean.groupChildSize = childBeanArrayList == null ? 0 : childBeanArrayList.size();
                        mGroupBeans.add(groupBean);
                        mChildBeans.add(childBeanArrayList);
                    }
                }
            }
            return null;
        }
        
        private ArrayList<ChildBean> addChild(File file, int fileType)
        {
            ArrayList<ChildBean> childBeanArrayList = new ArrayList<>();
            ChildBean childBean = null;
            
            for (File childFile : file.listFiles())
            {
                childBean = new ChildBean();
                childBean.filePath = childFile.getPath();
                childBean.type = fileType;
                childBean.fileName = childFile.getName();
                childBean.downTime = StringUtils.longToString(childFile.lastModified());
                childBean.fileSize = StringUtils.longToSize(childFile.length());
                childBeanArrayList.add(childBean);
            }
            return childBeanArrayList;
        }
        
        @Override
        protected void onPostExecute(Void result)
        {
            
            mPullRefreshListView.getRefreshableView().setAdapter(new ExpandableListViewAdapter());
            
            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();
            
            super.onPostExecute(result);
        }
    }
    
    private ArrayList<ArrayList<ChildBean>> mChildBeans = null;
    
    private ArrayList<GroupBean> mGroupBeans = null;
    
    private class ExpandableListViewAdapter extends BaseExpandableListAdapter
    {
        
        @Override
        public int getGroupCount()
        {
            if (mGroupBeans == null)
                return 0;
            return mGroupBeans.size();
        }
        
        @Override
        public int getChildrenCount(int i)
        {
            if (mChildBeans == null)
                return 0;
            return mChildBeans.get(i).size();
        }
        
        @Override
        public Object getGroup(int i)
        {
            return mGroupBeans.get(i);
        }
        
        @Override
        public Object getChild(int i, int i1)
        {
            ArrayList arrayList = mChildBeans.get(i);
            if (arrayList == null || arrayList.size() == 0)
                return null;
            return arrayList.get(i1);
        }
        
        @Override
        public long getGroupId(int i)
        {
            return i;
        }
        
        @Override
        public long getChildId(int i, int i1)
        {
            return i1;
        }
        
        @Override
        public boolean hasStableIds()
        {
            return true;
        }
        
        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_expandlist_group_item, null);
            TextView groupName = (TextView)view.findViewById(R.id.groupName);
            TextView groupSize = (TextView)view.findViewById(R.id.groupfile_size);
            TextView groupLocalPath = (TextView)view.findViewById(R.id.groupLocalPath);
            GroupBean groupBean = (GroupBean)getGroup(i);
            groupName.setText(groupBean.groupName);
            groupLocalPath.setText(groupBean.localPath);
            groupSize.setText(groupBean.groupChildSize + "个");
            return view;
        }
        
        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_expandlist_child_item, null);
            TextView fileName = (TextView)view.findViewById(R.id.fileName);
            TextView filePath = (TextView)view.findViewById(R.id.file_path);
            TextView file_time = (TextView)view.findViewById(R.id.file_time);
          final   ChildBean childBean = (ChildBean)getChild(i, i1);
            fileName.setText(childBean.fileName);
            filePath.setText(childBean.fileSize);
            file_time.setText(childBean.downTime);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromFile(new File(childBean.filePath));
                        if (childBean.type == 0)
                        {
                            if (childBean.fileName.toLowerCase().endsWith(".pdf"))
                            {
                                intent.setDataAndType(uri, "application/pdf");
                            }
                            else
                            {
                                intent.setDataAndType(uri, "text/plain");
                            }
                        }
                        else if (childBean.type == 1)
                        {
                            intent.setDataAndType(uri, "audio/*");
                        }
                        else if (childBean.type == 2)
                        {
                            intent.setDataAndType(uri, "video/*");
                        }
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(mContext,"请确认一下本地是否有相应的程序能打开此文件\n 如果没有，请下载一个",Toast.LENGTH_LONG).show();
                    }
                }
            });
            return view;
        }
        
        @Override
        public boolean isChildSelectable(int i, int i1)
        {
            return true;
        }
        
    }
    
}
