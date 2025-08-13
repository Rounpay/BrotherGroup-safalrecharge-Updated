package com.solution.brothergroup.DMTWithPipe.networkAPI;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.google.android.material.textfield.TextInputLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.DMTWithPipe.dto.BeneDetailNew;
import com.solution.brothergroup.DMTWithPipe.dto.GetChargedAmountRequestNew;
import com.solution.brothergroup.DMTWithPipe.dto.GetSenderRequestNew;
import com.solution.brothergroup.DMTWithPipe.dto.RequestSendMoneyNew;
import com.solution.brothergroup.DMTWithPipe.dto.SendMoneyRequestNew;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.CreateSenderResponse;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.Senderobject;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;


import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public enum UtilsMethodDMTPipe {

    INSTANCE;
    EditText edMobileOtp;

    public void setDMTOperatorList(Context context, ArrayList<OperatorList> list) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.dmtOperatorListPref, new Gson().toJson(list));

        editor.commit();
    }

    public ArrayList<OperatorList> getDMTOperatorList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        String numberList = prefs.getString(ApplicationConstant.INSTANCE.dmtOperatorListPref, "");
        Type type = new TypeToken<ArrayList<OperatorList>>() {
        }.getType();
        return new Gson().fromJson(numberList, type);
    }

    public void setIsDMTWithPipe(Context context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isDMTWithPipePref, data);
        editor.commit();

    }

    public boolean getIsDMTWithPipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isDMTWithPipePref, false);
    }


    /*-------------------START DMT WITH PIPE------------------------------*/

    public void GetSenderNew(final Activity context, String oid, final String MobileNumber, final CustomLoader loader) {
        try {
            String loginPref = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(loginPref, LoginResponse.class);
            com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe git = ApiClient.getClient().create(com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe.class);
            Call<CreateSenderResponse> call = git.GetSenderNew(new GetSenderRequestNew(oid, new Senderobject(MobileNumber),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME,
                    UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<CreateSenderResponse>() {

                @Override
                public void onResponse(Call<CreateSenderResponse> call, retrofit2.Response<CreateSenderResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                if (!response.body().isSenderNotExists()) {
                                    UtilMethods.INSTANCE.setSenderNumber(context, MobileNumber, response.body().getSenderName(), response.body().getSenderBalance(), response.body().toString());
                                    UtilMethods.INSTANCE.GetBanklist(context, loader, null);

                                    ActivityActivityMessage activityActivityMessage =
                                            new ActivityActivityMessage(MobileNumber + "," + response.body().getSenderName() + "," + response.body().getSid() + "," + response.body().getAvailbleLimit() + "," + response.body().getRemainingLimit(), "GetSender");
                                    GlobalBus.getBus().post(activityActivityMessage);

                                } else {
                                    //Error(context, response.body().getMsg() + "");
                                    ActivityActivityMessage activityActivityMessage =
                                            new ActivityActivityMessage("", "CallgetSenderSender");
                                    GlobalBus.getBus().post(activityActivityMessage);
                                }

                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<CreateSenderResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing())
                loader.dismiss();
            UtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }

    public void CreateSenderNew(final Activity context, final String oid, final String MobileNumber, String name, String lastName, String pincode, String address, final String otp, String dob, final CustomLoader loader) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Senderobject senderobject = new Senderobject(MobileNumber, name, lastName, pincode, address, otp, dob);
            com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe git = ApiClient.getClient().create(com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe.class);
            Call<RechargeReportResponse> call = git.CreateSenderNew(new GetSenderRequestNew(oid, senderobject,
                    LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                //  Successful(context, response.body().getMsg() + "");

                                Log.d("kjndvjdfv",response.body().getSID().toString());
                                openOTPDialog(context, response.body().getSID(), loader,"verifySenderNEW", oid, MobileNumber);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void VerifySenderNew(final Activity context, String sidFinal,final String oid, final String MobileNumber, String otp, final CustomLoader loader, final Dialog dialog) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe git = ApiClient.getClient().create(com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe.class);
            Call<RechargeReportResponse> call = git.VerifySenderNew(new GetSenderRequestNew(oid,sidFinal, new Senderobject(MobileNumber, otp,sidFinal),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                dialog.dismiss();
                                UtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                                GetSenderNew(context, oid, MobileNumber, loader);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }else {
                                UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetBeneficiaryNew(final Activity context, String oid, String MobileNumber, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe git = ApiClient.getClient().create(com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe.class);
            Call<RechargeReportResponse> call = git.GetBeneficiaryNew(new GetSenderRequestNew(oid, new Senderobject(MobileNumber),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                UtilMethods.INSTANCE.setBeneficiaryList(context, new Gson().toJson(response.body()).toString());
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }

                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void VerifyAccountNew(final Activity context, String oid, String mobileNo, String ifsc, String accountNo, String beneName,
                                 String bankName, String bankId, final CustomLoader loader) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe git = ApiClient.getClient().create(com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe.class);
            Call<RechargeReportResponse> call = git.VerifyAccountNew(new GetSenderRequestNew(oid, new Senderobject(mobileNo),
                    new BeneDetailNew(mobileNo, beneName, ifsc, accountNo, bankName, bankId),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("2")) {
                                UtilMethods.INSTANCE.Successful(context, "Verifications successfully done.");
                                ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("AccountVerified", response.body().getBeneName());
                                GlobalBus.getBus().post(activityActivityMessage);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("3") || response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void AddBeneficiaryNew(final Activity context, String oid, String sid, String otp, String SenderNO, String BeneMobileNO, String beniName, String ifsc, String accountNo, String bankName, final String bankId, final CustomLoader loader, final Activity activity) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe git = ApiClient.getClient().create(com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe.class);
            Call<RechargeReportResponse> call = git.AddBeneficiaryNew(new GetSenderRequestNew(oid, new Senderobject(SenderNO),
                    new BeneDetailNew(BeneMobileNO, beniName, ifsc, accountNo, bankName, bankId, 2), sid, otp,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                UtilMethods.INSTANCE.Successfulok(response.body().getMsg() + "", activity);
                                ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("", "beneAdded");
                                GlobalBus.getBus().post(activityActivityMessage);

                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetChargedAmountNew(final Activity context, final String oid, final String Amount, final String beneID, final String mobileNo, final String ifsc, final String accountNo, final String channel, final String bank, final String beneName, final CustomLoader loader, final Activity activity) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe git = ApiClient.getClient().create(com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe.class);
            Call<RechargeReportResponse> call = git.GetChargedAmountNew(new GetChargedAmountRequestNew(oid, Amount,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                double amount = Double.parseDouble(Amount);
                                double charged = Double.parseDouble(response.body().getChargedAmount());
                                double totalAmount = amount + charged;
                                ActivityActivityMessage activityActivityMessage = new ActivityActivityMessage(charged + "," + totalAmount, "SendMoney");
                                GlobalBus.getBus().post(activityActivityMessage);
//                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//
//
//                            LayoutInflater inflater = activity.getLayoutInflater();
//                            View view = inflater.inflate(R.layout.custom_dialog_send, null);
//                            AppCompatTextView message = (AppCompatTextView) view.findViewById(R.id.message);
//                            message.setText("You are going to transfer \u20B9 " + Amount + "\nyour Charged Amount is  \u20B9 " + response.body().getChargedAmount() + "\nNow total Transfer Amount is \u20B9 " + totalAmount);
//                            final Button okButton = (Button) view.findViewById(R.id.okButton);
//                            final Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
//                            okButton.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                   // GetDMTReceipt(context,"S190604122026309F91E","All",loader);
//
//
//                                }
//                            });
//                            cancelButton.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    alertDialog.dismiss();
//                                }
//                            });
//                            dialogBuilder.setView(view);
//                            dialogBuilder.setCancelable(false);
//                            alertDialog = dialogBuilder.create();
//                            alertDialog.show();

                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void SendMoneyNew(final Activity context, String oid, String securityKey, String beneID, String mobileNo,
                             String ifsc, String accountNo, String amount, String channel, String bank, String bankId,
                             String beneName, String beneMobile, final CustomLoader loader, final Activity activity, final TextView submitButton) {
        try {
            submitButton.setEnabled(false);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe git = ApiClient.getClient().create(com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe.class);
            Call<RechargeReportResponse> call = git.SendMoneyNew(new SendMoneyRequestNew(oid, new RequestSendMoneyNew(oid, oid, beneID, mobileNo, ifsc, accountNo,
                    amount, channel, bank, bankId, beneName, beneMobile), securityKey,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            submitButton.setEnabled(true);
                            if (response.body().getStatuscode().equalsIgnoreCase("2")) {
                                // Successfulok(context, response.body().getMsg(), activity);
                                if (response.body().getGroupID() != null && !response.body().getGroupID().isEmpty()) {
                                    UtilMethods.INSTANCE.GetDMTReceipt(activity, response.body().getGroupID(), "All", loader);

                                } else {
                                    UtilMethods.INSTANCE.Successfulok(response.body().getMsg(), activity);
                                }

                            } else if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                UtilMethods.INSTANCE.Processing(context, response.body().getMsg() + "");
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            } else if (response.body().getStatuscode().equalsIgnoreCase("3")) {
                                UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    submitButton.setEnabled(true);
                    if (loader.isShowing())
                        loader.dismiss();
                    try {
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void DeletebeneficiaryNew(final Activity context, String oid, String sid, String otp, final String MobileNumber,
                                     String beneID, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe git = ApiClient.getClient().create(com.solution.brothergroup.DMTWithPipe.networkAPI.EndPointInterfaceDMTPipe.class);
            Call<RechargeReportResponse> call = git.DeleteBeneficiaryNew(new GetSenderRequestNew(oid, new Senderobject(MobileNumber),
                    new BeneDetailNew(beneID), sid, otp,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                if (response.body().isOTPRequired()) {
                                    openDeleteBeneOTPDialog(context, loader, oid, sid, otp, MobileNumber, beneID, mApiCallBack);
                                } else {
                                    UtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                }
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /*-----------------------------------END OF DMT PIPE---------------------------------*/

    private void openOTPDialog(final Context context, String sidData,final CustomLoader loader, final String flag, final String oid, final String SenderNumber) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verifyotp, null);

        edMobileOtp = (EditText) view.findViewById(R.id.ed_mobile_otp);
        final TextInputLayout tilMobileOtp = (TextInputLayout) view.findViewById(R.id.til_mobile_otp);
        final Button okButton = (Button) view.findViewById(R.id.okButton);
        final Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okButton.setEnabled(true);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edMobileOtp.getText().toString().isEmpty()) {
                    tilMobileOtp.setErrorEnabled(false);
                    if (UtilMethods.INSTANCE.isNetworkAvialable((Activity) context)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        VerifySenderNew((Activity) context,sidData, oid, SenderNumber, edMobileOtp.getText().toString(), loader, dialog);
                    } else {
                        UtilMethods.INSTANCE.NetworkError((Activity) context, context.getResources().getString(R.string.err_msg_network_title), context.getResources().getString(R.string.err_msg_network));
                    }

                } else {
                    tilMobileOtp.setError(context.getString(R.string.err_msg_otp));
                }
            }
        });
        dialog.show();
    }

    private void openDeleteBeneOTPDialog(final Activity context, final CustomLoader loader, String oid, String sid, String otp, final String MobileNumber,
                                         String beneID, final ApiCallBack mApiCallBack) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verifyotp, null);

        edMobileOtp = (EditText) view.findViewById(R.id.ed_mobile_otp);
        final TextInputLayout tilMobileOtp = (TextInputLayout) view.findViewById(R.id.til_mobile_otp);
        final Button okButton = (Button) view.findViewById(R.id.okButton);
        final Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okButton.setEnabled(true);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edMobileOtp.getText().toString().isEmpty()) {
                    tilMobileOtp.setErrorEnabled(false);
                    if (UtilMethods.INSTANCE.isNetworkAvialable((Activity) context)) {
                        dialog.dismiss();
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        DeletebeneficiaryNew(context, oid, sid, edMobileOtp.getText().toString(), MobileNumber,
                                beneID, loader, mApiCallBack);
                    } else {
                        UtilMethods.INSTANCE.NetworkError((Activity) context, context.getResources().getString(R.string.err_msg_network_title), context.getResources().getString(R.string.err_msg_network));
                    }

                } else {
                    tilMobileOtp.setError(context.getString(R.string.err_msg_otp));
                }
            }
        });
        dialog.show();
    }


    public void apiErrorHandle(Activity context, int code, String msg) {
        if (code == 401) {
            UtilMethods.INSTANCE.ErrorWithTitle(context, "UNAUTHENTICATED " + code, msg + "");
        } else if (code == 404) {
            UtilMethods.INSTANCE.ErrorWithTitle(context, "API ERROR " + code, msg + "");
        } else if (code >= 400 && code < 500) {
            UtilMethods.INSTANCE.ErrorWithTitle(context, "CLIENT ERROR " + code, msg + "");
        } else if (code >= 500 && code < 600) {

            UtilMethods.INSTANCE.ErrorWithTitle(context, "SERVER ERROR " + code, msg + "");
        } else {
            UtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL/UNKNOWN ERROR " + code, msg + "");
        }
    }

    public void apiFailureError(Activity context, Throwable t) {
        if (t instanceof UnknownHostException || t instanceof IOException) {
            UtilMethods.INSTANCE.NetworkError((Activity) context);
        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
            UtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
        } else {
            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                UtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
            } else {
                UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
            }
        }
    }

    public interface ApiCallBack {
        void onSucess(Object object);
    }

    public interface ApiCallBackTwoMethod {
        void onSucess(Object object);

        void onError(String errorMsg);
    }
}
