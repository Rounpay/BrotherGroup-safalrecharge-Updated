package com.solution.brothergroup.Aeps.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.brothergroup.Api.Object.ReceiptObject;
import com.solution.brothergroup.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ReceiptDetailListAdapter extends RecyclerView.Adapter<ReceiptDetailListAdapter.MyViewHolder> {

    private List<ReceiptObject> operatorList;
    private Activity mContext;

    public ReceiptDetailListAdapter(List<ReceiptObject> operatorList, Activity mContext) {
        this.operatorList = operatorList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_receipt_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ReceiptObject operator = operatorList.get(position);
        holder.name.setText(operator.getName() + "");
        holder.value.setText(operator.getValue() + "");

        /*if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.name.setBackgroundColor(Color.parseColor("#cebcbc"));
            holder.value.setBackgroundColor(Color.parseColor("#cebcbc"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#cebcbc"));
            holder.name.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.value.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }*/
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name, value;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            name = view.findViewById(R.id.name);

            value = view.findViewById(R.id.value);
        }
    }
}



