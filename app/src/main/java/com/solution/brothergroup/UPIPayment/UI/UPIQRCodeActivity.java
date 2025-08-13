package com.solution.brothergroup.UPIPayment.UI;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.UserQRInfo;
import com.solution.brothergroup.Api.Request.MapQRToUserRequest;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.QRScan.Activity.QRScanActivity;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.CustomAlertDialog;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class UPIQRCodeActivity extends AppCompatActivity {
    TextView custCare, detail, upiId, OutletName;
    ImageView qrcode,cameraView;
    private CustomLoader loader;
    View btnView;
    boolean isDownload;
    LinearLayout shareView;
    private int REQUEST_PERMISSIONS = 1234;
    private Snackbar mSnackBar;
    View titleView;
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    private boolean isECollectEnable;
    private LoginResponse LoginDataResponse;
    private int INTENT_SCAN=9854;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        custCare = findViewById(R.id.custCare);
        upiId = findViewById(R.id.upiId);
        btnView = findViewById(R.id.btnView);
        qrcode = findViewById(R.id.qrcode);
        cameraView = findViewById(R.id.cameraView);
        titleView = findViewById(R.id.titleView);
        detail = findViewById(R.id.detail);
        OutletName = findViewById(R.id.OutletName);
        shareView = findViewById(R.id.shareView);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        String mLoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
         LoginDataResponse = new Gson().fromJson(mLoginResponse, LoginResponse.class);
        OutletName.setText(LoginDataResponse.getData().getName() + "");
        OutletName.setVisibility(View.VISIBLE);
        isECollectEnable = getIntent().getBooleanExtra("isECollectEnable", false);
        boolean isQRMappedToUser = getIntent().getBooleanExtra("isQRMappedToUser", false);
        boolean isBulkQRGeneration = getIntent().getBooleanExtra("isBulkQRGeneration", false);
        if (isBulkQRGeneration && !isQRMappedToUser) {
            cameraView.setVisibility(View.VISIBLE);
        }else {
            cameraView.setVisibility(View.GONE);
        }
        setQRCode();
       

        AppUserListResponse companyData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(this), AppUserListResponse.class);
        if (companyData != null && companyData.getCompanyProfile() != null) {
            String value = "";
            if (companyData.getCompanyProfile().getCustomerCareMobileNos() != null && !companyData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                value = companyData.getCompanyProfile().getCustomerCareMobileNos();
            }
            if (companyData.getCompanyProfile().getCustomerPhoneNos() != null && !companyData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
                value = value + ", " + companyData.getCompanyProfile().getCustomerPhoneNos();
            }
            if (companyData.getCompanyProfile().getCustomerWhatsAppNos() != null && !companyData.getCompanyProfile().getCustomerWhatsAppNos().isEmpty()) {
                value = value + ", " + companyData.getCompanyProfile().getCustomerWhatsAppNos();
            }

            custCare.setText("Customer Care - " + value);
        } else {
            custCare.setVisibility(View.GONE);
        }

        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDownload = true;
                shareit();
            }
        });

        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDownload = false;
                shareit();
            }
        });
        cameraView.setOnClickListener((View.OnClickListener) v ->
                startActivityForResult(new Intent(this, QRScanActivity.class), INTENT_SCAN));

        if (isECollectEnable) {
            UtilMethods.INSTANCE.GetVADetails(this, loader, LoginDataResponse,  object -> {
                UserQRInfo mUserQRInfo = (UserQRInfo) object;
                if (mUserQRInfo != null) {
                    titleView.setVisibility(View.GONE);
                    detail.setVisibility(View.VISIBLE);
                    detail.setText("BANK : " + mUserQRInfo.getBankName() + "\n" +
                            "IFSC : " + mUserQRInfo.getIfsc() + "\n" +
                            "Virtual Account : " + mUserQRInfo.getVirtualAccount());


                } else {
                    titleView.setVisibility(View.VISIBLE);
                    detail.setVisibility(View.GONE);
                }
            });
        } else {
            titleView.setVisibility(View.VISIBLE);
            detail.setVisibility(View.GONE);
        }

    }

    private void setQRCode() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);
        requestOptions.placeholder(R.drawable.rnd_logo);
        requestOptions.error(R.drawable.nodata);
        Glide.with(this).load(ApplicationConstant.INSTANCE.baseQrImageUrl + LoginDataResponse.getData().getUserID() +
                "&appid=" + ApplicationConstant.INSTANCE.APP_ID + "&imei=" + UtilMethods.INSTANCE.getIMEI(this) + "&regKey=&version=" + BuildConfig.VERSION_NAME +
                "&serialNo=" + UtilMethods.INSTANCE.getSerialNo(this) + "&sessionID=" + LoginDataResponse.getData().getSessionID() +
                "&session=" + LoginDataResponse.getData().getSession() + "&loginTypeID=" + LoginDataResponse.getData().getLoginTypeID()).
                apply(requestOptions)

                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        btnView.setVisibility(View.VISIBLE);
                        return false;
                    }
                })
                .into(qrcode);
    }


    public void shareit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_PERMISSIONS);

            } else {
                getBitmap();
            }
        } else {
            getBitmap();
        }


    }

    void getBitmap() {
        shareView.setDrawingCacheEnabled(true);
        Bitmap myBitmap = shareView.getDrawingCache();
        saveBitmap(isDownload, myBitmap);
        shareView.setDrawingCacheEnabled(false);
    }

    private void saveBitmap(boolean isDownload, Bitmap bitmap) {
        if (android.os.Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "QR_CODE");

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);
                    if (isDownload) {
                        Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                        MediaScannerConnection.scanFile(this, new String[]{uri.getPath()}, new String[]{"image/png"}, null);
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
            String fileName = "QR_CODE.png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (isDownload) {
                    Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                    MediaScannerConnection.scanFile(this, new String[]{file.getPath()}, new String[]{"image/png"}, null);
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
            } catch (IOException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

   /* public void saveBitmap(boolean isDownload, Bitmap bitmap) {

        File filePath = new File(Environment.getExternalStorageDirectory().toString() + "/" + getString(R.string.app_name));

        if (!filePath.exists()) {
            filePath.mkdir();
        }
        File imagePath = new File(filePath + "/Virtual_Account.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            if (isDownload) {
                Toast.makeText(this, "Successfully Download", Toast.LENGTH_SHORT).show();
                MediaScannerConnection.scanFile(this, new String[]{imagePath.getPath()}, new String[]{"image/jpeg"}, null);
            } else {
                sendMail(imagePath.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public void sendMail(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Virtual Account");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Virtual Account");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {

            int permissionCheck = PackageManager.PERMISSION_GRANTED;
            for (int permission : grantResults) {
                permissionCheck = permissionCheck + permission;
            }
            if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {

                shareit();
            } else {
                showWarningSnack(R.string.str_ShowOnPermisstionDenied, "Enable", true);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }


    void showWarningSnack(int stringId, String btn, final boolean isForSetting) {
        if (mSnackBar != null && mSnackBar.isShown()) {
            return;
        }
        mSnackBar = Snackbar.make(findViewById(android.R.id.content), stringId,
                Snackbar.LENGTH_INDEFINITE).setAction(btn,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isForSetting) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        } else {
                            ActivityCompat.requestPermissions(UPIQRCodeActivity.this, PERMISSIONS, REQUEST_PERMISSIONS);
                        }

                    }
                });

        mSnackBar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        TextView mainTextView = (TextView) (mSnackBar.getView()).
                findViewById(R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._12sdp));
        mainTextView.setMaxLines(4);
        mSnackBar.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String codeValue = data.getStringExtra("codeValue");
                Uri mUri = Uri.parse(codeValue);
                String pa = mUri.getQueryParameter("pa");
                String pn = mUri.getQueryParameter("pn");
                String tr = mUri.getQueryParameter("tr");
                String mc = mUri.getQueryParameter("mc");
                if (pa != null && mc != null && tr != null && pn != null) {


                    new CustomAlertDialog(this, true).WarningWithDoubleBtnCallBack(pn + " : " + pa, "QR Detais", "Link", true, new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            MapQRToUser(UPIQRCodeActivity.this,codeValue,loader);
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                }
            }


        }
    }

    public void MapQRToUser(final Activity context, String qrData, final CustomLoader loader) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.MapQRToUser(new MapQRToUserRequest(qrData,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null) {
                        if (response.body().getStatuscode() == 1) {
                            UtilMethods.INSTANCE.Successful(context, response.body().getMsg() + "");
                            cameraView.setVisibility(View.GONE);
                            setQRCode();
                        } else {
                            if (response.body().getVersionValid() == false) {
                                UtilMethods.INSTANCE.versionDialog(context);
                            } else {
                                UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();

                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                UtilMethods.INSTANCE.Error(context, t.getMessage());

                            }

                        } else {
                            UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            UtilMethods.INSTANCE.Error(context, e.getMessage());
        }

    }

}
