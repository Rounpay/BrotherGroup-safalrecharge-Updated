package com.solution.brothergroup.DthPlan.UI;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.brothergroup.DthPlan.dto.PlanValidity;
import com.solution.brothergroup.R;

import java.util.List;


public class DthPlanValidityGridAdapter extends RecyclerView.Adapter<DthPlanValidityGridAdapter.MyViewHolder> {

    private List<PlanValidity> operatorList;
    private Context mContext;

    public DthPlanValidityGridAdapter(Context mContext, List<PlanValidity> operatorList) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public DthPlanValidityGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_plan_validity, parent, false);

        return new DthPlanValidityGridAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DthPlanValidityGridAdapter.MyViewHolder holder, final int position) {
        final PlanValidity operator = operatorList.get(position);

        holder.amountTv.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getAmount());
        holder.validityTv.setText("" + operator.getValidity());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DthPlanInfoActivity) mContext).ItemClick("" + operator.getAmount(), "" + operator.getDetails());
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
