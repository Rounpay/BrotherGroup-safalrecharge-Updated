package com.solution.brothergroup.DMRNEW.ui;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.DMTReceiptResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.ListOblect;
import com.solution.brothergroup.Util.UtilMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DMRReciept extends AppCompatActivity {
    String response, flag;
    TextView Name, companyDetail, BankName, senderNo, AccountNo, ifsc, Date, outletDetail, mode;
    TextView cancel;
    RecyclerView list;

    DMTReceiptResponse dmtReceiptResponse;
    List<ListOblect> listOblects;
    List<ListOblect> listOblectsnew;
    DmrRecieptAdapter mAdapter;
    TextView tv_share;
    private LoginResponse LoginDataResponse;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dmr_reciept);
        response = getIntent().getStringExtra("response");
        flag = getIntent().getStringExtra("flag");
         LoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        getID();
        try {
            if (flag.equals("All")) {
                dataparse();

            } else {

                dataparse2();
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
        }else {
            int wid = LoginDataResponse.getData().getWid();
            if (wid > 0) {

                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseAppIconUrl + wid + "/logo.png")
                        .apply(requestOptions)
                        .into(logoIv);
            }
        }
        setOutletDetail();
        setCompanyDetail();
    }

    private void setCompanyDetail() {
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

        //companyDetail.setText(company);
    }


    private void dataparse() throws ParseException {

        Gson gson = new Gson();
        dmtReceiptResponse = gson.fromJson(response, DMTReceiptResponse.class);
        listOblects = dmtReceiptResponse.getTransactionDetail().getLists();
        if (listOblects.size() > 0) {
            if (flag.equals("All")) {
                mAdapter = new DmrRecieptAdapter(listOblects, DMRReciept.this);
            } else {
                listOblectsnew = new ArrayList<>();
                for (int i = 0; i < listOblects.size(); i++) {
                    String status = listOblects.get(i).getStatuscode() + "";
                    if (status.equals("2")) {
                        listOblectsnew.add(listOblects.get(i));
                    }
                }
                mAdapter = new DmrRecieptAdapter(listOblectsnew, DMRReciept.this);
            }
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            list.setLayoutManager(mLayoutManager);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(mAdapter);
        }
        Name.setText(dmtReceiptResponse.getTransactionDetail().getBeneName());
        BankName.setText(dmtReceiptResponse.getTransactionDetail().getBankName());
        mode.setText(dmtReceiptResponse.getTransactionDetail().getChannel()+"");
        senderNo.setText(dmtReceiptResponse.getTransactionDetail().getSenderNo());
        AccountNo.setText(dmtReceiptResponse.getTransactionDetail().getAccount());
        Date.setText("Your Transaction Detail On " + dmtReceiptResponse.getTransactionDetail().getEntryDate());
        ifsc.setText(dmtReceiptResponse.getTransactionDetail().getIfsc());
        // Email.setText(dmtReceiptResponse.getTransactionDetail().getEmail());
    }

    private void dataparse2() {
        Gson gson = new Gson();
        listOblectsnew = new ArrayList<>();
        dmtReceiptResponse = gson.fromJson(response, DMTReceiptResponse.class);
        listOblects = dmtReceiptResponse.getTransactionDetail().getLists();
        for (int i = 0; i < listOblects.size(); i++) {
            String status = listOblects.get(i).getStatuscode() + "";
            if (status.equals("2")) {
                listOblectsnew.add(listOblects.get(i));
            }
        }
        if (listOblectsnew.size() > 0) {
            mAdapter = new DmrRecieptAdapter(listOblectsnew, DMRReciept.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            list.setLayoutManager(mLayoutManager);
            list.setItemAnimator(new DefaultItemAnimator());
            list.setAdapter(mAdapter);
            Name.setText(dmtReceiptResponse.getTransactionDetail().getBeneName());
            mode.setText(dmtReceiptResponse.getTransactionDetail().getChannel()+"");
            BankName.setText(dmtReceiptResponse.getTransactionDetail().getBankName());
            senderNo.setText(dmtReceiptResponse.getTransactionDetail().getSenderNo());
            AccountNo.setText(dmtReceiptResponse.getTransactionDetail().getAccount());
            Date.setText("Your Transaction Detail On " + dmtReceiptResponse.getTransactionDetail().getEntryDate());
            ifsc.setText(dmtReceiptResponse.getTransactionDetail().getIfsc());
            // Email.setText(dmtReceiptResponse.getTransactionDetail().getEmail());

        } else {
            UtilMethods.INSTANCE.Error(this, "No Transaction to print");
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

    private void getID() {
        outletDetail = findViewById(R.id.outletDetail);
        Name = findViewById(R.id.Name);
      //  companyDetail = findViewById(R.id.companyDetail);
        BankName = findViewById(R.id.BankName);
        senderNo = findViewById(R.id.senderNo);

        AccountNo = findViewById(R.id.AccountNo);
        mode = findViewById(R.id.mode);
        Date = findViewById(R.id.Date);
        ifsc = findViewById(R.id.ifsc);
        list = findViewById(R.id.list);
        tv_share = findViewById(R.id.tv_share);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareit();
            }
        });
    }

    public void shareit() {
        /*File picFile = null;
        Bitmap myBitmap = null;

        // View v1 = getWindow().getDecorView().getRootView();
        mainView.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(mainView.getDrawingCache());
        mainView.setDrawingCacheEnabled(false);
        saveBitmap(myBitmap);*/

        File picFile=null;
        Bitmap myBitmap=null;

        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
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
                "Money Transfer Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Money Transfer Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

    void openWhatsapp(Uri myUri) {

        try {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Money Transfer Receipt");
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

 /*   public void saveBitmap(Bitmap bitmap) {
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
    }

   */ public void sendMail(String path) {
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

    /*public void sendMail() {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Money Transaction");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + "");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        final List<ResolveInfo> activities = getPackageManager().queryIntentActivities(emailIntent, 0);
        List<DialogItem> appNames = new ArrayList<DialogItem>();

        for (ResolveInfo info : activities) {
            appNames.add(new DialogItem(info.loadLabel(getPackageManager()).toString(),
                    info.loadIcon(getPackageManager())));
        }
        final List<DialogItem> newItem = appNames;
        ShareAdapter adapter = new ShareAdapter(newItem, this, activities, DMRReciept.this, lv_share);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        lv_share.setLayoutManager(gridLayoutManager);
        lv_share.setAdapter(adapter);
    }*/
}
