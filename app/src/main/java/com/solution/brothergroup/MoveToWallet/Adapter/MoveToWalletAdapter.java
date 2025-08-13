package com.solution.brothergroup.MoveToWallet.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.solution.brothergroup.Api.Object.WalletType;
import com.solution.brothergroup.R;

import java.util.ArrayList;

public class MoveToWalletAdapter extends ArrayAdapter<WalletType> {

    Context mContext;
    ArrayList<WalletType> mWalletType;
    boolean isSource;


    public MoveToWalletAdapter(Context mContext, ArrayList<WalletType> mWalletType, boolean isSource) {
        super(mContext, 0, mWalletType);
        this.mContext = mContext;
        this.mWalletType = mWalletType;
        this.isSource = isSource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner_move_to_wallet, parent, false);
        }

        TextView walletType = (TextView) convertView.findViewById(R.id.tv_wallet_type);
        WalletType item = mWalletType.get(position);
        //icon.setImageResource(flags[i]);
        String name = "";
        if (isSource) {
            name = "From " + item.getFromWalletType() + " Wallet";
        } else {
            name = "To " + item.getToWalletType() + " Wallet";
        }

        walletType.setText(name);


        return convertView;
    }



}
