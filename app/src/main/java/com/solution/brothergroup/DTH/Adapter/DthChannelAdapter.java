package com.solution.brothergroup.DTH.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.solution.brothergroup.DTH.dto.DthChannels;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.RecyclerViewStickyHeader.HeaderStickyListener;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class DthChannelAdapter extends ListAdapter<DthChannels, RecyclerView.ViewHolder> implements HeaderStickyListener {
    public static int ListData = 1;
    public static int Header = 2;
    public static final DiffUtil.ItemCallback<DthChannels> ModelDiffUtilCallback =
            new DiffUtil.ItemCallback<DthChannels>() {
                @Override
                public boolean areItemsTheSame(@NonNull DthChannels model, @NonNull DthChannels t1) {
                    return model.getHeader().equals(t1.getHeader());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull DthChannels model, @NonNull DthChannels t1) {
                    return model.equals(t1);
                }
            };
    String charText = "";
    int resourceId = 0;
    String operatorType = "";
    private ArrayList<DthChannels> rechargeStatus;
    private ArrayList<DthChannels> transactionsList;
    /*  public CommissionAdapter() {

      }*/
    RequestOptions requestOptions;
    private Context mContext;

    public DthChannelAdapter(ArrayList<DthChannels> transactionsList, Context mContext) {
        super(ModelDiffUtilCallback);
        this.transactionsList = transactionsList;
        this.rechargeStatus = new ArrayList<>();
        this.rechargeStatus.addAll(transactionsList);
        this.mContext = mContext;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.mipmap.ic_launcher_round);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Header) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticky_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dth_channel_name, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final DthChannels operator = transactionsList.get(position);
        if (getItemViewType(position) == ListData) {

            ((MyViewHolder) holder).name.setText(operator.getChannelName() + "");

        } else {
            ((HeaderViewHolder) holder).title.setText(operator.getHeader());
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (transactionsList.get(position).getHeader() != null && !transactionsList.get(position).getHeader().isEmpty()) {
            return Header;
        } else {
            return ListData;
        }

    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        Integer headerPosition = 0;
        for (Integer i = itemPosition; i > 0; i--) {
            if (isHeader(i)) {
                headerPosition = i;
                return headerPosition;
            }
        }
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.item_sticky_header;
    }

    @Override
    public String getHeaderString(int headerPosition) {
        return transactionsList.get(headerPosition).getHeader();
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        TextView tv = header.findViewById(R.id.opType);
        tv.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
        tv.setTextColor(Color.WHITE);
        tv.setText(transactionsList.get(headerPosition).getHeader());
    }

    @Override
    public boolean isHeader(int itemPosition) {
        if (transactionsList.get(itemPosition).getHeader() != null && !transactionsList.get(itemPosition).getHeader().isEmpty()) {
            return true;
        }
        return false;
    }

    // Filter Class
    public void filter(String charText) {
        this.charText = charText.toLowerCase(Locale.getDefault());
        transactionsList.clear();
        if (charText.length() == 0) {
            transactionsList.addAll(rechargeStatus);
        } else {
            /*for (DthChannels wp : rechargeStatus) {
                if (wp.getChannelName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getCategoryName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    transactionsList.add(wp);
                }
            }*/

            int CategoryId = 0;

            for (DthChannels wp : rechargeStatus) {
                if (wp.getChannelName().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getCategoryName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    if (CategoryId != wp.getCategoryID()) {
                        CategoryId = wp.getCategoryID();
                        transactionsList.add(new DthChannels(wp.getCategoryName(), 0, "", 0, "", "", false, false));
                    }
                    transactionsList.add(new DthChannels(null, wp.getId(), wp.getChannelName(), wp.getCategoryID(),
                            wp.getCategoryName(), wp.getCategories(), wp.isDel(), wp.isActive()));
                }
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public MyViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);


        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.opType);
            title.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));
            title.setTextColor(Color.WHITE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) title.getLayoutParams();
            params.bottomMargin = 10;
        }

    }
}