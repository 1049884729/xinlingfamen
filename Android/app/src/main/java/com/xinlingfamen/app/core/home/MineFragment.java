package com.xinlingfamen.app.core.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xinlingfamen.app.BaseFragment;
import com.xinlingfamen.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {


    public MineFragment() {
        // Required empty public constructor
    }


    private ListView listview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView= inflater.inflate(R.layout.fragment_mine, container, false);
        listview= (ListView) parentView.findViewById(R.id.listview);
        return parentView;
    }



    private class ListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=LayoutInflater.from(mContext).inflate(R.layout.item_listview,null);
            TextView tvName= (TextView) view.findViewById(R.id.tv_Name);
            return tvName;
        }
    }

}
