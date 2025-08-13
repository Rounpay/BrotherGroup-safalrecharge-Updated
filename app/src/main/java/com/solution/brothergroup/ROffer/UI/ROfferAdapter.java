package com.solution.brothergroup.ROffer.UI;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.brothergroup.R;
import com.solution.brothergroup.ROffer.dto.ROfferObject;

import java.util.ArrayList;


public class ROfferAdapter extends RecyclerView.Adapter<ROfferAdapter.MyViewHolder> {


    private ArrayList<ROfferObject> transactionsList;
    private Context mContext;

    public ROfferAdapter(ArrayList<ROfferObject> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public ROfferAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offer_adapter, parent, false);

        return new ROfferAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ROfferAdapter.MyViewHolder holder, int position) {
        final ROfferObject operator = transactionsList.get(position);

        holder.Description.setText(operator.getDesc() + "");
        holder.Amount.setText(mContext.getResources().getString(R.string.rupiya) + " " + operator.getRs());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ROfferActivity) mContext).ItemClick("" + operator.getRs(), operator.getDesc());
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Description, Amount;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            Description = view.findViewById(R.id.tv_description);
            Amount = view.findViewById(R.id.tv_amount);
        }
    }

}
