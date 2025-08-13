package com.solution.brothergroup.UPIPayment.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.Activities.PaymentRequest;
import com.solution.brothergroup.Api.Object.Bank;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QrCodeActivity extends AppCompatActivity {

    private LinearLayout shareView;
    private AppCompatImageView bankLogo;
    private AppCompatTextView bankName;
    private AppCompatTextView name;
    private AppCompatTextView number;
    private AppCompatImageView qrLogo;
    Bank selectedBank;
    int fromIntent;
    int parentIdIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        fromIntent = getIntent().getIntExtra("FROMINTENT", 0);
        parentIdIntent = getIntent().getIntExtra("ParentId", 0);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        setTitle("Scan & Pay");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shareView = findViewById(R.id.mainView);
        bankLogo = (AppCompatImageView) findViewById(R.id.bankLogo);
        bankName = (AppCompatTextView) findViewById(R.id.bankName);
        name = (AppCompatTextView) findViewById(R.id.name);
        number = (AppCompatTextView) findViewById(R.id.number);
        qrLogo = (AppCompatImageView) findViewById(R.id.qrLogo);


        final Bank selectedBank = (Bank) getIntent().getSerializableExtra("SelectedBank");


        if (selectedBank != null) {
            bankName.setText(selectedBank.getBankName() + "");
            name.setText(selectedBank.getAccountHolder() + "");
            number.setText(selectedBank.getAccountNo() + "");

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.defaultlogo);
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.basebankLogoUrl + selectedBank.getLogo() + "")
                    .apply(requestOptions).into(bankLogo);

            //ApplicationConstant.INSTANCE.baseQrLogoUrl + selectedBank.getId() + ".png"
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseQrLogoUrl + selectedBank.getrImageUrl())
                    .apply(requestOptions).into(qrLogo);
        }

        findViewById(R.id.paymentRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromIntent == 1) {
                    finish();
                } else {
                    Intent i = new Intent(QrCodeActivity.this, PaymentRequest.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    i.putExtra("SelectedBank", selectedBank);
                    i.putExtra("ParentId", parentIdIntent);
                    startActivity(i);
                }
            }
        });
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
            share(filePath);
        } catch (FileNotFoundException e) {
            // Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            // Log.e("GREC", e.getMessage(), e);
        }
    }

    public void share(String path) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT,
                "Scan & Pay");
        intent.putExtra(Intent.EXTRA_TEXT,
                "QR Code");
        intent.setType("image/*");
        Uri myUri = Uri.parse("file://" + path);
        intent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(intent, "Share via..."));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            shareit();
        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
