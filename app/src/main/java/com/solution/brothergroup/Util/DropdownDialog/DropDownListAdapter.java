package com.solution.brothergroup.Util.DropdownDialog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.pnsol.sdk.vo.request.EMI;
import com.roundpay.shoppinglib.Api.Object.PincodeArea;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;


import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class DropDownListAdapter extends RecyclerView.Adapter<DropDownListAdapter.MyViewHolder> {

    private static ClickListner mClickListner;
    Context context;
    int selectdPosition = -1;
    private List<DropDownModel> mList;
    boolean isShopping;

    public DropDownListAdapter(Context context, boolean isShopping, List<DropDownModel> mList, int selectdPosition, ClickListner mClickListner) {
        this.mList = mList;
        this.context = context;
        this.mClickListner = mClickListner;
        this.selectdPosition = selectdPosition;
        this.isShopping = isShopping;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_drop_down_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final DropDownModel item = mList.get(position);
        holder.text.setText(item.getName() + "");

        if (item.getDataObject() != null && item.getDataObject() instanceof BalanceType) {
            holder.amount.setVisibility(View.VISIBLE);
            holder.amount.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(((BalanceType) item.getDataObject()).getAmount() + ""));
        }
        if (item.getDataObject() != null && item.getDataObject() instanceof EMI) {
            holder.amount.setVisibility(View.VISIBLE);

            holder.amount.setText("Min " + UtilMethods.INSTANCE.formatedAmountWithRupees(((EMI) item.getDataObject()).getMinBankAmount() + ""));
        }

        if (item.getDataObject() != null && item.getDataObject() instanceof PincodeArea) {
            if (((PincodeArea) item.getDataObject()).getReachInHour() == 0 || isShopping) {
                holder.amount.setVisibility(View.GONE);
            } else {
                holder.amount.setVisibility(View.VISIBLE);
                holder.amount.setText("Reach Time - " + UtilMethods.INSTANCE.formatedAmount(((PincodeArea) item.getDataObject()).getReachInHour() + "") + " Hr");

            }
        }

        if (selectdPosition == -1 || position != selectdPosition) {
            holder.text.setTextColor(Color.BLACK);
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        holder.itemView.setOnClickListener(v -> {
            if (mClickListner != null) {
                selectdPosition = position;
                notifyDataSetChanged();

                mClickListner.onItemClick(position, item.getName(), item.getDataObject());


            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface ClickListner {
        void onItemClick(int clickPosition, String value, Object object);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView text, amount;
        View itemView;

        public MyViewHolder(View view) {
            super(view);
            itemView = view;
            text = view.findViewById(R.id.text);
            amount = view.findViewById(R.id.amount);
        }


    }
}
