package com.solution.brothergroup.DMTWithPipe.customView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.DMTWithPipe.Adapter.DMTOptionListAdapter;
import com.solution.brothergroup.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DMTCustomAlertDialog {
    private Context context;
    private SweetAlertDialog alertDialog;
    boolean isScreenOpen;
    AlertDialog alertDialogLogout;
    private AlertDialog alertDialogServiceList;
    private AlertDialog alertDialogSendReport;

    public DMTCustomAlertDialog(Context context, boolean isScreenOpen) {
        try {
            this.context = context;
            this.isScreenOpen = isScreenOpen;
            alertDialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                    TextView text = (TextView) alertDialog.findViewById(R.id.content_text);
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    text.setSingleLine(false);


                }
            });
        } catch (IllegalStateException ise) {

        } catch (Exception e) {

        }
    }

    public void dmtListDialog(String title, final ArrayList<OperatorList> opTypes, final DialogDMTListCallBack mDialogDMTListCallBack) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_service_list, null);
            alertDialogServiceList.setView(dialogView);

            ImageView iconTv = dialogView.findViewById(R.id.icon);
            ImageView bgView = dialogView.findViewById(R.id.bgView);
            RelativeLayout imageContainer = dialogView.findViewById(R.id.imageContainer);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context, opTypes.size() > 2 ? 3 : 2));
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);

            titleTv.setText(title);
            //   iconTv.setImageResource(ServiceIcon.INSTANCE.parentIcon(parentId));

            DMTOptionListAdapter mDMTOptionListAdapter = new DMTOptionListAdapter(opTypes, (Activity) context, new DMTOptionListAdapter.ClickView() {
                @Override
                public void onClick(OperatorList mOperatorList) {
                    alertDialogServiceList.dismiss();
                    if (mDialogDMTListCallBack != null) {
                        mDialogDMTListCallBack.onIconClick(mOperatorList);
                    }
                }



            }, R.layout.adapter_dashboard_option_grid);
            recyclerView.setAdapter(mDMTOptionListAdapter);

            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                }
            });


            alertDialogServiceList.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }


    public interface DialogDMTListCallBack {
        void onIconClick(OperatorList mOperatorList);
    }
}
