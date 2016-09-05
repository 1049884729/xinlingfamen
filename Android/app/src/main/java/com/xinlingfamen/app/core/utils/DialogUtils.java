package com.xinlingfamen.app.core.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.xinlingfamen.app.R;

/**
 * &lt;对话框&gt; &lt;功能详细描述&gt;
 *
 * @author xuff
 * @version [版本号, 2016/3/4]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DialogUtils
{
    public static Dialog getLoadingDialog(Context context, String title, boolean canCancel)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(title);
        try
        {
            progressDialog.setMessage(context.getResources().getString(R.string.loading_data));
        }
        catch (Resources.NotFoundException e)
        {
        }
        progressDialog.setCanceledOnTouchOutside(canCancel);
        return progressDialog;
    }
    
    public static Dialog getLoadingDialog(Context context)
    {
        return DialogUtils.getLoadingDialog(context, null, true);
    }
    
    /**
     * Dialog 封装
     *
     * @param context
     * @param theme
     * @param layoutResID
     * @return mDialog
     */
    public static Dialog initDialog(Context context, int theme, int layoutResID)
    {
        Dialog mDialog = new Dialog(context, theme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(layoutResID);
        mDialog.setCanceledOnTouchOutside(false);// 点击其他区域dialog消失
        // mDialog.setTitle(titleName);
        return mDialog;
    }
    
    public static ProgressDialog initProgressDialog(Context context)
    {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(context.getResources().getString(R.string.loading_data));
        
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }
    
    /**
     * ProgressDialog 封装
     *
     * @param context
     * @param message
     * @return mProgressDialog
     */
    public static ProgressDialog initProgressDialog(Context context, String message)
    {
        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        return mProgressDialog;
    }
    
    public interface DialogOkDeal
    {
        void deal();
    }
    
    public static void dialogFragment(Context context, String content,
        final DialogOkDeal dialogOkDeal)
    {
        AlertDialog.Builder alertDialog= new AlertDialog.Builder(context);
        alertDialog.setCancelable(true);
        alertDialog.setTitle("提示");
        alertDialog.setMessage(content);
        alertDialog.setNegativeButton(R.string.umeng_common_action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setPositiveButton(R.string.umeng_common_action_Ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogOkDeal.deal();
                dialogInterface.dismiss();
            }
        });
        alertDialog.create().show();
//        DialogContentFragment fragment = new DialogContentFragment()
//        {
//            @Override
//            public void deal()
//            {
//                dialogOkDeal.deal();
//            }
//        };
//        Bundle args = new Bundle();
//        args.putString(DialogContentFragment.ARG_PARAM1, cantent);
////        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.fragment_dialog_style);
//        fragment.setCancelable(true);
//        try {
//            if (fragment != null && !fragmentManager.isDestroyed())
//                fragment.show(fragmentManager, cantent);
//        } catch (Exception e) {
//
//        }
       
        
    }
    
}
