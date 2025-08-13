package com.solution.brothergroup.CMS.Activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.brothergroup.Aeps.Adapter.ReceiptDetailListAdapter;
import com.solution.brothergroup.Api.Object.ReceiptObject;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.CMS.Api.CmsApiResponse;
import com.solution.brothergroup.CMS.Api.CmsRequest;
import com.solution.brothergroup.CMS.Api.ReceiptDetail;
import com.solution.brothergroup.R;
import com.solution.brothergroup.UPIPayment.UI.CompanyProfileResponse;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class CmsReceiptActivity extends AppCompatActivity {

    private Activity context;
    private LinearLayout shareView;
    private ImageView statusIcon;
    private TextView statusTv;
    private TextView statusMsg;
    private TextView companyNameTv;
    private TextView addressTv,opNameTv;
    private RecyclerView recyclerView;
    private TextView outletDetail;
    private LinearLayout btRepeat;
    private LinearLayout btShare;
    private LinearLayout btWhatsapp;
    private ImageView closeIv,operatorImage;
    private double balAmount,transAmount,convenientFee;
    private String cmsTId,status,account,ifsc,channel,bankName,senderNo,senderName,beneName, txnRefrenceNo, txnId,opName,displayAccount,name,mobileNo,address,companyAddress,companyName,entryDate,pinCode,phoneNoSupport,mobileSupport,email,supportEmail;
    private int statusCode,oid;
    private boolean isError;
    private ArrayList<ReceiptObject> mReceiptObjects = new ArrayList<>();
    private LoginResponse mLoginPrefResponse;
    private CustomLoader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cms_receipt);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        findViews();
        mLoginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        cmsTId = getIntent().getStringExtra("cmsTId");
        hitCmsAPI();
        closeIv.setOnClickListener(v -> finish());
        btShare.setOnClickListener(v -> shareIt(false));
        btRepeat.setOnClickListener(v -> finish());

        if (UtilMethods.INSTANCE.isPackageInstalled("com.whatsapp", getPackageManager())) {
            btWhatsapp.setVisibility(View.VISIBLE);
            btWhatsapp.setOnClickListener(view -> shareIt(true));
        } else {
            btWhatsapp.setVisibility(View.GONE);
        }


    }

    private void hitCmsAPI() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CmsApiResponse> call = git.CMSTransactionReceipt(new CmsRequest(cmsTId,"",
                    0,
                    mLoginPrefResponse.getData().getUserID(), mLoginPrefResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), mLoginPrefResponse.getData().getSessionID(), mLoginPrefResponse.getData().getSession()));
            call.enqueue(new Callback<CmsApiResponse>() {

                @Override
                public void onResponse(Call<CmsApiResponse> call, retrofit2.Response<CmsApiResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (response.isSuccessful()) {
                        CmsApiResponse apiData = response.body();
                        if (apiData != null) {
                            if (apiData.getStatuscode() == 1) {
                                ReceiptDetail mReceiptDetail =apiData.getReceiptDetail();
                                  if(mReceiptDetail!=null){
                                     setUiData(mReceiptDetail);
                                  }
                            } else {
                                if (apiData.isVersionValid() == false) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, apiData.getMsg() + "");
                                }
                            }

                        }
                    }
                    else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<CmsApiResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            UtilMethods.INSTANCE.NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            UtilMethods.INSTANCE.ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                UtilMethods.INSTANCE.ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
        }
    }


    private void findViews() {
        context=this;
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        shareView = (LinearLayout) findViewById(R.id.shareView);
        //titleTv = findViewById(R.id.titleTv);
        statusIcon = (ImageView) findViewById(R.id.statusIcon);
        statusTv = (TextView) findViewById(R.id.statusTv);
        statusMsg = (TextView) findViewById(R.id.statusMsg);
        companyNameTv = (TextView) findViewById(R.id.companyNameTv);
        addressTv = (TextView) findViewById(R.id.addressTv);
        opNameTv = (TextView) findViewById(R.id.opNameTv);
        recyclerView = (RecyclerView) findViewById(R.id.transactionReceiptRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        outletDetail = (TextView) findViewById(R.id.outletDetail);
        btRepeat = (LinearLayout) findViewById(R.id.bt_repeat);
        btShare = (LinearLayout) findViewById(R.id.bt_share);
        btWhatsapp = (LinearLayout) findViewById(R.id.bt_whatsapp);
        closeIv = (ImageView) findViewById(R.id.closeIv);
        operatorImage =findViewById(R.id.operatorImage);

        ImageView logoIv = findViewById(R.id.appLogo);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.rnd_logo);
        requestOptions.error(R.drawable.rnd_logo);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        String appLogoUrl = UtilMethods.INSTANCE.getAppLogoUrl(this);
        if (appLogoUrl != null && !appLogoUrl.isEmpty()) {
            Glide.with(this)
                    .load(appLogoUrl)
                    .apply(requestOptions)
                    .into(logoIv);
        } else {
            int wid = UtilMethods.INSTANCE.getWIDPref(this);
            if (wid > 0) {

                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseAppIconUrl + wid + "/logo.png")
                        .apply(requestOptions)
                        .into(logoIv);
            }
        }
    }

    private void setUiData(ReceiptDetail mReceiptDetail) {

        statusCode =mReceiptDetail.getStatuscode();
        status=mReceiptDetail.getStatus();
        isError=mReceiptDetail.isError();
        transAmount=mReceiptDetail.getRequestedAmount();
        account=mReceiptDetail.getAccount();
        ifsc=mReceiptDetail.getIfsc();
        bankName=mReceiptDetail.getBankName();
        senderNo=mReceiptDetail.getSenderNo();
        senderName=mReceiptDetail.getSenderName();
        beneName=mReceiptDetail.getBeneName();
        channel=mReceiptDetail.getChannel();
        txnId =mReceiptDetail.getTransactionID();
        txnRefrenceNo =mReceiptDetail.getLiveID();
        oid =mReceiptDetail.getOid();
        opName =mReceiptDetail.getOpName();
        displayAccount =mReceiptDetail.getDisplayAccount();
        name =mReceiptDetail.getName();
        mobileNo =mReceiptDetail.getMobileNo();
        address =mReceiptDetail.getAddress();
        companyAddress =mReceiptDetail.getCompanyAddress();
        companyName =mReceiptDetail.getCompanyName();
        email =mReceiptDetail.getEmail();
        supportEmail =mReceiptDetail.getSupportEmail();
        mobileSupport =mReceiptDetail.getMobileSupport();
        phoneNoSupport=mReceiptDetail.getPhoneNoSupport();
        pinCode=mReceiptDetail.getPinCode();
        entryDate=mReceiptDetail.getEntryDate();

        String url=ApplicationConstant.INSTANCE.baseIconUrl + oid + ".png";
        Glide.with(this).load(url)
                /*.thumbnail(0.5f)
                .transition(new DrawableTransitionOptions().crossFade())*/
                .apply(new RequestOptions().placeholder(R.drawable.rnd_logo).error(R.drawable.rnd_logo).diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(operatorImage);


        if(opName!=null && !opName.isEmpty()){
            opNameTv.setText(opName);
        }else {
            opNameTv.setVisibility(View.GONE);
        }

        recyclerView.setAdapter(new ReceiptDetailListAdapter(mReceiptObjects, this));
        if (!isError  && status!=null && status.equalsIgnoreCase("SUCCESS")) {
            statusIcon.setImageResource(R.drawable.ic_check_mark);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.green));
            statusTv.setTextColor(getResources().getColor(R.color.green));
            statusTv.setText("Success");
            if (cmsTId != null && !cmsTId.isEmpty()) {
                statusMsg.setText("Transaction with TxnId " + cmsTId + " completed successfully");
            }
            else if (txnRefrenceNo != null && !txnRefrenceNo.isEmpty()) {
                statusMsg.setText("Transaction with LiveId " + txnRefrenceNo + " completed successfully");
            } /*else if (bankRrn != null && !bankRrn.isEmpty()) {
                statusMsg.setText("Transaction with bank rrn " + bankRrn + " completed successfully");
            } */ else {
                statusMsg.setText("Transaction completed successfully");
            }
        }
        else if(isError && status!=null && status.equalsIgnoreCase("FAILED")){
            statusIcon.setImageResource(R.drawable.ic_cross_mark);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
            statusTv.setTextColor(getResources().getColor(R.color.color_red));
            statusTv.setText("Failed");
            statusMsg.setVisibility(View.GONE);}

        if(account!=null && !account.isEmpty()){
            int totalLength=account.length();
           if(totalLength>4){
               int hideLength=totalLength-4;
               String hideStr="";
               for(int pos=0; pos<hideLength; pos++){
                   hideStr=hideStr+"x";
               }
               account = /*updateUTR.substring(0, 2) +*/ hideStr + account.substring((totalLength - 4),totalLength);
           }
        }



        if (transAmount!=0) {
            mReceiptObjects.add(new ReceiptObject("Requested Amount", "\u20B9 " + UtilMethods.INSTANCE.formatedAmount(transAmount + "")));
        }
         mReceiptObjects.add(new ReceiptObject("Convenient Fee", "\u20B9 " + UtilMethods.INSTANCE.formatedAmount(convenientFee + "")));

        if(txnId!=null && !txnId.isEmpty())
            mReceiptObjects.add(new ReceiptObject("Transaction Id ",txnId));

        if(bankName!=null && !bankName.isEmpty())
            mReceiptObjects.add(new ReceiptObject("Bank Name ",bankName));

        if(account!=null && !account.isEmpty())
            mReceiptObjects.add(new ReceiptObject("Account No ",account));

        if(ifsc!=null && !ifsc.isEmpty())
            mReceiptObjects.add(new ReceiptObject("IFSC ",ifsc));

         if(channel!=null && !channel.isEmpty())
            mReceiptObjects.add(new ReceiptObject("Channel ",channel));

        if(senderName!=null && !senderName.isEmpty())
            mReceiptObjects.add(new ReceiptObject("Sender Name ",senderName));

        if(senderNo!=null && !senderNo.isEmpty())
            mReceiptObjects.add(new ReceiptObject("Sender No ",senderNo));

        if(beneName!=null && !beneName.isEmpty())
            mReceiptObjects.add(new ReceiptObject("Bene Name ",beneName));


        if (txnRefrenceNo != null && !txnRefrenceNo.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Transaction ReferenceNo", txnRefrenceNo));
        }

        if (entryDate != null && !entryDate.isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Transaction Date", entryDate + ""));
        } else {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
            try {
                String dateStr = sdfDate.format(new Date());
                mReceiptObjects.add(new ReceiptObject("Transaction Date", dateStr + ""));
            } catch (Exception e) {

            }
        }

        setCompanyDetail(UtilMethods.INSTANCE.getCompanyProfileDetails(this));
        //String shopDetailsStr=UtilMethods.INSTANCE.getShopDetailsRetailer(this);
        /*if(shopDetailsStr!=null && !shopDetailsStr.isEmpty())
            outletDetail.setText(shopDetailsStr);*/
        hitShopDetailsApi();
    }

    void setCompanyDetail(CompanyProfileResponse companyData) {

        if (companyData != null && companyData.getCompanyProfile() != null) {
            companyNameTv.setText(companyData.getCompanyProfile().getName() + "");
            String company = "" + Html.fromHtml(companyData.getCompanyProfile().getAddress());
            if (companyData.getCompanyProfile().getPhoneNo() != null && !companyData.getCompanyProfile().getPhoneNo().isEmpty()) {
                company = company + "\nLandline No : " + companyData.getCompanyProfile().getPhoneNo();
            }
            if (companyData.getCompanyProfile().getMobileNo() != null && !companyData.getCompanyProfile().getMobileNo().isEmpty()) {
                company = company + "\nMobile No : " + companyData.getCompanyProfile().getMobileNo();
            }
            if (companyData.getCompanyProfile().getEmailId() != null && !companyData.getCompanyProfile().getEmailId().isEmpty()) {
                company = company + "\nEmail : " + companyData.getCompanyProfile().getEmailId();
            }
            addressTv.setText(company);
        } else {
            UtilMethods.INSTANCE.getCompanyProfile(this, null, object -> {
                CompanyProfileResponse companyData1 = (CompanyProfileResponse) object;
                if (companyData1 != null && companyData1.getCompanyProfile() != null) {
                    setCompanyDetail(companyData1);
                }
            });
        }
    }

    void hitShopDetailsApi() {

        String outletDetailStr = "";
        if (mLoginPrefResponse.getData().getName() != null && !mLoginPrefResponse.getData().getName().isEmpty()) {
            outletDetailStr = outletDetailStr + "Name : " + mLoginPrefResponse.getData().getName();
        }
        if (mLoginPrefResponse.getData().getOutletName() != null && !mLoginPrefResponse.getData().getOutletName().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Shop Name : " + mLoginPrefResponse.getData().getOutletName();
        }
        if (mLoginPrefResponse.getData().getMobileNo() != null && !mLoginPrefResponse.getData().getMobileNo().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Contact No : " + mLoginPrefResponse.getData().getMobileNo();
        }
        if (mLoginPrefResponse.getData().getEmailID() != null && !mLoginPrefResponse.getData().getEmailID().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Email : " + mLoginPrefResponse.getData().getEmailID();
        }
        if (mLoginPrefResponse.getData().getAddress() != null && !mLoginPrefResponse.getData().getAddress().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Address : " + mLoginPrefResponse.getData().getAddress();
        }
        outletDetail.setText(outletDetailStr);
    }



   private void shareIt(boolean isWhatsapp) {
        Bitmap myBitmap = Bitmap.createBitmap(shareView.getWidth(), shareView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(myBitmap);
        shareView.layout(0, 0, shareView.getWidth(), shareView.getHeight());
        shareView.draw(c);
        saveImage(myBitmap, isWhatsapp);

    }

    void saveImage(Bitmap bitmap, boolean isWhatsapp) {
        if (Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);
                    if (isWhatsapp) {
                        openWhatsapp(uri);
                    } else {
                        sendMail(uri);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/" + getString(R.string.app_name));

            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                Uri pathUri=this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (isWhatsapp) {
                    openWhatsapp(pathUri);
                } else {
                    sendMail(pathUri);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        return values;
    }

    void saveImageToStream(Bitmap bitmap, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (IOException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void sendMail(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

    void openWhatsapp(Uri myUri) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Receipt");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Receipt");
            sendIntent.setType("image/png");
            sendIntent.putExtra(Intent.EXTRA_STREAM, myUri);
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp"));
            startActivity(intent);


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
