package com.solution.brothergroup.Util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.widget.ImageViewCompat;

import com.solution.brothergroup.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RechargeStatusActivity extends AppCompatActivity {
    private RelativeLayout topView;
    private LinearLayout statusBg;
    ImageView bbps_logo;
    private AppCompatImageView closeIv;
    private AppCompatImageView statusIcon;
    private AppCompatTextView statusTv;
    private AppCompatTextView statusMsg;
    private AppCompatTextView opLable;
    private AppCompatTextView opTv;
    private AppCompatTextView numLabel;
    private AppCompatTextView numTv;
    private AppCompatTextView amtLabel;
    private AppCompatTextView amtTv;
    private AppCompatTextView comLabel;
    private AppCompatTextView comTv;
    private AppCompatTextView liveLabel;
    private AppCompatTextView liveTv;
    private AppCompatTextView txnLabel;
    private AppCompatTextView txnTv;
    private AppCompatTextView dateLabel;
    private AppCompatTextView dateTv, packageTv;
    private AppCompatTextView timeLabel;
    private AppCompatTextView timeTv;
    private AppCompatButton shareBtn, repetBtn;
    LinearLayout shareView;
    private String intentMsg, intentLiveID, intentTxnId, intentOpName, intentAmt, intentNum, intentPackageName;
    private int intentStatus;
    boolean intentIsBBPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_recharge_status);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        intentMsg = getIntent().getStringExtra("MESSAGE");
        intentStatus = getIntent().getIntExtra("STATUS",0);
        intentLiveID = getIntent().getStringExtra("LIVE_ID");
        intentTxnId = getIntent().getStringExtra("TRANSACTION_ID");
        intentOpName = getIntent().getStringExtra("OP_NAME");
        intentAmt = getIntent().getStringExtra("AMOUNT");
        intentNum = getIntent().getStringExtra("NUMBER");
        intentIsBBPS = getIntent().getBooleanExtra("IsBBPS",false);
        intentPackageName = getIntent().getStringExtra("PACKAGE_NAME");
        findViews();
        setUiData();
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityActivityMessage activityActivityMessage =
                        new ActivityActivityMessage("", "refreshvalue");
                GlobalBus.getBus().post(activityActivityMessage);
                finish();
            }
        });

        findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityActivityMessage activityActivityMessage =
                        new ActivityActivityMessage("", "refreshvalue");
                GlobalBus.getBus().post(activityActivityMessage);

                finish();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareit();
            }
        });

        repetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityActivityMessage activityActivityMessage =
                        new ActivityActivityMessage("", "repeatValue");
                GlobalBus.getBus().post(activityActivityMessage);
                finish();
            }
        });
    }


    private void findViews() {
        topView = findViewById(R.id.topView);
        shareView = findViewById(R.id.shareView);
        statusBg = findViewById(R.id.statusBg);
        closeIv = findViewById(R.id.closeIv);
        statusIcon = findViewById(R.id.statusIcon);
        statusTv = findViewById(R.id.statusTv);
        statusMsg = findViewById(R.id.statusMsg);
        bbps_logo= findViewById(R.id.bbps_logo);
        opLable = findViewById(R.id.opLable);
        opTv = findViewById(R.id.opTv);
        numLabel = findViewById(R.id.numLabel);
        numTv = findViewById(R.id.numTv);
        amtLabel = findViewById(R.id.amtLabel);
        amtTv = findViewById(R.id.amtTv);
        comLabel = findViewById(R.id.comLabel);
        comTv = findViewById(R.id.comTv);
        liveLabel = findViewById(R.id.liveLabel);
        liveTv = findViewById(R.id.liveTv);
        txnLabel = findViewById(R.id.txnLabel);
        txnTv = findViewById(R.id.txnTv);
        packageTv = findViewById(R.id.packageTv);
        dateLabel = findViewById(R.id.dateLabel);
        dateTv = findViewById(R.id.dateTv);
        timeLabel = findViewById(R.id.timeLabel);
        timeTv = findViewById(R.id.timeTv);
        shareBtn = findViewById(R.id.shareBtn);
        repetBtn = findViewById(R.id.repetBtn);

    }


    void setUiData() {
        if (intentStatus==1) {
            statusIcon.setImageResource(R.drawable.ic_pending);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_orange));
            ViewCompat.setBackgroundTintList(statusBg, AppCompatResources.getColorStateList(this, R.color.color_orange));
            statusTv.setTextColor(getResources().getColor(R.color.color_orange));
            statusTv.setText("Processing");
            statusMsg.setText("Transaction with reference id " + intentTxnId + " under process");
            repetBtn.setVisibility(View.GONE);
        } else if (intentStatus==2) {
            statusIcon.setImageResource(R.drawable.ic_check_mark);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.green));
            ViewCompat.setBackgroundTintList(statusBg, AppCompatResources.getColorStateList(this, R.color.green));
            statusTv.setTextColor(getResources().getColor(R.color.green));
            statusTv.setText("Success");
            statusMsg.setText("Transaction with reference id " + intentTxnId + " Completed successfully");
            repetBtn.setVisibility(View.GONE);
        } else {
            statusIcon.setImageResource(R.drawable.ic_cross_mark);
            ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
            ViewCompat.setBackgroundTintList(statusBg, AppCompatResources.getColorStateList(this, R.color.color_red));
            statusTv.setTextColor(getResources().getColor(R.color.color_red));
            statusTv.setText("Failed");
            statusMsg.setText(intentMsg);
            repetBtn.setVisibility(View.VISIBLE);
        }
        // statusMsg.setText(intentMsg);

        if(intentIsBBPS){
            bbps_logo.setVisibility(View.VISIBLE);
        }else{
            bbps_logo.setVisibility(View.GONE);
        }

        opTv.setText(intentOpName + "");
        amtTv.setText(getString(R.string.rupiya) + " " + intentAmt);
        liveTv.setText(intentLiveID + "");
        txnTv.setText(intentTxnId + "");
        //comTv.setText(intentComm + "");
        numTv.setText(intentNum + "");
        if (intentPackageName != null && !intentPackageName.isEmpty()) {
            packageTv.setVisibility(View.VISIBLE);
            packageTv.setText(intentPackageName + "");
        } else {
            packageTv.setVisibility(View.GONE);
        }


        SimpleDateFormat sdfDate = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm aa");

        try {
            String dateStr = sdfDate.format(new Date());
            dateTv.setText(dateStr);
        } catch (Exception e) {

        }

        try {
            String timeStr = sdfTime.format(new Date());
            dateTv.setText(dateTv.getText() + " " + timeStr);
            timeTv.setText(timeStr);
        } catch (Exception e) {

        }

    }

    public void shareit() {
        File picFile = null;
        Bitmap myBitmap = null;

        //  View v1 = getWindow().getDecorView().getRootView();
        shareView.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(shareView.getDrawingCache());
        shareView.setDrawingCacheEnabled(false);
        saveBitmap(myBitmap);


        //  Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.userimage);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                myBitmap, "Title", null);
        Uri imageUri = Uri.parse(path);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Select"));

    }

    public void saveBitmap(Bitmap bitmap) {
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
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

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

    @Override
    public void onBackPressed() {
        ActivityActivityMessage activityActivityMessage =
                new ActivityActivityMessage("", "refreshvalue");
        GlobalBus.getBus().post(activityActivityMessage);
        super.onBackPressed();
    }
}
