package com.solution.brothergroup.RECHARGEANDBBPS.UI;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.WindowInsetsControllerCompat;

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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.UtilMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SlipActivityRechargeReport extends AppCompatActivity {

    public AppCompatImageView operatorImage;
    TextView tvAmount;
    TextView bankBalance;
    TextView tvRechargeMobileNo, mobileNumberLabel;
    TextView tvliveId, tvTxnStatus;
    TextView tvoperatorname;
    TextView tvpdate;
    TextView tvptime;
    TextView tvtxid, outletDetail;
    TextView tvShare, address;
    LinearLayout shareView;
    RelativeLayout rlCancel;
    LinearLayout customer;
    String amount = "";
    String optional2="";
    String RechargeMobileNo = "";
    String liveId = "";
    String pdate = "";
    String ptime = "";
    String operatorname = "";
    String txid = "";
    String txStatus = "";
    String typerecharge = "";
    String imageurl = "";
    LinearLayout manin_lin;
    TextView CustomerNameText,CustomerNameLabel;

    LinearLayout llVia;
    private String oid;
    private LoginResponse LoginDataResponse;
    private String CustomerName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.recharge_report_print_popup);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        LoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        getIds();
    }

    private void getIds() {
        optional2= getIntent().getExtras().getString("optional2");
        amount = getIntent().getExtras().getString("amount");
        CustomerName= getIntent().getExtras().getString("o15");
        RechargeMobileNo = getIntent().getExtras().getString("RechargeMobileNo");
        liveId = getIntent().getExtras().getString("liveId");
        operatorname = getIntent().getExtras().getString("operatorname");
        pdate = getIntent().getExtras().getString("pdate");
        ptime = getIntent().getExtras().getString("ptime");
        txid = getIntent().getExtras().getString("txid");
        txStatus = getIntent().getExtras().getString("txStatus");
        typerecharge = getIntent().getExtras().getString("typerecharge");
        imageurl = getIntent().getExtras().getString("imageurl");
        oid = getIntent().getExtras().getString("OID", null);
        outletDetail = findViewById(R.id.outletDetail);
        CustomerNameText=findViewById(R.id.CustomerNameText);
        CustomerNameLabel=findViewById(R.id.CustomerNameLabel);
        setOutletDetail();
        shareView = findViewById(R.id.shareView);
        manin_lin = findViewById(R.id.manin_lin);
        operatorImage = findViewById(R.id.operatorImage);
        rlCancel = findViewById(R.id.rl_cancel);
        llVia = findViewById(R.id.ll_via);
        customer=findViewById(R.id.customer);
        tvTxnStatus = findViewById(R.id.tv_txstatus);
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        address = findViewById(R.id.address);
        AppUserListResponse companyData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this), AppUserListResponse.class);

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
        tvAmount = findViewById(R.id.tv_amount);
        bankBalance=findViewById(R.id.bankBalance);
        tvRechargeMobileNo = findViewById(R.id.tv_RechargeMobileNo);
        mobileNumberLabel = findViewById(R.id.mobileNumberLabel);
        tvliveId = findViewById(R.id.tv_liveId);
        tvoperatorname = findViewById(R.id.tv_operatorname);
        tvpdate = findViewById(R.id.tv_pdate);
        tvptime = findViewById(R.id.tv_ptime);

        tvShare = (TextView) findViewById(R.id.tv_share);
        tvShare = (TextView) findViewById(R.id.tv_share);
        tvtxid = findViewById(R.id.tv_txid);

       /* if (typerecharge.equalsIgnoreCase("SUCCESS")) {
            manin_lin.setVisibility(View.VISIBLE);

        } else {
            manin_lin.setVisibility(View.GONE);

        }*/

        if (imageurl != null) {
            Glide.with(this).load(imageurl)
                    .thumbnail(0.5f)
                    .transition(new DrawableTransitionOptions().crossFade())
                    .apply(new RequestOptions().placeholder(R.drawable.ic_operator_default_icon).error(R.drawable.ic_operator_default_icon).diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(operatorImage);
        } else {
            //operatorImage.operatorImage.setImageResource(R.drawable.ic_operator_default_icon);
        }

        tvAmount.setText(getString(R.string.rupiya) + " " + amount);
        bankBalance.setText(getString(R.string.rupiya)+""+optional2);
        CustomerNameText.setText(CustomerName);
        tvRechargeMobileNo.setText(RechargeMobileNo);
        tvliveId.setText(liveId);
        tvpdate.setText(pdate);
        tvptime.setText(ptime);
        tvoperatorname.setText(operatorname);
        tvtxid.setText(txid);
        tvTxnStatus.setText(txStatus);

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareit();
            }
        });

        if (oid != null && !oid.isEmpty() && !oid.equalsIgnoreCase("0")) {
            NumberListResponse mNumberListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getNumberList(this), NumberListResponse.class);

            getOperator(mNumberListResponse);
        }

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

    void getOperator(NumberListResponse response) {


        if (response != null && response.getData() != null && response.getData().getOperators() != null &&
                response.getData().getOperators().size() > 0) {
            for (OperatorList op : response.getData().getOperators()) {
                if ((op.getOid() + "").equalsIgnoreCase(oid)) {
                    if (op.getAccountName() != null && !op.getAccountName().isEmpty()) {
                        mobileNumberLabel.setText(op.getAccountName());
                        break;
                    }

                }
            }
        } else {
            UtilMethods.INSTANCE.NumberList(this, null, object -> {
                NumberListResponse operatorListResponse = (NumberListResponse) object;
                if (operatorListResponse != null && operatorListResponse.getData() != null &&
                        operatorListResponse.getData().getOperators() != null &&
                        operatorListResponse.getData().getOperators().size() > 0) {
                    getOperator(operatorListResponse);
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
        saveBitmap(myBitmap);

    }

    private void saveBitmap(Bitmap bitmap) {
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


                    sendMail(uri);

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

                sendMail(Uri.parse("file://" + file));

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
                "Recharge & Bill Payment Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Recharge & Bill Payment Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

   /* public void saveBitmap(Bitmap bitmap) {
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
            // Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            // Log.e("GREC", e.getMessage(), e);
        }
    }*/

    public void sendMail(String path) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Recharge Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

}
