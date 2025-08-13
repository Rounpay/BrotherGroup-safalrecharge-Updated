package com.solution.brothergroup.FingPayAEPS;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.UtilMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.ImageViewCompat;

public class AEPSStatusActivity extends AppCompatActivity {

    ImageView statusIcon;
    private LinearLayout shareView;
    private AppCompatTextView statusTv;
    private TextView address;
    private AppCompatImageView operatorImage;
    private AppCompatTextView statusMsg;
    private TextView opTv;
    private TextView amtTv;
    private View liveIdView;
    private TextView liveLabel;
    private TextView liveTv;
    private View txnIdView;
    private TextView txnTv;
    private TextView numTv;
    private TextView balTv;
    private View deviceView;
    private TextView deviceTv;
    private View dateView;
    private TextView dateTv;
    private TextView outletDetail;
    private LinearLayout btn_share;
    private TextView tv_share;
    private RelativeLayout rl_cancel;



    private String intentMsg, intentLiveID, intentTxnId, intentOpName, intentAmt, intentBal, intentNum, intentDeviceName;
    private int intentStatus;
    private LoginResponse LoginDataResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_aeps_slip);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        LoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        intentMsg = getIntent().getStringExtra("MESSAGE");
        intentStatus = getIntent().getIntExtra("STATUS", 0);
        intentLiveID = getIntent().getStringExtra("LIVE_ID");
        intentTxnId = getIntent().getStringExtra("TRANSACTION_ID");
        intentOpName = getIntent().getStringExtra("OP_NAME");
        intentAmt = getIntent().getStringExtra("AMOUNT");
        intentBal = getIntent().getStringExtra("BALANCE");
        intentNum = getIntent().getStringExtra("NUMBER");
        intentDeviceName = getIntent().getStringExtra("Device_NAME");
        findViews();
        setUiData();
        rl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareit();
            }
        });

        /*repetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });*/
    }


    private void findViews() {
        statusIcon =  findViewById(R.id.statusIcon);
        shareView = (LinearLayout) findViewById(R.id.shareView);
        statusTv = (AppCompatTextView) findViewById(R.id.statusTv);
        address = (TextView) findViewById(R.id.address);
        operatorImage = (AppCompatImageView) findViewById(R.id.operatorImage);
        statusMsg = (AppCompatTextView) findViewById(R.id.statusMsg);
        opTv = (TextView) findViewById(R.id.opTv);
        amtTv = (TextView) findViewById(R.id.amtTv);
        liveIdView =  findViewById(R.id.liveIdView);
        liveLabel = (TextView) findViewById(R.id.liveLabel);
        liveTv = (TextView) findViewById(R.id.liveTv);
        txnIdView =  findViewById(R.id.txnIdView);
        txnTv = (TextView) findViewById(R.id.txnTv);
        numTv = (TextView) findViewById(R.id.numTv);
        balTv = (TextView) findViewById(R.id.balTv);
        deviceView =  findViewById(R.id.deviceView);
        deviceTv = (TextView) findViewById(R.id.deviceTv);
        dateView =  findViewById(R.id.dateView);
        dateTv = (TextView) findViewById(R.id.dateTv);
        outletDetail = (TextView) findViewById(R.id.outletDetail);
        btn_share = (LinearLayout) findViewById(R.id.btn_share);
        tv_share = (TextView) findViewById(R.id.tv_share);
        rl_cancel = (RelativeLayout) findViewById(R.id.rl_cancel);

        ImageView logoIv = findViewById(R.id.logoIv);
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
            int wid = LoginDataResponse.getData().getWid();
            if (wid > 0) {

                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseAppIconUrl + wid + "/logo.png")
                        .apply(requestOptions)
                        .into(logoIv);
            }
        }
    }


    void setUiData() {
        if (intentStatus == 1) {
            statusIcon.setImageResource(R.drawable.ic_pending_outline);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_orange));
            statusTv.setTextColor(getResources().getColor(R.color.color_orange));
            statusTv.setText("Processing");
            statusMsg.setText("Transaction with reference id " + intentTxnId + " under process");
            /*repetBtn.setVisibility(View.GONE);*/
        } else if (intentStatus == 2) {
            statusIcon.setImageResource(R.drawable.ic_check_mark_outline);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.green));
            statusTv.setTextColor(getResources().getColor(R.color.green));
            statusTv.setText("Success");
            statusMsg.setText("Transaction with reference id " + intentTxnId + " Completed successfully");
            /*repetBtn.setVisibility(View.GONE);*/
        } else {
            statusIcon.setImageResource(R.drawable.ic_cross_mark_outline);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
            statusTv.setTextColor(getResources().getColor(R.color.color_red));
            statusTv.setText("Failed");
            liveLabel.setText("Reason");
            statusMsg.setText(intentMsg);
            /*repetBtn.setVisibility(View.VISIBLE);*/
        }
        // statusMsg.setText(intentMsg);


        opTv.setText(intentOpName + "");
        amtTv.setText(getString(R.string.rupiya) + " " + intentAmt);
        liveTv.setText(intentLiveID + "");
        numTv.setText(intentNum + "");
        balTv.setText(intentBal + "");

        if (intentDeviceName != null && !intentDeviceName.isEmpty()) {
            deviceView.setVisibility(View.VISIBLE);
            deviceTv.setText(intentDeviceName + "");
        } else {
            deviceView.setVisibility(View.GONE);
        }
        if (intentTxnId != null && !intentTxnId.isEmpty()) {
            txnIdView.setVisibility(View.VISIBLE);
            txnTv.setText(intentTxnId + "");
        } else {
            txnIdView.setVisibility(View.GONE);
        }
        if (intentLiveID != null && !intentLiveID.isEmpty()) {
            liveIdView.setVisibility(View.VISIBLE);
            liveTv.setText(intentLiveID + "");
        } else {
            liveIdView.setVisibility(View.GONE);
        }


        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy hh:mm aa");

        try {
            String dateStr = sdfDate.format(new Date());
            dateTv.setText(dateStr);
        } catch (Exception e) {

        }
        setCompanyDetail(new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this), AppUserListResponse.class));

        setOutletDetail();
    }

    void setCompanyDetail(AppUserListResponse companyData) {
        if (companyData != null && companyData.getCompanyProfile() != null) {
            String company = companyData.getCompanyProfile().getName() + "\n" + Html.fromHtml(companyData.getCompanyProfile().getAddress());
            if (companyData.getCompanyProfile().getPhoneNo() != null && !companyData.getCompanyProfile().getPhoneNo().isEmpty()) {
                company = company + "\nLandline No : " + companyData.getCompanyProfile().getPhoneNo();
            }
            if (companyData.getCompanyProfile().getMobileNo() != null && !companyData.getCompanyProfile().getMobileNo().isEmpty()) {
                company = company + "\nMobile No : " + companyData.getCompanyProfile().getMobileNo();
            }
            if (companyData.getCompanyProfile().getEmailId() != null && !companyData.getCompanyProfile().getEmailId().isEmpty()) {
                company = company + "\nEmail : " + companyData.getCompanyProfile().getEmailId();
            }
            address.setText(company);
        } else {
            UtilMethods.INSTANCE.GetCompanyProfile(this, object -> {
                AppUserListResponse companyData1 = (AppUserListResponse) object;
                if (companyData1 != null && companyData1.getCompanyProfile() != null) {
                    setCompanyDetail(companyData1);
                }
            });
        }
    }

    void setOutletDetail() {
        

        String outletDetailStr = "";
        if (LoginDataResponse.getData().getName() != null && !LoginDataResponse.getData().getName().isEmpty()) {
            outletDetailStr = outletDetailStr + "Name : " + LoginDataResponse.getData().getName();
        }
        if (LoginDataResponse.getData().getOutletName() != null && !LoginDataResponse.getData().getOutletName().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Shop Name : " + LoginDataResponse.getData().getOutletName();
        }
        if (LoginDataResponse.getData().getMobileNo() != null && !LoginDataResponse.getData().getMobileNo().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Contact No : " + LoginDataResponse.getData().getMobileNo();
        }
        if (LoginDataResponse.getData().getEmailID() != null && !LoginDataResponse.getData().getEmailID().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Email : " + LoginDataResponse.getData().getEmailID();
        }
        if (LoginDataResponse.getData().getAddress() != null && !LoginDataResponse.getData().getAddress().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Address : " + LoginDataResponse.getData().getAddress();
        }
        outletDetail.setText(outletDetailStr);

    }

    public void shareit() {
        File picFile = null;
        Bitmap myBitmap = null;

        //  View v1 = getWindow().getDecorView().getRootView();
        shareView.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(shareView.getDrawingCache());
        shareView.setDrawingCacheEnabled(false);
        saveBitmap(myBitmap,false);

    }

    private void saveBitmap(Bitmap bitmap, boolean isWhatsapp) {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".png");

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
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (isWhatsapp) {
                    openWhatsapp(Uri.parse("file://" + file));
                } else {
                    sendMail(Uri.parse("file://" + file));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        return values;
    }

    private void saveImageToStream(Bitmap bitmap, OutputStream outputStream) {
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

    public void sendMail(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "AEPS Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "AEPS Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

    void openWhatsapp(Uri myUri) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "AEPS Receipt");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Receipt");
            sendIntent.setType("image/png");
            /*Uri myUri = Uri.parse("file://" + path);*/
            sendIntent.putExtra(Intent.EXTRA_STREAM, myUri);
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp"));
            startActivity(intent);


        }

    }

    /*public void saveBitmap(Bitmap bitmap) {
        // Create a media file name
        Log.v("first", "first");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        String filePath = Environment.getExternalStorageDirectory().toString()
                + "/" + timeStamp + ".jpg";
        File imagePath = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Log.v("first", "second");
            sendMail(filePath);
        } catch (FileNotFoundException e) {
            //Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            //Log.e("GREC", e.getMessage(), e);
        }
    }*/

    public void sendMail(String path) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "AEPS Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }


}
