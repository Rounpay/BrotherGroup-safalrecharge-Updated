package com.solution.brothergroup.Util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import com.google.android.material.snackbar.Snackbar;
import com.solution.brothergroup.R;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class RunTimePermissionCheck {
    public Snackbar mSnackBar;
    private AlertDialog permisssionDialog;
    public int REQUEST_PERMISSIONS = 20;
    private AlertDialog alert;

    public void checkPhonePermission(final Activity mContext) {
        try {
            requestAppPermissions(mContext, new String[]{Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    R.string.str_ShowOnPermisstion,
                    REQUEST_PERMISSIONS,false);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




    public void requestAppPermissions(final Activity mContext, final String[] requestedPermissions,
                                      final int stringId, final int requestCode, boolean isFromDialog) {

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(mContext, permission);
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale ||
                    ActivityCompat.shouldShowRequestPermissionRationale(mContext, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
                if (isFromDialog) {
                    ActivityCompat.requestPermissions(mContext, requestedPermissions, requestCode);
                } else {

                    permissionDenyDialog(mContext, "Grant permissions", mContext.getString(stringId), "Grant", false, requestedPermissions, requestCode);
                }
            } else {
                ActivityCompat.requestPermissions(mContext, requestedPermissions, requestCode);
            }
        } else {
//            createFolder();
            //onSuccess permission grant
//            onPermissionsGranted(requestCode);
        }
    }


    public void onRequestPermissionsResult(final Activity mActivity, int requestCode, String[] permissions, int[] grantResults, RequestSucess requestSucess) {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {

            if (requestSucess != null) {

                requestSucess.sucess("");
            }


        } else {
            permissionDenyDialog(mActivity, "Permission denied", mActivity.getString(R.string.str_ShowOnPermisstionDenied), "Enable", true, null, 0);

        }
    }

    void permissionDenyDialog(final Activity mActivity, String Title, String Msg, String button, final boolean isEnable, final String[] requestedPermissions, final int requestCode) {
        if (permisssionDialog != null && permisssionDialog.isShowing()) {
            return;
        }
        permisssionDialog = new AlertDialog.Builder(mActivity)
                .setTitle(Title)
                .setCancelable(false)
                .setMessage(Msg)
                .setPositiveButton(button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isEnable) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + mActivity.getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            mActivity.startActivity(intent);
                        } else {
                            ActivityCompat.requestPermissions(mActivity,
                                    requestedPermissions, requestCode);
                        }
                    }
                })

                .show();
    }


    public interface RequestSucess {
        void sucess(String key);
    }
}