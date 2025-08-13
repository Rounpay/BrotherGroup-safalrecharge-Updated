package com.solution.brothergroup.Util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.solution.brothergroup.Activities.UpdateProfileActivity;
import com.solution.brothergroup.Api.Object.AreaMaster;
import com.solution.brothergroup.Api.Object.AssignedOpType;
import com.solution.brothergroup.Api.Response.FosAccStmtAndCollReportResponse;
import com.solution.brothergroup.AppUser.Activity.VoucherEntryActivity;
import com.solution.brothergroup.AppUser.Adapter.ChannelAreaListAdapter;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.Fragments.Adapter.HomeOptionListAdapter;
import com.solution.brothergroup.R;
import com.solution.brothergroup.usefull.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.pedant.SweetAlert.SweetAlertDialog;



public class CustomAlertDialog {

    boolean isScreenOpen;
    AlertDialog alertDialogLogout;
    private Activity context;
    private SweetAlertDialog alertDialog;
    private AlertDialog alertDialogSendReport;
    private AlertDialog alertDialogServiceList;

    public CustomAlertDialog(Activity context, boolean isScreenOpen) {
        try {
            this.context = context;
            this.isScreenOpen = isScreenOpen;
            alertDialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
                    TextView text = alertDialog.findViewById(R.id.content_text);
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    text.setSingleLine(false);


                }
            });
       /* TextView text =  alertDialog.findViewById(R.id.content_text);
        text.setGravity(Gravity.CENTER);
        //text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setSingleLine(false);
        text.setMaxLines(10);
        text.setLines(6);*/
        } catch (IllegalStateException ise) {

        } catch (Exception e) {

        }

    }

    public void ErrorWithSingleBtnCallBack(final String title, final String message, final String btnTxt,
                                           boolean isCancelable, final DialogCallBack dialogCallBack) {

        try {

            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setCancelable(isCancelable);

            alertDialog.setConfirmButton(btnTxt, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                if (dialogCallBack != null) {
                    dialogCallBack.onPositiveClick();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void Failed(final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if (message != null && !message.isEmpty() && message.length() > 1) {
                    alertDialog.setContentText(message);
                } else {
                    alertDialog.setContentText("Failed");
                }

                // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void SuccessfulWithTitle(final String title, final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setTitle(title);
                alertDialog.setContentText(message);
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void Successful(final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                if (message != null && !message.isEmpty() && message.length() > 1) {
                    alertDialog.setContentText(message);
                } else {
                    alertDialog.setContentText("Success");
                }
                 alertDialog.setCustomImage(R.drawable.ic_successsvg);
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }


    public void SuccessfulWithFinsh(final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity) context).finish();
                    }
                });
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void SuccessfulWithFinsh(final String message, boolean isCancelable) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setContentText(message);
                alertDialog.setCancelable(isCancelable);
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity) context).finish();
                    }
                });
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }
    public void SuccessfulWithCallBack(final String title, final String message, final String btnTxt,
                                       boolean isCancel, DialogCallBack mDialogCallBack) {

        try {
            alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setTitle(title);
            alertDialog.setCancelable(isCancel);
            // alertDialog.setCustomImage(R.drawable.ic_success);
            alertDialog.setConfirmButton(btnTxt, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick();
                }
            });
            alertDialog.setCancelButton("Cancel", sweetAlertDialog -> sweetAlertDialog.dismiss());
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }

    public void SuccessfulWithCallBack(final String message, final Activity activity) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        activity.finish();
                    }
                });
                alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                    }
                });
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void ExitWithCallBack(final String message, final Activity activity) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton("Exit", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        activity.finish();
                    }
                });
                alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                    }
                });
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void showMessage(final String title, final String message, int id, final int flag) {
        if (isScreenOpen) {
            try {
                if (title != null && !title.isEmpty()) {
                    alertDialog.setTitle(title);
                } else {
                    alertDialog.setTitle("Attention!");
                }
                if (message != null && !message.isEmpty() && message.length() > 1) {
                    alertDialog.setContentText(message);
                } else {
                    alertDialog.setContentText("Failed");
                }
                alertDialog.setCustomImage(id);
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        //1 For Update profile
                        if (flag == 1) {
                            context.startActivity(new Intent(context, UpdateProfileActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        }
                        sweetAlertDialog.cancel();
                    }
                });
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void sendReportDialog(final int type,String phoneNum, final DialogSingleCallBack mDialogSingleCallBack) {
        try {
            if (alertDialogSendReport != null && alertDialogSendReport.isShowing()) {
                return;
            }

           /* type = 1 //Recharge Report
            type = 2 // Bank List
            type = 3 // Call Back Request*/
            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogSendReport = dialogBuilder.create();
            alertDialogSendReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_send_report, null);
            alertDialogSendReport.setView(dialogView);

            final AppCompatEditText mobileEt = dialogView.findViewById(R.id.mobileEt);

            final AppCompatEditText emailEt = dialogView.findViewById(R.id.emailEt);
            TextView emailTitleTv= dialogView.findViewById(R.id.emailTitleTv);
            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView sendBtn = dialogView.findViewById(R.id.sendBtn);
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);
            if(phoneNum!=null ){
                mobileEt.setText(phoneNum);
            }
            if (type == 1) {
                titleTv.setText("Send Report");
            }
            if (type == 2) {
                titleTv.setText("Send Bank detail");
            }
            if (type == 3) {
                emailEt.setVisibility(View.GONE);
                emailTitleTv.setVisibility(View.GONE);
                titleTv.setText("Call Back Request");
            }




            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });

            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mobileEt.getText().toString().isEmpty()) {
                        mobileEt.setError("Please Enter Valid Mobile Number");
                        mobileEt.requestFocus();
                        return;
                    } else if (mobileEt.getText().toString().length() != 10) {
                        mobileEt.setError("Please Enter Valid Mobile Number");
                        mobileEt.requestFocus();
                        return;
                    } else if (type != 3&&emailEt.getText().toString().isEmpty()) {
                        emailEt.setError("Please Enter Valid Email Id");
                        emailEt.requestFocus();
                        return;
                    } else if (type != 3&&!emailEt.getText().toString().contains("@") || type != 3&&!emailEt.getText().toString().contains(".")) {
                        emailEt.setError("Please Enter Valid Email Id");
                        emailEt.requestFocus();
                        return;
                    }
                    if (mDialogSingleCallBack != null) {
                        alertDialogSendReport.dismiss();
                        mDialogSingleCallBack.onPositiveClick(mobileEt.getText().toString(), emailEt.getText().toString());
                    }
                }
            });


            alertDialogSendReport.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }

    public void Successfullogout(final String message, final Activity activity) {
        if (isScreenOpen) {
            try {
                if (alertDialogLogout != null && alertDialogLogout.isShowing()) {
                    return;
                }
                alertDialogLogout = new AlertDialog.Builder(activity).create();

                alertDialogLogout.setTitle("Logout!");

                alertDialogLogout.setMessage(message);

                alertDialogLogout.setButton(AlertDialog.BUTTON_POSITIVE, "Logout From All Device", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        UtilMethods.INSTANCE.Logout(activity, "3");

                    }
                });

                alertDialogLogout.setButton(AlertDialog.BUTTON_NEGATIVE, "Logout ", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        UtilMethods.INSTANCE.Logout(activity, "1");


                    }
                });

                alertDialogLogout.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        dissmiss();

                    }
                });
                alertDialogLogout.show();

              /*  alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton("Logout From All Device", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        UtilMethods.INSTANCE.logout(activity);
                        activity.finish();
                    }
                });
                alertDialog.setNeutralButton("Logout", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        UtilMethods.INSTANCE.logout(activity);
                        activity.finish();
                    }
                });
                alertDialog.show();
                alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                    }
                });
                alertDialog.show();*/
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }


    public void enableRealApiDialog(String msgTxt, final DialogSingleCallBack mDialogSingleCallBack) {
        try {
            if (alertDialogSendReport != null && alertDialogSendReport.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogSendReport = dialogBuilder.create();
            alertDialogSendReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_enable_realapi, null);
            alertDialogSendReport.setView(dialogView);


            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView msg = dialogView.findViewById(R.id.msg);
            msg.setText(msgTxt);
            View realApiLayoutContainer = dialogView.findViewById(R.id.realApiLayoutContainer);

            realApiLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mDialogSingleCallBack!=null) {
                        alertDialogSendReport.dismiss();
                        mDialogSingleCallBack.onPositiveClick("", "");
                    }
                }
            });
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });


            alertDialogSendReport.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }


    public void Successfulok(final String message, final Activity activity) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        activity.finish();
                    }
                });
                alertDialog.show();

            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void Errorok(final String message, final Activity activity) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        activity.finish();
                    }
                });
                alertDialog.show();

            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void WarningWithDoubleBtnCallBack(final String message,final String tite, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {
        if (isScreenOpen) {
            try {

                if (alertDialog != null && alertDialog.isShowing()) {
                    return;
                }

                alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                alertDialog.setContentText(message);
                alertDialog.setTitleText(tite);
                alertDialog.setCancelable(isCancelable);
                // alertDialogSingleBtn.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton(posBtn, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (dialogCallBack != null) {
                            dialogCallBack.onPositiveClick();
                        }
                    }
                });
                alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                    }
                });

                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }


    public void WarningWithDoubleBtnCallBack(final String message, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {
        if (isScreenOpen) {
            try {

                if (alertDialog != null && alertDialog.isShowing()) {
                    return;
                }

                alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                alertDialog.setContentText(message);
                alertDialog.setCancelable(isCancelable);
                // alertDialogSingleBtn.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton(posBtn, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (dialogCallBack != null) {
                            dialogCallBack.onPositiveClick();
                        }
                    }
                });
                alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();

                    }
                });

                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void Warning(final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void Warning(final String title, final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                alertDialog.setContentText(message);
                alertDialog.setTitle(title);
                // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }


    public void channelFosListDialog(Activity context, CustomLoader loader, LoginResponse loginPrefResponse) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_fos_channel_list, null);
            alertDialogServiceList.setView(dialogView);

            View imageContainerChannel=dialogView.findViewById( R.id.imageContainerChannel );

            View imageContainerFos=dialogView.findViewById( R.id.imageContainerFos );
            dialogView.findViewById( R.id.closeIv ).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogServiceList.dismiss();
                }
            });
            imageContainerChannel.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HitUserListApi(context,false,loader,loginPrefResponse);
                    alertDialogServiceList.dismiss();
                }
            } );
            imageContainerFos.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HitUserListApi(context,true,loader,loginPrefResponse);
                    alertDialogServiceList.dismiss();
                }
            } );




            alertDialogServiceList.show();


        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }


    public void HitUserListApi(Activity context,boolean isFromFos, CustomLoader loader, LoginResponse loginPrefResponse) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            final Calendar myCalendar = Calendar.getInstance();
            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            String today = sdf.format(myCalendar.getTime());
            String filterFromDate = today;
            String filterToDate = today;
            UtilMethods.INSTANCE.AccStmtAndCollFilterFosClick(context,"5000",filterFromDate,filterToDate,isFromFos?"1":"2",loader,loginPrefResponse
                    ,0 , new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            FosAccStmtAndCollReportResponse fosAccStmtAndCollReportResponse = (FosAccStmtAndCollReportResponse) object;
                            if(fosAccStmtAndCollReportResponse!=null &&fosAccStmtAndCollReportResponse.getAscReport()!=null &&
                                    fosAccStmtAndCollReportResponse.getAscReport().size()>0){
                                Intent i = new Intent(context, VoucherEntryActivity.class);
                                i.putExtra("Data", fosAccStmtAndCollReportResponse.getAscReport());
                                context.startActivity(i);
                            }else {
                                UtilMethods.INSTANCE.Error(context,"Data not found");
                            }
                        }


                    });
        } else {
            UtilMethods.INSTANCE.NetworkError(context);
        }
    }

    public void channelAreaListDialog(Activity context, ArrayList<AreaMaster> areaMaster) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_channel_area_list, null);
            alertDialogServiceList.setView(dialogView);

            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

            if(!areaMaster.get(0).getArea().equalsIgnoreCase("All")){
                AreaMaster master=new AreaMaster();
                master.setArea("All");
                master.setAreaID(0);
                areaMaster.add(0,master);
            }

            recyclerView.setAdapter(new ChannelAreaListAdapter(areaMaster, context,alertDialogServiceList));

            dialogView.findViewById(R.id.closeIv).setOnClickListener(new View.OnClickListener() {
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



    public void Error(final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                if (message != null && !message.isEmpty() && message.length() > 1) {
                    alertDialog.setContentText(message);
                    if (message.contains("(redirectToLogin)")) {
                        alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                UtilMethods.INSTANCE.logout(context);
                            }
                        });
                    }
                } else {
                    alertDialog.setContentText("Error");
                }
                // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void serviceListDialog(final int parentId, String title, final ArrayList<AssignedOpType> opTypes,
                                  final DialogServiceListCallBack mDialogServiceListCallBack) {
        try {
            if (alertDialogServiceList != null && alertDialogServiceList.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(context);
            alertDialogServiceList = dialogBuilder.create();
            alertDialogServiceList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = context.getLayoutInflater();
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

            HomeOptionListAdapter mDashboardOptionListAdapter = new HomeOptionListAdapter(opTypes, context, new HomeOptionListAdapter.ClickView() {
                @Override
                public void onClick(AssignedOpType opType) {
                    alertDialogServiceList.dismiss();
                    if (mDialogServiceListCallBack != null) {
                        mDialogServiceListCallBack.onIconClick(opType.getServiceID(), opType.getName());
                    }
                }

                @Override
                public void onPackageUpgradeClick() {
                    if (mDialogServiceListCallBack != null) {
                        mDialogServiceListCallBack.onUpgradePackage();
                    }
                }

            }, R.layout.adapter_dashboard_option_grid);
            recyclerView.setAdapter(mDashboardOptionListAdapter);

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

    public void appInstall(Activity mActivity, int type) {
        String panserviceDriver = "com.nsdl.panservicedriver";
        String ekyc = "protean.assisted.ekyc";
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(mActivity);
        alertDialogSendReport = dialogBuilder.create();
        alertDialogSendReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_app_install, null);
        alertDialogSendReport.setView(dialogView);
        alertDialogSendReport.setCancelable(false);
        AppCompatTextView sendBtnOne = dialogView.findViewById(R.id.sendBtnOne);
        TextView sendBtntwo = dialogView.findViewById(R.id.sendBtnTwo);
        ImageView closeIv = dialogView.findViewById(R.id.closeIv);
        sendBtnOne.setVisibility(View.GONE);
        sendBtntwo.setVisibility(View.GONE);
        sendBtnOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_download_arrow,0,0,0);
        sendBtntwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_download_arrow,0,0,0);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogSendReport.dismiss();
            }
        });
        if (UtilMethods.INSTANCE.appInstalledOrNot(mActivity,panserviceDriver)==false) {
            sendBtnOne.setVisibility(View.VISIBLE);
        } else {
            sendBtnOne.setVisibility(View.GONE);
        }
        if (UtilMethods.INSTANCE.appInstalledOrNot(mActivity,ekyc)==false) {
            sendBtntwo.setVisibility(View.VISIBLE);
        } else {
            sendBtntwo.setVisibility(View.GONE);
        }

        sendBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+panserviceDriver));
                marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                mActivity.startActivity(marketIntent);
                alertDialogSendReport.dismiss();
            }
        });
        sendBtntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+ekyc));
                marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                mActivity.startActivity(marketIntent);
                alertDialogSendReport.dismiss();
            }
        });
        alertDialogSendReport.show();


    }

    public interface DialogServiceListCallBack {
        void onIconClick(int serviceId, String name);

        void onUpgradePackage();

    }

    public void ErrorWithTitle(final String title, final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                alertDialog.setTitle(title);
                alertDialog.setContentText(message);
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void ErrorWithFinsh(final String message) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                alertDialog.setContentText(message);
                // alertDialog.setCustomImage(R.drawable.ic_error_red_24dp);
                alertDialog.setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ((Activity) context).finish();
                    }
                });
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void dissmiss() {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (Exception e) {

        }
    }

    public void WarningWithCallBack(final String message, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                alertDialog.setContentText(message);
                alertDialog.setCancelable(isCancelable);
                // alertDialog.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton(posBtn, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (dialogCallBack != null) {
                            dialogCallBack.onPositiveClick();
                        }
                    }
                });
                alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        if (dialogCallBack != null) {
                            dialogCallBack.onNegativeClick();
                        }
                    }
                });
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void WarningWithCallBack(final String message,final String title, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {
        if (isScreenOpen) {
            try {
                alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                alertDialog.setContentText(message);
                alertDialog.setCancelable(isCancelable);
                 alertDialog.setTitle(title);
                alertDialog.setConfirmButton(posBtn, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        alertDialog.dismiss();
                        if (dialogCallBack != null) {
                            dialogCallBack.onPositiveClick();
                        }
                    }
                });
                alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        alertDialog.dismiss();
                        if (dialogCallBack != null) {
                            dialogCallBack.onNegativeClick();
                        }
                    }
                });
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }


    public void WarningWithSingleBtnCallBack(final String message, String posBtn, boolean isCancelable, final DialogCallBack dialogCallBack) {
        if (isScreenOpen) {
            try {

                if (alertDialog != null && alertDialog.isShowing()) {
                    return;
                }

                alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                alertDialog.setContentText(message);
                alertDialog.setCancelable(isCancelable);
                // alertDialogSingleBtn.setCustomImage(R.drawable.ic_success);
                alertDialog.setConfirmButton(posBtn, new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        UtilMethods.INSTANCE.isPassChangeDialogShowing = false;
                        if (dialogCallBack != null) {
                            dialogCallBack.onPositiveClick();
                        }
                    }
                });
                UtilMethods.INSTANCE.isPassChangeDialogShowing = true;
                alertDialog.show();
            } catch (WindowManager.BadTokenException bte) {

            } catch (IllegalStateException ise) {

            } catch (Exception e) {

            }
        }
    }

    public void WarningWithSingleBtnCallBack(final String title, final String message, final String btnTxt,
                                             boolean isCancelable, final DialogCallBack dialogCallBack) {

        try {

            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
            alertDialog.setContentText(message);
            alertDialog.setTitle(title);
            alertDialog.setCancelable(isCancelable);

            alertDialog.setConfirmButton(btnTxt, sweetAlertDialog -> {
                sweetAlertDialog.dismiss();
                if (dialogCallBack != null) {
                    dialogCallBack.onPositiveClick();
                }
            });
            alertDialog.show();
        } catch (WindowManager.BadTokenException bte) {

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }

    }


    public SweetAlertDialog returnDialog() {
        return alertDialog;
    }


    public interface DialogSingleCallBack {
        void onPositiveClick(String mobile, String emailId);


    }

    public interface DialogCallBack {
        void onPositiveClick();

        void onNegativeClick();
    }


}
