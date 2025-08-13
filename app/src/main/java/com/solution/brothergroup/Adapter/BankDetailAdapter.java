package com.solution.brothergroup.Adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.Api.Object.Bank;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.CustomAlertDialog;

import java.util.List;

public class BankDetailAdapter extends RecyclerView.Adapter<BankDetailAdapter.MyViewHolder> {

    CustomAlertDialog mCustomAlertDialog;
    private List<Bank> operatorList;
    private Activity mContext;
RequestOptions requestOptions;
    public BankDetailAdapter(List<Bank> operatorList, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        mCustomAlertDialog = new CustomAlertDialog(mContext, true);
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.circle_logo);
        requestOptions.error(R.drawable.circle_logo);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public BankDetailAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_bank_list, parent, false);

        return new BankDetailAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BankDetailAdapter.MyViewHolder holder, int position) {
        final Bank operator = operatorList.get(position);
        holder.bankName.setText(operator.getBankName());
        holder.accountHolder.setText(operator.getAccountHolder());
        holder.accountNumber.setText(operator.getAccountNo());
        holder.ifscCode.setText(operator.getIfscCode());
        holder.branchName.setText(operator.getBranchName());

        Glide.with(mContext)
                .load(ApplicationConstant.INSTANCE.basebankLogoUrl + operator.getLogo().replaceAll(" ", "%20"))
                .apply(requestOptions)
                .into(holder.bankLogo);


        holder.shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(operator);
            }
        });

        holder.msgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReport(operator.getId() + "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    void sendReport(String reportId) {
        if (mCustomAlertDialog != null) {
            mCustomAlertDialog.sendReportDialog(2,"", new CustomAlertDialog.DialogSingleCallBack() {
                @Override
                public void onPositiveClick(String mobile, String emailId) {

                }
            });
        }
    }

    void share(Bank operator) {
        String shareContent = "Bank Name : " + operator.getBankName() + "\n" +
                "A/C No : " + operator.getAccountNo() + "\n" +
                "Account Holder : " + operator.getAccountHolder() + "\n" +
                "Branch Name : " + operator.getBranchName() + "\n" +
                "IFSC Code : " + operator.getIfscCode() + "\n";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        mContext.startActivity(shareIntent);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView bankLogo;
        private AppCompatTextView bankName;
        private AppCompatTextView accountNumber;
        private AppCompatTextView accountHolder;
        private AppCompatTextView branchName;
        private AppCompatTextView ifscCode;
        private ImageView shareIv, msgIv;


        public MyViewHolder(View view) {
            super(view);
            bankLogo = view.findViewById(R.id.bankLogo);
            bankName = view.findViewById(R.id.bankName);
            accountNumber = view.findViewById(R.id.accountNumber);
            accountHolder = view.findViewById(R.id.accountHolder);
            branchName = view.findViewById(R.id.branchName);
            ifscCode = view.findViewById(R.id.ifscCode);
            shareIv = view.findViewById(R.id.share);
            msgIv = view.findViewById(R.id.msg);
        }
    }
}



/*
public class BankListScreenAdapter extends RecyclerView.Adapter<BankListScreenAdapter.MyViewHolder> {

    private ArrayList<BankListObject> operatorList;
    private Context mContext;
    int resourceId = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView opImage;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            opImage = (ImageView) view.findViewById(R.id.opImage);

        }
    }

    public BankListScreenAdapter(ArrayList<BankListObject> operatorList, Context mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public BankListScreenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bank_list_adapter, parent, false);

        return new BankListScreenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BankListScreenAdapter.MyViewHolder holder, int position) {
        final BankListObject operator = operatorList.get(position);
        holder.title.setText(operator.getBankName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BankListScreen) mContext).ItemClick(operator.getBankId(), operator.getBankName(), operator.getAccVeri(), operator.getShortCode());
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

}
*/
