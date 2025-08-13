package com.solution.brothergroup.Adapter;

import android.app.Activity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solution.brothergroup.Api.Object.TargetAchieved;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.CustomAlertDialog;

import java.util.List;

public class AchievedTargetAdapter extends RecyclerView.Adapter<AchievedTargetAdapter.MyViewHolder> {

    CustomAlertDialog mCustomAlertDialog;
    private List<TargetAchieved> operatorList;
    private Activity mContext;

    public AchievedTargetAdapter(List<TargetAchieved> operatorList, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
        mCustomAlertDialog = new CustomAlertDialog(mContext, true);
    }

    @Override
    public AchievedTargetAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_achieved_target, parent, false);

        return new AchievedTargetAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AchievedTargetAdapter.MyViewHolder holder, int position) {
        final TargetAchieved operator = operatorList.get(position);
        holder.service.setText(operator.getService()+"");

      //  holder.todaySales.setText(operator.getIfscCode());
        int remainigTarget = (int) (operator.getTarget() - operator.getTargetTillDate());
        if(remainigTarget<0){
            holder.remainTarget.setTextColor(mContext.getResources().getColor(R.color.darkGreen));
        }
        holder.remainTarget.setText(mContext.getResources().getString(R.string.rupiya) + " " +  remainigTarget);
        holder.salesTarget.setText(mContext.getResources().getString(R.string.rupiya) + " " + (int)operator.getTarget());
        holder.todaySales.setText(mContext.getResources().getString(R.string.rupiya) + " " + (int) operator.getTodaySale());
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView service;
        private AppCompatTextView salesTarget;
        private AppCompatTextView todaySales;
        private AppCompatTextView remainTarget;



        public MyViewHolder(View view) {
            super(view);
            service = view.findViewById(R.id.service);
            salesTarget = view.findViewById(R.id.salesTarget);
            todaySales = view.findViewById(R.id.todaySales);
            remainTarget = view.findViewById(R.id.remainTarget);
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
