package com.solution.brothergroup.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.solution.brothergroup.R;
import com.solution.brothergroup.usefull.CustomLoader;

public class ChangePassUtils {
    Activity mActivity;
    private CustomLoader loader;
    private Dialog dialog;

    public ChangePassUtils(Activity mActivity) {
        this.mActivity = mActivity;
        loader = new CustomLoader(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
    }


    @SuppressLint("SetTextI18n")
    public void changePassword(final boolean isPin, boolean isCancelable) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.change_password, null);

        final TextInputLayout currentPasswordLayout = view.findViewById(R.id.currentPasswordLayout);
        final AppCompatTextView title = view.findViewById(R.id.title);
        final EditText currentPassword = view.findViewById(R.id.currentPassword);
        final TextInputLayout newPasswordLayout = view.findViewById(R.id.newPasswordLayout);
        final EditText newPassword = view.findViewById(R.id.newPassword);
        final TextInputLayout confirmPasswordLayout = view.findViewById(R.id.confirmPasswordLayout);
        final EditText confirmPassword = view.findViewById(R.id.confirmPassword);
        if (isPin) {
            title.setText("Change Pin");
            currentPasswordLayout.setHint("Current Password");
            newPasswordLayout.setHint("New Pin");
            confirmPasswordLayout.setHint("Confirm Pin");
        }
        final AppCompatButton okButton = view.findViewById(R.id.okButton);
        final AppCompatButton cancelButton = view.findViewById(R.id.cancelButton);
        if (isCancelable) {
            cancelButton.setVisibility(View.VISIBLE);
        } else {
            cancelButton.setVisibility(View.GONE);
        }
        dialog = new Dialog(mActivity);

        dialog.setTitle("Forgot Password");
        dialog.setCancelable(isCancelable);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilMethods.INSTANCE.isPassChangeDialogShowing = false;
                dialog.dismiss();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int flag = 0;

                if (currentPassword.getText() != null && currentPassword.getText().toString().trim().isEmpty()) {
                    flag++;
                    currentPassword.setError(mActivity.getResources().getString(R.string.password_error));
                    currentPassword.requestFocus();
                } else if (newPassword.getText() != null && newPassword.getText().toString().trim().isEmpty()) {
                    flag++;
                    newPassword.setError(mActivity.getResources().getString(R.string.password_error));
                    newPassword.requestFocus();
                } else if (newPassword.getText().toString().trim().equalsIgnoreCase(currentPassword.getText().toString().trim())) {
                    flag++;
                    newPassword.setError(mActivity.getResources().getString(R.string.samepass_error));
                    newPassword.requestFocus();
                } else if (confirmPassword.getText() != null && newPassword.getText() != null &&
                        !newPassword.getText().toString().trim().equalsIgnoreCase(confirmPassword.getText().toString().trim())) {
                    flag++;
                    confirmPassword.setError(mActivity.getResources().getString(R.string.newpass_error));
                    confirmPassword.requestFocus();
                }


                if (flag == 0) {
                    if (UtilMethods.INSTANCE.isNetworkAvialable(mActivity)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.ChangePinPassword(mActivity, isPin, currentPassword.getText().toString().trim()
                                , newPassword.getText().toString().trim(), confirmPassword.getText().toString().trim(), loader, dialog);
                    } else {
                        UtilMethods.INSTANCE.dialogOk(mActivity, mActivity.getResources().getString(R.string.err_msg_network_title),
                                mActivity.getResources().getString(R.string.err_msg_network), 2);
                    }
                }

            }
        });
        UtilMethods.INSTANCE.isPassChangeDialogShowing = true;
        dialog.show();
    }

    public Dialog returnDialog() {
        return dialog;
    }
}
