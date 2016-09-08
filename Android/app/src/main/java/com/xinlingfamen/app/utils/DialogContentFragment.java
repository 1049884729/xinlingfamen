package com.xinlingfamen.app.utils;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xinlingfamen.app.R;

/**
 * A simple {@link Fragment} subclass. Use the {@link DialogContentFragment#newInstance} factory method to create an
 * instance of this fragment.
 */
public abstract class DialogContentFragment extends DialogFragment implements DialogUtils.DialogOkDeal
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    
    // TODO: Rename and change types of parameters
    private String mParam1;
    
    public DialogContentFragment()
    {
        // Required empty public constructor
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            
        }
        setCancelable(false);

    }
    
    private TextView tv_Content;
    
    private View parentView;
    
    private Button button_cancel;
    
    private Button button_Ok;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_dialog_content, container, false);
        tv_Content = (TextView)parentView.findViewById(R.id.dialog_content);
        tv_Content.setText(Html.fromHtml(mParam1));
        button_cancel = (Button)parentView.findViewById(R.id.btn_cancel);
        button_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });
        button_Ok = (Button)parentView.findViewById(R.id.btn_ok);
        button_Ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                deal();
                dismiss();
            }
        });
        return parentView;
    }

}
