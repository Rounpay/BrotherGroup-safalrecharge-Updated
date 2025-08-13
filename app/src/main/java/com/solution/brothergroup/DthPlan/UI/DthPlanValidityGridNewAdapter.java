package com.solution.brothergroup.DthPlan.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.solution.brothergroup.DthPlan.dto.PlanValidity;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.List;


public class DthPlanValidityGridNewAdapter extends RecyclerView.Adapter<DthPlanValidityGridNewAdapter.MyViewHolder> {

    private List<PlanValidity> operatorList;
    private Context mContext;
    int layout;

    public DthPlanValidityGridNewAdapter(int layout, Context mContext, List<PlanValidity> operatorList) {
        this.operatorList = operatorList;
        this.mContext = mContext;this.layout = layout;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final PlanValidity operator = operatorList.get(position);

        holder.amountTv.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(operator.getAmount()));
        holder.validityTv.setText("" + operator.getValidity());
        holder.itemView.setOnClickListener(v -> {
            if (mContext instanceof DthPlanInfoActivity) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getAmount(), "" + operator.getDetails());
            } else if (mContext instanceof DthPlanInfoNewActivity) {
                ((DthPlanInfoNewActivity) mContext).ItemClick("" + operator.getAmount(), "" + operator.getDetails());
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView validityTv;
        public TextView amountTv;
        public TextView description;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            validityTv = view.findViewById(R.id.validityTv);
            amountTv = view.findViewById(R.id.amountTv);

        }
    }

}
