package com.solution.brothergroup.DMRNEW.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.solution.brothergroup.DMRNEW.dto.DialogItem;
import com.solution.brothergroup.R;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 29-12-2017.
 */

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.MyViewHolder> {

    List<ResolveInfo> activities;
    int resourceId = 0;
    CustomLoader loader;
    Dialog dialog = null;
    String path = "";
    Activity activity;
    RecyclerView lv_share;
    private List<DialogItem> operatorList;
    private Context mContext;

    public ShareAdapter(List<DialogItem> operatorList, Context mContext, List<ResolveInfo> activities, Activity activity, RecyclerView lv_share) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        this.activities = activities;
        this.activity = activity;
        this.lv_share = lv_share;
    }

    @Override
    public ShareAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_dialog_item, parent, false);

        loader = new CustomLoader(mContext, android.R.style.Theme_Translucent_NoTitleBar);

        return new ShareAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ShareAdapter.MyViewHolder holder, final int position) {
        final DialogItem operator = operatorList.get(position);
        final ResolveInfo info = activities.get(position);
        lv_share.setVisibility(View.VISIBLE);
        holder.tv.setText("");
        holder.tv.setTextSize(15.0f);
        holder.tv.setCompoundDrawablesWithIntrinsicBounds(operator.icon, null, null, null);
        int dp5 = (int) (5 * mContext.getResources().getDisplayMetrics().density + 0.5f);
        holder.tv.setCompoundDrawablePadding(dp5);
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_share.setVisibility(View.GONE);
                shareit();
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                        "Money Transaction");
                emailIntent.putExtra(Intent.EXTRA_TEXT,
                        "Receipt");
                emailIntent.setType("image/png");
                Uri myUri = Uri.parse("file://" + path);
                emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
                if (info.activityInfo.packageName.equals("com.facebook.katana")) {
                    Toast.makeText(mContext, "Facebook Selected ", Toast.LENGTH_LONG).show();
                } else {
                    // start the selected activity
                    Log.i("Tag", "Hi..hello. Intent is selected");
                    emailIntent.setPackage(info.activityInfo.packageName);
                    mContext.startActivity(emailIntent);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public void deleteDone() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void shareit() {
        File picFile = null;
        Bitmap myBitmap = null;
        View v1 = activity.getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
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
            path = filePath;
        } catch (FileNotFoundException e) {
            // Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            // Log.e("GREC", e.getMessage(), e);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tv);

        }
    }

}
